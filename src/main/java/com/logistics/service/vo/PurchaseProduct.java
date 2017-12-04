
package com.logistics.service.vo;

import cn.assist.easydao.annotation.Id;

import cn.assist.easydao.annotation.Temporary;

import cn.assist.easydao.pojo.BasePojo;
import java.util.Date;
import java.math.*;


/** purchase_product
	ID	INT(11)
	PID	VARCHAR(30)
	PRODUCT_PRICE	DECIMAL(10)
	PE_ID	INT(11)
	CREATE_TIME	DATETIME(19)
	UPDATR_TIME	DATETIME(19)
	REMARK	VARCHAR(255)
*/
public class PurchaseProduct extends BasePojo {
	@Temporary
	private static final long serialVersionUID = 1L;
	@Id
	private Integer id;
	private String pid;
	private Long productPrice;
	private Integer peId;
	private Date createTime;
	private Date updatrTime;
	private String remark;

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
	public void setProductPrice(Long productPrice){
		this.productPrice=productPrice;
	}
	public Long getProductPrice(){
		return productPrice;
	}
	public void setPeId(Integer peId){
		this.peId=peId;
	}
	public Integer getPeId(){
		return peId;
	}
	public void setCreateTime(Date createTime){
		this.createTime=createTime;
	}
	public Date getCreateTime(){
		return createTime;
	}
	public void setUpdatrTime(Date updatrTime){
		this.updatrTime=updatrTime;
	}
	public Date getUpdatrTime(){
		return updatrTime;
	}
	public void setRemark(String remark){
		this.remark=remark;
	}
	public String getRemark(){
		return remark;
	}
}

