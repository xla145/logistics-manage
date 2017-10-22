package com.logistics.service.goods.impl;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.common.Sort;
import cn.assist.easydao.common.SqlExpr;
import cn.assist.easydao.common.SqlSort;
import cn.assist.easydao.dao.BaseDao;
import cn.assist.easydao.pojo.PagePojo;

import com.alibaba.fastjson.JSON;
import com.logistics.base.cache.MCache;
import com.logistics.base.constant.OrderGoodsStatusConstant;
import com.logistics.base.utils.CommonUtil;
import com.logistics.base.utils.DataObj;
import com.logistics.service.goods.OrderGoodsService;
import com.logistics.service.model.OrderGoodsModel;

import com.logistics.service.pay.api.IPayOrderService;
import com.logistics.service.pay.vo.PayInfo;
import com.logistics.service.pay.vo.PayOrderDetail;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 
* @ClassName: OrderGoodsServiceImpl
* @Description: TODO(这里用一句话描述这个类的作用)
* @author Administrator
* @date 2017年7月21日
*
 */
@Service("OrderGoodsService")
public class OrderGoodsServiceImpl implements OrderGoodsService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private IPayOrderService iPayOrderService;

    @MCache(expire = 30)
	@Override
	public PagePojo<OrderGoodsModel> orderGoodsPage(Map<String, Object> map, int pageNo, int pageSize) {
    	StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("SELECT p.supplier_name,o.order_id,o.order_name,o.product_name,o.pid,o.product_price,o.buy_mobile,o.create_time,o.product_original_price,(o.product_original_price * o.buy_count) total_original_price,og.adres_name,og.adres_addr,og.adres_mobile,o.buy_count,o.total_amount,o.pay_cash,o.pay_noncash,og.courier_company,og.courier_number,og.order_goods_status ");
		sql.append("FROM `order` o JOIN order_goods og ON(o.order_id = og.order_id) JOIN product p ON(o.pid = p.pid) ");
		sql.append("WHERE 1 = 1 ");
		String createStartTime = (String) map.get("createStartTime");//下单时间
		String createEndTime = (String) map.get("createEndTime");
		String[] status  = (String[]) map.get("status");
		Integer supplierId  = (Integer) map.get("supplierId");
		String mobile = (String) map.get("mobile");
		if (createStartTime != null && StringUtils.isNotEmpty(createStartTime) && createEndTime == null) {
			sql.append("AND o.create_time >= ? ");
			params.add(createStartTime);
		}
		if (createStartTime == null && createEndTime != null && StringUtils.isNotEmpty(createEndTime)) {
			sql.append("AND o.create_time <= ? ");
			params.add(createEndTime);
		}
		if (createStartTime != null && StringUtils.isNotEmpty(createStartTime) && createEndTime != null && StringUtils.isNotEmpty(createEndTime)) {
			sql.append("AND o.create_time BETWEEN ? AND ? ");
			params.add(createStartTime);
			params.add(createEndTime);
		}
		if (status != null && status.length > 0 && StringUtils.isNotEmpty(status[0])) {
			sql.append("AND (");
			for (String s:status[0].split(",")) {
				sql.append("og.order_goods_status =  ? OR ");
				params.add(s);
			}
			sql.delete(sql.length() -3,sql.length());//去除OR 三个字符
			sql.append(")");
		}
		if (supplierId != null) {
			sql.append("AND p.supplier_id =  ? ");
			params.add(supplierId);
		}
		if (mobile != null && StringUtils.isNotEmpty(mobile)) {
			sql.append("AND o.buy_mobile = ? ");
			params.add(mobile);
		}
		Sort sort = new Sort("o.create_time",SqlSort.DESC);
		return BaseDao.dao.queryForListPage(OrderGoodsModel.class, sql.toString(), params, sort, pageNo, pageSize);
	}
    /**
     * @param
     */
	@Override
	public DataObj<OrderGoodsModel> updateOrderLogistics(OrderGoodsModel orderGoodsModel) {
		logger.info("添加好物商品"+ JSON.toJSON(orderGoodsModel));
		try {
			List<Object> params = new ArrayList<Object>();
			StringBuffer sql = new StringBuffer();
			sql.append("UPDATE order_goods SET courier_company = ?, courier_number = ? , update_time = now(), deliver_goods_time = now() ,order_goods_status = ?");
			sql.append("WHERE order_id = ?");
			params.add(orderGoodsModel.getCourierCompany());
			params.add(orderGoodsModel.getCourierNumber());
			params.add(orderGoodsModel.getOrderId());
			params.add(OrderGoodsStatusConstant.RECEIVED);
			int result = BaseDao.dao.update(sql.toString(),params.toArray());
			if (result < 1) {
				return new DataObj<>("修改物流信息失败！");
			}
		} catch (Exception e){
			e.printStackTrace();
			logger.error("添加好物商品失败"+e.getMessage());
			return new DataObj<>("修改物流信息异常！");
		}
		return DataObj.getSuccessData(orderGoodsModel);
	}

	@Override
	public OrderGoodsModel getOrderGoodsModel(String orderId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT p.supplier_name,o.order_id,o.order_name,o.pid,o.product_img,o.product_name,o.product_price,o.buy_mobile,o.buy_count,o.pay_cash,o.pay_noncash,og.freight,o.create_time,og.adres_name,og.adres_mobile,og.adres_addr,og.order_goods_status,og.message,o.is_sms_notice,og.courier_company,og.courier_number,og.remark ");
		sql.append("FROM `order` o JOIN order_goods og ON(o.order_id = og.order_id) JOIN product p ON(o.pid = p.pid) ");
		sql.append("WHERE o.order_id = ?");
		OrderGoodsModel orderGoodsModel = BaseDao.dao.queryForEntity(OrderGoodsModel.class,sql.toString(), orderId);
		orderGoodsModel.setOrderStatus(OrderGoodsStatusConstant.getStatusName(orderGoodsModel.getOrderGoodsStatus()));
		List<PayInfo> payOrderDetail = iPayOrderService.getPayOrderDetailByOrderId(orderId);
		orderGoodsModel.setPayInfos(payOrderDetail);
		System.out.println(JSON.toJSON(orderGoodsModel));
		return orderGoodsModel;
	}

	/**
	 *
	 * @param map

	 * @return
	 */
	@Override
	public List<OrderGoodsModel> orderGoodsList(Map<String, Object> map) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("SELECT p.supplier_name,o.order_id,o.order_name,o.product_name,o.pid,o.product_price,o.buy_mobile,o.create_time,o.product_original_price,(o.product_original_price * o.buy_count) total_original_price,og.adres_name,og.adres_addr,og.adres_mobile,o.buy_count,o.total_amount,o.pay_cash,o.pay_noncash,og.courier_company,og.courier_number,og.order_goods_status ");
		sql.append("FROM `order` o JOIN order_goods og ON(o.order_id = og.order_id) JOIN product p ON(o.pid = p.pid) ");
		sql.append("WHERE 1 = 1 ");
		String createStartTime = (String) map.get("createStartTime");//下单时间
		String createEndTime = (String) map.get("createEndTime");
		String[] status  = (String[]) map.get("status");
		Integer supplierId  = (Integer) map.get("supplierId");
		String mobile = (String) map.get("mobile");
		if (createStartTime != null && StringUtils.isNotEmpty(createStartTime) && createEndTime == null) {
			sql.append("AND o.create_time >= ? ");
			params.add(createStartTime);
		}
		if (createStartTime == null && createEndTime != null && StringUtils.isNotEmpty(createEndTime)) {
			sql.append("AND o.create_time <= ? ");
			params.add(createEndTime);
		}
		if (createStartTime != null && StringUtils.isNotEmpty(createStartTime) && createEndTime != null && StringUtils.isNotEmpty(createEndTime)) {
			sql.append("AND o.create_time BETWEEN ? AND ? ");
			params.add(createStartTime);
			params.add(createEndTime);
		}
		if (status != null && status.length > 0 && StringUtils.isNotEmpty(status[0])) {
			sql.append("AND (");
			for (String s:status[0].split(",")) {
				sql.append("og.order_goods_status =  ? OR ");
				params.add(s);
			}
			sql.delete(sql.length() -3,sql.length());//去除OR 三个字符
			sql.append(")");
		}
		if (supplierId != null) {
			sql.append("AND p.supplier_id =  ? ");
			params.add(supplierId);
		}
		if (mobile != null && StringUtils.isNotEmpty(mobile)) {
			sql.append("AND o.buy_mobile = ? ");
			params.add(mobile);
		}
		return BaseDao.dao.queryForListEntity(OrderGoodsModel.class, sql.toString(), params);
	}

	@Override
	public DataObj<String> addCourier(String[] courier) {
		logger.info("添加物流信息"+JSON.toJSON(courier));
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("UPDATE order_goods SET courier_company = CASE order_id ");
			for (String c:courier) {
				String[] goods = c.split(",");
				sql.append(" WHEN '"+goods[0] +"' THEN '" + goods[1]+"'");
			}
			sql.append(" END, ");
			sql.append("courier_number = CASE order_id ");
			for (String c:courier) {
				String[] goods = c.split(",");
				sql.append(" WHEN '"+goods[0] +"' THEN '" + goods[2]+"'");
			}
			sql.append(" END,order_goods_status = "+OrderGoodsStatusConstant.RECEIVED.getId() +" ,deliver_goods_time = now() ");
			sql.append("WHERE order_id in (");
			StringBuffer sqlIn = new StringBuffer("'");
			for (String c:courier) {
				String[] goods = c.split(",");
				sqlIn.append(goods[0]+"','");
			}
			sqlIn.append("'");
			sql.append(sqlIn.toString());
			sql.append(")");
			int result = BaseDao.dao.update(sql.toString());
			if (result < 1) {
				return new DataObj<>("添加物流信息失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("添加物流信息"+e.getMessage());
			return new DataObj<>("添加物流信息失败！");
		}
		return DataObj.getSuccessData("");
	}

	@Override
	public List<OrderGoodsModel> orderGoodsList(String ids) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT o.order_id,o.product_name,p.supplier_name,og.adres_addr,og.adres_name ");
		sql.append("FROM `order` o JOIN order_goods og ON(o.order_id = og.order_id) JOIN product p ON(o.pid = p.pid) ");
		sql.append("WHERE og.order_goods_status= ? and o.order_id in ("+CommonUtil.arrayToSqlIn(ids.split(","))+")");
		return BaseDao.dao.queryForListEntity(OrderGoodsModel.class,sql.toString(),OrderGoodsStatusConstant.PENDING.getId());
	}
}
