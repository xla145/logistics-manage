package com.logistics.base.constant;

/**
 * 产品类型
 * */
public enum CouponTypeConstant{

	ACTIVITY(1,"活动劵"),GOODS(2,"商品劵"),TRAVEL(3,"旅游劵"),GENERAL(99,"通用劵");

	private Integer id;

	private String name;


	private CouponTypeConstant(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static String getCouponNameById(Integer id){
		CouponTypeConstant[] couponTypeConstants = CouponTypeConstant.values();
		for (CouponTypeConstant coupon:couponTypeConstants) {
			if (coupon.getId() == id) {
				return coupon.getName();
			}
		}
		return "";
	}
}
