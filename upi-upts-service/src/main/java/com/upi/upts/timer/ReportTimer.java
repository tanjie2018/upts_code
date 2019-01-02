package com.upi.upts.timer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
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
 * 每天定时执行一次，生成报表信息
 * @author tanjie
 */
@Component
public class ReportTimer {

	private static Logger logger = LoggerFactory.getLogger(ReportTimer.class);
	private static FuturesMarketAPIServiceImpl apiServiceImpl;
	@Autowired
	private ApplicationContext applicationContext;
	@Autowired
	private CandleServiceImpl candleServiceImpl;
	private static String id = "";
	private static Date tDate = null;
	@PostConstruct
	public void init() {
		try {
			String date = StringUtil.getNowFormatDate(UiisConstant.UPI_NORMAL_DATE);
			tDate = new SimpleDateFormat(UiisConstant.UPI_NORMAL_FORMAT).parse(date+" 00:01:59");
		} catch (ParseException e) {
			logger.error("时间转换异常",e);
		}
		getReport();
	}
	
	private Timer reportTimer = new Timer();
	public void getReport() {
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				System.out.println(System.currentTimeMillis());
				
			}
		};
		//执行task任务，tDate只开始执行的时间，间隔时间 1000*60*60*24
		reportTimer.scheduleAtFixedRate(task, tDate, 1000*60*60*24);
	}
	
	private static FuturesMarketAPIServiceImpl getApiServiceImpl() {
		APIConfiguration config = BaseConfigration.config();
		if(StringUtil.isEmpty(apiServiceImpl)) {
			apiServiceImpl = new FuturesMarketAPIServiceImpl(config);
		}
		return apiServiceImpl;
	}
}
