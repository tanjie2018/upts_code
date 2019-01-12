package com.upi.upts.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.upi.upts.common.UiisConstant;
import com.upi.upts.okexapi.utils.DateUtils;

@SuppressWarnings({"unchecked","rawtypes"})
public class StringUtil {

	private static final String YEAR_MOUTH_DAY_HOUR_MINUTE_SECOND = "yyyy-MM-dd HH:mm:ss";

	private static final String YEAR_MOUTH_DAY_FORMAT = "yyyy-MM-dd"; // 默认格式

	private static final String MOUTH_DAY_HOUR_MINUTE_SECOND = "MMddHHmmss";

	public static final String TIMEFORMAT_FOR_UPI = "yyyyMMddHHmmss";

	private static Logger logger = LoggerFactory
			.getLogger(StringUtil.class);

	/**
	 * 判断字符串是否为空 在这里 null与(null)都被当做空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {

		return null == str || "".equals(str.trim()) || "null".equals(str) || "(null)".equals(str);
	}



	/**
	 * RetrievalReferenceNumber
	 * @param IIN
	 * @return
	 */
	public synchronized static String getRRNumber() {

		String timeStr = StringUtil.getNowFormatDate(MOUTH_DAY_HOUR_MINUTE_SECOND);

		String RetrievalReferenceNumber = timeStr+getRandNum(2);

		return RetrievalReferenceNumber;
	}
	/**
	 * 把日期转换成字符串
	 * 
	 * @param date
	 * @param ymdformat
	 * @return
	 */

	public static String convertDate(Date date, String ymdformat) {

		if (null == ymdformat || "".equals(ymdformat.trim())) {
			ymdformat = YEAR_MOUTH_DAY_FORMAT;
		}
		SimpleDateFormat sdf = null;
		String result = null;
		try {
			sdf = new SimpleDateFormat(ymdformat);
			result = sdf.format(date);
			return result;
		} catch (Exception e) {
			logger.error("",e);
		} finally {

		}

		return null;
	}


	/**
	 * 字符串转换成日期
	 * 
	 * @param date
	 * @param ymdformat
	 * @return
	 */
	public static Date converDate(String date, String ymdformat) {

		if (null == ymdformat || "".equals(ymdformat.trim())) {
			ymdformat = YEAR_MOUTH_DAY_FORMAT;
		}
		SimpleDateFormat sdf = null;
		Date result = null;
		try {
			sdf = new SimpleDateFormat(ymdformat);
			result = sdf.parse(date);
			return result;
		} catch (Exception e) {
			logger.error("",e);
		} finally {

		}

		return null;

	}

	/**
	 * 把Object转换成String
	 * 
	 * @param object
	 * @return
	 */
	public static String objToString(Object object) {

		if (null == object || "".equals(object) || "(null)".equals(object) || "null".equals(object)) {
			return "";
		}
		return object.toString();
	}

	/**
	 * 两个时间的间隔时间
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws Exception
	 */
	public static String getTimes(String startTime, String endTime) throws Exception {

		SimpleDateFormat sdf = new SimpleDateFormat(YEAR_MOUTH_DAY_HOUR_MINUTE_SECOND);

		if (StringUtil.isEmpty(startTime) || StringUtil.isEmpty(endTime)) {
			return "";
		}
		if (startTime.length() != 19 || endTime.length() != 19) {
			return "";
		}
		long d = sdf.parse(startTime).getTime();
		long d2 = sdf.parse(endTime).getTime();
		return String.valueOf(((d2 - d) / 1000));
	}

	/**
	 * 获取当前时间
	 * @param str
	 * @return
	 */
	public static String getNowFormatDate(String str) {

		SimpleDateFormat sdf = new SimpleDateFormat(str);
		String date = sdf.format(new Date());
		return date;
	}

	/**
	 * 获取超时时间
	 * @param str
	 * @return
	 */
	public static String getTimeoutFormatTime(String str) {

		SimpleDateFormat sdf = new SimpleDateFormat(str);
		Date date = new Date();
		date.setTime(date.getTime() + 45*1000);
		String datestr = sdf.format(date);
		return datestr;
	}

	/**
	 * 获取超时时间
	 * @param str
	 * @return
	 */
	public static String getTimeoutUplanFormatTime(String str) {

		SimpleDateFormat sdf = new SimpleDateFormat(str);
		Date date = new Date();
		date.setTime(date.getTime() + 10000);
		String datestr = sdf.format(date);
		return datestr;
	}

	/**
	 * 根据length值 随即产生length值长度随机数
	 * @param length
	 * @return
	 */
	public static String getRandNum(int length) {

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int j = (int) (Math.random() * 10);
			sb.append(j);
		}
		return sb.toString();
	}

	/**
	 * 
	 * @param temp
	 *            做条件的Map
	 * @param key
	 *            Map的键
	 * @return 该map如果包含该key则返回该 key对应的value,否则返回空字符串
	 */
	public static String contaninsKey(Map<String, Object> temp, String key) {

		if (temp.containsKey(key)) {
			return temp.get(key).toString();
		}
		return "";
	}

	/**
	 * 
	 * @param str
	 *            要拆分的字符串
	 * @param split
	 *            拆分符号
	 * @param len
	 *            向后移动几位
	 * @return
	 */
	public static String lastIndexOf(String str, String split, int len) {

		String string = "";
		if (isEmpty(str) || str.trim().length() <= 0) {
			return string;
		}
		string = str.substring(str.lastIndexOf(split) + len);

		return string;
	}

	/**
	 * 判断多个对象不为空
	 * @param obj该参数长度
	 *            >=1
	 * @return boolean
	 */
	public static boolean isEmpty(Object... obj) {

		if (obj.length <= 0) {
			return true;
		}
		for (int i = 0; i < obj.length; i++) {
			Object object = obj[i];
			if (object instanceof Map) {
				Map<String, Object> map = (Map<String, Object>) object;
				if (map.isEmpty()) {
					return true;
				}
			} else if (object instanceof List) {
				List list = (List) object;
				if (list.isEmpty()) {
					return true;
				}
			} else {
				if (null == object || "".equals(object) || "null".equals(object) || "(null)".equals(object)) {
					return true;
				}
			}
		}
		return false;
	}

	// 用于按时间排序
	public static void sortByDate(List<Map<String, Object>> list, String key) {

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if (!list.isEmpty()) {
				for (int i = 0; i < list.size() - 1; i++) {
					for (int j = 0; j < list.size() - 1 - i; j++) {
						String date_j = (String) list.get(j).get(key);
						Date time_j = sdf.parse(date_j);
						String date_j_1 = (String) list.get(j + 1).get(key);
						Date time_j_1 = sdf.parse(date_j_1);
						if (time_j.getTime() > time_j_1.getTime()) {
							Map<String, Object> temp = new HashMap();
							temp.putAll(list.get(j));
							list.get(j).putAll(list.get(j + 1));
							list.get(j + 1).putAll(temp);
						}

					}
				}
			}
		} catch (Exception e) {
			logger.error("",e);
		}
	}

	// 用于按字符排序
	public static List<Map<String, Object>> sortByData(List<Map<String, Object>> list, String key) {

		logger.info("排序前：" + list);

		String[] sort = new String[list.size()];

		for (int i = 0; i < list.size(); i++) {

			sort[i] = list.get(i).get(key).toString();

		}

		Arrays.sort(sort);

		List<Map<String, Object>> sortList = new ArrayList<Map<String, Object>>();

		for (int i = 0; i < sort.length; i++) {

			for (Map<String, Object> mapSort : list) {

				if (mapSort.get(key).equals(sort[i])) {

					list.remove(mapSort);

					sortList.add(mapSort);

					break;

				}
			}
		}

		logger.info("排序后:" + sortList);

		return sortList;

	}

	/* 
	 * 返回长度为【strLength】的随机数，在前面补0 
	 */  
	private static String getFixLenthString(int strLength) {  
		Random rm = new Random();  
		// 获得随机数  
		double pross = (1 + rm.nextDouble()) * Math.pow(10, strLength);  
		// 将获得的获得随机数转化为字符串  
		String fixLenthString = String.valueOf(pross);  
		// 返回固定的长度的随机数  
		return fixLenthString.substring(1, strLength + 1);  
	}

	/** 
	 *  
	 * @param data1 
	 * @param data2 
	 * @return data1 与 data2拼接的结果 
	 */  
	public static byte[] addBytes(byte[] data1, byte[] data2) {  
		byte[] data3 = new byte[data1.length + data2.length];  
		System.arraycopy(data1, 0, data3, 0, data1.length);  
		System.arraycopy(data2, 0, data3, data1.length, data2.length);  
		return data3;  

	}  
	
	/** 
     * 将时间戳转换为时间
     */
    public static String stampToDate(String stamp,String format){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);//"yyyy-MM-dd HH:mm:ss"
        long lt = new Long(stamp);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }
   
    /** 
     * 将时间转换为时间戳
     */    
    public static String dateToStamp(String time,String format) throws ParseException{
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);//"yyyy-MM-dd HH:mm:ss"
        Date date = simpleDateFormat.parse(time);
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }
    
    /**
     * 获取某一偏移的UTC时间
     * @throws ParseException 
     */    
    public static String getUTCTimeOffset(long offset) throws ParseException {
//        String res;
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(UiisConstant.UPI_UTC_FORMAT);
//        Date date = null;
//		date = simpleDateFormat.parse(DateUtils.getUnixTime());
//        long ts = date.getTime()-offset;
//        date = new Date(ts);
//        res = simpleDateFormat.format(date);
//        return res;
    	TimeZone timezone = TimeZone.getTimeZone("GMT-0");
    	SimpleDateFormat format = new SimpleDateFormat(UiisConstant.UPI_UTC_FORMAT);
    	format.setTimeZone(timezone);
		Date date = new Date(System.currentTimeMillis()-offset);
		return format.format(date);
    }
    
    /**
     * 传入日期减去一天，日期格式"yyyy-MM-dd"
     * @param date
     * @return
     * @throws ParseException
     */
    public static String subDay(String date) { 
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		Date dt = null;
		try {
			dt = sdf.parse(date);
		} catch (ParseException e) {
			logger.info("日期转换异常",e);
			return null;
		}
		Calendar rightNow = Calendar.getInstance(); 
		rightNow.setTime(dt);
		rightNow.add(Calendar.DAY_OF_MONTH, -1);
		Date dt1 = rightNow.getTime();
		String reStr = sdf.format(dt1);
		return reStr;
	}

}
