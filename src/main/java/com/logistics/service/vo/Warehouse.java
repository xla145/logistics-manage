
package com.logistics.service.vo;

import cn.assist.easydao.annotation.Id;

import cn.assist.easydao.annotation.Temporary;

import cn.assist.easydao.pojo.BasePojo;
import java.util.Date;
import java.math.*;


/** warehouse
	WID	VARCHAR(30)
	NAME	VARCHAR(128)
	ADDRESS	VARCHAR(128)
	PRINCIPAL	VARCHAR(255)
	MOBILE	CHAR(11)
	STATUS	INT(2)
	REMARK	VARCHAR(256)
	CREATE_TIME	DATETIME(19)
	UPDATE_TIME	DATETIME(19)
*/
public class Warehouse extends BasePojo {
	@Temporary
	private static final long serialVersionUID = 1L;
	@Id
	private String wid;
	private String name;
	private String address;
	private String principal;
	private String mobile;
	private Integer status;
	private String remark;
	private Date createTime;
	private Date updateTime;

	public void setWid(String wid){
		this.wid=wid;
	}
	public String getWid(){
		return wid;
	}
	public void setName(String name){
		this.name=name;
	}
	public String getName(){
		return name;
	}
	public void setAddress(String address){
		this.address=address;
	}
	public String getAddress(){
		return address;
	}
	public void setPrincipal(String principal){
		this.principal=principal;
	}
	public String getPrincipal(){
		return principal;
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

