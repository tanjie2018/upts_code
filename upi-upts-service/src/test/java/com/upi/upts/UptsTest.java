package com.upi.upts;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.upi.upts.common.CommonVO;
import com.upi.upts.common.UiisConstant;
import com.upi.upts.model.Trade;
import com.upi.upts.okexapi.utils.DateUtils;
import com.upi.upts.timer.ReportTimer;
import com.upi.upts.util.StringUtil;

public class UptsTest {

	public static void main(String[] args) {
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
		String utcTimeOffset = getUTCTimeOffset(720000);
		System.out.println(utcTimeOffset);
		
	}
	
	 public static String getUTCTimeOffset(long offset) {
	        String res;
	        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(UiisConstant.UPI_UTC_FORMAT);
	        Date date = null;
	        System.out.println(DateUtils.getUnixTime());
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

}
