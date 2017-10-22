package com.logistics.service.member.impl;

import cn.assist.easydao.common.Sort;
import cn.assist.easydao.common.SqlSort;
import cn.assist.easydao.dao.BaseDao;
import cn.assist.easydao.pojo.PagePojo;
import com.logistics.base.utils.CommonUtil;
import com.logistics.service.member.MemberOpinionService;
import com.logistics.service.model.MemberOpinionModel;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("MemberOpinionService")
public class MemberOpinionImpl implements MemberOpinionService {

	@Override
	public PagePojo<MemberOpinionModel> memberOpinionPage(Map<String, Object> map, int pageNo, int pageSize) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("SELECT mo.id,m.uid,mo.create_time,mo.feedback,m.mobile ");
		sql.append("FROM member_opinion mo JOIN member m ON (mo.uid = m.uid) ");
		sql.append("WHERE 1 = 1 ");
		String createTime = (String) map.get("createTime");//反馈时间
		Integer uid  = (Integer) map.get("uid");
		String mobile = (String) map.get("mobile");
		if (createTime != null && StringUtils.isNotEmpty(createTime)) {
			String[] time = createTime.split(" - ");
			sql.append("AND mo.create_time BETWEEN ? AND ? ");
			params.add(time[0]);
			params.add(time[1]);
		}
		if (uid != null) {
			sql.append("AND m.uid =  ? ");
			params.add(uid);
		}
		if (mobile != null && StringUtils.isNotEmpty(mobile)) {
			sql.append("AND m.mobile like  ? ");
			params.add(CommonUtil.queryLike(mobile));
		}
		Sort sort = new Sort("mo.create_time",SqlSort.DESC);
		return BaseDao.dao.queryForListPage(MemberOpinionModel.class, sql.toString(), params, sort, pageNo, pageSize);
	}

	@Override
	public MemberOpinionModel getMemberOpinion(Integer id) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT mo.id,m.uid,mo.create_time,mo.feedback,m.mobile,m.avatar ");
		sql.append("FROM member_opinion mo JOIN member m ON (mo.uid = m.uid) ");
		sql.append("WHERE mo.id = ? ");
		return BaseDao.dao.queryForEntity(MemberOpinionModel.class, sql.toString(), id);
	}

}
