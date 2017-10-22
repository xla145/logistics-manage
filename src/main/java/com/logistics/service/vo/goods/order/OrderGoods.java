package com.logistics.service.vo.goods.order;
import cn.assist.easydao.annotation.Id;
import cn.assist.easydao.annotation.Temporary;
import cn.assist.easydao.pojo.BasePojo;

import java.math.BigDecimal;

/**
 * 
 * 商品信息
 *
 * @author caixb
 */
public class OrderGoods extends BasePojo{

	@Temporary
	private static final long serialVersionUID = 1L;
	
	@Id
	private String orderId;				//订单id
	private String adresName;			//收件人姓名
	private String adresMobile;			//收件人手机
	private String adresAddr;			//收件人地址
	private String message;				//买家留言
	private Integer orderGoodsStatus;	//商品订单状态 0：初始状态  5：待发货  10：待收货  15：已完成  20：已退款 25：已取消
	private String courierCompany;		//物流公司名
	private String courierNumber;		//物流单号
	private String remark;				//备注
	private BigDecimal freight; //运费
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getAdresName() {
		return adresName;
	}
	public void setAdresName(String adresName) {
		this.adresName = adresName;
	}
	public String getAdresMobile() {
		return adresMobile;
	}
	public void setAdresMobile(String adresMobile) {
		this.adresMobile = adresMobile;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getFreight() {
		return freight;
	}

	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}
}
