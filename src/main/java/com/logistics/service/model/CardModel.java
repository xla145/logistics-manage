package com.logistics.service.model;

import com.logistics.service.vo.card.Card;
import com.logistics.service.vo.coupon.Coupon;

import java.util.List;

/**
 * Created by Administrator on 2017/9/14/014.
 */
public class CardModel extends Card {

    private String validPeriod;//有效期

    private List<CouponModel> couponList; // 优惠券列表

    public List<CouponModel> getCouponList() {
        return couponList;
    }

    public void setCouponList(List<CouponModel> couponList) {
        this.couponList = couponList;
    }

    public String getValidPeriod() {
        return validPeriod;
    }

    public void setValidPeriod(String validPeriod) {
        this.validPeriod = validPeriod;
    }
}
