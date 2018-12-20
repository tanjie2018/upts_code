package com.upi.upts.common;

import java.util.HashMap;

/**响应码常量
 * @author tanj_5
 *
 */
public class UiisReplyCode {

	public static final HashMap<String, String> CODE_MAP = new HashMap<String, String>();

	//交易正常
	public static final String APPROVED = "00";
	//缺少参数
	public static final String LACK_PARAMS="LACK_PARAMS";
	//参数错误
	public static final String PARAM_ERROR="PARAM_ERROR";
	//系统错误
	public static final String SYSTEMERROR="SYSTEMERROR";
	//签名错误
	public static final String SIGNERROR="SIGNERROR";
	//无效请求
	public static final String INVALID_REQUEST="INVALID_REQUEST";
	//加签异常
	public static final String SIGNFAIL="SIGNFAIL";
	//此交易订单号不存在
	public static final String ORDERNOTEXIST = "ORDERNOTEXIST";
	//源交易已完成清算，不再更新状态
	public static final String ORIGINALTRANSACTIONHASSETTLED = "ORIGINAL_TRANSACTION_HAS_SETTLED";
	//交易类型异常
	public static final String MSGTYPEERROR="MSGTYPEERROR";
	//支付中
	public static final String USERPAYING="USERPAYING";
	//金额为零
	public static final String FEEISZERO="FEEISZERO";
	//链接超时
	public static final String ATCONEX = "ATConnectionTimeoutException";
	//交易超时
	public static final String ATSOCEX = "ATSocketTimeoutException";
	//链接超时
	public static final String WPHKCONEX = "WPHKConnectionTimeoutException";
	//交易超时
	public static final String WPHKATSOCEX = "WPHKSocketTimeoutException";
	//响应状态异常
	public static final String HTTPSTATEERROR = "HttpStateError";

	static {
		CODE_MAP.put("00", "Approved");
		CODE_MAP.put("LACK_PARAMS", "缺少参数");
		CODE_MAP.put("PARAM_ERROR", "参数错误");
		CODE_MAP.put("SYSTEMERROR", "系统错误");
		CODE_MAP.put("INVALID_REQUEST", "无效请求");
		CODE_MAP.put("SIGNERROR", "签名错误");
		CODE_MAP.put("SIGNFAIL", "加签异常");
		CODE_MAP.put("ORDERNOTEXIST", "此交易订单号不存在");
		CODE_MAP.put("ORIGINAL_TRANSACTION_HAS_SETTLED", "源交易已进入清算流程");
		CODE_MAP.put("MSGTYPEERROR", "交易类型异常");
		CODE_MAP.put("USERPAYING", "用户支付中，需要输入密码");
		CODE_MAP.put("FEEISZERO", "异常交易：金额为零");
		CODE_MAP.put("ATConnectionTimeoutException", "线路异常");
		CODE_MAP.put("ATSocketTimeoutException", "交易超时");
		CODE_MAP.put("WPHKConnectionTimeoutException", "线路异常");
		CODE_MAP.put("WPHKSocketTimeoutException", "交易超时");
		CODE_MAP.put("HttpStateError", "HTTP响应状态异常");
	}
}
