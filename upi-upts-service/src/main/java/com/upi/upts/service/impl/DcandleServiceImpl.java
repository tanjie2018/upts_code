package com.upi.upts.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upi.upts.model.Dcandle;
import com.upi.upts.repository.DcandleRepository;
import com.upi.upts.service.DcandleService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DcandleServiceImpl implements DcandleService {

	@Autowired
	private DcandleRepository candleRepository;
	
	@Override
	public void insert(Dcandle candle) {
		log.info("Candle入库信息"+candle.toString());
		try {
			candleRepository.save(candle);
		} catch (Exception e) {
			log.error("行情数据入库异常",e);
		}
		log.info("完成入库");
	}

	@Override
	public List<Dcandle> getAllCandles() {
		List<Dcandle> candlesList = candleRepository.findAll();
		return candlesList;
	}

}
