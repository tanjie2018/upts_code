package com.upi.upts.common;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.upi.upts.model.Candle;
import com.upi.upts.model.Param;
import com.upi.upts.model.Trade;
import com.upi.upts.model.Trend;
import com.upi.upts.service.impl.ParamServiceImpl;
import com.upi.upts.service.impl.TradeServiceImpl;
import com.upi.upts.util.StringUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CommonVO {
	
	@Autowired
	private ParamServiceImpl paramSer;
	@Autowired
	private TradeServiceImpl tradeServiceImpl;
	
	public static ConcurrentHashMap<String, Param> paramMap = new ConcurrentHashMap<String, Param>();
	//Key值为策略值
	public static ConcurrentHashMap<String, LinkedList<Trade>> nbTradeMap = new ConcurrentHashMap<String, LinkedList<Trade>>();
	public static ConcurrentHashMap<String, LinkedList<Trade>> nsTradeMap = new ConcurrentHashMap<String, LinkedList<Trade>>();
	
	//订单map,Key值为订单号,用于交易监控
	public static ConcurrentHashMap<String, LinkedList<Trade>> orderMap = new ConcurrentHashMap<String, LinkedList<Trade>>();
	
	@PostConstruct
	void init() {
		//初始化参数
		List<Param> allParam = paramSer.getAllParam();
		if(!StringUtil.isEmpty(allParam)) {
			for(Param param: allParam) {
				paramMap.put(param.getParamKey(), param);
			}
			log.info("初始化参数："+allParam);
		}
		//拉取历史未完成订单
		List<Trade> undoTrades1 = tradeServiceImpl.getUndoTrades("2");
		List<Trade> undoTrades2 = tradeServiceImpl.getUndoTrades("0");
		for(Trade trade : undoTrades1) {
			if(UiisConstant.BUY.equals(trade.getSider())) {
				LinkedList<Trade> btradeList = nbTradeMap.get(trade.getStrategy());
				if(StringUtil.isEmpty(btradeList)) {
					btradeList = new LinkedList<Trade>();
					btradeList.add(trade);
					nbTradeMap.put(trade.getStrategy(), btradeList);
				}else {
					btradeList.add(trade);
				}
			}else {
				LinkedList<Trade> stradeList = nsTradeMap.get(trade.getStrategy());
				if(StringUtil.isEmpty(stradeList)) {
					stradeList = new LinkedList<Trade>();
					stradeList.add(trade);
					nsTradeMap.put(trade.getStrategy(), stradeList);
				}else {
					stradeList.add(trade);
				}
			}
		}
		for(Trade trade : undoTrades2) {
			if(UiisConstant.BUY.equals(trade.getSider())) {
				LinkedList<Trade> btradeList = nbTradeMap.get(trade.getStrategy());
				if(StringUtil.isEmpty(btradeList)) {
					btradeList = new LinkedList<Trade>();
					btradeList.add(trade);
					nbTradeMap.put(trade.getStrategy(), btradeList);
				}else {
					btradeList.add(trade);
				}
			}else {
				LinkedList<Trade> stradeList = nsTradeMap.get(trade.getStrategy());
				if(StringUtil.isEmpty(stradeList)) {
					stradeList = new LinkedList<Trade>();
					stradeList.add(trade);
					nsTradeMap.put(trade.getStrategy(), stradeList);
				}else {
					stradeList.add(trade);
				}
			}
		}
		log.info("历史买多队列："+nbTradeMap);
		log.info("历史买空队列："+nsTradeMap);
	}
	
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
