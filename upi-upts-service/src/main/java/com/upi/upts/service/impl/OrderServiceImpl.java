package com.upi.upts.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.upi.upts.common.CommonVO;
import com.upi.upts.common.UiisConstant;
import com.upi.upts.model.Candle;
import com.upi.upts.model.Trend;
import com.upi.upts.okexapi.bean.futures.param.Order;
import com.upi.upts.okexapi.bean.futures.result.OrderResult;
import com.upi.upts.okexapi.config.APIConfiguration;
import com.upi.upts.okexapi.service.futures.impl.FuturesMarketAPIServiceImpl;
import com.upi.upts.okexapi.service.futures.impl.FuturesTradeAPIServiceImpl;
import com.upi.upts.task.ITask;
import com.upi.upts.util.BaseConfigration;
import com.upi.upts.util.StringUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 下单交易
 * @author tanj_5
 *
 */
@Component
@Scope("prototype")
@Slf4j
public class OrderServiceImpl {

	private static FuturesTradeAPIServiceImpl apiServiceImpl;
	
	public boolean orderDeal(Order order){
		OrderResult torder = getApiServiceImpl().order(order);
		if("0".equals(String.valueOf(torder.getError_code()))) {
			
			return true;
		}else {
			log.error("交易异常："+torder.getError_code(),torder.getError_messsage());
			return false;
		}
	}
	
	private static FuturesTradeAPIServiceImpl getApiServiceImpl() {
		APIConfiguration config = BaseConfigration.config();
		if(StringUtil.isEmpty(apiServiceImpl)) {
			apiServiceImpl = new FuturesTradeAPIServiceImpl(config);
		}
		return apiServiceImpl;
	}
}
