package com.logistics.service.model;

import cn.assist.easydao.annotation.Temporary;
import com.logistics.service.pay.vo.PayInfo;
import com.logistics.service.pay.vo.PayOrderDetail;
import com.logistics.service.vo.order.Order;

import java.math.BigDecimal;
import java.util.List;

/**
 * 
* @ClassName: ProductOrderActivityModel
* @Description: TODO(产品活动的业务模型)
* @author Administrator
* @date 2017年7月19日
*
 */
public class OrderGoodsModel extends Order{
	@Temporary
	private static final long serialVersionUID = 1L;

	private String adresMobile;//收货人电话
	private String adresName;//收货人
	private String adresAddr;//收货地址
	private String message;//留言
	private Integer orderGoodsStatus;//订单状态
	private String orderStatus;//订单状态对应的名字
	private String courierCompany;//快递公司
	private String courierNumber;//快递单号
	private BigDecimal freight;//运费
	private boolean isShip;//是否发货
	private String remark;//备注
	private List<PayInfo> payInfos;//支付信息
	private String supplierName;//商家
	private BigDecimal totalOriginalPrice;//进货总价

	public BigDecimal getFreight() {
		return freight;
	}

	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}

	public String getAdresMobile() {
		return adresMobile;
	}

	public void setAdresMobile(String adresMobile) {
		this.adresMobile = adresMobile;
	}

	public String getAdresName() {
		return adresName;
	}

	public void setAdresName(String adresName) {
		this.adresName = adresName;
	}

	public String getAdresAddr() {
		return adresAddr;
	}

	public void setAdresAddr(String adresAddr) {
		this.adresAddr = adresAddr;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getOrderGoodsStatus() {
		return orderGoodsStatus;
	}

	public void setOrderGoodsStatus(Integer orderGoodsStatus) {
		this.orderGoodsStatus = orderGoodsStatus;
	}

	public String getCourierCompany() {
		return courierCompany;
	}

	public void setCourierCompany(String courierCompany) {
		this.courierCompany = courierCompany;
	}

	public String getCourierNumber() {
		return courierNumber;
	}

	public void setCourierNumber(String courierNumber) {
		this.courierNumber = courierNumber;
	}

	public boolean isShip() {
		return isShip;
	}

	public void setShip(boolean ship) {
		isShip = ship;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<PayInfo> getPayInfos() {
		return payInfos;
	}

	public void setPayInfos(List<PayInfo> payInfos) {
		this.payInfos = payInfos;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public BigDecimal getTotalOriginalPrice() {
		return totalOriginalPrice;
	}

	public void setTotalOriginalPrice(BigDecimal totalOriginalPrice) {
		this.totalOriginalPrice = totalOriginalPrice;
	}


}
