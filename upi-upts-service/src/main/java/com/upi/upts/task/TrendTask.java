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
		Candle cur = trend.getCur();
		if(StringUtil.isEmpty(cur)) {
			trend.setCur(candle);
		}else {
			trend.setPre(cur);
			trend.setCur(candle);
			//赋值上场趋势
			trend.setPreDirec(trend.getCurDirec());
			//判断当前趋势
			if(candle.getHigh()>cur.getHigh()&&candle.getLow()>=cur.getLow()) {
				trend.setCurDirec(UiisConstant.UP);
			}else if (candle.getHigh()<=cur.getHigh()&&candle.getLow()<cur.getLow()) {
				trend.setCurDirec(UiisConstant.DOWN);
			}else {
				if(candle.getHigh()>cur.getHigh()&&candle.getLow()<cur.getLow()) {
					trend.setCurDirec(candle.getProp());
				}
//				if(!UiisConstant.CENTRE.equals(candle.getProp())) {
//					trend.setCurDirec(candle.getProp());
//				}
//				trend.setIsWait(UiisConstant.YES);
			}
			//趋势反转，重置该趋势下建仓信息，提示卖点
			if(!trend.getCurDirec().equals(trend.getPreDirec())) {
				trend.setIsOpenOrder(false);
				trend.setIsSellPoint(true);
			}else {
				trend.setIsSellPoint(false);
			}
			//判断建平点
//			if(UiisConstant.YES.equals(trend.getIsTurn())&&trend.getCurDirec().equals(trend.getPreDirec())&&candle.getProp().equals(trend.getCurDirec())) {
//				trend.setIsBuyPoint(UiisConstant.YES);
//			}else {
//				trend.setIsBuyPoint(UiisConstant.NO);
//			}
			//趋势确认加K线确认，(是否需要加双向K线)
			if(!trend.getIsOpenOrder()&&trend.getCurDirec().equals(trend.getPreDirec())&&candle.getProp().equals(trend.getCurDirec())) {
				trend.setIsBuyPoint(true);
			}else {
				trend.setIsBuyPoint(false);
			}
//			if(!trend.getCurDirec().equals(trend.getPreDirec())&&!UiisConstant.YES.equals(trend.getIsWait())) {
//				trend.setIsTurn(UiisConstant.YES);
//				trend.setIsSellPoint(UiisConstant.YES);
//			}else {
//				trend.setIsTurn(UiisConstant.NO);
//				trend.setIsSellPoint(UiisConstant.NO);
//			}
			if(trend.getIsBuyPoint()) {
				tradeServiceImpl.openOrder(candle,level);
				trend.setIsOpenOrder(true);
			}
			if(trend.getIsSellPoint()) {
				tradeServiceImpl.closeOrder(candle, level);
				trend.setIsSellPoint(false);
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
