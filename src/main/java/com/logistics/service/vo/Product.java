
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
	TITLE	VARCHAR(150)
	SUB_TITLE	VARCHAR(512)
	PRICE	DECIMAL(10,2)
	ORIGINAL_PRICE	DECIMAL(11,2)
	STATUS	INT(11)
	LABEL	VARCHAR(50)
	INFO	VARCHAR(715827882)
	REMARK	VARCHAR(255)
	SUPPLIER_ID	INT(11)
	SUPPLIER_NAME	VARCHAR(50)
	IMAGE_URL	VARCHAR(500)
	CREATE_TIME	DATETIME(19)
	RELEASE_TIME	DATETIME(19)
	PLACE_TIME	DATETIME(19)
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
	private String title;
	private String subTitle;
	private BigDecimal price;
	private BigDecimal originalPrice;
	private Integer status;
	private String label;
	private String info;
	private String remark;
	private Integer supplierId;
	private String supplierName;
	private String imageUrl;
	private Date createTime;
	private Date releaseTime;
	private Date placeTime;
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
	public void setTitle(String title){
		this.title=title;
	}
	public String getTitle(){
		return title;
	}
	public void setSubTitle(String subTitle){
		this.subTitle=subTitle;
	}
	public String getSubTitle(){
		return subTitle;
	}
	public void setPrice(BigDecimal price){
		this.price=price;
	}
	public BigDecimal getPrice(){
		return price;
	}
	public void setOriginalPrice(BigDecimal originalPrice){
		this.originalPrice=originalPrice;
	}
	public BigDecimal getOriginalPrice(){
		return originalPrice;
	}
	public void setStatus(Integer status){
		this.status=status;
	}
	public Integer getStatus(){
		return status;
	}
	public void setLabel(String label){
		this.label=label;
	}
	public String getLabel(){
		return label;
	}
	public void setInfo(String info){
		this.info=info;
	}
	public String getInfo(){
		return info;
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
	public void setReleaseTime(Date releaseTime){
		this.releaseTime=releaseTime;
	}
	public Date getReleaseTime(){
		return releaseTime;
	}
	public void setPlaceTime(Date placeTime){
		this.placeTime=placeTime;
	}
	public Date getPlaceTime(){
		return placeTime;
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

