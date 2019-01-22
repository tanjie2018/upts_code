//package com.upi.upts.timer;
//
//import java.util.LinkedList;
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
//import org.springframework.context.ApplicationContext;
//import org.springframework.stereotype.Component;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONArray;
//import com.upi.upts.common.UiisConstant;
//import com.upi.upts.model.Candle;
//import com.upi.upts.model.Dcandle;
//import com.upi.upts.okexapi.config.APIConfiguration;
//import com.upi.upts.okexapi.service.futures.impl.FuturesMarketAPIServiceImpl;
//import com.upi.upts.service.impl.CandleServiceImpl;
//import com.upi.upts.service.impl.DcandleServiceImpl;
//import com.upi.upts.task.TrendTask;
//import com.upi.upts.util.BaseConfigration;
//import com.upi.upts.util.StringUtil;
//import com.upi.upts.util.ThreadUtil;
//import com.upi.upts.util.UptsUtil;
//
///**
// * 每秒获取一次K线信息,分钟线
// * @author tanjie
// */
//@Component
//public class DcandleGetTimer {
//
//	private static Logger logger = LoggerFactory.getLogger(DcandleGetTimer.class);
//	private static FuturesMarketAPIServiceImpl apiServiceImpl;
//	@Autowired
//	private DcandleServiceImpl candleServiceImpl;
//	private static LinkedList<String> idList = new LinkedList<>();
//	@PostConstruct
//	public void init() {
//		getTrade();
//	}
//	
//	public void getTrade() {
//		 Runnable runnable = new Runnable() {  
//		        public void run() {  
//		        	try {
//						JSONArray candles = null;
//						long start = System.currentTimeMillis();
//						try {
//							candles = getApiServiceImpl().getInstrumentCandles(UiisConstant.instrumentId, StringUtil.getUnixTimeOffset(180000L,UiisConstant.UPI_UTC_TIME), "", 60);
//						} catch (Exception e) {
//							long end = System.currentTimeMillis();
//							long interval = end-start;
//							logger.error("API调用异常,耗时时间："+interval,e);
//							return;
//						}
//						String[] strs = candles.getString(1).replace("[", "").replace("]", "").split(",");
//						String candleId = strs[0];
//						Double volume = Double.valueOf(strs[6]);
//						
//						if(!idList.contains(candleId)) {
//							idList.addLast(candleId);
//							if(idList.size()>3) {
//								idList.removeFirst();
//							}
//							Dcandle candle = JSON.parseObject(JSON.toJSONString(UptsUtil.strToCandle(strs)),Dcandle.class);
//							candle.setVolume(volume);
//							logger.info("获取candle为："+candle);
////							TrendTask task = applicationContext.getBean(TrendTask.class);
////							task.init(candle, "SC");
////							ThreadUtil.getThreadPoolExecutor().submit(task);
//							candleServiceImpl.insert(candle);
//						}
//					} catch (Exception e) {
//						logger.error("行情定时器运行异常",e);
//					} 
////		        	logger.info("成交单号："+trades.getTrade_id()+",成交价格："+trades.getPrice()+",成交时间："+trades.getTimestamp()+",成交方向"+trades.getSide());
//		        }  
//		    };  
//		 ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();  
//		 // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间  
////		 service.scheduleAtFixedRate(runnable, 5, 1, TimeUnit.SECONDS); 
//		 service.scheduleWithFixedDelay(runnable, 5000, 25000, TimeUnit.MILLISECONDS);//两次调用间隔15秒
//	}
//	
//	private static FuturesMarketAPIServiceImpl getApiServiceImpl() {
//		APIConfiguration config = BaseConfigration.config();
//		if(StringUtil.isEmpty(apiServiceImpl)) {
//			apiServiceImpl = new FuturesMarketAPIServiceImpl(config);
//		}
//		return apiServiceImpl;
//	}
//}
