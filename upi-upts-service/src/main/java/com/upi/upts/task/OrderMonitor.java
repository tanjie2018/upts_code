package com.upi.upts.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.upi.upts.common.CommonVO;
import com.upi.upts.common.UiisConstant;
import com.upi.upts.model.Candle;
import com.upi.upts.model.Trend;
import com.upi.upts.okexapi.config.APIConfiguration;
import com.upi.upts.okexapi.service.futures.impl.FuturesTradeAPIServiceImpl;
import com.upi.upts.service.impl.TradeServiceImpl;
import com.upi.upts.util.BaseConfigration;
import com.upi.upts.util.StringUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 监控交易订单：查单
 * @author tanj_5
 */
@Component
@Scope("prototype")
@Slf4j
public class OrderMonitor implements ITask{

	private String instrumentId;
	private long orderId;
	
	private static FuturesTradeAPIServiceImpl apiServiceImpl;
	
	@Override
	public Object call() throws Exception {
		JSONObject order = getApiServiceImpl().getOrder(instrumentId, orderId);
		String status = (String) order.get("status");
		return null;
	}
	
	public void init(String instrumentId,long orderId) {
		this.instrumentId = instrumentId;
		this.orderId = orderId;
	}
	
	private static FuturesTradeAPIServiceImpl getApiServiceImpl() {
		APIConfiguration config = BaseConfigration.config();
		if(StringUtil.isEmpty(apiServiceImpl)) {
			apiServiceImpl = new FuturesTradeAPIServiceImpl(config);
		}
		return apiServiceImpl;
	}

}
