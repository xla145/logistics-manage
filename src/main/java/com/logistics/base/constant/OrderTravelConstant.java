package com.logistics.base.constant;

/**
 * 旅游单常量
 * 
 *
 * @author caixb
 */
public class OrderTravelConstant {
	/**草稿**/
	public static final int STATUS_DRAFT = -99;
	
	/**等待审核**/
	public static final int STATUS_WAIT_INITIAL = 0;

	/**待付款**/
	public static final int STATUS_WAIT_PAY = 2;

	/**已付款**/
	public static final int STATUS_WAIT_TRAVEL = 5;
	
	/**已完成 **/
	public static final int STATUS_WAIT_FINISH = 10;

	/**已取消 **/
	public static final int STATUS_WAIT_CANCEL = 20;
	
}
