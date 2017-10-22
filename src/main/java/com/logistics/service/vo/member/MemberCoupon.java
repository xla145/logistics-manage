package com.logistics.service.vo.member;

import cn.assist.easydao.annotation.Id;
import cn.assist.easydao.annotation.Temporary;
import cn.assist.easydao.pojo.BasePojo;
import com.logistics.base.utils.BigDecimalUtils;

import java.math.BigDecimal;
import java.util.Date;
/*
 * 发放代金券
 * */
public class MemberCoupon extends BasePojo {
	@Temporary
	private static final long serialVersionUID = 1L;
	@Id
	private String mcid;//
	private Integer uid;//用户编号
	private String couponCid;//代金券编号
	private String couponName;//优惠券名字
	private String couponInfo;//优惠券描述
	private Integer couponType;//优惠券类型
	private Integer couponMsg;//优惠券tips
	private BigDecimal couponAmount;//优惠券面额
	private Date startTime;//优惠券有效开始时间
	private Date endTime;//优惠券有效结束时间
	private Integer operatorId;//发放人
	private String operatorName;//发放人名字
	private Integer status;//使用状态
	private Date createTime;//创建时间
	private Date updateTime;//更新时间
	private Date useTime;//使用时间
	private String useRemark;//使用备注
	private String source;//来源
	private Integer sourceType;//来源类型 1：自动发放 2：后台人员发放
	private String remark;//发放原因

	public String getMcid() {
		return mcid;
	}

	public void setMcid(String mcid) {
		this.mcid = mcid;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getCouponCid() {
		return couponCid;
	}

	public void setCouponCid(String couponCid) {
		this.couponCid = couponCid;
	}

	public String getCouponName() {
		return couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}

	public String getCouponInfo() {
		return couponInfo;
	}

	public void setCouponInfo(String couponInfo) {
		this.couponInfo = couponInfo;
	}

	public Integer getCouponType() {
		return couponType;
	}

	public void setCouponType(Integer couponType) {
		this.couponType = couponType;
	}

	public Integer getCouponMsg() {
		return couponMsg;
	}

	public void setCouponMsg(Integer couponMsg) {
		this.couponMsg = couponMsg;
	}

	public BigDecimal getCouponAmount() {
		return couponAmount;
	}

	public void setCouponAmount(BigDecimal couponAmount) {
		this.couponAmount = couponAmount;
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

	public Integer getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public Date getUseTime() {
		return useTime;
	}

	public void setUseTime(Date useTime) {
		this.useTime = useTime;
	}

	public String getUseRemark() {
		return useRemark;
	}

	public void setUseRemark(String useRemark) {
		this.useRemark = useRemark;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Integer getSourceType() {
		return sourceType;
	}

	public void setSourceType(Integer sourceType) {
		this.sourceType = sourceType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}

