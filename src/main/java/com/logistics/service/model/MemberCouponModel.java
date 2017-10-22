package com.logistics.service.model;

import com.logistics.service.vo.member.MemberCoupon;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2017/7/25/025.
 */
public class MemberCouponModel extends MemberCoupon {
    private Integer number;
    private Integer couponType;
    private String validPeriod;
    private BigDecimal price;
    private Integer type;
    private Integer payStatus;//  0:支付未完成， 10:支付成功, 20:支付失败
    private MemberCoupon memberCoupon;
    private String catName;// 产品类型名称
    private Integer pid; // 产品编号
    private BigDecimal condition;// 消费金额
    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getCouponType() {
        return couponType;
    }

    public void setCouponType(Integer couponType) {
        this.couponType = couponType;
    }

    public String getValidPeriod() {
        return validPeriod;
    }

    public void setValidPeriod(String validPeriod) {
        this.validPeriod = validPeriod;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public MemberCoupon getMemberCoupon() {
        return memberCoupon;
    }

    public void setMemberCoupon(MemberCoupon memberCoupon) {
        this.memberCoupon = memberCoupon;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public BigDecimal getCondition() {
        return condition;
    }

    public void setCondition(BigDecimal condition) {
        this.condition = condition;
    }
}
