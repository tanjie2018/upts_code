package com.upi.upts.timer;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

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
import com.upi.upts.task.TrendTaskSingle;
import com.upi.upts.util.BaseConfigration;
import com.upi.upts.util.StringUtil;
import com.upi.upts.util.ThreadUtil;
import com.upi.upts.util.UptsUtil;

/**
 * 从DB获取K线信息
 * @author tanjie
 */
@Component
public class CandlesFromDBTimer {

	private static Logger logger = LoggerFactory.getLogger(CandlesFromDBTimer.class);
	private static FuturesMarketAPIServiceImpl apiServiceImpl;
	@Autowired
	private ApplicationContext applicationContext;
	@Autowired
	private CandleServiceImpl candleServiceImpl;
	@Autowired
	private ReportTimer reportTimer;
	@PostConstruct
	public void init() {
		getTrade();
	}
	
	public void getTrade() {
		 Runnable runnable = new Runnable() {  
		        public void run() {  
					List<Candle> candlesList = candleServiceImpl.getAllCandles();
					logger.info("candlesSize:"+candlesList.size());
					for(Candle candle : candlesList) {
						
						//如果遇到00:00:00则生成报表
						if(candle.getTime().contains("00:00:00")) {
							reportTimer.saveReport(candle.getOpen(), candle.getTime().substring(0, 10));
						}
						
						logger.info("获取candle为："+candle);
						TrendTaskSingle task = applicationContext.getBean(TrendTaskSingle.class);
		        		task.init(candle, "DB");
		        		try {
							task.dealJob();
						} catch (Exception e) {
							logger.error("任务处理异常:"+candle,e);
						}
					}
		        }  
		    };  
		 ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();  
		 // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间  
		 service.scheduleAtFixedRate(runnable, 5, UiisConstant.DATE_TIME, TimeUnit.SECONDS); 
	}
	
}
