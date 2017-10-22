package com.logistics.service.coupon.impl;

import cn.assist.easydao.common.*;
import cn.assist.easydao.dao.BaseDao;
import cn.assist.easydao.pojo.PagePojo;
import com.alibaba.fastjson.JSON;
import com.logistics.base.constant.CouponConstant;
import com.logistics.base.constant.CouponTypeConstant;
import com.logistics.base.utils.CommonUtil;
import com.logistics.base.utils.DataObj;
import com.logistics.base.utils.DateUtil;
import com.logistics.service.coupon.CouponService;
import com.logistics.service.model.CouponModel;
import com.logistics.service.vo.coupon.Coupon;
import com.logistics.service.vo.coupon.CouponRule;
import com.logistics.service.vo.coupon.CouponType;
import com.logistics.service.vo.rule.RuleCoupon;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 *
 */
@Service("CouponService")
public class CouponServiceImpl implements CouponService{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	/**
	 * 分页查询
	 * @param pageNo 查询页码
	 * @param pageSize 每页数量
	 * @return
	 */
	public PagePojo<CouponModel> getCouponPage(Map<String,Object> map, int pageNo, int pageSize){
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("SELECT c.cid,c.`name`,c.create_time,c.type,CONCAT_WS(' ~ ',c.start_time,c.end_time) valid_period,c.price,c.msg,c.`status`,c.remaining,c.use_range,rc.condition,rc.pid,pc.name cat_name " );
		sql.append("FROM coupon c LEFT JOIN rule_coupon rc ON(c.cid = rc.coupon_id) LEFT JOIN product_category pc ON(rc.cat_id = pc.cat_id) ");
		sql.append("WHERE 1 = 1 AND c.status != -1 ");
		String createTime = (String) map.get("createTime");
		Integer status  = (Integer) map.get("status");
		String name  = (String) map.get("name");
		Integer useRange  = (Integer) map.get("useRange");
		if (createTime != null && StringUtils.isNotEmpty(createTime)) {
			sql.append("AND c.create_time BETWEEN ? AND  ? ");
			String[] time = createTime.split(" - ");
			params.add(time[0]);
			params.add(time[1]);
		}
		if (status != null) {
			sql.append("AND c.status =  ? ");
			params.add(status);
		}
		if (useRange != null) {
			sql.append("AND c.use_range =  ? ");
			params.add(useRange);
		}
		if (name != null && StringUtils.isNotEmpty(name)) {
			sql.append("AND c.name like  ? ");
			params.add(CommonUtil.queryLike(name));
		}
		Sort sort = new Sort("c.create_time", SqlSort.DESC);
		return BaseDao.dao.queryForListPage(CouponModel.class,sql.toString(),params,sort, pageNo, pageSize);
	}
	
	/**
	 * 添加 或修改代金券信息
	 * 
	 * @param couponModel 代金券信息
	 * @return
	 */
	@Transactional
	public DataObj<CouponModel> addCouponModel(CouponModel couponModel) {
		logger.info("代金券信息"+JSON.toJSON(couponModel));
		/**
		 * 处理添加代金券逻辑
		 * */
		String cid = CommonUtil.getId("YHJ");//生成优惠劵编号
		Coupon coupon = new Coupon();
		coupon.setCid(cid);
		coupon.setType(couponModel.getType());
		coupon.setName(couponModel.getName());
		coupon.setMsg(couponModel.getMsg());
		coupon.setPrice(couponModel.getPrice());
		coupon.setUseRange(couponModel.getUseRange());
		coupon.setStatus(10);
		if (couponModel.getUseRange() != 1) {
			String[] time = couponModel.getValidPeriod().split(" - ");
			coupon.setStartTime(DateUtil.StringToDate(time[0],"yyyy-MM-dd HH:mm:ss"));
			coupon.setEndTime(DateUtil.StringToDate(time[1],"yyyy-MM-dd HH:mm:ss"));
			coupon.setStock(couponModel.getStock());
			coupon.setRemaining(couponModel.getStock());
		}
		coupon.setCreateTime(new Date());
		coupon.setUpdateTime(new Date());
		try {
			int result = BaseDao.dao.insert(coupon);
			if (result < 0) {
				logger.error("添加优惠券失败!");
				return new DataObj<>("添加优惠券失败！");
			}
			// 非通用劵添加劵规则
			if (couponModel.getType() != CouponTypeConstant.GENERAL.getId()) {
				RuleCoupon ruleCoupon = new RuleCoupon();
				ruleCoupon.setCatId(couponModel.getCatId());
				ruleCoupon.setCouponId(cid);
				ruleCoupon.setCondition(couponModel.getCondition());
				ruleCoupon.setPid(couponModel.getPid());
				ruleCoupon.setCreateTime(new Date());
				ruleCoupon.setUpdateTime(new Date());
				result = BaseDao.dao.insert(ruleCoupon);
				if (result < 0) {
					return new DataObj<>("添加劵规则失败！");
				}
			}
		}catch (Exception e){
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			logger.error("添加优惠券异常!"+e.getMessage());
			return new DataObj<>("添加优惠券异常！");
		}
		return DataObj.getSuccessData(couponModel);
	}
	/**
	 * 获取代金券信息
	 * @param cid
	 * @return
	 * */
	public Coupon getCouponModel(String cid) {
		Coupon coupon = BaseDao.dao.queryForEntity(Coupon.class, new Conditions("cid",SqlExpr.EQUAL,cid));
		return coupon;
	}
	/**
	 * 批量删除代金券信息
	 * */
	@Transactional
	public DataObj<String> delCoupon(String[] ids) {
		try {
			Conditions conn = new Conditions("cid", SqlExpr.IN, ids);
			List<Coupon> couponList = BaseDao.dao.queryForListEntity(Coupon.class, conn);
			for (Coupon coupon:couponList) {
				if(coupon.getStatus() == 1) {
					return new DataObj<>("已发放出去的优惠券无法删除，请重新选择");
				}
			}
			StringBuffer sql = new StringBuffer();
			sql.append("update coupon set status = -1, update_time = now() where cid in (");
			sql.append(CommonUtil.arrayToSqlIn(ids));
			sql.append(")");
			int result= BaseDao.dao.delete(sql.toString());
			if (result < 1) {
				return new DataObj<>("已发放出去的优惠券无法删除，请重新选择");
			}
		} catch (Exception e){
			e.printStackTrace();
			logger.error("发放优惠券异常!");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return new DataObj<>("发放优惠券异常！");
		}
		return DataObj.getSuccessData("成功!");
	}
	/**
	 * 获取优惠券信息
	 * @return
	 * */
	public List<CouponType> getCouponType() {
		return BaseDao.dao.queryForListEntity(CouponType.class, "SELECT * FROM coupon_type");
	}

	@Override
	public List<CouponRule> getCouponRule() {
		return BaseDao.dao.queryForListEntity(CouponRule.class, "SELECT * FROM coupon_rule");
	}

	@Override
	public List<Coupon> getCouponByUseRange(Integer useRange) {
		Date date = new Date();
		String time = DateUtil.formatDate(date,"yyyy-MM-dd HH:mm:ss");
		Conditions conn = new Conditions("use_range",SqlExpr.EQUAL,useRange);
		conn.add(new Conditions("status",SqlExpr.EQUAL, CouponConstant.COUPON_ENABLE_STATUS), SqlJoin.AND);
		return BaseDao.dao.queryForListEntity(Coupon.class,conn);
	}

	@Override
	public List<CouponModel> getCouponListByCardId(Integer cardId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT c.cid,c.name,c.info,c.type,c.msg,c.price,c.stock,c.remaining,c.status,c.remark,c.end_time,c.start_time,c.create_time,c.update_time,c.use_range,COUNT(c.cid) number ");
		sql.append("FROM card_coupon cc JOIN coupon c ON(c.cid = cc.coupon_id) ");
		sql.append("WHERE cc.card_id = ? ");
		sql.append("GROUP BY c.cid");
		return BaseDao.dao.queryForListEntity(CouponModel.class,sql.toString(),cardId);
	}

	/**
	 * 获取优惠券信息
	 * @return
	 * */
	public PagePojo<Coupon> getCouponPage(Conditions conn, int pageNo, int pageSize) {
		Sort sort = new Sort("create_time", SqlSort.DESC);
		return BaseDao.dao.queryForListPage(Coupon.class, conn, sort, pageNo, pageSize);
	}	
}
