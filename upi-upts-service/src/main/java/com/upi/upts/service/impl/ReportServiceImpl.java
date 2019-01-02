package com.upi.upts.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upi.upts.model.Candle;
import com.upi.upts.model.Report;
import com.upi.upts.repository.CandleRepository;
import com.upi.upts.repository.ReportRepository;
import com.upi.upts.repository.TradeRepository;
import com.upi.upts.service.CandleService;
import com.upi.upts.service.ReportService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ReportServiceImpl implements ReportService {

	@Autowired
	private ReportRepository reportRepository;
	@Autowired
	private TradeServiceImpl tradeServiceImpl; 
	
	@Override
	public void insert(Report report) {
		
		log.info("完成入库");
	}

}
