package com.upi.upts.common;

import java.math.BigDecimal;
import java.util.HashMap;

/**常量类
 * @author tanj_5
 *
 */
public class UiisConstant {

	/*******************线程池参数***********************/
	public static final String instrumentId = "ETH-USD-190329";
	
	public static final int FIVE_MINUTES = 300;
	public static final int THIRTY_MINUTES = 1800;
	public static final int ONE_HOUR = 3600;
	public static final int TWO_HOUR = 7200;
	public static final int DATE_TIME = 86400;
//	public static final int FIVE_MINUTES = 30;
//	public static final int THIRTY_MINUTES = 60;
//	public static final int ONE_HOUR = 120;
//	public static final int TWO_HOUR = 150;
//	public static final int DATE_TIME = 200;
	public static final int INT_MAX = 1999999999;
	
	public static final String FIVE = "FIVE";
	public static final String THREE = "THREE";
	public static final String ONE = "ONE";
	public static final String TWO = "TWO";
	public static final String FOUR = "FOUR";
	
	public static final String UP = "U";
	public static final String DOWN = "D";
	public static final String CENTRE = "C";
	
	public static final String YES = "Y";
	public static final String NO = "N";
	
	public static final String BUY = "buy";
	public static final String SELL = "sell";
	
	public static final Double DEEP = 0.008;
	public static final Double FORCE_PERCENT = 0.09;
	
	public static final int MAXINUM_POOL_SIZE = 2000;	
	public static final int CORE_POOL_SIZE = 1000;
	public static final long KEEP_ALIVE_TIME = 600L;
	public static final int CAPACITY = 1000;
    
	public static final String UPI_DATE_FORMAT = "yyyyMMdd";
	public static final String UPI_TIME_FORMAT = "yyyyMMddHHmmss";
	public static final String UPI_MILLISECOND_FORMAT = "yyyyMMddHHmmssSSS";
	public static final String UPI_NORMAL_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String UPI_NORMAL_DATE = "yyyy-MM-dd";
	public static final String UPI_UTC_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.S'Z'";
	
	public static final String BUY_QUEURE_SIZE = "buyQueueSize";//历史买队列深度--不支持分布式
	public static final String SELL_QUEURE_SIZE = "sellQueueSize";//历史卖队列深度--不支持分布式
	public static final String QUEURE_SIZE_MAX = "queueSizeMax";//历史卖队列深度--不支持分布式
	public static final String FORCE_ORDER_NO = "forceOrderNum";//历史爆仓单数--不支持分布式
	
	public static final String CHARSET = "UTF-8";
}
