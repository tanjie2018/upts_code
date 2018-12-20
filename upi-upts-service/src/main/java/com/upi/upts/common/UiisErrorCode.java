package com.upi.upts.common;

import java.util.HashMap;

public class UiisErrorCode {

	//COM线路异常
	public static final String COMCONEX = "ComConnectionTimeoutException";
	//COM通讯异常
	public static final String COMSOCEX = "ComSocketTimeoutException";
	//系统错误
	public static final String SYSTEMERROR = "SYSTEMERROR";

	//参数为空
	public static final String PARAM_ERROR = "PARAM_ERROR";

	//此交易订单号不存在
	public static final String ORDERNOTEXIST = "ORDERNOTEXIST";

	//签名错误
	public static final String SIGNERROR = "SIGNERROR";
	
	//验签失败
	public static final String SIGNATURE_VERIFICATION_FAILS = "SIGNATURE_VERIFICATION_FAILS";
	
	//未知异常
	public static final String UNDEFINED_ERROR = "UAIS0000";

	// 参数为空
	public static final String PARAM_NULL_ERROR = "UAIS0001";

	//请求参数不符合要求
	public static final String PARAM_NOT_SUITABLE_ERROR = "UAIS0002";

	//DB更新失败
	public static final String DB_UPDATE_ERROR = "UAIS0003";

	//DB添加失败
	public static final String DB_INSERT_ERROR = "UAIS0004";

	//DB删除失败
	public static final String DB_DELETE_ERROR = "UAIS0005";

	//DB查询失败
	public static final String DB_SELECT_ERROR = "UAIS0016";

	//数据异常
	public static final String FIELD_ABNORMAL = "UAIS0006";

	//验签失败
	public static final String VERIFY_ERROR = "UAIS0007";

	//字符转换异常
	public static final String STRING_ENCODEING_EXCEPTION = "UAIS0008";

	//传送COM异常
	public static final String COM_TRANS_EXCEPTION = "UAIS0009";

	//交易类型错误
	public static final String MSG_TYPE_ERROR = "UAIS0010";

	//一致性校验错误
	public static final String CHECK_ERROR = "UAIS0011";

	//bitmap错误
	public static final String BITMAP_ERROR = "UAIS0012";

	//加密机调用异常
	public static final String HSM_ERROR = "UAIS0013";

	//无法定位原始交易
	public static final String UNABLE_LOCATE_ORIGINAL_TRACSACTION = "UAIS0014";

	//服务IP获取异常
	public static final String SERVERIP_GET_FAIL = "UAIS0015";

	public static final HashMap<String, String> ERROR_MAP = new HashMap<String, String>();

	static {
		ERROR_MAP.put(SYSTEMERROR,"系统错误");
		ERROR_MAP.put(PARAM_ERROR,"参数错误");
		ERROR_MAP.put(ORDERNOTEXIST,"此交易订单号不存在");
		ERROR_MAP.put(SIGNERROR,"签名错误");
		ERROR_MAP.put(SIGNATURE_VERIFICATION_FAILS,"验签失败");//SYS?
		
		ERROR_MAP.put(UNDEFINED_ERROR,"未知异常");
		ERROR_MAP.put(PARAM_NULL_ERROR,"参数为空");
		ERROR_MAP.put(PARAM_NOT_SUITABLE_ERROR,"请求参数不符合要求");
		ERROR_MAP.put(DB_UPDATE_ERROR,"DB更新失败");
		ERROR_MAP.put(DB_INSERT_ERROR,"DB添加失败");
		ERROR_MAP.put(DB_DELETE_ERROR,"DB删除失败");
		ERROR_MAP.put(FIELD_ABNORMAL,"数据异常");
		ERROR_MAP.put(VERIFY_ERROR,"验签失败");
		ERROR_MAP.put(STRING_ENCODEING_EXCEPTION,"字符转换异常");
		ERROR_MAP.put(COM_TRANS_EXCEPTION,"COM传输异常");
		ERROR_MAP.put(MSG_TYPE_ERROR,"交易类型错误");
		ERROR_MAP.put(BITMAP_ERROR,"位图错误");
		ERROR_MAP.put(HSM_ERROR,"加密机调用异常");
		ERROR_MAP.put(UNABLE_LOCATE_ORIGINAL_TRACSACTION,"无法定位原始交易");
		ERROR_MAP.put(SERVERIP_GET_FAIL,"服务器IP获取失败");
		ERROR_MAP.put(DB_SELECT_ERROR,"DB查询失败");
	}

}
