package com.upi.upts.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.upi.upts.common.CommonVO;
import com.upi.upts.common.UiisConstant;
import com.upi.upts.model.Candle;
import com.upi.upts.model.Trend;
import com.upi.upts.service.impl.TradeServiceImpl;
import com.upi.upts.util.StringUtil;

import lombok.extern.slf4j.Slf4j;

@Component
@Scope("prototype")
@Slf4j
public class TrendTask implements ITask{

	private Candle candle;
	private String level;
	
	@Autowired
	private TradeServiceImpl tradeServiceImpl;
	
	@Override
	public Object call() throws Exception {
		Trend trend = CommonVO.trendMap.get(level);
		if(StringUtil.isEmpty(trend)) {
			trend = new Trend();
			CommonVO.trendMap.put(level, trend);
		}
		Candle cur = trend.getCur();
		if(StringUtil.isEmpty(cur)) {
			trend.setCur(candle);
		}else {
			
			trend.setPre(cur);
			trend.setCur(candle);
			if((candle.getHigh()<=cur.getHigh()&&candle.getLow()>=cur.getLow())||UiisConstant.CENTRE.equals(candle.getProp())) {
				//K1包含K2则趋势不变，方向不变,如开盘和收盘一样，忽略
				return null;
			}
			//赋值K1方向
			trend.setPreDirec(trend.getCurDirec());
			//赋值K1趋势
			trend.setPreTrendDirec(trend.getCurTrendDirec());
			
			//判断当前方向
			if(candle.getHigh()>cur.getHigh()&&candle.getLow()>=cur.getLow()&&UiisConstant.UP.equals(candle.getProp())) {
				trend.setCurDirec(UiisConstant.UP);
			}else if (candle.getHigh()<=cur.getHigh()&&candle.getLow()<cur.getLow()&&UiisConstant.DOWN.equals(candle.getProp())) {
				trend.setCurDirec(UiisConstant.DOWN);
			}else {
				if(candle.getHigh()>cur.getHigh()&&candle.getLow()<cur.getLow()) {
					trend.setCurDirec(candle.getProp());
				}
			}
			
			//设置当前趋势
			if(trend.getCurDirec().equals(trend.getPreDirec())&&trend.getCurDirec().equals(candle.getProp())) {
				trend.setCurTrendDirec(candle.getProp());
			}
			
			//一旦趋势反转，则设置该趋势下是否下单属性为fasle，以便后续下单
			if(!trend.getPreTrendDirec().equals(trend.getCurTrendDirec())) {
				trend.setIsOpenOrder(false);
			}
			//设置转向卖单是否完成，以便后续卖单
			if(!trend.getPreDirec().equals(trend.getCurDirec())) {
				trend.setIsCloseOrder(false);
			}
			
			//买点判断
			if(!trend.getIsOpenOrder()&&UiisConstant.UP.equals(trend.getCurTrendDirec())&&UiisConstant.UP.equals(candle.getProp())) {
				//满足买多
				log.info("买多第一步成立"+trend);
				if(UiisConstant.UP.equals(cur.getProp())&&candle.getClose()>cur.getClose()) {
					log.info("买多第二步成立");
					trend.setIsBuyPoint(true);
				}else if(UiisConstant.DOWN.equals(cur.getProp())&&candle.getClose()>cur.getOpen()) {
					log.info("买多第二步成立");
					trend.setIsBuyPoint(true);
				}else {
					trend.setIsBuyPoint(false);
				}
			}else if(!trend.getIsOpenOrder()&&UiisConstant.DOWN.equals(trend.getCurTrendDirec())&&UiisConstant.DOWN.equals(candle.getProp())){
				log.info("买空第一步成立"+trend);
				//满足买空
				if(UiisConstant.UP.equals(cur.getProp())&&candle.getClose()<cur.getOpen()) {
					log.info("买空第二步成立");
					trend.setIsBuyPoint(true);
				}else if(UiisConstant.DOWN.equals(cur.getProp())&&candle.getClose()<cur.getClose()) {
					log.info("买空第二步成立");
					trend.setIsBuyPoint(true);
				}else {
					trend.setIsBuyPoint(false);
				}
			}else {
				trend.setIsBuyPoint(false);
			}
			
			//判断卖点
			if(!trend.getIsCloseOrder()&&UiisConstant.UP.equals(trend.getCurDirec())&&candle.getProp().equals(UiisConstant.UP)) {
				log.info("卖空第一步成立："+candle);
				//判断卖空
				if (cur.getProp().equals(UiisConstant.DOWN)&&candle.getClose()>cur.getOpen()) {
					log.info("卖空第二部成立："+"candle.getClose():"+candle.getClose()+",cur.getClose():"+cur.getOpen());
					trend.setIsSellPoint(true);
				}else if(cur.getProp().equals(UiisConstant.UP)&&candle.getClose()>cur.getClose()) {
					log.info("卖空第二部成立："+"candle.getClose():"+candle.getClose()+",cur.getClose():"+cur.getClose());
					trend.setIsSellPoint(true);
				}else {
					trend.setIsSellPoint(false);
					log.info("卖空第二步不成立："+trend);
				}
			}else if(!trend.getIsCloseOrder()&&UiisConstant.DOWN.equals(trend.getCurDirec())&&candle.getProp().equals(UiisConstant.DOWN)) {
				log.info("卖多第一步成立："+candle);
				//判断卖多
				if(cur.getProp().equals(UiisConstant.DOWN)&&candle.getClose()<cur.getClose()) {
					log.info("卖多第二部成立："+"candle.getClose():"+candle.getClose()+",cur.getClose():"+cur.getClose());
					trend.setIsSellPoint(true);
				}else if(cur.getProp().equals(UiisConstant.UP)&&candle.getClose()<cur.getOpen()) {
					log.info("卖多第二部成立："+"candle.getClose():"+candle.getClose()+",cur.getOpen():"+cur.getOpen());
					trend.setIsSellPoint(true);
				}else {
					trend.setIsSellPoint(false);
					log.info("卖多第二步不成立："+trend);
				}
			}else {
				trend.setIsSellPoint(false);
			}
			
			//卖点处理
			if(trend.getIsBuyPoint()) {
//				if(!"DC".equals(level)) {
//					//正常
//					tradeServiceImpl.openOrder(candle,"NM");
//				}
				//不止损
				tradeServiceImpl.openNlOrder(candle, level);
				trend.setIsOpenOrder(true);
				log.info("买点处理完毕："+candle);
			}
			//卖点处理
//			if(trend.getIsSellPoint()) {
			//用买点来处理卖点
			if(trend.getIsBuyPoint()) {
//				if(!"DC".equals(level)) {
//					//正常
//					tradeServiceImpl.closeOrder(candle, "NM");
//				}
				//不止损
				tradeServiceImpl.closeNlOrder(candle, level);
				trend.setIsCloseOrder(true);
				log.info("卖点处理完毕："+candle);
				
//				修改点1,测试不通过
//				如果出现卖点且方向与趋势相同，且非买点，且趋势上的买单已完成，则在该时间点重新下该趋势上的订单，因为这种情况下该趋势原订单已备平仓
//				if(!trend.getIsBuyPoint()&&trend.getIsOpenOrder()&&trend.getCurTrendDirec().equals(trend.getCurDirec())) {
//					tradeServiceImpl.openNlOrder(candle, level);
//				}
				
//				修改点2：做分钟线
//				需将一些K线做近视处理，否则将难以判断
			}
			
			//判断是否主动平仓
			if(!candle.getProp().equals(UiisConstant.CENTRE)) {
				//不止损主动平仓
				tradeServiceImpl.closeProfitOrder(candle, level);
			}
			log.info(level+" level trend:"+trend);
		}
		return null;
	}
	
	public void init(Candle candle,String level) {
		this.candle = candle;
		this.level = level;
	}

}
