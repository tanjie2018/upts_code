package com.upi.upts;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.upi.upts.common.CommonVO;
import com.upi.upts.common.UiisConstant;
import com.upi.upts.control.UptsController;
import com.upi.upts.model.Trade;
import com.upi.upts.okexapi.utils.DateUtils;
import com.upi.upts.timer.ReportTimer;
import com.upi.upts.util.StringUtil;

public class TimeTest {

	public static void main(String[] args) throws Exception {
		//"2019-01-08T09:15:38Z"
		
		UUID randomUUID = UUID.randomUUID();
		System.out.println(randomUUID);
		
		String id = TimeZone.getDefault().getDisplayName();
		System.out.println(id);
		
		Date date00 = Date.from(Instant.now());
		SimpleDateFormat sdf = new SimpleDateFormat(UiisConstant.UPI_UTC_FORMAT);
		Date date11 = sdf.parse("2019-01-25T16:26:23.131Z");
		System.out.println(date11.getTime()+":"+System.currentTimeMillis());
		sdf.setTimeZone(TimeZone.getTimeZone("GMT-0"));
		date11 = sdf.parse("2019-01-25T16:26:23.131Z");
		System.out.println(date11.getTime()+":"+System.currentTimeMillis());
		
		
		long epochSecond = Instant.now().getEpochSecond();
		System.out.println(epochSecond+":"+System.currentTimeMillis()+":"+date00.getTime());
		Date date01 = Date.from(Instant.now(Clock.system(ZoneId.of("Asia/Shanghai"))));
		
		System.out.println(date00);
		System.out.println(date01);
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(UiisConstant.UPI_UTC_FORMAT);
//		LocalDateTime date1 = LocalDateTime.parse("2019-01-22T06:12:23.131+08:00",dtf);
//		LocalDateTime date1 = LocalDateTime.parse("2019-01-22T06:12:23.131+08:00", DateTimeFormatter.ISO_DATE_TIME);
//		date1.atZone(ZoneId.of("Asia/Shanghai"));
//		Instant from = Instant.from(date1);
//		System.out.println(from.toString());
		
		String unixTime = StringUtil.getUnixTime(UiisConstant.UPI_UTC_FORMAT);
		System.out.println(unixTime);
		Clock systemUTC = Clock.systemUTC();
		systemUTC = Clock.systemDefaultZone();
		Instant instant = Instant.now(systemUTC);
		instant.atZone(ZoneId.of("Asia/Shanghai"));
		System.out.println("zoneId:"+systemUTC.getZone()+",systemUTC:"+instant.toString());
		
		LocalDateTime now2 = LocalDateTime.now(systemUTC);
		
		
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
	 
	 public static String getDatePoor(Date endDate, Date nowDate) {
		 
		    long nd = 1000 * 24 * 60 * 60;
		    long nh = 1000 * 60 * 60;
		    long nm = 1000 * 60;
		    // long ns = 1000;
		    // 获得两个时间的毫秒时间差异
		    long diff = endDate.getTime() - nowDate.getTime();
		    // 计算差多少天
		    long day = diff / nd;
		    // 计算差多少小时
		    long hour = diff % nd / nh;
		    // 计算差多少分钟
		    long min = diff % nd % nh / nm;
		    // 计算差多少秒//输出结果
		    // long sec = diff % nd % nh % nm / ns;
		    return day + "天" + hour + "小时" + min + "分钟";
		}

}
