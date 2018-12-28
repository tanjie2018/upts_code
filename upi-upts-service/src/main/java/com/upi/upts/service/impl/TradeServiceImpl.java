package com.upi.upts.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.upi.upts.common.CommonVO;
import com.upi.upts.common.UiisConstant;
import com.upi.upts.model.Candle;
import com.upi.upts.model.Trade;
import com.upi.upts.repository.TradeRepository;
import com.upi.upts.service.TradeService;
import com.upi.upts.util.StringUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Scope("prototype")
@Transactional
public class TradeServiceImpl implements TradeService {

	@Autowired
	private TradeRepository tradeRepository;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public void insert(Trade trade) {
		tradeRepository.save(trade);
	}

	@Override
	public void update(Trade trade) {
		try {
			entityManager.merge(trade);
		} catch (Exception e) {
			log.error("交易信息更新失败",e);
		}
	}

	@Override
	public List<Trade> getUndoTrades() {
		return tradeRepository.findTradesByFlag("0");
	}
	
	public void openOrder(Candle candle,String level) {
		Trade trade = new Trade();
		trade.setOrderId(level+StringUtil.getNowFormatDate(UiisConstant.UPI_MILLISECOND_FORMAT));
//		trade.setBtime(StringUtil.getNowFormatDate(UiisConstant.UPI_TIME_FORMAT));
		trade.setBtime(candle.getTime());
		trade.setBprice(candle.getClose());
		
		trade.setStrategy(level);
		trade.setSize((int) (10000/trade.getBprice()));
		trade.setBsum(trade.getBprice()*trade.getSize());
		trade.setFlag("0");
		if(UiisConstant.UP.equals(candle.getProp())) {
			trade.setSider(UiisConstant.BUY);
			BlockingQueue<Trade> tQueue = CommonVO.bTradeMap.get(level);
			if(StringUtil.isEmpty(tQueue)) {
				tQueue = new LinkedBlockingQueue<>();
			}
			log.info("买多处理，队列值为："+tQueue.size()+",candle:"+candle);
			if(tQueue.size()!=0) {
				log.error("队列中已存在订单");
				return;
			}else {
				try {
					tQueue.put(trade);
				} catch (InterruptedException e) {
					log.error("订单生成失败",e);
				}
			}
			CommonVO.bTradeMap.put(level, tQueue);
		}else {
			trade.setSider(UiisConstant.SELL);
			BlockingQueue<Trade> tQueue = CommonVO.sTradeMap.get(level);
			if(StringUtil.isEmpty(tQueue)) {
				tQueue = new LinkedBlockingQueue<>();
			}
			log.info("买空处理，队列值为："+tQueue.size()+",candle:"+candle);
			if(tQueue.size()!=0) {
				log.error("队列中已存在订单");
				return;
			}else {
				try {
					tQueue.put(trade);
				} catch (InterruptedException e) {
					log.error("订单生成失败",e);
				}
			}
			CommonVO.sTradeMap.put(level, tQueue);
		}
		update(trade);
		log.info("交易信息入库："+trade);
	}
	
	public void closeOrder(Candle candle,String level) throws CloneNotSupportedException {
		Trade trade = null;
		if(UiisConstant.UP.equals(candle.getProp())) {
			BlockingQueue<Trade> tQueue = CommonVO.sTradeMap.get(level);
			if(!StringUtil.isEmpty(tQueue)&&tQueue.size()>0) {
				log.info("卖空处理，队列值为："+tQueue.size());
				try {
					trade = tQueue.take();
				} catch (InterruptedException e) {
					log.error("订单获取失败",e);;
				}
			}
		}else {
			BlockingQueue<Trade> tQueue = CommonVO.bTradeMap.get(level);
			if(!StringUtil.isEmpty(tQueue)&&tQueue.size()>0) {
				log.info("卖多处理，队列值为："+tQueue.size());
				try {
					trade = tQueue.take();
				} catch (InterruptedException e) {
					log.error("订单获取失败",e);
				}
			}
		}
		if(!StringUtil.isEmpty(trade)) {
			trade.setSprice(candle.getClose());
			trade.setSsum(candle.getClose()*trade.getSize());
			trade.setStime(candle.getTime());
			if(candle.getProp().equals(UiisConstant.UP)) {
				trade.setProfit(BigDecimal.valueOf(trade.getBsum()-trade.getSsum()).setScale(3, BigDecimal.ROUND_HALF_UP));
				trade.setRang(BigDecimal.valueOf((trade.getBprice()-trade.getSprice())/trade.getBprice()).setScale(3, BigDecimal.ROUND_HALF_UP));
			}else {
				trade.setProfit(BigDecimal.valueOf(trade.getSsum()-trade.getBsum()).setScale(3, BigDecimal.ROUND_HALF_UP));
				trade.setRang(BigDecimal.valueOf((trade.getSprice()-trade.getBprice())/trade.getBprice()).setScale(3, BigDecimal.ROUND_HALF_UP));
			}
			trade.setFlag("1");
			update(trade);
		}
	}

}
