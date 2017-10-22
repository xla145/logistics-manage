package com.logistics.service.coupon.impl;

import cn.assist.easydao.common.*;
import cn.assist.easydao.dao.BaseDao;
import cn.assist.easydao.pojo.PagePojo;
import com.alibaba.fastjson.JSON;
import com.logistics.base.constant.CouponConstant;
import com.logistics.base.constant.MemberStatusConstant;
import com.logistics.base.log.Log;
import com.logistics.base.utils.CommonUtil;
import com.logistics.base.utils.DataObj;
import com.logistics.base.utils.DateUtil;
import com.logistics.service.coupon.CouponMemberService;
import com.logistics.service.coupon.CouponService;
import com.logistics.service.member.MemberService;
import com.logistics.service.model.MemberCouponModel;
import com.logistics.service.sys.log.ISysOperatorLogService;
import com.logistics.service.vo.coupon.Coupon;
import com.logistics.service.vo.member.Member;
import com.logistics.service.vo.member.MemberCoupon;
import com.logistics.service.vo.sys.SysOperatorLog;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * test
 *
 * @author caibin
 */
@Service("CouponMemberService")
public class CouponMemberServiceImpl implements CouponMemberService{
	private final static Integer ADMIN_RELEASE = 2;//后台人员发放
	private static Logger logger = LoggerFactory.getLogger(CouponMemberServiceImpl.class);

	@Autowired
	private MemberService memberService;
	@Autowired
	private CouponService couponService;
	@Autowired
	private ISysOperatorLogService iSysOperatorLogService;
	/**
	 * 分页查询
	 * @param pageNo 查询页码
	 * @param pageSize 每页数量
	 * @return
	 */
	public PagePojo<MemberCouponModel> getCouponMemberPage(Map<String,Object> map, int pageNo, int pageSize){
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("SELECT mc.mcid,c.cid coupon_cid,c.`name` coupon_name,c.type coupon_type,CONCAT_WS(' ~ ',c.start_time,c.end_time) valid_period,c.price,mc.create_time,mc.operator_name,mc.remark,mc.`status`,mc.uid, mc.source ");
		sql.append("FROM coupon c JOIN member_coupon mc ON (c.cid = mc.coupon_cid) LEFT JOIN rule_coupon rc ON(c.cid = rc.coupon_id) LEFT JOIN product_category pc ON(rc.cat_id = pc.cat_id) ");
		sql.append("WHERE 1 = 1 ");
		String createTime = (String) map.get("createTime");
		String mcid = (String) map.get("mcid");
		Integer type  = (Integer) map.get("type");
		String name  = (String) map.get("name");
		Integer source = (Integer) map.get("source");
		if (createTime != null && StringUtils.isNotEmpty(createTime)) {
			String[] times = createTime.split(" - ");
			sql.append("AND mc.create_time BETWEEN ? AND ? ");
			params.add(times[0]);
			params.add(times[1]);
		}
		if (type != null) {
			sql.append("AND c.type =  ? ");
			params.add(type);
		}
		if (source != null) {
			sql.append("AND mc.source_type =  ? ");
			params.add(source);
		}
		if (mcid != null && StringUtils.isNotEmpty(mcid)) {
			sql.append("AND mc.mcid =  ? ");
			params.add(mcid.trim());
		}
		if (name != null && StringUtils.isNotEmpty(name)) {
			sql.append("AND c.name like  ? ");
			params.add(CommonUtil.queryLike(name));
		}
		Sort sort = new Sort("mc.create_time", SqlSort.DESC);
		return BaseDao.dao.queryForListPage(MemberCouponModel.class,sql.toString(),params,sort, pageNo, pageSize);
	}
	/**
	 * 获取代金券信息
	 * @param mcid
	 * @return
	 * */
	public MemberCoupon getCouponMember(String mcid) {
		MemberCoupon couponMember = BaseDao.dao.queryForEntity(MemberCoupon.class, new Conditions("mcid",SqlExpr.EQUAL,mcid));
		return couponMember;
	}

	@Log(operateName = "发放优惠券")
	@Transactional
	@Override
	public DataObj<MemberCouponModel> addCouponMember(String cid,String uid,Integer number,String remark,Integer operatorId,String operatorName) {
		StringBuffer sql = new StringBuffer();
		try {
			Coupon coupon = couponService.getCouponModel(cid);
			String[] uids = uid.split(" , ");
			Integer sumNumber = number*uids.length;
			if (coupon.getRemaining() < sumNumber) {
				return new DataObj<>("发放优惠券出错（原因发放优惠券总张数不能超过剩余张数！）");
			}
			sql.append("UPDATE coupon SET remaining = remaining - ? WHERE cid = ?");
			int result = BaseDao.dao.update(sql.toString(),sumNumber,cid);
			if (result < 0) {
				return new DataObj<>("发放优惠券出错（原因更新优惠券数量出错！）");
			}
			StringBuffer batchSql = new StringBuffer();
			batchSql.append("INSERT INTO member_coupon(mcid,uid,coupon_cid,coupon_name,coupon_type,coupon_msg,start_time,end_time,coupon_amount,operator_id,operator_name,source,source_type,remark,create_time,update_time) VALUES ");
			List<Object> params = new ArrayList<Object>();
			for (int i = 0; i < number; i++){ //插入number条数据
				for (int j = 0; j < uids.length; j++) { //uid数据
					batchSql.append("(?,?,?,?,?,?,?,?,?,?,?,?,?,?,now(),now()),");
					params.add(CommonUtil.getId("CMj"));
					params.add(uids[j]);
					params.add(cid);
					params.add(coupon.getName());
					params.add(coupon.getType());
					params.add(coupon.getMsg());
					params.add(coupon.getStartTime());
					params.add(coupon.getEndTime());
					params.add(coupon.getPrice());
					params.add(operatorId);
					params.add(operatorName);
					params.add("后台发放！");
					params.add(ADMIN_RELEASE);
					params.add(remark);
				}
			}
			int num = BaseDao.dao.insert(batchSql.deleteCharAt(batchSql.length() - 1).toString(),params.toArray());
			if (num < 0) {
				return new DataObj<>("发放优惠券出错！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("发放优惠券出错，原因是"+e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return new DataObj<>("发放优惠券出错！");
		}
		return DataObj.getSuccessData(null);
	}

	/**
	 *
	 * @param map
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@Override
	public PagePojo<Member> memberPage(Map<String, Object> map, int pageNo, int pageSize) {
		String mobile = (String) map.get("mobile");
		Conditions con = new Conditions("mobile",SqlExpr.EQUAL,mobile);
		con.add(new Conditions("status", SqlExpr.EQUAL,MemberStatusConstant.MEMBER_ENABLE_STATUS),SqlJoin.AND);
		return memberService.memberPage(con,pageNo,pageSize);
	}

	/**
	 *
	 * @param map
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@Override
	public PagePojo<Coupon> couponPage(Map<String, Object> map, int pageNo, int pageSize) {
		String name = (String) map.get("name");
		String type = (String) map.get("type");
		Conditions conn = new Conditions("name",SqlExpr.EQUAL,name);
		Date date = new Date();
		String time = DateUtil.formatDate(date,"yyyy-MM-dd HH:mm:ss");
		conn.add(new Conditions("type",SqlExpr.EQUAL,type),SqlJoin.AND);
		conn.add(new Conditions("status",SqlExpr.EQUAL,CouponConstant.COUPON_ENABLE_STATUS),SqlJoin.AND);
		conn.add(new Conditions("use_range",SqlExpr.UNEQUAL, CouponConstant.COUPON_USE_RANGE_CARD),SqlJoin.AND);
		conn.add(new Conditions("end_time",SqlExpr.GT,time),SqlJoin.AND);
		return couponService.getCouponPage(conn,pageNo,pageSize);
	}

}
