package com.logistics.base.constant;

/**
 * 
* @ClassName: OrderTravelStatusConstant
* @Description: TODO(旅游订单状态管理)
* @author Administrator
* @date 2017年7月19日
*
 */
public enum OrderTravelStatusConstant {

	DRAFT(-99,"草稿"),WAIT_INITIAL(0,"待处理"),WAIT_PAY(2,"待付款"),WAIT_TRAVEL(5,"已付款"),WAIT_FINISH(10,"已完成"),REDUCE(20,"已取消");
	
	private Integer id;
	private String status;
	
	private OrderTravelStatusConstant(Integer id, String status) {
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
}
