package com.upi.upts;

import java.text.ParseException;
import java.time.Instant;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.upi.upts.common.UiisConstant;
import com.upi.upts.model.Candle;
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
import com.upi.upts.util.StringUtil;
import com.upi.upts.util.UptsUtil;

public class UptsSpotTest {

	public static void main(String[] args) throws ParseException {
		APIConfiguration config = BaseConfigration.config();
		String instrument = "ETH-USD-190329";
		FuturesMarketAPIServiceImpl apiServiceImpl = new FuturesMarketAPIServiceImpl(config);
		//合约信息
		List<Instruments> instruments = apiServiceImpl.getInstruments();
		System.out.println(JSON.toJSONString(instruments));
		//获取深度列表
		Book instrumentBook = apiServiceImpl.getInstrumentBook(instrument, 50);
		//获取全部ticker信息
		List<Ticker> allInstrumentTicker = apiServiceImpl.getAllInstrumentTicker();
		//获取某个ticker信息
		Ticker instrumentTicker = apiServiceImpl.getInstrumentTicker(instrument);
		//获取成交数据
		List<Trades> instrumentTrades = apiServiceImpl.getInstrumentTrades(instrument, 1, 1, 1);
		
		//获取指数信息
		Index instrumentIndex = apiServiceImpl.getInstrumentIndex(instrument);
		//获取爆仓单
		List<Liquidation> instrumentLiquidation = apiServiceImpl.getInstrumentLiquidation(instrument, 1, 1, 2, 200);
		//获取K线数据
		//[timestamp,open,high,low,close,volume,currency_volume]
//		JSONArray instrumentCandles = apiServiceImpl.getInstrumentCandles("ETH-USD-181228", "2018-12-17T02:31:00Z", "2018-12-17T09:55:00Z", 3000);
//		JSONArray instrumentCandles = apiServiceImpl.getInstrumentCandles(instrument, "2019-01-20T04:22:36Z", "", 300);
		System.out.println("hahah:"+StringUtil.getUnixTimeOffset(720000L,UiisConstant.UPI_UTC_TIME));
		JSONArray instrumentCandles = apiServiceImpl.getInstrumentCandles(instrument, StringUtil.getUnixTimeOffset(720000L,UiisConstant.UPI_UTC_TIME), "", 300);
		
		
 		System.out.println(JSON.toJSONString(instruments));
 		System.out.println(JSON.toJSONString(instrumentBook));
 		System.out.println(JSON.toJSONString(allInstrumentTicker));
 		System.out.println(JSON.toJSONString(instrumentTicker));
 		System.out.println("成交记录"+JSON.toJSONString(instrumentTrades));
 		System.out.println(JSON.toJSONString(instrumentIndex));
 		System.out.println(JSON.toJSONString(instrumentLiquidation));
 		System.out.println(JSON.toJSONString(instrumentCandles));
 		String[] strs = instrumentCandles.getString(1).replace("[", "").replace("]", "").split(",");
 		String candleId = strs[0];
 		System.out.println(candleId);
 		Candle candle = UptsUtil.strToCandle(strs);
 		System.out.println(candle);
	}
	
	 public static String getUnixTime() {
	        return Instant.now().toString();
	    }

}
