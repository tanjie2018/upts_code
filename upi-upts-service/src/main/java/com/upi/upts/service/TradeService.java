package com.upi.upts.service;

import java.util.List;

import com.upi.upts.model.Trade;

public interface TradeService {
	public void insert(Trade trade);
	public void update(Trade trade);
	public List<Trade> getUndoTrades();
}
