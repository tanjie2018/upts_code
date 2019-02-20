package com.upi.upts.timer;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.upi.upts.common.LocalConfig;
import com.upi.upts.common.UiisConstant;
import com.upi.upts.model.Candle;
import com.upi.upts.okexapi.config.APIConfiguration;
import com.upi.upts.okexapi.service.futures.impl.FuturesMarketAPIServiceImpl;
import com.upi.upts.service.impl.CandleServiceImpl;
import com.upi.upts.task.TrendTask;
import com.upi.upts.task.TrendTaskSingle;
import com.upi.upts.util.BaseConfigration;
import com.upi.upts.util.StringUtil;
import com.upi.upts.util.ThreadUtil;
import com.upi.upts.util.UptsUtil;

/**
 * 每秒获取一次K线信息
 * @author tanjie
 */
@Component
@DependsOn(value= {"localConfig"})
public class CandleGetTimer {

	private static Logger logger = LoggerFactory.getLogger(CandleGetTimer.class);
	private static FuturesMarketAPIServiceImpl apiServiceImpl;
	@Autowired
	private ApplicationContext applicationContext;
	@Autowired
	private CandleServiceImpl candleServiceImpl;
	@Autowired
	private ReportTimer reportTimer;
	private static LinkedList<String> idList = new LinkedList<>();
	@PostConstruct
	public void init() {
		logger.info("Start CandleGetTimer");
		getTrade();
	}
	
	public void getTrade() {
		 Runnable runnable = new Runnable() {  
		        public void run() {  
		        	try {
						JSONArray candles = null;
						long start = System.currentTimeMillis();
						try {
							candles = getApiServiceImpl().getInstrumentCandles(UiisConstant.instrumentId, StringUtil.getUnixTimeOffset(720000L,UiisConstant.UPI_UTC_TIME), "", 300);
						} catch (Exception e) {
							long end = System.currentTimeMillis();
							long interval = end-start;
							logger.error("API调用异常,耗时时间："+interval,e);
							return;
						}
						String[] strs = candles.getString(1).replace("[", "").replace("]", "").split(",");
						String candleId = strs[0];
						if(!idList.contains(candleId)) {
							idList.addLast(candleId);
							if(idList.size()>3) {
								idList.removeFirst();
							}
							Candle candle = UptsUtil.strToCandle(strs);
							logger.info("获取candle为："+candle);
							//多线程处理方式
//							TrendTask task = applicationContext.getBean(TrendTask.class);
//							task.init(candle, "SC");
//							ThreadUtil.getThreadPoolExecutor().submit(task);
							//单线程处理方式
							TrendTaskSingle task = applicationContext.getBean(TrendTaskSingle.class);
			        		task.init(candle, "SC");
			        		try {
								task.dealJob();
							} catch (Exception e) {
								logger.error("任务处理异常:"+candle,e);
							}
							candleServiceImpl.insert(candle);
							//如果遇到00:00:00则生成报表
							if(candle.getTime().contains("00:00:00")) {
								reportTimer.saveReport(candle.getOpen(), candle.getTime().substring(0, 10));
							}
						}
					} catch (Exception e) {
						logger.error("行情定时器运行异常",e);
					} 
//		        	logger.info("成交单号："+trades.getTrade_id()+",成交价格："+trades.getPrice()+",成交时间："+trades.getTimestamp()+",成交方向"+trades.getSide());
		        }  
		    };  
		 ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();  
		 // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间  
//		 service.scheduleAtFixedRate(runnable, 5, 1, TimeUnit.SECONDS); 
		 service.scheduleWithFixedDelay(runnable, 5000, UiisConstant.InitialDelay, TimeUnit.MILLISECONDS);//两次调用间隔300毫秒
	}
	
	private static FuturesMarketAPIServiceImpl getApiServiceImpl() {
		APIConfiguration config = BaseConfigration.config();
		if(StringUtil.isEmpty(apiServiceImpl)) {
			apiServiceImpl = new FuturesMarketAPIServiceImpl(config);
		}
		return apiServiceImpl;
	}
}
