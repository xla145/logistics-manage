package com.logistics.service.member.impl;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.common.Sort;
import cn.assist.easydao.common.SqlExpr;
import cn.assist.easydao.common.SqlSort;
import cn.assist.easydao.dao.BaseDao;
import cn.assist.easydao.pojo.PagePojo;
import com.logistics.base.utils.RecordBean;
import com.logistics.service.member.MemberService;
import com.logistics.service.model.MemberCouponModel;
import com.logistics.service.model.MemberModel;
import com.logistics.service.vo.member.Member;
import com.logistics.service.vo.member.MemberAddr;
import com.logistics.service.vo.member.MemberFootprints;
import com.logistics.service.vo.member.MemberFootprintsImages;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MemberServiceImpl implements MemberService {


	@Override
	public PagePojo<Member> memberPage(Map<String, Object> map, int pageNo, int pageSize) {
		String mobile = (String) map.get("mobile");
		Conditions conn = new Conditions("mobile",SqlExpr.EQUAL,mobile);
		Sort sort = new Sort("create_time",SqlSort.DESC);
		return BaseDao.dao.queryForListPage(Member.class,conn,sort, pageNo, pageSize);	
	}

	@Override
	public PagePojo<MemberCouponModel> memberCouponPage(Conditions conn, int pageNo, int pageSize) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("SELECT c.cid coupon_cid,c.type coupon_type,c.`name` coupon_name,CONCAT_WS('~',c.start_time,c.end_time) valid_period,mc.mcid,mc.create_time,mc.status,mc.source ");
		sql.append("FROM coupon c JOIN  member_coupon mc ON(c.cid = mc.coupon_cid)");
		sql.append("WHERE 1 = 1 ");
		Sort sort = new Sort("mc.create_time",SqlSort.DESC);
		if(conn != null && StringUtils.isNotBlank(conn.getConnSql())){
			sql.append(" and " + conn.getConnSql());
			params = conn.getConnParams();
		}
		return BaseDao.dao.queryForListPage(MemberCouponModel.class, sql.toString(), params, sort, pageNo, pageSize);
	}

	@Override
	public List<MemberAddr> memberAddrList(Conditions conn) {
		return BaseDao.dao.queryForListEntity(MemberAddr.class,conn);
	}

	@Override
	public Member getMember(Integer uid) {
		Conditions conn = new Conditions("uid",SqlExpr.EQUAL,uid);
		return BaseDao.dao.queryForEntity(Member.class,conn);
	}

	@Override
	public PagePojo<Member> memberPage(Conditions con, int pageNo, int pageSize) {
		Sort sort = new Sort("create_time",SqlSort.DESC);
		return BaseDao.dao.queryForListPage(Member.class,con,sort,pageNo,pageSize);
	}

	@Override
	public Member getMemberByMobile(String mobile) {
		return BaseDao.dao.queryForEntity(Member.class,new Conditions("mobile",SqlExpr.EQUAL,mobile));
	}

	@Override
	public RecordBean<String> updateAvatar(Integer uid, String avatar) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE member SET avatar = ? WHERE uid =?");
		int result = BaseDao.dao.update(sql.toString(),avatar,uid);
		if (result != 1) {
			return RecordBean.error("修改头像失败！");
		}
		return RecordBean.success(avatar);
	}

}
