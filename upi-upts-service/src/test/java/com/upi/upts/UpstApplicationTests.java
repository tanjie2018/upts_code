//package com.upi.upts;
//
//import java.util.List;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import com.upi.upts.common.UiisConstant;
//import com.upi.upts.model.Candle;
//import com.upi.upts.model.Trade;
//import com.upi.upts.repository.ReportRepository;
//import com.upi.upts.repository.TradeRepository;
//import com.upi.upts.util.StringUtil;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class UpstApplicationTests {
//
//	@Autowired
//	private TradeRepository tradeRepository;
//	
//	@Test
//	public void contextLoads() throws CloneNotSupportedException {
//		List<Trade> trades = tradeRepository.findDateData(StringUtil.subDay(StringUtil.getNowFormatDate(UiisConstant.UPI_NORMAL_DATE)), "1");
//		List<Trade> trades2 = tradeRepository.findDateData(null, "2");
//		System.out.println("交易记录："+trades);
//		System.out.println("交易记录："+trades2);
//	}
//
//}
