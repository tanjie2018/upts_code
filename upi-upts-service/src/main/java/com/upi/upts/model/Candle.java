package com.upi.upts.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "upts_candle_info")
@Data
public class Candle implements Cloneable{
	@Id
	private String id;
	private String time;
	private Double open;
	private Double high;
	private Double low;
	private Double close;
	private String prop;
	private BigDecimal rang;
	
	@Override
	public Candle clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return (Candle) super.clone();
	}
}
