package com.upi.upts.timer;

import java.math.BigDecimal;
import java.text.DecimalFormat;
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

import com.upi.upts.common.CommonVO;
import com.upi.upts.common.UiisConstant;
import com.upi.upts.model.Candle;
import com.upi.upts.service.impl.CandleServiceImpl;
import com.upi.upts.task.ITask;
import com.upi.upts.task.TrendTask;
import com.upi.upts.util.StringUtil;
import com.upi.upts.util.ThreadUtil;

/**
 * 行情信息入库
 * @author tanjie
 */
@Component
public class CandleTimer {

	private static Logger logger = LoggerFactory.getLogger(CandleTimer.class);

	@Autowired
	private CandleServiceImpl candleServiceImpl;
	@Autowired
	private ApplicationContext applicationContext;
	
	@PostConstruct
	public void init() {
		getTrade();
	}
	
	public void getTrade() {
		Runnable runnable5 = new Runnable() {  
	        public void run() {  
	        	logger.info("###############five runing###################");
	        	saveCandleRecord(UiisConstant.FIVE);
	        }  
		};  
	    Runnable runnable3 = new Runnable() {  
	        public void run() {  
	        	logger.info("###############three runing###################");
	        	saveCandleRecord(UiisConstant.THREE);
	        }  
	    }; 
	    Runnable runnable1 = new Runnable() {  
	        public void run() {  
	        	logger.info("###############one runing###################");
	        	saveCandleRecord(UiisConstant.ONE);
	        }  
	    };
	    Runnable runnable2 = new Runnable() {  
	        public void run() {  
	        	logger.info("###############two runing###################");
	        	saveCandleRecord(UiisConstant.TWO);
	        }  
	    };
	    Runnable runnable4 = new Runnable() {  
	        public void run() {  
	        	logger.info("###############four runing###################");
	        	saveCandleRecord(UiisConstant.FOUR);
	        }  
	    };
		ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();  
		// 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间  
		service.scheduleAtFixedRate(runnable5, 5, UiisConstant.FIVE_MINUTES, TimeUnit.SECONDS); 
		service.scheduleAtFixedRate(runnable3, 5, UiisConstant.THIRTY_MINUTES, TimeUnit.SECONDS); 
		service.scheduleAtFixedRate(runnable1, 5, UiisConstant.ONE_HOUR, TimeUnit.SECONDS); 
		service.scheduleAtFixedRate(runnable2, 5, UiisConstant.TWO_HOUR, TimeUnit.SECONDS); 
		service.scheduleAtFixedRate(runnable4, 5, UiisConstant.DATE_TIME, TimeUnit.SECONDS); 
	}
	
	
	private void saveCandleRecord(String level) {
		Candle candle;
		try {
			candle = CommonVO.candleMap.get(level).clone();
			CommonVO.candleMap.get(level).setId("");
		} catch (CloneNotSupportedException e) {
			logger.error("对象克隆失败",e);
			return;
		}
		if(StringUtil.isEmpty(candle.getId())) {
			logger.info(level+"级别，首次空跑，跳过入库");
			return;
		}
		if(candle.getClose()>candle.getOpen()) {
			candle.setProp(UiisConstant.UP);
		}else if(candle.getClose()<candle.getOpen()) {
			candle.setProp(UiisConstant.DOWN);
		}else {
			candle.setProp(UiisConstant.CENTRE);
		}
		candle.setRang(BigDecimal.valueOf((candle.getClose()-candle.getOpen())/candle.getOpen()*100).setScale(3, BigDecimal.ROUND_HALF_UP));
		candle.setTime(StringUtil.getNowFormatDate(UiisConstant.UPI_TIME_FORMAT));
		TrendTask task = applicationContext.getBean(TrendTask.class);
		task.init(candle, level);
		ThreadUtil.getThreadPoolExecutor().submit(task);
		candleServiceImpl.insert(candle);
	}
}
