package com.upi.upts.control;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * 提供交易等相关信息查询
 * @author tanjie
 *
 */
@RestController
public class UptsController {
	
	@GetMapping("/upts/profit/{date}")
	public String getProfitByDate(@PathVariable String date) {
		//date传入格式yyyyMMdd
		
		return null; 
	}

}
