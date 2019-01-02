package com.upi.upts.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * 统计涵盖历史多空最多队列深度及其和的最大值
 * 存储相关初始化参数
 * @author tanj_5
 */
@Entity
@Table(name = "upts_param_info")
@Data
public class Param implements Cloneable{
	@Id
	private String paramKey;
	//参数值
	private String paramValue;
	//参数描述
	private String desc;
	
	@Override
	public Param clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return (Param) super.clone();
	}
}
