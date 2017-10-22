
package com.logistics.service.vo.travel;

import cn.assist.easydao.annotation.Id;

import cn.assist.easydao.annotation.Temporary;

import cn.assist.easydao.pojo.BasePojo;
import java.util.Date;
import java.math.*;

/**
 * 定制旅游信息
 */
public class TravelCustom extends BasePojo {
	@Temporary
	private static final long serialVersionUID = 1L;
	@Id
	private String mid;
	private String name;
	private String contact;
	private Integer uid;
	private Date planTime;
	private String departure;
	private String destination;
	private Integer travelNumber;
	private Integer travelDay;
	private Integer roomNumber;
	private BigDecimal budget;
	private Integer source;
	private String specialNeed;
	private Integer status;
	private Integer hotelType;
	private Date createTime;
	private String attachment;
	private Date updateTime;
	private String remark;
	private String operationLog;

	public void setMid(String mid){
		this.mid=mid;
	}
	public String getMid(){
		return mid;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public void setName(String name){
		this.name=name;
	}
	public String getName(){
		return name;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public void setPlanTime(Date planTime){
		this.planTime=planTime;
	}
	public Date getPlanTime(){
		return planTime;
	}
	public void setDeparture(String departure){
		this.departure=departure;
	}
	public String getDeparture(){
		return departure;
	}
	public void setDestination(String destination){
		this.destination=destination;
	}
	public String getDestination(){
		return destination;
	}
	public void setTravelNumber(Integer travelNumber){
		this.travelNumber=travelNumber;
	}
	public Integer getTravelNumber(){
		return travelNumber;
	}
	public void setTravelDay(Integer travelDay){
		this.travelDay=travelDay;
	}
	public Integer getTravelDay(){
		return travelDay;
	}
	public void setRoomNumber(Integer roomNumber){
		this.roomNumber=roomNumber;
	}
	public Integer getRoomNumber(){
		return roomNumber;
	}
	public void setBudget(BigDecimal budget){
		this.budget=budget;
	}
	public BigDecimal getBudget(){
		return budget;
	}
	public void setSource(Integer source){
		this.source=source;
	}
	public Integer getSource(){
		return source;
	}
	public void setSpecialNeed(String specialNeed){
		this.specialNeed=specialNeed;
	}
	public String getSpecialNeed(){
		return specialNeed;
	}
	public void setStatus(Integer status){
		this.status=status;
	}
	public Integer getStatus(){
		return status;
	}
	public void setHotelType(Integer hotelType){
		this.hotelType=hotelType;
	}
	public Integer getHotelType(){
		return hotelType;
	}
	public void setCreateTime(Date createTime){
		this.createTime=createTime;
	}
	public Date getCreateTime(){
		return createTime;
	}
	public void setAttachment(String attachment){
		this.attachment=attachment;
	}
	public String getAttachment(){
		return attachment;
	}
	public void setUpdateTime(Date updateTime){
		this.updateTime=updateTime;
	}
	public Date getUpdateTime(){
		return updateTime;
	}
	public void setRemark(String remark){
		this.remark=remark;
	}
	public String getRemark(){
		return remark;
	}
	public void setOperationLog(String operationLog){
		this.operationLog=operationLog;
	}
	public String getOperationLog(){
		return operationLog;
	}
}

