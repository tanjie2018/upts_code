package com.upi.upts.model;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class Trend implements Cloneable{

	private Candle pre;
	private Candle cur;
	//向上'U',向下'D'
	private String preTrendDirec = "C";//趋势
	private String curTrendDirec = "C";//趋势
	private String preDirec = "C";//方向
	private String curDirec = "C";//方向
	private Boolean isTurn = false;
	private Boolean isBuyPoint= false;
	private Boolean isSellPoint = false;
//	private Boolean isWait = true;
	private Boolean isOpenOrder = false;
	
	@Override
	public Trend clone() throws CloneNotSupportedException {
		return (Trend) super.clone();
	}
}
