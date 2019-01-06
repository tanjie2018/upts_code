package com.upi.upts.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upi.upts.model.Candle;
import com.upi.upts.repository.CandleRepository;
import com.upi.upts.service.CandleService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CandleServiceImpl implements CandleService {

	@Autowired
	private CandleRepository candleRepository;
	
	@Override
	public void insert(Candle candle) {
		log.info("Candle入库信息"+candle.toString());
		try {
			candleRepository.save(candle);
		} catch (Exception e) {
			log.error("行情数据入库异常",e);
		}
		log.info("完成入库");
	}

	@Override
	public List<Candle> getAllCandles() {
		List<Candle> candlesList = candleRepository.findAll();
		return candlesList;
	}

}
