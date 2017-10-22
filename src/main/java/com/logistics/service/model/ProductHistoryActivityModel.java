package com.logistics.service.model;

import cn.assist.easydao.annotation.Id;
import cn.assist.easydao.annotation.Temporary;
import com.logistics.service.vo.product.Product;
/**
 * 业务模型 好玩活动模型
 * @author Administrator
 * */
public class ProductHistoryActivityModel extends Product {
	@Temporary
	private static final long serialVersionUID = 1L;
	@Id
	private Integer id;//编号
	private Integer browse;
	private String historyTitle;
	private String historyImgUrl;
	private String activityTime;
	private String address;

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

	public String getHistoryTitle() {
		return historyTitle;
	}

	public void setHistoryTitle(String historyTitle) {
		this.historyTitle = historyTitle;
	}

	public String getHistoryImgUrl() {
		return historyImgUrl;
	}

	public void setHistoryImgUrl(String historyImgUrl) {
		this.historyImgUrl = historyImgUrl;
	}

	public String getActivityTime() {
		return activityTime;
	}

	public void setActivityTime(String activityTime) {
		this.activityTime = activityTime;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
