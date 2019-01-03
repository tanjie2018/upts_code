package com.upi.upts.timer;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
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
import com.upi.upts.model.Report;
import com.upi.upts.model.Trade;
import com.upi.upts.okexapi.bean.futures.result.Trades;
import com.upi.upts.okexapi.config.APIConfiguration;
import com.upi.upts.okexapi.service.futures.impl.FuturesMarketAPIServiceImpl;
import com.upi.upts.service.impl.CandleServiceImpl;
import com.upi.upts.service.impl.ReportServiceImpl;
import com.upi.upts.service.impl.TradeServiceImpl;
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
	private static Date tDate = null;
	@Autowired
	private ReportServiceImpl reportServiceImpl;
	@Autowired
	private TradeServiceImpl tradeServiceImpl;
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
				List<Trades> trades = getApiServiceImpl().getInstrumentTrades(UiisConstant.instrumentId, 1, 1, 1);
				Double price = trades.get(0).getPrice();
				logger.info("日终清算价格："+price);
				reportServiceImpl.insert(saveReport(price));
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
	
	private Report saveReport(Double price) {
		Report report = new Report();
		String settleDate = StringUtil.subDay(StringUtil.getNowFormatDate(UiisConstant.UPI_NORMAL_DATE));
		//计算爆仓单及损失
		List<Trade> forceOrders = tradeServiceImpl.getDateData(settleDate, "4");
		if(!StringUtil.isEmpty(forceOrders)) {
			report.setForceOrderNum(String.valueOf(forceOrders.size()));
			BigDecimal forceSum = new BigDecimal(0);
			for(Trade trade:forceOrders) {
				forceSum = forceSum.add(trade.getProfit());
			}
			report.setForceOrderCost(forceSum);
		}else {
			report.setForceOrderCost(new BigDecimal(0));
			report.setForceOrderNum("0");
		}
		//计算利润
		List<Trade> tradeOrders = tradeServiceImpl.getDateData(settleDate, "1");
		List<Trade> closeOrders = tradeServiceImpl.getDateData(settleDate, "3");
		if(StringUtil.isEmpty(tradeOrders)) {
			tradeOrders = new LinkedList<>();
		}
		if(StringUtil.isEmpty(closeOrders)) {
			closeOrders = new LinkedList<>();
		}
		tradeOrders.addAll(closeOrders);
		if(StringUtil.isEmpty(tradeOrders)) {
			report.setSprofit(new BigDecimal(0));
			logger.info("已完成交易数为零");
		}else {
			logger.info("已完成交易:"+tradeOrders);
			BigDecimal tradeSum = new BigDecimal(0);
			for(Trade trade:tradeOrders) {
				tradeSum = tradeSum.add(trade.getProfit());
				logger.info("tradeSum:"+tradeSum.toString());
			}
			report.setSprofit(tradeSum);
		}
		//计算未完成订单
		List<Trade> undoOrders = tradeServiceImpl.getDateData(null, "2");
		if(StringUtil.isEmpty(undoOrders)) {
			report.setScost(new BigDecimal(0));
			logger.info("未完成交易数为零");
		}else {
			logger.info("未完成交易:"+undoOrders);
			BigDecimal undoSum = new BigDecimal(0);
			for(Trade trade:undoOrders) {
				double subCost = Math.abs(trade.getBprice()-price);
				BigDecimal undoCost = BigDecimal.valueOf(subCost).multiply(new BigDecimal(trade.getSize())).setScale(3, BigDecimal.ROUND_HALF_UP);
				undoSum = undoSum.add(undoCost);
				logger.info("消失金额："+undoSum);
			}
			report.setScost(undoSum);
		}
		report.setId(settleDate);
		return report;
	}
}
