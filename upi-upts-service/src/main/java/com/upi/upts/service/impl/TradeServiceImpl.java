package com.upi.upts.service.impl;

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
	
	public void handle(Candle candle) {
		Trade trade = new Trade();
		trade.setOrderId(StringUtil.getNowFormatDate(UiisConstant.UPI_MILLISECOND_FORMAT));
		trade.setBtime(StringUtil.getNowFormatDate(UiisConstant.UPI_TIME_FORMAT));
		trade.setBprice(candle.getClose());
		
		trade.setSize((int) (10000/trade.getBprice()));
		trade.setBsum(trade.getBprice()*trade.getSize());
		trade.setFlag("0");
		if(UiisConstant.UP.equals(candle.getProp())) {
			trade.setSider(UiisConstant.BUY);
			CommonVO.bTradeMap.put(trade.getOrderId(), trade);
		}else {
			trade.setSider(UiisConstant.SELL);
			CommonVO.sTradeMap.put(trade.getOrderId(), trade);
		}
		update(trade);
		log.info("交易信息入库："+trade);
	}

}
