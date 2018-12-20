package com.upi.upts;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.upi.upts.okexapi.bean.account.result.Currency;
import com.upi.upts.okexapi.bean.account.result.Wallet;
import com.upi.upts.okexapi.bean.account.result.WithdrawFee;
import com.upi.upts.okexapi.config.APIConfiguration;
import com.upi.upts.okexapi.service.account.impl.AccountAPIServiceImpl;
import com.upi.upts.util.BaseConfigration;

public class UptsAccountTest {

	public static void main(String[] args) {
		APIConfiguration config = BaseConfigration.config();
		AccountAPIServiceImpl apiServiceImpl = new AccountAPIServiceImpl(config);
		//获取币种列表
		List<Currency> currencies = apiServiceImpl.getCurrencies();
		//钱包账户信息
		List<Wallet> wallet = apiServiceImpl.getWallet();
		//单一币种账户信息
		List<Wallet> wallet2 = apiServiceImpl.getWallet("btc");
		//资金划转
		//提币手续费
		List<WithdrawFee> withdrawFee = apiServiceImpl.getWithdrawFee("btc");
		//所有币种提币记录
		JSONArray depositHistory = apiServiceImpl.getDepositHistory();
//		List<Ticker> tickers = spotProductAPIServiceImpl.getTickers();
//		List<Wallet> wallet = apiServiceImpl.getWallet();
		
		System.out.println(JSON.toJSONString(currencies));
		System.out.println(JSON.toJSONString(wallet));
		System.out.println(JSON.toJSONString(wallet2));
		System.out.println(JSON.toJSONString(withdrawFee));
		System.out.println(JSON.toJSONString(depositHistory));

	}

}
