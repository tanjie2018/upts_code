package com.upi.upts.common;

import java.util.HashMap;
import java.util.LinkedList;

/**常量类
 * @author tanj_5
 *
 */
public class UiisParam {

	public static final String acqId = "acq_id";
	public static final String appid = "appid";
	public static final String attach = "attach";
	public static final String authCode = "auth_code";
	public static final String bankType = "bank_type";
	public static final String body = "body";
	public static final String cashFee = "cash_fee";
	public static final String cashFeeType = "cash_fee_type";
	public static final String channelId = "channel_id";
	public static final String codeUrl = "code_url";
	public static final String couponFee = "coupon_fee";
	public static final String createTime = "create_time";
	public static final String detail = "detail";
	public static final String deviceInfo = "device_info";
	public static final String errCode = "err_code";
	public static final String errCodeDes = "err_code_des";
	public static final String feeType = "fee_type";
	public static final String goodsTag = "goods_tag";
	public static final String identity = "identity";
	public static final String issId = "iss_id";
	public static final String limitPay = "limit_pay";
	public static final String mchId = "mch_id";
	public static final String mchType = "mch_type";
	public static final String mwebUrl = "mweb_url";
	public static final String needReceipt = "need_receipt";
	public static final String notifyUrl = "notify_url";
	public static final String openid = "openid";
	public static final String outTradeNo = "out_trade_no";
	public static final String pan = "pan";
	public static final String payUrl = "pay_url";
	public static final String prepayId = "prepay_id";
	public static final String promotionDetail = "promotion_detail";
	public static final String recall = "recall";
	public static final String resultCode = "result_code";
	public static final String returnCode = "return_code";
	public static final String returnMsg = "return_msg";
	public static final String sceneInfo = "scene_info";
	public static final String settlementTotalFee = "settlement_total_fee";
	public static final String spbillCreateIp = "spbill_create_ip";
	public static final String subAppid = "sub_appid";
	public static final String subMchId = "sub_mch_id";
	public static final String subMchName = "sub_mch_name";
	public static final String subMchSimpleName = "sub_mch_simple_name";
	public static final String subOpenid = "sub_openid";
	public static final String timeEnd = "time_end";
	public static final String timeExpire = "time_expire";
	public static final String timeStart = "time_start";
	public static final String totalFee = "total_fee";
	public static final String tradeState = "trade_state";
	public static final String tradeStateDesc = "trade_state_desc";
	public static final String tradeType = "trade_type";
	public static final String transTp = "trans_tp";
	public static final String transactionId = "transaction_id";
	public static final String updateTime = "update_time";
	public static final String wcPayData = "wc_pay_data";
	public static final String wcRiskInfoRes = "wc_risk_info_res";
	public static final String wcTransactionId = "wc_transaction_id";
	public static final String outRefundNo = "out_refund_no";
	public static final String refundFee = "refund_fee";
	public static final String refundFeeType = "refund_fee_type";
	public static final String refundDesc = "refund_desc";
	public static final String refundAccount = "refund_account";
	public static final String refundId = "refund_id";
	public static final String refundCreateTime = "refund_create_time";
	public static final String settlementRefundFee = "settlement_refund_fee";
	public static final String cashRefundFee = "cash_refund_fee";
	public static final String couponRefundFee = "coupon_refund_fee";
	public static final String refundSuccessTime = "refund_success_time";
	public static final String refundDetail = "refund_detail";
	public static final String rawSpbillNo = "raw_spbill_no";
	public static final String wxSpbillNo = "wx_spbill_no";
	public static final String settlementFeeType = "settlement_fee_type";
	public static final String refundChannel = "refund_channel";
	public static final String refundStatus = "refund_status";
	public static final String refundRecvAccout = "refund_recv_accout";
	public static final String priAccNo = "pri_acc_no";//pan
	
	//非入库使用
	public static final String nonceStr = "nonce_str";
	public static final String certId = "cert_id";
	public static final String sign = "sign";
	
	
	public static HashMap<String, String> FIELD_MAP = new HashMap<>();
	static {
//		FIELD_MAP.put("sign", "sign");
//		FIELD_MAP.put("cert_id", "certId");
		FIELD_MAP.put("refund_detail", "refundDetail");
		FIELD_MAP.put("acq_id", "acqId");
		FIELD_MAP.put("appid", "appid");
		FIELD_MAP.put("attach", "attach");
		FIELD_MAP.put("auth_code", "authCode");
		FIELD_MAP.put("bank_type", "bankType");
		FIELD_MAP.put("body", "body");
		FIELD_MAP.put("cash_fee", "cashFee");
		FIELD_MAP.put("cash_fee_type", "cashFeeType");
		FIELD_MAP.put("channel_id", "channelId");
		FIELD_MAP.put("code_url", "codeUrl");
		FIELD_MAP.put("coupon_fee", "couponFee");
		FIELD_MAP.put("create_time", "createTime");
		FIELD_MAP.put("detail", "detail");
		FIELD_MAP.put("device_info", "deviceInfo");
		FIELD_MAP.put("err_code", "errCode");
		FIELD_MAP.put("err_code_des", "errCodeDes");
		FIELD_MAP.put("fee_type", "feeType");
		FIELD_MAP.put("goods_tag", "goodsTag");
		FIELD_MAP.put("identity", "identity");
		FIELD_MAP.put("iss_id", "issId");
		FIELD_MAP.put("limit_pay", "limitPay");
		FIELD_MAP.put("mch_id", "mchId");
		FIELD_MAP.put("mch_type", "mchType");
		FIELD_MAP.put("mweb_url", "mwebUrl");
		FIELD_MAP.put("need_receipt", "needReceipt");
		FIELD_MAP.put("notify_url", "notifyUrl");
		FIELD_MAP.put("openid", "openid");
		FIELD_MAP.put("out_refund_no", "outRefundNo");
		FIELD_MAP.put("out_trade_no", "outTradeNo");
		FIELD_MAP.put("pan", "pan");
		FIELD_MAP.put("pay_url", "payUrl");
		FIELD_MAP.put("prepay_id", "prepayId");
		FIELD_MAP.put("promotion_detail", "promotionDetail");
		FIELD_MAP.put("recall", "recall");
		FIELD_MAP.put("result_code", "resultCode");
		FIELD_MAP.put("return_code", "returnCode");
		FIELD_MAP.put("return_msg", "returnMsg");
		FIELD_MAP.put("scene_info", "sceneInfo");
		FIELD_MAP.put("settlement_total_fee", "settlementTotalFee");
		FIELD_MAP.put("spbill_create_ip", "spbillCreateIp");
		FIELD_MAP.put("sub_appid", "subAppid");
		FIELD_MAP.put("sub_mch_id", "subMchId");
		FIELD_MAP.put("sub_mch_name", "subMchName");
		FIELD_MAP.put("sub_mch_simple_name", "subMchSimpleName");
		FIELD_MAP.put("sub_openid", "subOpenid");
		FIELD_MAP.put("time_end", "timeEnd");
		FIELD_MAP.put("time_expire", "timeExpire");
		FIELD_MAP.put("time_start", "timeStart");
		FIELD_MAP.put("total_fee", "totalFee");
		FIELD_MAP.put("trade_state", "tradeState");
		FIELD_MAP.put("trade_state_desc", "tradeStateDesc");
		FIELD_MAP.put("trade_type", "tradeType");
		FIELD_MAP.put("trans_tp", "transTp");
		FIELD_MAP.put("transaction_id", "transactionId");
		FIELD_MAP.put("update_time", "updateTime");
		FIELD_MAP.put("wc_pay_data", "wcPayData");
		FIELD_MAP.put("wc_risk_info_res", "wcRiskInfoRes");
		FIELD_MAP.put("wc_transaction_id", "wcTransactionId");
		FIELD_MAP.put("refund_fee", "totalFee");//映射totalFee
		FIELD_MAP.put("refund_fee_type", "feeType");//映射feeType
		FIELD_MAP.put("refund_desc", "tradeStateDesc");//映射tradeStateDesc
		FIELD_MAP.put("refund_account", "authCode");//映射authCode
		FIELD_MAP.put("refund_id", "transactionId");//映射transactionId
		FIELD_MAP.put("refund_create_time", "timeEnd");//映射timeEnd
		FIELD_MAP.put("settlement_refund_fee", "settlementTotalFee");//映射settlementTotalFee
		FIELD_MAP.put("cash_refund_fee", "cashFee");//映射cashFee
		FIELD_MAP.put("coupon_refund_fee", "couponFee");//映射couponFee
		FIELD_MAP.put("refund_success_time", "timeExpire");//映射timeExpire
		FIELD_MAP.put("refund_detail", "promotionDetail");//映射promotionDetail
		FIELD_MAP.put("raw_spbill_no", "rawSpbillNo");
		FIELD_MAP.put("wx_spbill_no", "wxSpbillNo");
		FIELD_MAP.put("settlement_fee_type", "settlementFeeType");
		FIELD_MAP.put("refund_channel", "prepayId");//退款渠道
		FIELD_MAP.put("refund_status", "tradeState");//退款状态
		FIELD_MAP.put("refund_recv_accout", "wcRiskinfoRes");//退款入账账户
		FIELD_MAP.put("pri_acc_no", "pan");
	}
	
	public static HashMap<String, String> CURRENCY_MAP_FROM_AT = new HashMap<>();
	static {
		
		CURRENCY_MAP_FROM_AT.put("cashFee", "cashFeeType");
		CURRENCY_MAP_FROM_AT.put("couponFee", "cashFeeType");
		CURRENCY_MAP_FROM_AT.put("settlementTotalFee", "FeeType");//settlement_total_fee以及settlement_refund_fee都为settlementTotalFee
		CURRENCY_MAP_FROM_AT.put("totalFee", "feeType");
		
	}
	
	public static HashMap<String, String> CURRENCY_MAP_FROM_WEIXIN = new HashMap<>();
	static {
		
		CURRENCY_MAP_FROM_WEIXIN.put("cashFee", "cashFeeType");
		CURRENCY_MAP_FROM_WEIXIN.put("couponFee", "cashFeeType");
		CURRENCY_MAP_FROM_WEIXIN.put("settlementTotalFee", "settlementFeeType");//settlement_total_fee与settlement_refund_fee都为settlementTotalFee
		CURRENCY_MAP_FROM_WEIXIN.put("totalFee", "feeType");
	}
	
	public static LinkedList<String> REQUIRE_LIST = new LinkedList<String>();
	static {
		REQUIRE_LIST.add(UiisParam.nonceStr);
		REQUIRE_LIST.add(UiisParam.issId);
	}
}
