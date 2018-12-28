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
			if(candle.getHigh()<=cur.getHigh()&&candle.getLow()>=cur.getLow()) {
				//K1包含K2则趋势不变，方向不变
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
				if(UiisConstant.UP.equals(cur.getProp())&&candle.getClose()>cur.getClose()) {
					trend.setIsBuyPoint(true);
				}else if(UiisConstant.DOWN.equals(cur.getProp())&&candle.getClose()>cur.getOpen()) {
					trend.setIsBuyPoint(true);
				}else {
					trend.setIsBuyPoint(false);
				}
			}else if(!trend.getIsOpenOrder()&&UiisConstant.DOWN.equals(trend.getCurTrendDirec())&&UiisConstant.DOWN.equals(candle.getProp())){
				//满足买空
				if(UiisConstant.UP.equals(cur.getProp())&&candle.getClose()<cur.getOpen()) {
					trend.setIsBuyPoint(true);
				}else if(UiisConstant.DOWN.equals(cur.getProp())&&candle.getClose()<cur.getClose()) {
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
					trend.setIsSellPoint(true);
				}else if(cur.getProp().equals(UiisConstant.UP)&&candle.getClose()>cur.getClose()) {
					trend.setIsSellPoint(true);
				}else {
					trend.setIsSellPoint(false);
					log.info("卖空第二步不成立："+trend);
				}
			}else if(!trend.getIsCloseOrder()&&UiisConstant.DOWN.equals(trend.getCurDirec())&&candle.getProp().equals(UiisConstant.DOWN)) {
				log.info("卖多第一步成立："+candle);
				//判断卖多
				if(cur.getProp().equals(UiisConstant.DOWN)&&candle.getClose()<cur.getClose()) {
					trend.setIsSellPoint(true);
				}else if(cur.getProp().equals(UiisConstant.UP)&&candle.getClose()<cur.getOpen()) {
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
				//正常
//				tradeServiceImpl.openOrder(candle,level);
				//不止损
				tradeServiceImpl.openNlOrder(candle, "D");
				trend.setIsOpenOrder(true);
				log.info("买点处理完毕："+candle);
			}
			//卖点处理
			if(trend.getIsSellPoint()) {
				//在正常
//				tradeServiceImpl.closeOrder(candle, level);
				//不止损
				tradeServiceImpl.closeNlOrder(candle, "D");
				trend.setIsCloseOrder(true);
				log.info("卖点处理完毕："+candle);
			}
			
			//判断是否主动平仓
			if(!candle.getProp().equals(UiisConstant.CENTRE)) {
				//不止损
				tradeServiceImpl.closeProfitOrder(candle, "D");
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
