package com.logistics.service.activtiy.impl;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.common.Sort;
import cn.assist.easydao.common.SqlExpr;
import cn.assist.easydao.common.SqlSort;
import cn.assist.easydao.dao.BaseDao;
import cn.assist.easydao.pojo.PagePojo;

import com.logistics.base.cache.MCache;
import com.logistics.base.constant.OrderActivityStatusConstant;
import com.logistics.base.utils.CommonUtil;
import com.logistics.base.utils.DataObj;
import com.logistics.service.model.OrderActivityModel;
import com.logistics.service.activtiy.IOrderActivityService;
import com.logistics.service.pay.vo.PayOrderDetail;
import com.logistics.service.vo.activity.OrderActivityInvite;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * 
* @ClassName: ProductOrderActivityServiceImpl
* @Description: TODO(好玩活动订单 业务实现)
* @author Administrator
* @date 2017年7月19日
*
 */
@Service("OrderActivityService")
public class OrderActivityServiceImpl implements IOrderActivityService {

	@MCache(expire = 300)
	@Override
	public PagePojo<OrderActivityModel> orderActivityPage(Map<String, Object> map, int pageNo,
			int pageSize) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("SELECT o.order_id,o.order_name,p.supplier_name,o.create_time,o.buy_mobile,o.product_price,o.pay_cash,o.pay_noncash,oa.order_activity_status,oa.invite_count ");
		sql.append("FROM `order` o JOIN order_activity oa ON (o.order_id = oa.order_id) JOIN product p ON(o.pid = p.pid) ");
		sql.append("WHERE 1 = 1 ");
		String mobile = (String) map.get("mobile");
		Integer supplierId = (Integer) map.get("supplierId");
		String placeOrderStartTime = (String) map.get("startTime");
		String placeOrderEndTime = (String) map.get("endTime");
		String activityTitle = (String) map.get("title");
		Integer status  = (Integer) map.get("status");
		if (placeOrderStartTime != null && StringUtils.isNotEmpty(placeOrderStartTime) && placeOrderEndTime == null) {
			placeOrderStartTime = placeOrderStartTime + " 00:00:00";
			sql.append("AND o.create_time >= ? ");
			params.add(placeOrderStartTime);
		}
		if (placeOrderStartTime == null && placeOrderEndTime != null && StringUtils.isNotEmpty(placeOrderEndTime)) {
			placeOrderEndTime = placeOrderEndTime + " 23:59:59";
			sql.append("AND o.create_time <= ? ");
			params.add(placeOrderEndTime);
		}
		if (placeOrderStartTime != null && StringUtils.isNotEmpty(placeOrderStartTime) && placeOrderEndTime != null && StringUtils.isNotEmpty(placeOrderEndTime)) {
			placeOrderStartTime = placeOrderStartTime + " 00:00:00";
			placeOrderEndTime = placeOrderEndTime + " 23:59:59";
			sql.append("AND o.create_time BETWEEN ? AND ? ");
			params.add(placeOrderStartTime);
			params.add(placeOrderEndTime);
		}
		if (status != null) {
			sql.append("AND oa.order_activity_status =  ? ");
			params.add(status);
		}
		if (supplierId != null) {
			sql.append("AND p.supplier_id =  ? ");
			params.add(supplierId);
		}
		if (activityTitle != null && StringUtils.isNotEmpty(activityTitle)) {
			sql.append("AND o.order_name like ? ");
			params.add(CommonUtil.queryLike(activityTitle.trim()));
		}
		if (mobile != null && StringUtils.isNotEmpty(mobile)) {
			sql.append("AND buy_mobile = ? ");
			params.add(mobile.trim());
		}
		Sort sort = new Sort("o.create_time",SqlSort.DESC);
		return BaseDao.dao.queryForListPage(OrderActivityModel.class, sql.toString(), params, sort, pageNo, pageSize);	
	}
	
	@Override
	public DataObj<String> delOrderActivity(String[] ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataObj<OrderActivityModel> updateOrderActivity(OrderActivityModel productOrderActivity) {
		List<OrderActivityInvite> memderList = productOrderActivity.getMemberList();
		String orderId = productOrderActivity.getOrderId();
		BaseDao.dao.update("DELETE FROM activity_incidental_member WHERE order_id = ?",orderId);
		StringBuffer sql = new StringBuffer();
		List<Object> parmas =new ArrayList<Object>();
		sql.append("INSERT INTO activity_incidental_member(order_id,name,phone,update_time,create_time) VALUES");
		for(OrderActivityInvite member:memderList){
			sql.append("(?,?,?,now(),now()),");
			parmas.add(orderId);
			parmas.add(CommonUtil.nullToBlank(member.getName()));
			parmas.add(member.getMobile());
		}
		int result = BaseDao.dao.insert(sql.deleteCharAt(sql.length()-1).toString(), parmas.toArray());
		if (result > 1) {
			return DataObj.getSuccessData(productOrderActivity);
		}
		return new DataObj<>("修改携带人数信息失败！");
	}
	/**
	 * 获取活动订单相关的信息
	 * @param orderId
	 * @return
	 * */
	@Override
	public OrderActivityModel getOrderActivity(String orderId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT p.supplier_name,o.order_id,o.order_name,o.product_price,o.buy_mobile,o.create_time,oa.activity_addr,o.is_sms_notice,oa.order_activity_status,CONCAT_WS('~',oa.activity_start_time,activity_end_time) activity_time ");
		sql.append("FROM `order` o JOIN order_activity oa ON(o.order_id = oa.order_id) JOIN product p ON(o.pid = p.pid) ");
		sql.append("WHERE o.order_id = ?");
		OrderActivityModel orderActivityModel = BaseDao.dao.queryForEntity(OrderActivityModel.class, sql.toString(), orderId);
		orderActivityModel.setOrderStatus(OrderActivityStatusConstant.getStatusName(orderActivityModel.getOrderActivityStatus()));
		Conditions con = new Conditions("order_id",SqlExpr.EQUAL,orderId);
		List<OrderActivityInvite> memderList = BaseDao.dao.queryForListEntity(OrderActivityInvite.class, con);
		orderActivityModel.setMemberList(memderList);
		List<PayOrderDetail> detailsList = BaseDao.dao.queryForListEntity(PayOrderDetail.class,new Conditions("order_id",SqlExpr.EQUAL,orderId));
		orderActivityModel.setDetailList(detailsList);
		return orderActivityModel;
	}
	/**
	 * 退款操作 
	 */
	@Override
	public DataObj<OrderActivityModel> refundsOrderActivity(OrderActivityModel productOrderActivity) {
		
		return null;
	}

	@Override
	public List<OrderActivityModel> orderActivityList(Map<String, Object> map) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("SELECT o.order_id,o.order_name,o.supplier_name,o.create_time,o.buy_mobile,o.product_price,oa.order_activity_status ");
		sql.append("FROM `order` o JOIN order_activity oa ON (o.order_id = oa.order_id) JOIN product p ON(o.pid = p.pid) ");
		sql.append("WHERE 1 = 1 ");
		String mobile = (String) map.get("mobile");
		String supplierId = (String) map.get("supplierId");
		String placeOrderStartTime = (String) map.get("startTime");
		String placeOrderEndTime = (String) map.get("endTime");
		String activityTitle = (String) map.get("title");
		Integer status  = (Integer) map.get("status");
		if (placeOrderStartTime != null && StringUtils.isNotEmpty(placeOrderStartTime) && placeOrderEndTime == null) {
			placeOrderStartTime = placeOrderStartTime + " 00:00:00";
			sql.append("AND o.create_time >= ? ");
			params.add(placeOrderStartTime);
		}
		if (placeOrderStartTime == null && placeOrderEndTime != null && StringUtils.isNotEmpty(placeOrderEndTime)) {
			placeOrderEndTime = placeOrderEndTime + " 23:59:59";
			sql.append("AND o.create_time <= ? ");
			params.add(placeOrderEndTime);
		}
		if (placeOrderStartTime != null && StringUtils.isNotEmpty(placeOrderStartTime) && placeOrderEndTime != null && StringUtils.isNotEmpty(placeOrderEndTime)) {
			placeOrderStartTime = placeOrderStartTime + " 00:00:00";
			placeOrderEndTime = placeOrderEndTime + " 23:59:59";
			sql.append("AND o.create_time BETWEEN ? AND ? ");
			params.add(placeOrderStartTime);
			params.add(placeOrderEndTime);
		}
		if (status != null) {
			sql.append("AND oa.order_activity_status =  ? ");
			params.add(status);
		}
		if (supplierId != null) {
			sql.append("AND p.supplier_id =  ? ");
			params.add(status);
		}
		if (activityTitle != null && StringUtils.isNotEmpty(activityTitle)) {
			sql.append("AND o.order_name like ? ");
			params.add(CommonUtil.queryLike(activityTitle.trim()));
		}
		if (mobile != null && StringUtils.isNotEmpty(mobile)) {
			sql.append("AND buy_mobile = ? ");
			params.add(mobile.trim());
		}
		System.out.println(sql.toString());
		return BaseDao.dao.queryForListEntity(OrderActivityModel.class, sql.toString(), params);
	}

}
