package com.upi.upts;

import java.time.Instant;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.upi.upts.okexapi.bean.account.result.Currency;
import com.upi.upts.okexapi.bean.account.result.Wallet;
import com.upi.upts.okexapi.bean.account.result.WithdrawFee;
import com.upi.upts.okexapi.bean.futures.result.Book;
import com.upi.upts.okexapi.bean.futures.result.Index;
import com.upi.upts.okexapi.bean.futures.result.Instruments;
import com.upi.upts.okexapi.bean.futures.result.Liquidation;
import com.upi.upts.okexapi.bean.futures.result.Ticker;
import com.upi.upts.okexapi.bean.futures.result.Trades;
import com.upi.upts.okexapi.config.APIConfiguration;
import com.upi.upts.okexapi.service.account.impl.AccountAPIServiceImpl;
import com.upi.upts.okexapi.service.futures.impl.FuturesMarketAPIServiceImpl;
import com.upi.upts.okexapi.service.futures.impl.FuturesTradeAPIServiceImpl;
import com.upi.upts.okexapi.service.spot.impl.SpotAccountAPIServiceImpl;
import com.upi.upts.okexapi.utils.DateUtils;
import com.upi.upts.util.BaseConfigration;

public class UptsSpotTest {

	public static void main(String[] args) {
		APIConfiguration config = BaseConfigration.config();
		FuturesMarketAPIServiceImpl apiServiceImpl = new FuturesMarketAPIServiceImpl(config);
		//合约信息
		List<Instruments> instruments = apiServiceImpl.getInstruments();
		//获取深度列表
		Book instrumentBook = apiServiceImpl.getInstrumentBook("ETH-USD-181228", 50);
		//获取全部ticker信息
		List<Ticker> allInstrumentTicker = apiServiceImpl.getAllInstrumentTicker();
		//获取某个ticker信息
		Ticker instrumentTicker = apiServiceImpl.getInstrumentTicker("ETH-USD-181228");
		//获取成交数据
		List<Trades> instrumentTrades = apiServiceImpl.getInstrumentTrades("ETH-USD-181228", 1, 10, 220);
		
		//获取指数信息
		Index instrumentIndex = apiServiceImpl.getInstrumentIndex("ETH-USD-181228");
		//获取爆仓单
		List<Liquidation> instrumentLiquidation = apiServiceImpl.getInstrumentLiquidation("ETH-USD-181228", 1, 1, 2, 200);
		//获取K线数据
		//[timestamp,open,high,low,close,volume,currency_volume]
//		JSONArray instrumentCandles = apiServiceImpl.getInstrumentCandles("ETH-USD-181228", "2018-12-17T02:31:00Z", "2018-12-17T09:55:00Z", 3000);
		JSONArray instrumentCandles = apiServiceImpl.getInstrumentCandles("ETH-USD-181228", "", "", 300);
		instrumentCandles = apiServiceImpl.getInstrumentCandles("ETH-USD-181228", "2018-12-26T06:31:00Z", "", 300);
		String timestamp = DateUtils.getUnixTime();
		
		
		
 		System.out.println(JSON.toJSONString(instruments));
 		System.out.println(JSON.toJSONString(instrumentBook));
 		System.out.println(JSON.toJSONString(allInstrumentTicker));
 		System.out.println(JSON.toJSONString(instrumentTicker));
 		System.out.println(JSON.toJSONString(instrumentTrades));
 		System.out.println(JSON.toJSONString(instrumentIndex));
 		System.out.println(JSON.toJSONString(instrumentLiquidation));
 		System.out.println(JSON.toJSONString(instrumentCandles));
 		System.out.println(getUnixTime());
 		

	}
	
	 public static String getUnixTime() {
	        return Instant.now().toString();
	    }

}
