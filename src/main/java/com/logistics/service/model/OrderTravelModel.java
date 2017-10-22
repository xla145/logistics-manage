package com.logistics.service.model;

import com.logistics.service.vo.travel.OrderTravel;

import java.lang.ref.PhantomReference;

/**
 * Created by Administrator on 2017/8/31/031.
 */
public class OrderTravelModel extends OrderTravel {

    private Integer buyUid;//购买用户uid
    private String buyName;//购买用户
    private String buyMobile;//下单用户手机号

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
}
