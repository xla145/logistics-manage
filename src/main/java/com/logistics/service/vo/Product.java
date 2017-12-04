
package com.logistics.service.vo;

import cn.assist.easydao.annotation.Id;

import cn.assist.easydao.annotation.Temporary;

import cn.assist.easydao.pojo.BasePojo;
import java.util.Date;
import java.math.*;


/** product
	PID	VARCHAR(30)
	CAT_ID	INT(11)
	NAME	VARCHAR(150)
	REFERENCE_PRICE	DECIMAL(10,2)
	PRICE	DECIMAL(10,2)
	UNIT	VARCHAR(10)
	REMARK	VARCHAR(255)
	SUPPLIER_ID	INT(11)
	SUPPLIER_NAME	VARCHAR(50)
	IMAGE_URL	VARCHAR(500)
	CREATE_TIME	DATETIME(19)
	UPDATE_TIME	DATETIME(19)
	WEIGHT	INT(11)
*/
public class Product extends BasePojo {
	@Temporary
	private static final long serialVersionUID = 1L;
	@Id
	private String pid;
	private Integer catId;
	private String name;
	private BigDecimal referencePrice;
	private BigDecimal price;
	private String unit;
	private String remark;
	private Integer supplierId;
	private String supplierName;
	private String imageUrl;
	private Date createTime;
	private Date updateTime;
	private Integer weight;

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
	public void setName(String name){
		this.name=name;
	}
	public String getName(){
		return name;
	}
	public void setReferencePrice(BigDecimal referencePrice){
		this.referencePrice=referencePrice;
	}
	public BigDecimal getReferencePrice(){
		return referencePrice;
	}
	public void setPrice(BigDecimal price){
		this.price=price;
	}
	public BigDecimal getPrice(){
		return price;
	}
	public void setUnit(String unit){
		this.unit=unit;
	}
	public String getUnit(){
		return unit;
	}
	public void setRemark(String remark){
		this.remark=remark;
	}
	public String getRemark(){
		return remark;
	}
	public void setSupplierId(Integer supplierId){
		this.supplierId=supplierId;
	}
	public Integer getSupplierId(){
		return supplierId;
	}
	public void setSupplierName(String supplierName){
		this.supplierName=supplierName;
	}
	public String getSupplierName(){
		return supplierName;
	}
	public void setImageUrl(String imageUrl){
		this.imageUrl=imageUrl;
	}
	public String getImageUrl(){
		return imageUrl;
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
	public void setWeight(Integer weight){
		this.weight=weight;
	}
	public Integer getWeight(){
		return weight;
	}
}

