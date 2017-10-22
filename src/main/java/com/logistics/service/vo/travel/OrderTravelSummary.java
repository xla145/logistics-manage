
package com.logistics.service.vo.travel;

import java.math.BigDecimal;
import java.util.Date;

import cn.assist.easydao.annotation.Id;
import cn.assist.easydao.annotation.Temporary;

/**
 * 
 * 
 *
 * @author caixb
 * 2017年8月24日
 */
public class OrderTravelSummary extends OrderSummary {
	
	@Temporary
	private static final long serialVersionUID = 1L;
	
	@Id
	private String orderId;
	private String travelPlace;
	private Date travelTime;
	private String travelDays;
	private Integer rooms;
	private String demands;
	private Integer orderTravelStatus;
	private String remark;
	private String explain;
	private String goodsCancelReason;
	private Date orderCancelTime;
	private Date orderSuccessTime;
	private String mcid;
	private String couponCid;
	private String couponName;
	private BigDecimal couponAmount;
	public void setOrderId(String orderId){
		this.orderId=orderId;
	}
	public String getOrderId(){
		return orderId;
	}

	public String getGoodsCancelReason() {
		return goodsCancelReason;
	}

	public void setGoodsCancelReason(String goodsCancelReason) {
		this.goodsCancelReason = goodsCancelReason;
	}

	public Date getOrderCancelTime() {
		return orderCancelTime;
	}

	public void setOrderCancelTime(Date orderCancelTime) {
		this.orderCancelTime = orderCancelTime;
	}

	public Date getOrderSuccessTime() {
		return orderSuccessTime;
	}

	public void setOrderSuccessTime(Date orderSuccessTime) {
		this.orderSuccessTime = orderSuccessTime;
	}

	public String getTravelPlace() {
		return travelPlace;
	}

	public void setTravelPlace(String travelPlace) {
		this.travelPlace = travelPlace;
	}

	public Date getTravelTime() {
		return travelTime;
	}

	public void setTravelTime(Date travelTime) {
		this.travelTime = travelTime;
	}

	public void setTravelDays(String travelDays){
		this.travelDays=travelDays;
	}
	public String getTravelDays(){
		return travelDays;
	}
	public void setRooms(Integer rooms){
		this.rooms=rooms;
	}
	public Integer getRooms(){
		return rooms;
	}
	public void setDemands(String demands){
		this.demands=demands;
	}
	public String getDemands(){
		return demands;
	}
	public Integer getOrderTravelStatus() {
		return orderTravelStatus;
	}
	public void setOrderTravelStatus(Integer orderTravelStatus) {
		this.orderTravelStatus = orderTravelStatus;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

	public String getMcid() {
		return mcid;
	}

	public void setMcid(String mcid) {
		this.mcid = mcid;
	}

	public String getCouponCid() {
		return couponCid;
	}

	public void setCouponCid(String couponCid) {
		this.couponCid = couponCid;
	}

	public String getCouponName() {
		return couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}

	public BigDecimal getCouponAmount() {
		return couponAmount;
	}

	public void setCouponAmount(BigDecimal couponAmount) {
		this.couponAmount = couponAmount;
	}
}

