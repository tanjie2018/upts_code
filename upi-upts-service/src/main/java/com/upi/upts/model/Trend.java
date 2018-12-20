package com.upi.upts.model;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class Trend implements Cloneable{

	private Candle pre;
	private Candle cur;
	//向上'U',向下'D'
	private String preDirec = "C";
	private String curDirec = "C";
	private String isTurn = "N";
	private String isBuyPoint= "N";
	private String isSellPoint = "N";
	private String isWait = "Y";
	
	@Override
	public Trend clone() throws CloneNotSupportedException {
		return (Trend) super.clone();
	}
}
