package com.logistics.base.constant;

/**
 * 
* @ClassName: OrderActivityStatusConstant
* @Description: TODO(好物订单状态)
* @author Administrator
* @date 2017年7月19日
*
 */
public enum OrderGoodsStatusConstant {

	INITIAL(0,"待付款"),PENDING(5,"待发货"),RECEIVED(10,"待收货"),COMPLETED(15,"已完成"),REFUNDED(20,"已退款"),CANCELLED(25,"已取消");
	
	private Integer id;
	private String status;
	
	private OrderGoodsStatusConstant(Integer id, String status) {
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
		OrderGoodsStatusConstant[] statusConstants = OrderGoodsStatusConstant.values();
		for (OrderGoodsStatusConstant s:statusConstants) {
			if (s.getId() == status) {
				return s.getStatus();
			}
		}
		return "";
	}
}
