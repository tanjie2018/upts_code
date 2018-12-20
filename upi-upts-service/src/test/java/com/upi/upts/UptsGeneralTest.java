package com.upi.upts;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.upi.upts.okexapi.bean.futures.result.ExchangeRate;
import com.upi.upts.okexapi.bean.futures.result.ServerTime;
import com.upi.upts.okexapi.config.APIConfiguration;
import com.upi.upts.okexapi.service.futures.impl.GeneralAPIServiceImpl;
import com.upi.upts.okexapi.utils.DateUtils;
import com.upi.upts.util.BaseConfigration;

public class UptsGeneralTest {

	public static void main(String[] args) {
		String url = "https://www.okex.com/api/general/v3/time";
		//获取币种列表
		url = "https://www.okex.com/api/account/v3/currencies";
		
		APIConfiguration config = BaseConfigration.config();
        GeneralAPIServiceImpl apiServiceImpl = new GeneralAPIServiceImpl(config);
        ServerTime serverTime = apiServiceImpl.getServerTime();
        String iso = serverTime.getIso();
        ExchangeRate exchangeRate = apiServiceImpl.getExchangeRate();
        System.out.println(iso);
		
		System.out.println(JSON.toJSONString(serverTime));
		System.out.println(JSON.toJSONString(exchangeRate));
		System.out.println(DateUtils.getUnixTime());
		
	}

}
