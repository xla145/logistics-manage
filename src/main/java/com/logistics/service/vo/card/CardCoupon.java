package com.logistics.service.vo.card;
import cn.assist.easydao.annotation.Id;

import cn.assist.easydao.annotation.Temporary;

import cn.assist.easydao.pojo.BasePojo;

/** card_coupon
	ID	INT(1)
	COUPON_ID	VARCHAR(64)
	CARD_ID	INT(11)
*/
public class CardCoupon extends BasePojo {
	@Temporary
	private static final long serialVersionUID = 1L;
	@Id
	private Integer id;
	private String couponId;
	private Integer cardId;

	public void setId(Integer id){
		this.id=id;
	}
	public Integer getId(){
		return id;
	}
	public void setCouponId(String couponId){
		this.couponId=couponId;
	}
	public String getCouponId(){
		return couponId;
	}
	public void setCardId(Integer cardId){
		this.cardId=cardId;
	}
	public Integer getCardId(){
		return cardId;
	}
}

