package com.upi.upts.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "upts_dcandle_info")
@Data
public class Dcandle implements Cloneable{
	@Id
	private String id;
	private String time;
	private Double open;
	private Double high;
	private Double low;
	private Double close;
	private String prop;
	private BigDecimal rang;
	private Double volume;
	
	@Override
	public Dcandle clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return (Dcandle) super.clone();
	}
}
