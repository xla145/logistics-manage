package com.logistics.service.vo.travel;
import java.math.BigDecimal;

import cn.assist.easydao.annotation.Temporary;
import cn.assist.easydao.pojo.BasePojo;

/**
 * 旅游概要
 *
 * @author caixb
 * */
public class ProductTravelSummary extends BasePojo {
	
	@Temporary
	private static final long serialVersionUID = 1L;

	private String pid;
	private String name;
	private String subTitle;                                                                              
	private String title;
	private BigDecimal price;
	private BigDecimal originalPrice;
	
	private String destination;//目的地
	private String departure;//出发地
	private String departTime;//出发时间（多个）
	private String suggestTime;//建议出发时间
	private Integer travelStatus;//状态
	private Integer travelDay;//旅游天数
	private String withCost;//费用包含
	private String withoutCost;//费用不包含
	private String trafficMode;// 往返交通
	
	
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSubTitle() {
		return subTitle;
	}
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getOriginalPrice() {
		return originalPrice;
	}
	public void setOriginalPrice(BigDecimal originalPrice) {
		this.originalPrice = originalPrice;
	}
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
	public Integer getTravelStatus() {
		return travelStatus;
	}
	public void setTravelStatus(Integer travelStatus) {
		this.travelStatus = travelStatus;
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
	public String getTrafficMode() {
		return trafficMode;
	}
	public void setTrafficMode(String trafficMode) {
		this.trafficMode = trafficMode;
	}

	public String getSuggestTime() {
		return suggestTime;
	}

	public void setSuggestTime(String suggestTime) {
		this.suggestTime = suggestTime;
	}
}
