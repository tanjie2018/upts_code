package com.upi.upts;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;

import com.upi.upts.common.CommonVO;
import com.upi.upts.common.UiisConstant;
import com.upi.upts.control.UptsController;
import com.upi.upts.model.Trade;
import com.upi.upts.okexapi.utils.DateUtils;
import com.upi.upts.timer.ReportTimer;
import com.upi.upts.util.StringUtil;

public class UptsTest {

	public static void main(String[] args) throws Exception {
//		String string = "[1,2,3]";
//
//		String timeStampToDateStr = StringUtil.stampToDate("1545890700000", UiisConstant.UPI_NORMAL_FORMAT);
//		System.out.println(timeStampToDateStr);
//		
//		String str = "1.5458907E12";
//		Double valueOf = Double.valueOf(str);
//		System.out.println(valueOf.longValue());
//		String unixTime = "";
//		String timeStamp = "1545888300000";
//		try {
//			unixTime = DateUtils.getUnixTime();
//			Date parseUTCTime = DateUtils.parseUTCTime(unixTime);
//			String string2 = parseUTCTime.toString();
//			System.out.println(string2);
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		String utcTime = StringUtil.getNowFormatDate(UiisConstant.UPI_UTC_FORMAT);
//		System.out.println(utcTime);
////		long ct = System.currentTimeMillis()-600000L;
//		
//		String utcTimeOffset = StringUtil.getUTCTimeOffset(600000L);
//		System.out.println(utcTimeOffset);
//		
//		
//		String stampToDate = StringUtil.stampToDate(String.valueOf(System.currentTimeMillis()-600000L), UiisConstant.UPI_UTC_FORMAT);
//		System.out.println(stampToDate);
		
//		ReportTimer timer = new ReportTimer();
//		timer.getReport();
		//"2019-01-08T09:15:38Z"
		String unixTime = StringUtil.getUnixTime(UiisConstant.UPI_UTC_FORMAT);
		System.out.println(unixTime);
		Clock systemUTC = Clock.systemUTC();
		
		LocalDateTime now2 = LocalDateTime.now(systemUTC);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(UiisConstant.UPI_UTC_FORMAT);
		
		LocalDateTime date1 = LocalDateTime.parse("2019-01-22T06:12:23.131Z",dtf);
//		date1.
		
		String format1 = dtf.format(now2);
		System.out.println(format1);
		
		String format = now2.format(DateTimeFormatter.ISO_DATE_TIME);
		System.out.println(format);
		ZonedDateTime now1 = ZonedDateTime.now(Clock.systemUTC());
		System.out.println(now1.getZone());
//		DateTimeFormatter.ISO_INSTANT.
//		System.out.println(now1.toString());
//		long millis = systemUTC.millis();
//		System.out.println("millis:"+millis+":"+System.currentTimeMillis());
		
		Instant now = Instant.now();
		System.out.println(now.getEpochSecond()+":"+Calendar.getInstance().getTimeInMillis());
		
		long timeInMillis = Calendar.getInstance().getTimeInMillis();
		Date date = new Date(timeInMillis);
		System.out.println("UTC NOW:"+now.toString());
		System.out.println(now);
		System.out.println("Date:"+date);
		String string ="";
		try {
			string = StringUtil.getUnixTimeOffset(10000L,UiisConstant.UPI_UTC_TIME);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println(string);
		String utcTimeStr = "";
		try {
			utcTimeStr = unixTime;
			System.out.println(utcTimeStr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String localTimeFromUTC = StringUtil.getLocalTimeFromUTC(utcTimeStr, UiisConstant.UPI_UTC_FORMAT, UiisConstant.UPI_NORMAL_FORMAT);
		System.out.println(localTimeFromUTC);
		String er = StringUtil.getLocalTimeFromUTC("2019-01-22T01:30:00.000Z", UiisConstant.UPI_UTC_FORMAT, UiisConstant.UPI_NORMAL_FORMAT);
		System.out.println(er);
		
		
	}
	
	 public static String getUTCTimeOffset(long offset) {
	        String res;
	        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(UiisConstant.UPI_UTC_FORMAT);
	        Date date = null;
			try {
//				date = simpleDateFormat.parse("2019-01-08T09:15:38Z");
				date = simpleDateFormat.parse(DateUtils.getUnixTime());
			} catch (ParseException e) {
				System.out.println(e);
			}
	        long ts = date.getTime()-offset;
	        date = new Date(ts);
	        res = simpleDateFormat.format(date);
	        return res;
	    }
	 
	 public static void getCurrentUtcTime() {
	        Date l_datetime = new Date();
	        DateFormat formatter = new SimpleDateFormat(UiisConstant.UPI_UTC_FORMAT);
	        TimeZone l_timezone = TimeZone.getTimeZone("GMT-0");
	        formatter.setTimeZone(l_timezone);
	        String l_utc_date = formatter.format(l_datetime);
	        System.out.println(l_utc_date +"(Local)");
	 }
	 
	 public static String getUTCTimeStr() {
		 	TimeZone l_timezone = TimeZone.getTimeZone("GMT-0");
			Calendar cal = Calendar.getInstance(l_timezone);
			String valueOf = String.valueOf(cal.getTimeInMillis());
			System.out.println(valueOf);
			
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(UiisConstant.UPI_UTC_FORMAT);
			simpleDateFormat.setTimeZone(l_timezone);
			Date date = new Date(cal.getTimeInMillis()-720000);
			String format = simpleDateFormat.format(date);
			System.out.println(format);
			Date date2 = new Date(System.currentTimeMillis()-720000);
			format = simpleDateFormat.format(date2);
			System.out.println(format);
			return format;
	}

}
