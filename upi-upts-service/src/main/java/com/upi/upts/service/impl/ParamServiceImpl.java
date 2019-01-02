package com.upi.upts.service.impl;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.upi.upts.common.CommonVO;
import com.upi.upts.common.UiisConstant;
import com.upi.upts.model.Candle;
import com.upi.upts.model.Param;
import com.upi.upts.model.Trade;
import com.upi.upts.repository.ParamRepository;
import com.upi.upts.repository.TradeRepository;
import com.upi.upts.service.ParamService;
import com.upi.upts.service.TradeService;
import com.upi.upts.util.StringUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Scope("prototype")
@Transactional
public class ParamServiceImpl implements ParamService {

	@Autowired
	private ParamRepository paramRepository;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public void insert(Param param) {
		paramRepository.save(param);
	}

	@Override
	public void update(Param param) {
		try {
			entityManager.merge(param);
		} catch (Exception e) {
			log.error("参数信息更新失败",e);
		}
	}

	@Override
	public List<Param> getAllParam() {
		List<Param> params = paramRepository.findAll();
		return params;
	}

}
