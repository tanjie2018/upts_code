package com.upi.upts.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "upts_trade_info")
@Data
public class Trade implements Cloneable{
	@Id
	private String orderId;
	//建仓时间
	private String btime;
	//建仓价格
	private Double bprice;
	//平仓价格
	private Double sprice;
	//平仓时间
	private String stime;
	//多空方向
	private String sider;
	//数量
	private int size;
	//期间最高价
	private Double high;
	//期间最近价
	private Double low;
	//订单状态,0建，1平
	private String flag;
	//利润
	private BigDecimal profit;
	//幅度
	private BigDecimal rang;
	//买入总价
	private Double bsum;
	//品仓总价
	private Double ssum;
	//策略
	private String strategy;
	
	@Override
	public Trade clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return (Trade) super.clone();
	}
}
