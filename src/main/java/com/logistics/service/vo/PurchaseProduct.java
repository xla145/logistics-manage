
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
	BUY_COUNT	INT(11)
	TOTAL_AMOUNT	DECIMAL(10,2)
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
	private Integer catId;
	private String wid;
	private String wName;
	private String catName;
	private BigDecimal productPrice;
	private Integer peId;
	private Integer buyCount;
	private BigDecimal totalAmount;
	private Date createTime;
	private Date updateTime;
	private String productName;
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

	public BigDecimal getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(BigDecimal productPrice) {
		this.productPrice = productPrice;
	}

	public void setPeId(Integer peId){
		this.peId=peId;
	}
	public Integer getPeId(){
		return peId;
	}
	public void setBuyCount(Integer buyCount){
		this.buyCount=buyCount;
	}
	public Integer getBuyCount(){
		return buyCount;
	}
	public void setTotalAmount(BigDecimal totalAmount){
		this.totalAmount=totalAmount;
	}
	public BigDecimal getTotalAmount(){
		return totalAmount;
	}
	public void setCreateTime(Date createTime){
		this.createTime=createTime;
	}
	public Date getCreateTime(){
		return createTime;
	}
	public void setRemark(String remark){
		this.remark=remark;
	}
	public String getRemark(){
		return remark;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getCatId() {
		return catId;
	}

	public void setCatId(Integer catId) {
		this.catId = catId;
	}

	public String getCatName() {
		return catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}

	public String getWid() {
		return wid;
	}

	public void setWid(String wid) {
		this.wid = wid;
	}

	public String getwName() {
		return wName;
	}

	public void setwName(String wName) {
		this.wName = wName;
	}
}

