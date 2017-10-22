package com.logistics.base.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 订单相关的常量
 * 
 *
 * @author caixb
 */
public class OrderConstant {

	/**订单未支付**/
	public static final int ORDER_PAY_STATUS_NOPAY = 0;
	
	/**订单已支付**/
	public static final int ORDER_PAY_STATUS_SUCCESS = 6;
	
	
	public static Map<Integer, String> payStatusMap = new HashMap<Integer, String>();
	static{
		payStatusMap.put(ORDER_PAY_STATUS_NOPAY, "未支付");
		payStatusMap.put(ORDER_PAY_STATUS_SUCCESS, "已支付");
	}
	
	
}
