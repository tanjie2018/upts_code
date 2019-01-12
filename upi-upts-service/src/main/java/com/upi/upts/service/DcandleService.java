package com.upi.upts.service;

import java.util.List;

import com.upi.upts.model.Candle;
import com.upi.upts.model.Dcandle;

public interface DcandleService {
	
	public void insert(Dcandle candle);
	
	public List<Dcandle> getAllCandles();
	
}
