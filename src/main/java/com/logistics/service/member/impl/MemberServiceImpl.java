package com.logistics.service.member.impl;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.common.Sort;
import cn.assist.easydao.common.SqlSort;
import cn.assist.easydao.dao.BaseDao;
import cn.assist.easydao.pojo.PagePojo;
import com.logistics.base.utils.RecordBean;
import com.logistics.service.member.IMemberService;
import com.logistics.service.vo.Member;
import org.springframework.stereotype.Service;
import sun.security.util.Cache;

import java.util.Date;
import java.util.List;

/**
 * 客户信息
 */
@Service("IMemberService")
public class MemberServiceImpl implements IMemberService {

    /**
     * 获取客户信息
     * @param conn
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public PagePojo<Member> getMemberPage(Conditions conn, int pageNo, int pageSize) {
        Sort sort = new Sort("create_time", SqlSort.DESC);
        return BaseDao.dao.queryForListPage(Member.class,conn,sort,pageNo,pageSize);
    }

    /**
     * 获取客户信息
     * @param conn
     * @return
     */
    @Override
    public List<Member> getMemberList(Conditions conn) {
        return BaseDao.dao.queryForListEntity(Member.class,conn);
    }

    /**
     * 获取客户信息
     * @param uid
     * @return
     */
    @Override
    public Member getMember(Integer uid) {
        return BaseDao.dao.queryForEntity(Member.class,uid);
    }

    /**
     * 添加客户信息
     * @param member
     * @return
     */
    @Override
    public RecordBean<Member> addMember(Member member) {
        member.setCreateTime(new Date());
        member.setUpdateTime(new Date());
        int result = BaseDao.dao.insert(member);
        if (result > 0) {
            return RecordBean.success("添加客户成功！");
        }
        return RecordBean.error("添加客户失败！");
    }

    @Override
    public RecordBean<Member> updateMember(Member member) {
        member.setUpdateTime(new Date());
        int result = BaseDao.dao.update(member);
        if (result > 0) {
            return RecordBean.success("修改客户成功！");
        }
        return RecordBean.error("修改客户失败！");
    }
}
