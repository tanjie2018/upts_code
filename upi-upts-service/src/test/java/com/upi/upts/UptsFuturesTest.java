package com.upi.upts;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.upi.upts.okexapi.bean.account.result.Currency;
import com.upi.upts.okexapi.bean.account.result.Wallet;
import com.upi.upts.okexapi.bean.account.result.WithdrawFee;
import com.upi.upts.okexapi.config.APIConfiguration;
import com.upi.upts.okexapi.service.account.impl.AccountAPIServiceImpl;
import com.upi.upts.okexapi.service.futures.impl.FuturesTradeAPIServiceImpl;
import com.upi.upts.util.BaseConfigration;

public class UptsFuturesTest {

	public static void main(String[] args) {
		APIConfiguration config = BaseConfigration.config();
		FuturesTradeAPIServiceImpl apiServiceImpl = new FuturesTradeAPIServiceImpl(config);
		//合约账户币种杠杆
		JSONArray accountsLedgerByCurrency = apiServiceImpl.getAccountsLedgerByCurrency("eth");
		
		System.out.println(JSON.toJSONString(accountsLedgerByCurrency));

	}

}
