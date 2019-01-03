package com.upi.upts.model;

import java.math.BigDecimal;
import java.text.Bidi;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**每天1:00统计上一天的数据，一天一次，根据stime统计
 * 日终结算
 * @author tanj_5
 */
@Entity
@Table(name = "upts_report_info")
@Data
public class Report implements Cloneable{
	@Id
	//统计日期
	private String id;
	//日爆仓单数
	private String forceOrderNum;
	//日爆仓单损失
	private BigDecimal forceOrderCost;
	//日利润，当日平仓
	private BigDecimal sprofit;
	//消失成本，未完成订单按现价平仓时的亏损值，
	private BigDecimal scost;
	
	@Override
	public Report clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return (Report) super.clone();

	}
}
