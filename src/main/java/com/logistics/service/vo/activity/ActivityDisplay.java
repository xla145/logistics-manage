
package com.logistics.service.vo.activity;

import cn.assist.easydao.annotation.Id;

import cn.assist.easydao.annotation.Temporary;

import cn.assist.easydao.pojo.BasePojo;
import java.util.Date;

/**
 * 活动足迹信息
 */
public class ActivityDisplay extends BasePojo {
	@Temporary
	private static final long serialVersionUID = 1L;
	@Id
	private String id;
	private String name;
	private String title;
	private String describe;
	private String addr;
	private Integer status;
	private Integer operatorId;
	private String author;
	private Date createTime;
	private Date expireTime;
	private Date activityTime;
	private String remark;
	private String url;
	private Integer number;//图片数量
	private Date releaseTime;//发布时间
	private Date updateTime;//更新时间

	public void setId(String id){
		this.id=id;
	}
	public String getId(){
		return id;
	}
	public void setName(String name){
		this.name=name;
	}
	public String getName(){
		return name;
	}
	public void setTitle(String title){
		this.title=title;
	}
	public String getTitle(){
		return title;
	}
	public void setDescribe(String describe){
		this.describe=describe;
	}
	public String getDescribe(){
		return describe;
	}
	public void setAddr(String addr){
		this.addr=addr;
	}
	public String getAddr(){
		return addr;
	}
	public void setStatus(Integer status){
		this.status=status;
	}
	public Integer getStatus(){
		return status;
	}
	public void setOperatorId(Integer operatorId){
		this.operatorId=operatorId;
	}
	public Integer getOperatorId(){
		return operatorId;
	}
	public void setAuthor(String author){
		this.author=author;
	}
	public String getAuthor(){
		return author;
	}
	public void setCreateTime(Date createTime){
		this.createTime=createTime;
	}
	public Date getCreateTime(){
		return createTime;
	}
	public void setExpireTime(Date expireTime){
		this.expireTime=expireTime;
	}
	public Date getExpireTime(){
		return expireTime;
	}
	public void setActivityTime(Date activityTime){
		this.activityTime=activityTime;
	}
	public Date getActivityTime(){
		return activityTime;
	}
	public void setRemark(String remark){
		this.remark=remark;
	}
	public String getRemark(){
		return remark;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Date getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(Date releaseTime) {
		this.releaseTime = releaseTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}

