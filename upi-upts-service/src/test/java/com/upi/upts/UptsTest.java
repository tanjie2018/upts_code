package com.upi.upts;

import java.util.concurrent.ConcurrentHashMap;

import com.upi.upts.common.CommonVO;
import com.upi.upts.model.Trade;

public class UptsTest {

	public static void main(String[] args) {
		ConcurrentHashMap<String, Trade> bTradeMap = new ConcurrentHashMap<String, Trade>();
		Trade trade = bTradeMap.get("five");
		CommonVO.sTradeMap.remove("five");
		System.out.println("OK");

	}

}
