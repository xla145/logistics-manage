
package com.logistics.service.vo;

import cn.assist.easydao.annotation.Id;

import cn.assist.easydao.annotation.Temporary;

import cn.assist.easydao.pojo.BasePojo;
import java.util.Date;
import java.math.*;


/** member
	UID	INT(11)
	NAME	VARCHAR(20)
	MOBILE	VARCHAR(11)
	STATUS	INT(5)
	SEX	INT(2)
	REMARK	VARCHAR(128)
	CREATE_TIME	DATETIME(19)
	UPDATE_TIME	DATETIME(19)
*/
public class Member extends BasePojo {
	@Temporary
	private static final long serialVersionUID = 1L;
	@Id
	private Integer uid;
	private String name;
	private String mobile;
	private Integer status;
	private Integer sex;
	private String remark;
	private Date createTime;
	private Date updateTime;

	public void setUid(Integer uid){
		this.uid=uid;
	}
	public Integer getUid(){
		return uid;
	}
	public void setName(String name){
		this.name=name;
	}
	public String getName(){
		return name;
	}
	public void setMobile(String mobile){
		this.mobile=mobile;
	}
	public String getMobile(){
		return mobile;
	}
	public void setStatus(Integer status){
		this.status=status;
	}
	public Integer getStatus(){
		return status;
	}
	public void setSex(Integer sex){
		this.sex=sex;
	}
	public Integer getSex(){
		return sex;
	}
	public void setRemark(String remark){
		this.remark=remark;
	}
	public String getRemark(){
		return remark;
	}
	public void setCreateTime(Date createTime){
		this.createTime=createTime;
	}
	public Date getCreateTime(){
		return createTime;
	}
	public void setUpdateTime(Date updateTime){
		this.updateTime=updateTime;
	}
	public Date getUpdateTime(){
		return updateTime;
	}
}

