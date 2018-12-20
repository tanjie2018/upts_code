package com.upi.upts.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.upi.upts.model.Trade;
import com.upi.upts.repository.TradeRepository;
import com.upi.upts.service.TradeService;

public class TradeServiceImpl implements TradeService {

	@Autowired
	private TradeRepository tradeRepository;
	
	@Override
	public void insert(Trade trade) {
		tradeRepository.save(trade);
	}

	@Override
	public void update(Trade trade) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Trade> getUndoTrades() {
		// TODO Auto-generated method stub
		return null;
	}

}
