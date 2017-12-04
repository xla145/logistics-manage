
package com.logistics.service.vo;

import cn.assist.easydao.annotation.Id;

import cn.assist.easydao.annotation.Temporary;

import cn.assist.easydao.pojo.BasePojo;
import java.util.Date;
import java.math.*;


/** `order`
	ORDER_ID	VARCHAR(128)
	ORDER_TYPE	VARCHAR(255)
	ORDER_NAME	VARCHAR(256)
	BUY_UID	INT(11)
	BUY_NAME	VARCHAR(64)
	BUY_MOBILE	VARCHAR(11)
	WID	VARCHAR(30)
	CAT_ID	INT(11)
	PRODUCT_ID	VARCHAR(128)
	PRODUCT_IMG	VARCHAR(512)
	PRODUCT_NAME	VARCHAR(256)
	PRODUCT_PRICE	DECIMAL(10,2)
	PRODUCT_ORIGINAL_PRICE	DECIMAL(10,2)
	BUY_COUNT	INT(11)
	TOTAL_AMOUNT	DECIMAL(10,2)
	CREATE_TIME	DATETIME(19)
	UPDATE_TIME	DATETIME(19)
	REMARK	VARCHAR(256)
*/
public class Purchase extends BasePojo {
	@Temporary
	private static final long serialVersionUID = 1L;
	@Id
	private String orderId;
	private String orderType;
	private String orderName;
	private Integer buyUid;
	private String buyName;
	private String buyMobile;
	private String wid;
	private Integer catId;
	private String productId;
	private String productImg;
	private String productName;
	private BigDecimal productPrice;
	private BigDecimal productOriginalPrice;
	private Integer buyCount;
	private BigDecimal totalAmount;
	private Date createTime;
	private Date updateTime;
	private String remark;

	public void setOrderId(String orderId){
		this.orderId=orderId;
	}
	public String getOrderId(){
		return orderId;
	}
	public void setOrderType(String orderType){
		this.orderType=orderType;
	}
	public String getOrderType(){
		return orderType;
	}
	public void setOrderName(String orderName){
		this.orderName=orderName;
	}
	public String getOrderName(){
		return orderName;
	}
	public void setBuyUid(Integer buyUid){
		this.buyUid=buyUid;
	}
	public Integer getBuyUid(){
		return buyUid;
	}
	public void setBuyName(String buyName){
		this.buyName=buyName;
	}
	public String getBuyName(){
		return buyName;
	}
	public void setBuyMobile(String buyMobile){
		this.buyMobile=buyMobile;
	}
	public String getBuyMobile(){
		return buyMobile;
	}
	public void setWid(String wid){
		this.wid=wid;
	}
	public String getWid(){
		return wid;
	}
	public void setCatId(Integer catId){
		this.catId=catId;
	}
	public Integer getCatId(){
		return catId;
	}
	public void setProductId(String productId){
		this.productId=productId;
	}
	public String getProductId(){
		return productId;
	}
	public void setProductImg(String productImg){
		this.productImg=productImg;
	}
	public String getProductImg(){
		return productImg;
	}
	public void setProductName(String productName){
		this.productName=productName;
	}
	public String getProductName(){
		return productName;
	}
	public void setProductPrice(BigDecimal productPrice){
		this.productPrice=productPrice;
	}
	public BigDecimal getProductPrice(){
		return productPrice;
	}
	public void setProductOriginalPrice(BigDecimal productOriginalPrice){
		this.productOriginalPrice=productOriginalPrice;
	}
	public BigDecimal getProductOriginalPrice(){
		return productOriginalPrice;
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

