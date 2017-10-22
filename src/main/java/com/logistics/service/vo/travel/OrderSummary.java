package com.logistics.service.vo.travel;
import cn.assist.easydao.annotation.Temporary;
import cn.assist.easydao.pojo.BasePojo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * 订单
 *
 * @author caixb
 */
public class OrderSummary extends BasePojo{

	@Temporary
	private static final long serialVersionUID = 1L;
	
	
	private String orderId;					//订单id
	private Integer catId;                  // 产品分类编号
	private String orderName;				//订单名称
	private Integer buyUid;					//买家uid
	private String buyName;					//买家名称
	private String buyMobile;				//买家手机号
	private Integer sellUid;				//卖家uid
	private Integer payStatus;				//支付状态 0：未支付   6：已支付
	private String pid;						//商品id
	private Integer productGroupType;			//商品一级分类id
	private String productName;				//商品名称
	private BigDecimal productPrice;		//商品售价
	private BigDecimal productOriginalPrice;//商品原价
	private Integer buyCount;				//购买数量
	private BigDecimal totalAmount;			//订单总额（售价*数量）
	private BigDecimal payCash;				//订单现金支付金额
	private BigDecimal payNoncash;			//订单非现金支付金额
	private Date createTime;				//订单创建时间
	private Integer supplierId; //供应商编号
	private String supplierName;//供应商名字
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderName() {
		return orderName;
	}
	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}
	public Integer getBuyUid() {
		return buyUid;
	}
	public void setBuyUid(Integer buyUid) {
		this.buyUid = buyUid;
	}
	public String getBuyName() {
		return buyName;
	}
	public void setBuyName(String buyName) {
		this.buyName = buyName;
	}
	public String getBuyMobile() {
		return buyMobile;
	}
	public void setBuyMobile(String buyMobile) {
		this.buyMobile = buyMobile;
	}
	public Integer getSellUid() {
		return sellUid;
	}
	public void setSellUid(Integer sellUid) {
		this.sellUid = sellUid;
	}
	public Integer getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}

	public Integer getProductGroupType() {
		return productGroupType;
	}

	public void setProductGroupType(Integer productGroupType) {
		this.productGroupType = productGroupType;
	}

	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public BigDecimal getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(BigDecimal productPrice) {
		this.productPrice = productPrice;
	}

	public BigDecimal getProductOriginalPrice() {
		return productOriginalPrice;
	}

	public void setProductOriginalPrice(BigDecimal productOriginalPrice) {
		this.productOriginalPrice = productOriginalPrice;
	}

	public Integer getBuyCount() {
		return buyCount;
	}

	public void setBuyCount(Integer buyCount) {
		this.buyCount = buyCount;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getPayCash() {
		return payCash;
	}

	public void setPayCash(BigDecimal payCash) {
		this.payCash = payCash;
	}

	public BigDecimal getPayNoncash() {
		return payNoncash;
	}

	public void setPayNoncash(BigDecimal payNoncash) {
		this.payNoncash = payNoncash;
	}

	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public Integer getCatId() {
		return catId;
	}

	public void setCatId(Integer catId) {
		this.catId = catId;
	}
}
