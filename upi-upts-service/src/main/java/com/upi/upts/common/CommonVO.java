package com.upi.upts.common;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.upi.upts.model.Candle;
import com.upi.upts.model.Trade;
import com.upi.upts.model.Trend;

@Component
public class CommonVO {
	
	public static ConcurrentHashMap<String, Candle> candleMap = new ConcurrentHashMap<String, Candle>();
	static {
		candleMap.put(UiisConstant.FIVE, new Candle());
		candleMap.put(UiisConstant.THREE, new Candle());
		candleMap.put(UiisConstant.ONE, new Candle());
		candleMap.put(UiisConstant.TWO, new Candle());
		candleMap.put(UiisConstant.FOUR, new Candle());
	}
	
	public static ConcurrentHashMap<String, Trend> trendMap = new ConcurrentHashMap<String, Trend>();
	static {
		trendMap.put(UiisConstant.FIVE, new Trend());
		trendMap.put(UiisConstant.THREE, new Trend());
		trendMap.put(UiisConstant.ONE, new Trend());
		trendMap.put(UiisConstant.TWO, new Trend());
		trendMap.put(UiisConstant.FOUR, new Trend());
	}
	
//	//考虑使用队列存储订单
//	public static ConcurrentHashMap<String, Trade> bTradeMap = new ConcurrentHashMap<String, Trade>();
//	public static ConcurrentHashMap<String, Trade> sTradeMap = new ConcurrentHashMap<String, Trade>();
	//考虑使用队列存储订单
	public static ConcurrentHashMap<String, BlockingQueue<Trade>> bTradeMap = new ConcurrentHashMap<String, BlockingQueue<Trade>>();
	public static ConcurrentHashMap<String, BlockingQueue<Trade>> sTradeMap = new ConcurrentHashMap<String, BlockingQueue<Trade>>();
	
}
