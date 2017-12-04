
package com.logistics.service.vo;

import cn.assist.easydao.annotation.Id;

import cn.assist.easydao.annotation.Temporary;

import cn.assist.easydao.pojo.BasePojo;
import java.util.Date;
import java.math.*;


/** sales_product
	ID	INT(11)
	PID	VARCHAR(30)
	CAT_ID	INT(11)
	W_ID	INT(11)
	BUY_COUNT	INT(11)
	SALES_PRICE	DECIMAL(10)
	PRODUCT_PRICE	DECIMAL(10)
	SALES_ID	VARCHAR(30)
	CREATE_TIME	DATETIME(19)
	UPDATE_TIME	DATETIME(19)
	REMARK	VARCHAR(255)
*/
public class SalesProduct extends BasePojo {
	@Temporary
	private static final long serialVersionUID = 1L;
	@Id
	private Integer id;
	private String pid;
	private Integer catId;
	private Integer wId;
	private Integer buyCount;
	private Long salesPrice;
	private Long productPrice;
	private String salesId;
	private Date createTime;
	private Date updateTime;
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
	public void setCatId(Integer catId){
		this.catId=catId;
	}
	public Integer getCatId(){
		return catId;
	}
	public void setWId(Integer wId){
		this.wId=wId;
	}
	public Integer getWId(){
		return wId;
	}
	public void setBuyCount(Integer buyCount){
		this.buyCount=buyCount;
	}
	public Integer getBuyCount(){
		return buyCount;
	}
	public void setSalesPrice(Long salesPrice){
		this.salesPrice=salesPrice;
	}
	public Long getSalesPrice(){
		return salesPrice;
	}
	public void setProductPrice(Long productPrice){
		this.productPrice=productPrice;
	}
	public Long getProductPrice(){
		return productPrice;
	}
	public void setSalesId(String salesId){
		this.salesId=salesId;
	}
	public String getSalesId(){
		return salesId;
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

