package com.logistics.service.model;

import java.util.Date;
import java.util.List;

import cn.assist.easydao.annotation.Temporary;
import com.logistics.service.vo.product.Product;
import com.logistics.service.vo.product.ProductImage;
import com.logistics.service.vo.travel.ProductTravelArrange;

/**
 * 业务模型 好物商品
 * @author Administrator
 * */
public class ProductTravelModel extends Product {
	@Temporary
	private static final long serialVersionUID = 1L;

	private String catName;// 产品分类名称
	private Integer catWeight;// 产品类型权重
	private String destination;//目的地
	private String departure;//出发地
	private String departTime;//出发时间（多个）
	private String suggestTime;//建议时间
	private Integer travelStatus;//状态
	private String stroke;//行程亮点
	private Integer travelDay;//旅游天数
	private String withCost;//费用包含
	private String withoutCost;//费用不包含
	private String bookings;//预定须知
	private String remark;//备注
	private Integer wantNumber;//想去的人数 基数
	private String trafficMode;// 往返交通
	private Date createTime;//
	private Date updateTime;
	private List<ProductTravelArrange> productTravelArranges;//行程安排
	private List<ProductImage> productImages;//轮播图片

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getDeparture() {
		return departure;
	}

	public void setDeparture(String departure) {
		this.departure = departure;
	}

	public String getDepartTime() {
		return departTime;
	}

	public void setDepartTime(String departTime) {
		this.departTime = departTime;
	}

	public String getSuggestTime() {
		return suggestTime;
	}

	public void setSuggestTime(String suggestTime) {
		this.suggestTime = suggestTime;
	}

	public Integer getTravelStatus() {
		return travelStatus;
	}

	public void setTravelStatus(Integer TravelStatus) {
		this.travelStatus = TravelStatus;
	}

	public String getStroke() {
		return stroke;
	}

	public void setStroke(String stroke) {
		this.stroke = stroke;
	}

	public Integer getTravelDay() {
		return travelDay;
	}

	public void setTravelDay(Integer travelDay) {
		this.travelDay = travelDay;
	}

	public String getWithCost() {
		return withCost;
	}

	public void setWithCost(String withCost) {
		this.withCost = withCost;
	}

	public String getWithoutCost() {
		return withoutCost;
	}

	public void setWithoutCost(String withoutCost) {
		this.withoutCost = withoutCost;
	}

	public String getBookings() {
		return bookings;
	}

	public void setBookings(String bookings) {
		this.bookings = bookings;
	}

	public String getCatName() {
		return catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}

	public Integer getCatWeight() {
		return catWeight;
	}

	public void setCatWeight(Integer catWeight) {
		this.catWeight = catWeight;
	}

	@Override
	public String getRemark() {
		return remark;
	}

	@Override
	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public Date getCreateTime() {
		return createTime;
	}

	@Override
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public Date getUpdateTime() {
		return updateTime;
	}

	@Override
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public List<ProductTravelArrange> getProductTravelArranges() {
		return productTravelArranges;
	}

	public void setProductTravelArranges(List<ProductTravelArrange> productTravelArranges) {
		this.productTravelArranges = productTravelArranges;
	}

	public List<ProductImage> getProductImages() {
		return productImages;
	}

	public void setProductImages(List<ProductImage> productImages) {
		this.productImages = productImages;
	}

	public Integer getWantNumber() {
		return wantNumber;
	}

	public void setWantNumber(Integer wantNumber) {
		this.wantNumber = wantNumber;
	}

	public String getTrafficMode() {
		return trafficMode;
	}

	public void setTrafficMode(String trafficMode) {
		this.trafficMode = trafficMode;
	}

}
