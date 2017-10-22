package com.logistics.base.constant;

/**
 * 
* @ClassName: OrderActivityStatusConstant
* @Description: TODO(活动订单状态)
* @author Administrator
* @date 2017年7月19日
*
 */
public enum OrderActivityStatusConstant {

	INITIAL(0,"待付款"),STARTED(10,"待开始"),STARTING(12,"已开始"),ENDED(15,"已结束"),CANCELLED(20,"已取消");
	
	private Integer id;
	private String status;
	
	private OrderActivityStatusConstant(Integer id, String status) {
		this.id = id;
		this.status = status;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public static String getStatusName(Integer status){
		OrderActivityStatusConstant[] statusConstants = OrderActivityStatusConstant.values();
		for (OrderActivityStatusConstant s:statusConstants) {
			if (s.getId() == status) {
				return s.getStatus();
			}
		}
		return "";
	}
}
