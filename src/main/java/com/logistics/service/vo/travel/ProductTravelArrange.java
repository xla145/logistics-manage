
package com.logistics.service.vo.travel;

import cn.assist.easydao.annotation.Id;

import cn.assist.easydao.annotation.Temporary;

import cn.assist.easydao.pojo.BasePojo;

/**
 * 行程安排
 */
public class ProductTravelArrange extends BasePojo {
	@Temporary
	private static final long serialVersionUID = 1L;
	@Id
	private Integer id;
	private String pid;
	private String title;
	private String dining;
	private String diningName;
	private String accommodation;
	private String trafficMode;
	private String trafficModeName;
	private String content;

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
	public void setTitle(String title){
		this.title=title;
	}
	public String getTitle(){
		return title;
	}
	public void setDining(String dining){
		this.dining=dining;
	}
	public String getDining(){
		return dining;
	}
	public void setAccommodation(String accommodation){
		this.accommodation=accommodation;
	}
	public String getAccommodation(){
		return accommodation;
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
	public void setContent(String content){
		this.content=content;
	}
	public String getContent(){
		return content;
	}

	public String getDiningName() {
		return diningName;
	}

	public void setDiningName(String diningName) {
		this.diningName = diningName;
	}
}

