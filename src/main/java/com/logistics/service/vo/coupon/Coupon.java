package com.logistics.service.vo.coupon;

import cn.assist.easydao.annotation.Id;
import cn.assist.easydao.annotation.Temporary;
import cn.assist.easydao.pojo.BasePojo;

import java.math.BigDecimal;
import java.util.Date;

public class Coupon extends BasePojo {
	@Temporary
	private static final long serialVersionUID = 1L;
	@Id
	private String cid;//优惠券编号
	private String name;//优惠券名称
	private Integer type;//优惠券类型
	private String info;//优惠券描述
	private Date startTime;//有效结束时间
	private Date endTime;//有效开始时间
	private String msg;//优惠券使用提示信息
	private BigDecimal price;//优惠券面额
	private Integer status;//状态 0：未使用 10：使用
	private Integer stock;//库存
	private Integer remaining;//剩余数
	private Date createTime;//创建时间
	private Date updateTime;//更新时间
	private String remark;//备注
	private Integer useRange;//使用范围

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Integer getRemaining() {
		return remaining;
	}

	public void setRemaining(Integer remaining) {
		this.remaining = remaining;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getUseRange() {
		return useRange;
	}

	public void setUseRange(Integer useRange) {
		this.useRange = useRange;
	}
}

