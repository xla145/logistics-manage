package com.logistics.service.member;


import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.pojo.PagePojo;
import com.logistics.base.utils.RecordBean;
import com.logistics.service.vo.Member;
import com.logistics.service.vo.Purchase;

import java.util.List;
import java.util.Map;

/**
 * 客户管理
 */
public interface IMemberService {

    /**
     * 获取客户信息
     * @param conn
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PagePojo<Member> getMemberPage(Conditions conn, int pageNo, int pageSize);



    /**
     * 获取客户信息
     * @param conn
     * @return
     */
    public List<Member> getMemberList(Conditions conn);


    /**
     * 获取客户信息
     * @param uid
     * @return
     */
    public Member getMember(Integer uid);

    /**
     * 添加客户信息
     * @param member
     * @return
     */
    public RecordBean<Member> addMember(Member member);

    /**
     * 修改用户信息
     * @param member
     * @return
     */
    public RecordBean<Member> updateMember(Member member);

}
