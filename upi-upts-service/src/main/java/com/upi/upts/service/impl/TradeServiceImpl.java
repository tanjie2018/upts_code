package com.upi.upts.service.impl;

import java.math.BigDecimal;
import java.util.List;

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
		trade.setOrderId(StringUtil.getNowFormatDate(UiisConstant.UPI_MILLISECOND_FORMAT));
		trade.setBtime(StringUtil.getNowFormatDate(UiisConstant.UPI_TIME_FORMAT));
		trade.setBprice(candle.getClose());
		
		trade.setSize((int) (10000/trade.getBprice()));
		trade.setBsum(trade.getBprice()*trade.getSize());
		trade.setFlag("0");
		if(UiisConstant.UP.equals(candle.getProp())) {
			trade.setSider(UiisConstant.BUY);
			CommonVO.bTradeMap.put(level, trade);
		}else {
			trade.setSider(UiisConstant.SELL);
			CommonVO.sTradeMap.put(level, trade);
		}
		update(trade);
		log.info("交易信息入库："+trade);
	}
	
	public void closeOrder(Candle candle,String level) {
		Trade trade = null;
		if(UiisConstant.UP.equals(candle.getProp())) {
			try {
				trade = CommonVO.sTradeMap.get(level).clone();
				CommonVO.sTradeMap.remove(level);
			} catch (CloneNotSupportedException e) {
				log.error("克隆异常",e);
			}
			
		}else {
			try {
				trade = CommonVO.bTradeMap.get(level).clone();
				CommonVO.sTradeMap.remove(level);
			} catch (CloneNotSupportedException e) {
				log.error("克隆异常",e);
			}
		}
		if(!StringUtil.isEmpty(trade)) {
			trade.setSprice(candle.getClose());
			trade.setSsum(candle.getClose()*trade.getSize());
			trade.setStime(StringUtil.getNowFormatDate(UiisConstant.UPI_TIME_FORMAT));
			trade.setProfit(BigDecimal.valueOf(trade.getSsum()-trade.getBsum()).setScale(3, BigDecimal.ROUND_HALF_UP));
			trade.setProfit(BigDecimal.valueOf((trade.getSprice()-trade.getBprice())/trade.getBprice()).setScale(3, BigDecimal.ROUND_HALF_UP));
			update(trade);
		}
	}

}
