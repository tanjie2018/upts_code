package com.upi.upts.control;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.upi.upts.common.UiisConstant;
import com.upi.upts.model.Trade;
import com.upi.upts.repository.ReportRepository;
import com.upi.upts.repository.TradeRepository;
import com.upi.upts.util.StringUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 提供交易等相关信息查询
 * @author tanjie
 *
 */
@Slf4j
@RestController
public class UptsController {
	
	@Autowired
	private TradeRepository tradeRepository;
	
	@Autowired
	private ReportRepository reportRepository;
	
	/**
	 * 根据日期获取当天的盈利，如果日期为空，则获取所有的盈利
	 * @param date
	 * @return
	 */
	@GetMapping("/upts/profit/{date}")
	public String getProfit(@PathVariable String date) {
		//date传入格式yyyyMMdd
		SimpleDateFormat format = new SimpleDateFormat(UiisConstant.UPI_DATE_FORMAT);
		Date formatDate = null;
		if(!StringUtil.isEmpty(date)) {
			try {
				formatDate = format.parse(date);
			} catch (ParseException e) {
				log.error("输入日期非yyyyMMdd格式："+date,e);
				return "输入日期非yyyyMMdd格式："+date;
			}
			date = new SimpleDateFormat(UiisConstant.UPI_NORMAL_DATE).format(formatDate);
		}
		
		String profit = tradeRepository.getProfitDate(date);
		return profit; 
	}
	
	/**
	 * 根据日期获取当天的日报，如果日期为空，则获取所有的日报
	 * @param date
	 * @return
	 */
	@GetMapping("/upts/report/{date}")
	public String getReport(@PathVariable String date) {
		log.info("传入日期："+date);
		//date传入格式yyyyMMdd
		SimpleDateFormat format = new SimpleDateFormat(UiisConstant.UPI_DATE_FORMAT);
		Date formatDate = null;
		if(!StringUtil.isEmpty(date)) {
			try {
				formatDate = format.parse(date);
			} catch (ParseException e) {
				log.error("输入日期非yyyyMMdd格式："+date,e);
				return "输入日期非yyyyMMdd格式："+date;
			}
			date = new SimpleDateFormat(UiisConstant.UPI_NORMAL_DATE).format(formatDate);
			return JSONObject.toJSONString(reportRepository.findById(date));
		}else {
			return JSONObject.toJSONString(reportRepository.findAll());
		}
	}
	
	/**
	 * 交易查询接口
	 * date格式为yyyyMMdd或者tt,为tt时则忽略，只用flag参数查询；flag必须为0-9之间的数字
	 * @param date
	 * @param flag
	 * @return
	 */
	@GetMapping("/upts/trade/{date}/{flag}")
	public String getSellOrders(@PathVariable String date,@PathVariable String flag) {
		if(!Pattern.compile("[0-9]").matcher(flag).matches()) {
			return "format error";
		}
		if("tt".equals(date)) {
			date=null;
		}else {
			SimpleDateFormat format = new SimpleDateFormat(UiisConstant.UPI_DATE_FORMAT);
			Date formatDate = null;
			if(!StringUtil.isEmpty(date)) {
				try {
					formatDate = format.parse(date);
				} catch (ParseException e) {
					log.error("输入日期非yyyyMMdd格式："+date,e);
					return "输入日期非yyyyMMdd格式："+date;
				}
				date = new SimpleDateFormat(UiisConstant.UPI_NORMAL_DATE).format(formatDate);
			}else {
				date = null;
			}
		}
		List<Trade> tradeData = tradeRepository.findDateData(date, flag);
		return JSON.toJSONString(tradeData);
	}
	
}
