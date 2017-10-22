
package com.logistics.service.vo.activity;

import cn.assist.easydao.annotation.Id;

import cn.assist.easydao.annotation.Temporary;

import cn.assist.easydao.pojo.BasePojo;

/**
 * 活动足迹图片信息
 */
public class ActivityDisplayImgs extends BasePojo {
	@Temporary
	private static final long serialVersionUID = 1L;
	@Id
	private Integer id;
	private String activityDisplayId;
	private String url;

	public void setId(Integer id){
		this.id=id;
	}
	public Integer getId(){
		return id;
	}

	public String getActivityDisplayId() {
		return activityDisplayId;
	}

	public void setActivityDisplayId(String activityDisplayId) {
		this.activityDisplayId = activityDisplayId;
	}

	public void setUrl(String url){
		this.url=url;
	}
	public String getUrl(){
		return url;
	}
}

