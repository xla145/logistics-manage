package com.logistics.service.model;

import cn.assist.easydao.annotation.Id;
import cn.assist.easydao.annotation.Temporary;
import com.logistics.service.vo.product.Product;
import com.logistics.service.vo.product.ProductImage;

import java.util.Date;
import java.util.List;
/**
 * 业务模型 好玩活动模型
 * @author Administrator
 * */
public class ProductActivityModel extends Product {
	@Temporary
	private static final long serialVersionUID = 1L;
	@Id
	private Integer id;//编号
	private Integer browse;//浏览人数
	private Integer wantNumber;//想去的人数
	private String address;
	private Integer activityStatus;
	private Integer applyMax;
	private Integer activityType;//活动类型
	private Integer applyCurrent;//当前参加人数
	private Date updateTime;
	private Date activityStartTime;//活动开始时间
	private Date activityEndTime;//活动结束时间
	private Date applyStartTime;//报名开始时间
	private Date applyEndTime;//报名结束时间
	private String activityTime;//活动区间
	private Integer carryNumber;//携带人数
	private Integer sumOrder;//总订单数
	private List<ProductImage> imageList;//轮播图片

	public ProductActivityModel() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getBrowse() {
		return browse;
	}

	public void setBrowse(Integer browse) {
		this.browse = browse;
	}

	public Integer getWantNumber() {
		return wantNumber;
	}

	public void setWantNumber(Integer wantNumber) {
		this.wantNumber = wantNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getActivityStatus() {
		return activityStatus;
	}

	public void setActivityStatus(Integer activityStatus) {
		this.activityStatus = activityStatus;
	}

	public Integer getApplyMax() {
		return applyMax;
	}

	public void setApplyMax(Integer applyMax) {
		this.applyMax = applyMax;
	}

	@Override
	public Date getUpdateTime() {
		return updateTime;
	}

	@Override
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getActivityStartTime() {
		return activityStartTime;
	}

	public void setActivityStartTime(Date activityStartTime) {
		this.activityStartTime = activityStartTime;
	}

	public Date getActivityEndTime() {
		return activityEndTime;
	}

	public void setActivityEndTime(Date activityEndTime) {
		this.activityEndTime = activityEndTime;
	}

	public Date getApplyStartTime() {
		return applyStartTime;
	}

	public void setApplyStartTime(Date applyStartTime) {
		this.applyStartTime = applyStartTime;
	}

	public Date getApplyEndTime() {
		return applyEndTime;
	}

	public void setApplyEndTime(Date applyEndTime) {
		this.applyEndTime = applyEndTime;
	}

	public Integer getSumOrder() {
		return sumOrder;
	}

	public void setSumOrder(Integer sumOrder) {
		this.sumOrder = sumOrder;
	}

	public List<ProductImage> getImageList() {
		return imageList;
	}

	public void setImageList(List<ProductImage> imageList) {
		this.imageList = imageList;
	}

	public Integer getCarryNumber() {
		return carryNumber;
	}

	public void setCarryNumber(Integer carryNumber) {
		this.carryNumber = carryNumber;
	}

	public String getActivityTime() {
		return activityTime;
	}

	public void setActivityTime(String activityTime) {
		this.activityTime = activityTime;
	}

	public Integer getApplyCurrent() {
		return applyCurrent;
	}

	public void setApplyCurrent(Integer applyCurrent) {
		this.applyCurrent = applyCurrent;
	}

	public Integer getActivityType() {
		return activityType;
	}

	public void setActivityType(Integer activityType) {
		this.activityType = activityType;
	}
}

