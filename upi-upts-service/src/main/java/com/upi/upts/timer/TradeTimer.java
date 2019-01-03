//package com.upi.upts.timer;
//
//import java.util.List;
//import java.util.concurrent.Executors;
//import java.util.concurrent.ScheduledExecutorService;
//import java.util.concurrent.TimeUnit;
//
//import javax.annotation.PostConstruct;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import com.upi.upts.common.CommonVO;
//import com.upi.upts.common.UiisConstant;
//import com.upi.upts.model.Candle;
//import com.upi.upts.okexapi.bean.futures.result.Trades;
//import com.upi.upts.okexapi.config.APIConfiguration;
//import com.upi.upts.okexapi.service.futures.impl.FuturesMarketAPIServiceImpl;
//import com.upi.upts.service.impl.CandleServiceImpl;
//import com.upi.upts.util.BaseConfigration;
//import com.upi.upts.util.StringUtil;
//
///**
// * 获取最新交易信息，每秒一次
// * @author tanjie
// */
//@Component
//public class TradeTimer {
//
//	private static Logger logger = LoggerFactory.getLogger(TradeTimer.class);
//	private static FuturesMarketAPIServiceImpl apiServiceImpl;
//	private static boolean nFlag = false;
//	@Autowired
//	private CandleServiceImpl candleServiceImpl;
//	
//	@PostConstruct
//	public void init() {
//		getTrade();
//	}
//	
//	public void getTrade() {
//		 Runnable runnable = new Runnable() {  
//		        public void run() {  
//		        	List<Trades> instrumentTrades = null;
//					try {
//						instrumentTrades = getApiServiceImpl().getInstrumentTrades(UiisConstant.instrumentId, 1, 1, 1);
//					} catch (Exception e) {
//						logger.error("API调用异常",e);
//						return;
//					}
//		        	Trades trades = instrumentTrades.get(0);
//		        	deal(getFCandle(UiisConstant.FIVE), trades, "F");
//		        	deal(getFCandle(UiisConstant.THREE), trades, "T");
//		        	deal(getFCandle(UiisConstant.ONE), trades, "O");
//		        	deal(getFCandle(UiisConstant.TWO), trades, "W");
//		        	deal(getFCandle(UiisConstant.FOUR), trades, "D");
////		        	logger.info("成交单号："+trades.getTrade_id()+",成交价格："+trades.getPrice()+",成交时间："+trades.getTimestamp()+",成交方向"+trades.getSide());
//		        }  
//		    };  
//		 ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();  
//		 // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间  
//		 service.scheduleAtFixedRate(runnable, 5, 1, TimeUnit.SECONDS); 
//	}
//	
//	private static FuturesMarketAPIServiceImpl getApiServiceImpl() {
//		APIConfiguration config = BaseConfigration.config();
//		if(StringUtil.isEmpty(apiServiceImpl)) {
//			apiServiceImpl = new FuturesMarketAPIServiceImpl(config);
//		}
//		return apiServiceImpl;
//	}
//	
//	private static Candle getFCandle(String level) {
//		return CommonVO.candleMap.get(level);
//	}
//	
//	private void deal(Candle candle,Trades trades,String flag) {
//		if(StringUtil.isEmpty(candle.getId())) {
//			candle.setId(flag+StringUtil.getNowFormatDate(UiisConstant.UPI_TIME_FORMAT));
//			candle.setOpen(trades.getPrice());
//			candle.setHigh(trades.getPrice());
//			candle.setLow(trades.getPrice());
//		}else {
//			if(candle.getHigh()<trades.getPrice()) {
//				candle.setHigh(trades.getPrice());
//			}
//			if(candle.getLow()>trades.getPrice()) {
//				candle.setLow(trades.getPrice());
//			}
//		}
//		candle.setClose(trades.getPrice());
//		if(nFlag) {
//			candle.setTime(StringUtil.getNowFormatDate(UiisConstant.UPI_TIME_FORMAT));
//			candleServiceImpl.insert(candle);
//			candle.setId("");
//		}
//	}
//}
