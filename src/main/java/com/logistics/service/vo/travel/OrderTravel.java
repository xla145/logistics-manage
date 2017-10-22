
package com.logistics.service.vo.travel;

import cn.assist.easydao.annotation.Id;
import cn.assist.easydao.annotation.Temporary;
import cn.assist.easydao.pojo.BasePojo;

import java.util.Date;

/**
 * 旅游订单
 */
public class OrderTravel extends BasePojo {
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

	public void setOrderId(String orderId){
		this.orderId=orderId;
	}
	public String getOrderId(){
		return orderId;
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
}

