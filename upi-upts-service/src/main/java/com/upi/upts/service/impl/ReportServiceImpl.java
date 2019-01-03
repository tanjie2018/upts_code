package com.upi.upts.service.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.upi.upts.model.Candle;
import com.upi.upts.model.Report;
import com.upi.upts.repository.CandleRepository;
import com.upi.upts.repository.ReportRepository;
import com.upi.upts.repository.TradeRepository;
import com.upi.upts.service.CandleService;
import com.upi.upts.service.ReportService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Scope("prototype")
@Transactional
public class ReportServiceImpl implements ReportService {

	@Autowired
	private ReportRepository reportRepository;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public void insert(Report report) {
//		reportRepository.save(report);
		try {
			entityManager.merge(report);
		} catch (Exception e) {
			log.error("报表信息更新失败",e);
		}
	}

}
