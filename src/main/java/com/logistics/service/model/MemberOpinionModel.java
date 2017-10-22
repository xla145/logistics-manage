package com.logistics.service.model;

import com.logistics.service.vo.member.MemberOpinion;

/**
 * Created by Administrator on 2017/7/27/027.
 */
public class MemberOpinionModel extends MemberOpinion {
    private String avatar;//头像
    private String mobile;

    @Override
    public String getAvatar() {
        return avatar;
    }

    @Override
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String getMobile() {
        return mobile;
    }

    @Override
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
