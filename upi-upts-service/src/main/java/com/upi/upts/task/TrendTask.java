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
			trend.setPreDirec(trend.getCurDirec());
			if(candle.getHigh()>cur.getHigh()&&candle.getLow()>cur.getLow()) {
				trend.setCurDirec(UiisConstant.UP);
				trend.setIsWait(UiisConstant.NO);
			}else if (candle.getHigh()<cur.getHigh()&&candle.getLow()<cur.getLow()) {
				trend.setCurDirec(UiisConstant.DOWN);
				trend.setIsWait(UiisConstant.NO);
			}else {
				if(!UiisConstant.CENTRE.equals(candle.getProp())) {
					trend.setCurDirec(candle.getProp());
				}
				trend.setIsWait(UiisConstant.YES);
			}
			if(UiisConstant.YES.equals(trend.getIsTurn())&&trend.getCurDirec().equals(trend.getPreDirec())&&candle.getProp().equals(trend.getCurDirec())) {
				trend.setIsBuyPoint(UiisConstant.YES);
			}else {
				trend.setIsBuyPoint(UiisConstant.NO);
			}
			if(!trend.getCurDirec().equals(trend.getPreDirec())&&!UiisConstant.YES.equals(trend.getIsWait())) {
				trend.setIsTurn(UiisConstant.YES);
				trend.setIsSellPoint(UiisConstant.YES);
			}else {
				trend.setIsTurn(UiisConstant.NO);
				trend.setIsSellPoint(UiisConstant.NO);
			}
			if(UiisConstant.YES.equals(trend.getIsBuyPoint())) {
				tradeServiceImpl.openOrder(candle,level);
			}
			if(UiisConstant.YES.equals(trend.getIsSellPoint())) {
				tradeServiceImpl.closeOrder(candle, level);
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
