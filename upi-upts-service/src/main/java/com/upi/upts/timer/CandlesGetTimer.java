package com.upi.upts.timer;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.upi.upts.common.CommonVO;
import com.upi.upts.common.UiisConstant;
import com.upi.upts.model.Candle;
import com.upi.upts.okexapi.bean.futures.result.Trades;
import com.upi.upts.okexapi.config.APIConfiguration;
import com.upi.upts.okexapi.service.futures.impl.FuturesMarketAPIServiceImpl;
import com.upi.upts.service.impl.CandleServiceImpl;
import com.upi.upts.task.TrendTask;
import com.upi.upts.util.BaseConfigration;
import com.upi.upts.util.StringUtil;
import com.upi.upts.util.ThreadUtil;
import com.upi.upts.util.UptsUtil;

/**
 * 每秒获取一次K线信息
 * @author tanjie
 */
@Component
public class CandlesGetTimer {

	private static Logger logger = LoggerFactory.getLogger(CandlesGetTimer.class);
	private static FuturesMarketAPIServiceImpl apiServiceImpl;
	@Autowired
	private ApplicationContext applicationContext;
	@Autowired
	private CandleServiceImpl candleServiceImpl;
	private static String id = "";
	@PostConstruct
	public void init() {
		getTrade();
	}
	
	public void getTrade() {
		 Runnable runnable = new Runnable() {  
		        public void run() {  
		        	JSONArray candles = null;
					try {
						candles = getApiServiceImpl().getInstrumentCandles(UiisConstant.instrumentId, StringUtil.getUTCTimeOffset(UiisConstant.DATE_TIME*1000L), "", 300);
					} catch (Exception e) {
						logger.error("API调用异常",e);
						return;
					}
					logger.info("candles:"+candles);
					for(int i=candles.size()-1;i>0;i--) {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						String[] strs = candles.getString(i).replace("[", "").replace("]", "").split(",");
			        	String candleId = String.valueOf(Double.valueOf(strs[0]).longValue());
			        	if(!candleId.equals(id)) {
			        		id = candleId;
			        		Candle candle = UptsUtil.strToCandle(strs);
			        		logger.info("获取candle为："+candle);
			        		TrendTask task = applicationContext.getBean(TrendTask.class);
			        		task.init(candle, "DC");
			        		ThreadUtil.getThreadPoolExecutor().submit(task);
//			        		candleServiceImpl.insert(candle);
			        	}
					}
		        	
//		        	logger.info("成交单号："+trades.getTrade_id()+",成交价格："+trades.getPrice()+",成交时间："+trades.getTimestamp()+",成交方向"+trades.getSide());
		        }  
		    };  
		 ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();  
		 // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间  
		 service.scheduleAtFixedRate(runnable, 5, UiisConstant.DATE_TIME, TimeUnit.SECONDS); 
	}
	
	private static FuturesMarketAPIServiceImpl getApiServiceImpl() {
		APIConfiguration config = BaseConfigration.config();
		if(StringUtil.isEmpty(apiServiceImpl)) {
			apiServiceImpl = new FuturesMarketAPIServiceImpl(config);
		}
		return apiServiceImpl;
	}
}
