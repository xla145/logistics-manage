
package com.logistics.service.vo;

import cn.assist.easydao.annotation.Id;

import cn.assist.easydao.annotation.Temporary;

import cn.assist.easydao.pojo.BasePojo;
import java.util.Date;

/** purchase_type
	ID	INT(11)
	NAME	VARCHAR(20)
	CREATE_TIME	DATETIME(19)
	UPDATE_TIME	DATETIME(19)
	REMARK	VARCHAR(255)
*/
public class PurchaseType extends BasePojo {
	@Temporary
	private static final long serialVersionUID = 1L;
	@Id
	private Integer id;
	private String name;
	private Date createTime;
	private Date updateTime;
	private String remark;

	public void setId(Integer id){
		this.id=id;
	}
	public Integer getId(){
		return id;
	}
	public void setName(String name){
		this.name=name;
	}
	public String getName(){
		return name;
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
	public void setRemark(String remark){
		this.remark=remark;
	}
	public String getRemark(){
		return remark;
	}
}

