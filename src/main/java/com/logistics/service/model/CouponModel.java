package com.logistics.service.model;

import com.logistics.service.vo.coupon.Coupon;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2017/7/24/024.
 * 优惠券业务模型
 */
public class CouponModel extends Coupon {
    private String ruleName;//规则名称
    private String validPeriod;//有效期
    private Integer number;// 代金券的数量
    private String typeName;//类型名称
    private Integer catId;// 产品类型
    private String catName;// 产品类型名称
    private BigDecimal condition;// 消费金额
    private String pid; //产品编号

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getValidPeriod() {
        return validPeriod;
    }

    public void setValidPeriod(String validPeriod) {
        this.validPeriod = validPeriod;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getCatId() {
        return catId;
    }

    public void setCatId(Integer catId) {
        this.catId = catId;
    }

    public BigDecimal getCondition() {
        return condition;
    }

    public void setCondition(BigDecimal condition) {
        this.condition = condition;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }
}

