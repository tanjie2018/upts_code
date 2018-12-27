package com.upi.upts;

import java.util.concurrent.ConcurrentHashMap;

import com.upi.upts.common.CommonVO;
import com.upi.upts.common.UiisConstant;
import com.upi.upts.model.Trade;
import com.upi.upts.util.StringUtil;

public class UptsTest {

	public static void main(String[] args) {
		String string = "[1,2,3]";

		String timeStampToDateStr = StringUtil.stampToDate("1545890700000", UiisConstant.UPI_NORMAL_FORMAT);
		System.out.println(timeStampToDateStr);
	}

}
