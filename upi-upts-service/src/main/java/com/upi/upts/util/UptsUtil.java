package com.upi.upts.util;

import java.math.BigDecimal;

import com.upi.upts.common.UiisConstant;
import com.upi.upts.model.Candle;

public class UptsUtil {

	public static Candle strToCandle(String str) {
		String[] strs = str.split(",");
		Candle candle = new Candle();
		candle.setId(strs[0]);
		candle.setOpen(Double.valueOf(strs[1]));
		candle.setHigh(Double.valueOf(strs[2]));
		candle.setLow(Double.valueOf(strs[3]));
		candle.setClose(Double.valueOf(strs[4]));
		candle.setTime(strs[0]);
		if(candle.getClose()>candle.getOpen()) {
			candle.setProp(UiisConstant.UP);
		}else if(candle.getClose()<candle.getOpen()) {
			candle.setProp(UiisConstant.DOWN);
		}else {
			candle.setProp(UiisConstant.CENTRE);
		}
		candle.setRang(BigDecimal.valueOf((candle.getClose()-candle.getOpen())/candle.getOpen()*100).setScale(3, BigDecimal.ROUND_HALF_UP));
		return candle;
	}
}
