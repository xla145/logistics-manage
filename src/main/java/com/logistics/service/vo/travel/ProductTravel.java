
package com.logistics.service.vo.travel;

import cn.assist.easydao.annotation.Id;

import cn.assist.easydao.annotation.Temporary;

import cn.assist.easydao.pojo.BasePojo;
import java.util.Date;

/**
 * 旅游模型
 */
public class ProductTravel extends BasePojo {
	@Temporary
	private static final long serialVersionUID = 1L;
	@Id
	private Integer id;
	private String pid;
	private String destination;
	private String departure;
	private String departTime;
	private String suggestTime;
	private Integer travelStatus;
	private String stroke;
	private Integer travelDay;
	private String withCost;
	private String withoutCost;
	private String bookings;
	private String remark;
	private String trafficMode;// 往返交通
	private String trafficModeName;

	public void setId(Integer id){
		this.id=id;
	}
	public Integer getId(){
		return id;
	}
	public void setPid(String pid){
		this.pid=pid;
	}
	public String getPid(){
		return pid;
	}
	public void setDestination(String destination){
		this.destination=destination;
	}
	public String getDestination(){
		return destination;
	}
	public void setDeparture(String departure){
		this.departure=departure;
	}
	public String getDeparture(){
		return departure;
	}
	public void setDepartTime(String departTime){
		this.departTime=departTime;
	}
	public String getDepartTime(){
		return departTime;
	}
	public void setSuggestTime(String suggestTime){
		this.suggestTime=suggestTime;
	}
	public String getSuggestTime(){
		return suggestTime;
	}

	public Integer getTravelStatus() {
		return travelStatus;
	}

	public void setTravelStatus(Integer travelStatus) {
		this.travelStatus = travelStatus;
	}

	public void setStroke(String stroke){
		this.stroke=stroke;
	}
	public String getStroke(){
		return stroke;
	}

	public Integer getTravelDay() {
		return travelDay;
	}

	public void setTravelDay(Integer travelDay) {
		this.travelDay = travelDay;
	}

	public void setWithCost(String withCost){
		this.withCost=withCost;
	}
	public String getWithCost(){
		return withCost;
	}
	public void setWithoutCost(String withoutCost){
		this.withoutCost=withoutCost;
	}
	public String getWithoutCost(){
		return withoutCost;
	}
	public void setBookings(String bookings){
		this.bookings=bookings;
	}
	public String getBookings(){
		return bookings;
	}
	public void setRemark(String remark){
		this.remark=remark;
	}
	public String getRemark(){
		return remark;
	}
	public String getTrafficMode() {
		return trafficMode;
	}

	public void setTrafficMode(String trafficMode) {
		this.trafficMode = trafficMode;
	}

	public String getTrafficModeName() {
		return trafficModeName;
	}

	public void setTrafficModeName(String trafficModeName) {
		this.trafficModeName = trafficModeName;
	}
}

