package com.upi.upts.service;

import java.util.List;

import com.upi.upts.model.Candle;

public interface CandleService {
	
	public void insert(Candle candle);
	
	public List<Candle> getAllCandles();
	
}
