package com.logistics.service.travel.order.impl;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.common.Sort;
import cn.assist.easydao.common.SqlExpr;
import cn.assist.easydao.common.SqlSort;
import cn.assist.easydao.dao.BaseDao;
import cn.assist.easydao.pojo.PagePojo;
import com.alibaba.fastjson.JSON;
import com.logistics.base.constant.OrderConstant;
import com.logistics.base.constant.OrderTravelConstant;
import com.logistics.base.constant.ProductConstant;
import com.logistics.base.log.Log;
import com.logistics.base.utils.CommonUtil;
import com.logistics.base.utils.DateUtil;
import com.logistics.base.utils.RecordBean;
import com.logistics.service.coupon.CouponMemberService;
import com.logistics.service.helper.DataBean;
import com.logistics.service.member.MemberService;
import com.logistics.service.model.MemberCouponModel;
import com.logistics.service.model.OrderTravelModel;
import com.logistics.service.model.OrderTravelPersonnelModel;
import com.logistics.service.model.ProductTravelModel;
import com.logistics.service.pay.api.IPayOrderService;
import com.logistics.service.pay.constant.PayOrderConstant;
import com.logistics.service.pay.vo.PayInfo;
import com.logistics.service.pay.vo.PayOrder;
import com.logistics.service.pay.vo.PayRefundOrder;
import com.logistics.service.sys.log.ISysOperatorLogService;
import com.logistics.service.travel.IProductTravelService;
import com.logistics.service.travel.order.ITravelLiveOrderService;
import com.logistics.service.travel.order.ITravelOrderService;
import com.logistics.service.travel.order.ITravelRefundService;
import com.logistics.service.vo.member.Member;
import com.logistics.service.vo.member.MemberCoupon;
import com.logistics.service.vo.order.Order;
import com.logistics.service.vo.travel.OrderTravel;
import com.logistics.service.vo.travel.OrderTravelPersonnel;
import com.logistics.service.vo.travel.OrderTravelSummary;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 
 *	旅居订单
 *
 * @author caixb
 */
@Service("ITravelLiveOrderService")
public class TravelLiveOrderServiceImpl implements ITravelLiveOrderService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IPayOrderService payOrderService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private IProductTravelService iProductTravelService;
	@Autowired
	private ITravelRefundService iTravelRefundService;
	@Autowired
	private CouponMemberService couponMemberService;
	@Autowired
	private ISysOperatorLogService iSysOperatorLogService;

	/**
	 * 分页查询旅游订单
	 * 
	 * @param conn
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PagePojo<OrderTravelSummary> getOrderTravel(Conditions conn, int pageNo,int pageSize){
		StringBuffer sql = new StringBuffer("select ");
		sql.append("a.order_id");
		sql.append(",a.buy_uid");
		sql.append(",a.buy_name");
		sql.append(",a.buy_mobile");
		sql.append(",a.order_name");
		sql.append(",a.pid");
		sql.append(",a.product_name");
		sql.append(",a.pay_status");
		sql.append(",a.product_price");
		sql.append(",a.total_amount");
		sql.append(",a.buy_count");
		sql.append(",a.pay_cash");
		sql.append(",a.pay_noncash");
		sql.append(",a.create_time");
		sql.append(",a.finish_time");
		
		sql.append(",b.order_travel_status");
		sql.append(",b.travel_place");
		sql.append(",b.travel_time");
		sql.append(",b.travel_days");
		sql.append(",b.rooms");
		sql.append(",b.explain");
		
		sql.append(" from `order` as a inner join `order_travel` as b on a.order_id = b.order_id");
		sql.append(" where 1 = 1");
		List<Object> params = new ArrayList<Object>();
		if(conn != null && StringUtils.isNotBlank(conn.getConnSql())){
			sql.append(" and " + conn.getConnSql());
			params = conn.getConnParams();
		}
		Sort sort = new Sort("a.create_time", SqlSort.DESC);
		PagePojo<OrderTravelSummary> page = BaseDao.dao.queryForListPage(OrderTravelSummary.class, sql.toString(), params, sort, pageNo, pageSize);
		
		return page;
	}

	@Override
	public PagePojo<OrderTravelSummary> getOrderTravel(Map<String, Object> map, int pageNo, int pageSize) {
		StringBuffer sql = new StringBuffer("select ");
		sql.append("a.order_id");
		sql.append(",a.buy_uid");
		sql.append(",a.buy_name");
		sql.append(",a.buy_mobile");
		sql.append(",a.order_name");
		sql.append(",a.pid");
		sql.append(",a.product_name");
		sql.append(",a.pay_status");
		sql.append(",a.product_price");
		sql.append(",a.total_amount");
		sql.append(",a.buy_count");
		sql.append(",a.pay_cash");
		sql.append(",a.pay_noncash");
		sql.append(",a.create_time");
		sql.append(",a.finish_time");
		sql.append(",a.supplier_name");

		sql.append(",b.order_travel_status");
		sql.append(",b.travel_place");
		sql.append(",b.travel_time");
		sql.append(",b.travel_days");
		sql.append(",b.rooms");
		sql.append(",b.explain");

		sql.append(" from `order` as a inner join `order_travel` as b on a.order_id = b.order_id");
		sql.append(" where 1 = 1 ");

		sql.append("AND cat_id ="+ProductConstant.TRAVEL_LIVE+" ");
		List<Object> params = new ArrayList<Object>();
		String bookTime = (String) map.get("bookTime");
		String pid = (String) map.get("pid");
		String orderId = (String) map.get("orderId");
		Integer supplierId = (Integer) map.get("supplierId");
		Integer status  = (Integer) map.get("status");
		String mobile  = (String) map.get("mobile");

		if (bookTime != null && StringUtils.isNotEmpty(bookTime)) {
			sql.append("AND a.create_time BETWEEN ? AND ? ");
			String[] time = bookTime.split(" - ");
			params.add(time[0]);
			params.add(time[1]);
		}
		if (status != null) {
			sql.append("AND b.order_travel_status =  ? ");
			params.add(status);
		}
		if (pid != null) {
			sql.append("AND a.pid = ? ");
			params.add(pid);
		}
		if (orderId != null) {
			sql.append("AND a.order_id = ? ");
			params.add(orderId);
		}
		if (supplierId != null) {
			sql.append("AND a.supplier_id = ? ");
			params.add(supplierId);
		}
		if (mobile != null && StringUtils.isNotEmpty(mobile)) {
			sql.append("AND a.buy_mobile = ? ");
			params.add(mobile.trim());
		}
		Sort sort = new Sort("a.create_time",SqlSort.DESC);
		return BaseDao.dao.queryForListPage(OrderTravelSummary.class,sql.toString(),params,sort,pageNo,pageSize);
	}

	@Transactional
	@Override
	public RecordBean<OrderTravelModel> addOrderTravel(String pid, List<OrderTravelPersonnel> orderTravelPersonnels, OrderTravelModel orderTravelModel,String operName,Integer operId) {
		String orderId = CommonUtil.getId("AOD");
		try {
			//获取旅游商品信息
			ProductTravelModel product = iProductTravelService.getProductTravel(pid);
			Member member = memberService.getMemberByMobile(orderTravelModel.getBuyMobile());
			//获取用户的uid
			//创建订单信息
			Order order = new Order();
			order.setOrderId(orderId);
			order.setOrderName(product.getName());
			order.setBuyUid(member.getUid());
			order.setBuyMobile(orderTravelModel.getBuyMobile());
			order.setBuyName(orderTravelModel.getBuyName());
			order.setPayStatus(PayOrderConstant.PAY_ORDER_STATUS_NOPAY);
			order.setProductGroupType(product.getProductGroupType());
			order.setCatId(ProductConstant.TRAVEL_LIVE);
			order.setPid(pid);
			order.setProductImg(product.getImageUrl());
			order.setProductName(product.getTitle());
			order.setProductInfo(product.getInfo());
			order.setProductPrice(product.getPrice());
			order.setSupplierId(product.getSupplierId());
			order.setSupplierName(product.getSupplierName());
			order.setProductOriginalPrice(product.getOriginalPrice());
			order.setBuyCount(1);
//			order.setTotalAmount(product.getPrice());
//			order.setPayCash(product.getPrice());
			order.setTotalAmount(new BigDecimal(0));
			order.setPayCash(new BigDecimal(0));
			order.setCreateTime(new Date());
			order.setUpdateTime(new Date());
			order.setExpireTime(DateUtil.getDateAddOffSet(1,1));
			int result = BaseDao.dao.insert(order);
			if (result != 1 ) {
				return RecordBean.error("创建旅游订单失败！");
			}
			OrderTravel orderTravel = new OrderTravel();
			orderTravel.setOrderId(orderId);
			orderTravel.setTravelPlace(orderTravelModel.getTravelPlace());
			orderTravel.setTravelTime(orderTravelModel.getTravelTime());
			orderTravel.setTravelDays(product.getTravelDay()+"");
			orderTravel.setRooms(orderTravelModel.getRooms());
			orderTravel.setDemands(orderTravelModel.getDemands());
			orderTravel.setExplain(orderTravelModel.getExplain());
			orderTravel.setOrderTravelStatus(orderTravelModel.getOrderTravelStatus());
			result = BaseDao.dao.insert(orderTravel);
			if (result != 1 ) {
				return RecordBean.error("创建订单信息失败！");
			}
			//删除携带人员数据在进行添加
			result = BaseDao.dao.update("DELETE FROM order_travel_personnel WHERE order_id = ?",orderTravelModel.getOrderId());
			//添加携带人员信息
			List<OrderTravelPersonnel> personnelList = new ArrayList<OrderTravelPersonnel>();
			orderTravelPersonnels = CommonUtil.removeNullFromList(orderTravelPersonnels);//去除数组中的空对象
			if (orderTravelPersonnels != null && orderTravelPersonnels.size() > 0){
				return addOrderTravelPerson(orderTravelPersonnels,orderTravel.getOrderId());
//				StringBuffer sql = new StringBuffer();
//				List<Object> params = new ArrayList<Object>();
//				sql.append("INSERT INTO order_travel_personnel(order_id,type,type_name,refer_price,cn_name,en_name,mobile,sex,sex_name,credentials,credentials_name,credentials_id,issue_at,valid_time,issue_time,create_time) VALUES ");
//				for (int i = 0; i < orderTravelPersonnels.size(); i++){
//					if (orderTravelPersonnels.get(i) != null) { //防止里面有空对象
//						OrderTravelPersonnel orderTravelPersonnel = orderTravelPersonnels.get(i);
//						sql.append("(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now()),");
//						params.add(orderId);
//						params.add(orderTravelPersonnel.getType());
//						params.add(orderTravelPersonnel.getTypeName());
//						params.add(orderTravelPersonnel.getReferPrice());
//						params.add(orderTravelPersonnel.getCnName());
//						params.add(orderTravelPersonnel.getEnName());
//						params.add(orderTravelPersonnel.getMobile());
//						params.add(orderTravelPersonnel.getSex());
//						params.add(orderTravelPersonnel.getSexName());
//						params.add(orderTravelPersonnel.getCredentials());
//						params.add(orderTravelPersonnel.getCredentialsName());
//						params.add(orderTravelPersonnel.getCredentialsId());
//						params.add(orderTravelPersonnel.getIssueAt());
//						params.add(orderTravelPersonnel.getValidTime());
//						params.add(orderTravelPersonnel.getIssueTime());
//					}
//				}
//				int personnelResult = BaseDao.dao.insert(sql.deleteCharAt(sql.length()-1).toString(),params.toArray());
//				if (personnelResult < 1) {
//					return RecordBean.error("创建旅游携带人员信息失败！");
//				}
			}
			//创建支付单
			String oper = " 操作员：" + operId + "(" + operName + "), 操作时间:" + DateUtil.getDateTimeStr();
			DataBean<String> bean = payOrderService.createPayOrder(orderId,member.getUid(),0,pid,product.getName(),product.getProductGroupType(),product.getOriginalPrice().doubleValue(),order.getBuyCount(),order.getTotalAmount().doubleValue(),order.getExpireTime(),oper);
			if (!bean.isSuccess()) {
				return RecordBean.error(bean.getMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("创建旅游订单信息异常,原因是（"+e.getMessage()+")");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动回滚事务
			return RecordBean.error("创建旅游订单信息异常");
		}
		return RecordBean.success("成功！",orderTravelModel);
	}

	@Transactional
	@Override
	public RecordBean<OrderTravelModel> updateOrderTravel(String pid, List<OrderTravelPersonnel> orderTravelPersonnels, OrderTravelModel orderTravelModel) {
		try {
			//获取旅游商品信息
			ProductTravelModel product = iProductTravelService.getProductTravel(pid);
			if (product.getStatus() != ProductConstant.PRODUCT_STATUS_ONOFFER) {
				return RecordBean.error("商品已下架,请选择重新上架商品或取消订单！");
			}
			Member member = memberService.getMemberByMobile(orderTravelModel.getBuyMobile());
			//获取用户的uid
			//创建订单信息
			Order order = new Order();
			order.setOrderId(orderTravelModel.getOrderId());
			order.setOrderName(product.getTitle());
			order.setBuyUid(member.getUid());
			order.setBuyName(orderTravelModel.getBuyName());
			order.setBuyMobile(orderTravelModel.getBuyMobile());
			order.setSellUid(product.getSupplierId());
			order.setPid(pid);
			order.setCatId(ProductConstant.TRAVEL_LIVE);
			order.setProductImg(product.getImageUrl());
			order.setProductName(product.getName());
			order.setProductInfo(product.getInfo());
			order.setProductPrice(product.getPrice());
			order.setSupplierId(product.getSupplierId());
			order.setSupplierName(product.getSupplierName());
			order.setProductOriginalPrice(product.getOriginalPrice());
			order.setUpdateTime(new Date());
			int result = BaseDao.dao.update(order,new Conditions("order_id", SqlExpr.EQUAL, orderTravelModel.getOrderId()));
			if (result != 1 ) {
				return RecordBean.error("修改订单信息失败！");
			}
			OrderTravel orderTravel = new OrderTravel();
			orderTravel.setOrderId(orderTravelModel.getOrderId());
			orderTravel.setTravelPlace(orderTravelModel.getTravelPlace());
			orderTravel.setTravelTime(orderTravelModel.getTravelTime());
			orderTravel.setTravelDays(product.getTravelDay()+"");
			orderTravel.setRooms(orderTravelModel.getRooms());
			orderTravel.setDemands(orderTravelModel.getDemands());
			orderTravel.setExplain(orderTravelModel.getExplain());
			orderTravel.setOrderTravelStatus(orderTravelModel.getOrderTravelStatus());
			result = BaseDao.dao.update(orderTravel,new Conditions("order_id", SqlExpr.EQUAL, orderTravelModel.getOrderId()));
			if (result != 1 ) {
				return RecordBean.error("修改订单信息失败！");
			}
			//删除携带人员数据在进行添加
			result = BaseDao.dao.update("DELETE FROM order_travel_personnel WHERE order_id = ?",orderTravelModel.getOrderId());
			//添加携带人员信息
			List<OrderTravelPersonnel> personnelList = new ArrayList<OrderTravelPersonnel>();
			orderTravelPersonnels = CommonUtil.removeNullFromList(orderTravelPersonnels);//去除数组中的空对象
			if (orderTravelPersonnels != null && orderTravelPersonnels.size() > 0){
				return addOrderTravelPerson(orderTravelPersonnels,orderTravel.getOrderId());
//				StringBuffer sql = new StringBuffer();
//				List<Object> params = new ArrayList<Object>();
//				sql.append("INSERT INTO order_travel_personnel(order_id,type,type_name,refer_price,cn_name,en_name,mobile,sex,sex_name,credentials,credentials_name,credentials_id,issue_at,valid_time,issue_time,create_time) VALUES ");
//				for (int i = 0; i < orderTravelPersonnels.size(); i++){
//					if (orderTravelPersonnels.get(i) != null) { //防止里面有空对象
//						OrderTravelPersonnel orderTravelPersonnel = orderTravelPersonnels.get(i);
//						sql.append("(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now()),");
//						params.add(orderTravelModel.getOrderId());
//						params.add(orderTravelPersonnel.getType());
//						params.add(orderTravelPersonnel.getTypeName());
//						params.add(orderTravelPersonnel.getReferPrice());
//						params.add(orderTravelPersonnel.getCnName());
//						params.add(orderTravelPersonnel.getEnName());
//						params.add(orderTravelPersonnel.getMobile());
//						params.add(orderTravelPersonnel.getSex());
//						params.add(orderTravelPersonnel.getSexName());
//						params.add(orderTravelPersonnel.getCredentials());
//						params.add(orderTravelPersonnel.getCredentialsName());
//						params.add(orderTravelPersonnel.getCredentialsId());
//						params.add(orderTravelPersonnel.getIssueAt());
//						params.add(orderTravelPersonnel.getValidTime());
//						params.add(orderTravelPersonnel.getIssueTime());
//					}
//				}
//				int personnelResult = BaseDao.dao.insert(sql.deleteCharAt(sql.length()-1).toString(),params.toArray());
//				if (personnelResult < 1) {
//					return RecordBean.error("修改旅游携带人员信息失败！");
//				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("修改旅游订单信息异常,原因是（"+e.getMessage()+")");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动回滚事务
			return RecordBean.error("修改旅游订单信息异常");
		}
		return RecordBean.success("成功！",orderTravelModel);
	}

	/**
	 * 根据id查询旅游申请订单
	 * 
	 * @param orderId
	 * @return
	 */
	public OrderTravelSummary getOrderTravel(String orderId){
		StringBuffer sql = new StringBuffer("select ");
		sql.append("a.order_id");
		sql.append(",a.cat_id");
		sql.append(",a.buy_uid");
		sql.append(",a.buy_name");
		sql.append(",a.buy_mobile");
		sql.append(",a.sell_uid");
		sql.append(",a.product_group_type");
		sql.append(",a.order_name");
		sql.append(",a.pid");
		sql.append(",a.product_name");
		sql.append(",a.pay_status");
		sql.append(",a.product_price");
		sql.append(",a.product_original_price");
		sql.append(",a.total_amount");
		sql.append(",a.buy_count");
		sql.append(",a.pay_cash");
		sql.append(",a.pay_noncash");
		sql.append(",a.create_time");

		sql.append(",b.order_travel_status");
		sql.append(",b.travel_place");
		sql.append(",b.travel_time");
		sql.append(",b.travel_days");
		sql.append(",b.rooms");
		sql.append(",b.demands");
		sql.append(",b.explain");
		sql.append(",b.goods_cancel_reason");
		sql.append(",b.order_cancel_time");
		sql.append(",b.order_success_time");

		sql.append(" from `order` as a inner join `order_travel` as b on a.order_id = b.order_id");
		sql.append(" where a.order_id = ?");

		return BaseDao.dao.queryForEntity(OrderTravelSummary.class, sql.toString(), orderId);
	}
	
	/**
	 * 根据订单id 查询人员信息
	 * 
	 * 
	 * @param orderId
	 * @return
	 */
	public List<OrderTravelPersonnel> getOrderTravelPersonnel(String orderId){
		return BaseDao.dao.queryForListEntity(OrderTravelPersonnel.class,new Conditions("order_id",SqlExpr.EQUAL,orderId));
	}

	/**
	 * 根据订单id 查询人员信息
	 *
	 *
	 * @param orderId
	 * @return
	 */
	@Override
	public List<OrderTravelPersonnelModel> getOrderTravelPersonnelModel(String orderId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT otp.id,otp.cn_name,otp.en_name,otp.type,otp.type_name,otp.sex,otp.sex_name,otp.mobile,otp.credentials,otp.credentials_name,otp.credentials_id,otp.issue_at,otp.issue_time,otp.valid_time,otp.refer_price ");
		sql.append("FROM order_travel_personnel otp ");
		sql.append("WHERE otp.order_id = ? ");
		return BaseDao.dao.queryForListEntity(OrderTravelPersonnelModel.class, sql.toString(),orderId);
	}

	/**
	 * 订单确认收款
	 * 
	 * @param orderId		订单id
	 * @param payAmount		实际支付金额
	 * @param payWay		支付方式 		参考com.logistics.base.constant.PayOrderConstant
	 * @param remark		备注
	 * @param operId		操作人id
	 * @param operName		操作人姓名
	 * @return
	 */
	@Log(operateName = "订单确认收款")
	@Transactional
	public RecordBean<String> proceeds(String orderId, double payAmount, int payWay, String remark, int operId, String operName){
		String oper = remark + " 操作员：" + operId + "(" + operName + "), 操作时间:" + DateUtil.getDateTimeStr();
		OrderTravelSummary orderTravel = getOrderTravel(orderId);
		if(orderTravel == null){
			return RecordBean.error("订单号【"+orderId+"】的旅游订单不存在！");
		}
		int payType = PayOrderConstant.PAY_TYPE_BASIC; //支付单
		int orderStatus = orderTravel.getOrderTravelStatus();
		int payStatus = orderTravel.getPayStatus();
		
		if(orderStatus == OrderTravelConstant.STATUS_DRAFT || orderStatus == OrderTravelConstant.STATUS_WAIT_FINISH){
			logger.info("[旅游订单确认收款]-[操作失败]-[订单状态不正确，不能确认收款]-orderId:" + orderId + ",payAmount:" + payAmount + ",orderStatus:" + orderStatus + ",payStatus:" + payStatus + ",oper:" + oper);
			return RecordBean.error("订单状态不正确，不能确认收款！");
		}
		//如果已支付成功， 再次支付该订单 则为补差价单
		if(payStatus == OrderConstant.ORDER_PAY_STATUS_SUCCESS){
			payType = PayOrderConstant.PAY_TYPE_REPLENISH; //差价单
		}
		//创建支付信息
        DataBean<String> bean = payOrderService.payCash(orderId,payType,payWay,payAmount, true, remark);
		if(!bean.isSuccess()){
			logger.info("[旅游订单确认收款]-[操作失败]-[支付失败]-orderId:" + orderId + ",payAmount:" + payAmount + ",orderStatus:" + orderStatus + ",payStatus:" + payStatus + ",oper:" + oper);
			return RecordBean.error(bean.getMsg());
		}

		//订单单支付完成通知
		boolean b = orderPaySuccess(orderTravel, payAmount, remark, operId, operName);
		if(!b){
			logger.info("[旅游订单支付完成]-[收款成功]-[更新订单状态失败]-orderId:" + orderId + ",payAmount:" + payAmount + ",orderStatus:" + orderStatus + ",payStatus:" + payStatus + ",operId:" + operId + ",operName:" + operName);
			return RecordBean.error("更新订单状态失败");
		}
		return RecordBean.success("success!");
	}

	/**
	 * 订单取消(只能取消旅游订单， 且状态必须是未完成或未取消的)
	 *
	 * 订单取消 会直接结束掉当前订单（状态改为 “已取消”），如果有支付， 会同时创建出退款单
	 *
	 * @param orderId
	 * @param reason
	 * @param operId
	 * @param operName
	 * @return
	 */
	@Log(operateName = "取消订单")
	public boolean orderCancel(String orderId, String reason, int operId, String operName,String ip){
		String msg = "订单取消:"+"订单号【"+orderId+"】"+",操作人【"+operName+"】"+",取消原因【"+reason+"】";
		Order order = BaseDao.dao.queryForEntity(Order.class,new Conditions("order_id",SqlExpr.EQUAL,orderId));
		if(order == null){
			return false;
		}
		int payStatus = order.getPayStatus();
		OrderTravelSummary orderTravel = getOrderTravel(orderId);
		if(orderTravel == null){
			return false;
		}
		int orderTravelStatus = orderTravel.getOrderTravelStatus();
		//初始状态 + 等待出行的才可以取消订单
		if(orderTravelStatus != OrderTravelConstant.STATUS_WAIT_INITIAL && orderTravelStatus != OrderTravelConstant.STATUS_WAIT_TRAVEL && orderTravelStatus != 	OrderTravelConstant.STATUS_WAIT_PAY){
			logger.info("[旅游订单 订单取消]-[操作失败]-[订单支付状态不对]-orderId:" + orderId + ",payStatus:" + payStatus + ",orderTravelStatus:" + orderTravelStatus + ",operId:" + operId + ",operName:" + operName);
			return false;
		}
		Date date = new Date();
		//退回优惠券
		payOrderService.returnCoupon(orderTravel.getBuyUid(),orderId,date,"取消订单，退回优惠券！");
		String sql = "update `order_travel` set `order_travel_status` = ?, `goods_cancel_reason` = ?, `order_cancel_time` = ? where `order_id` = ? and (`order_travel_status` = ? or `order_travel_status` = ? or `order_travel_status` = ?)";
		int result = BaseDao.dao.update(sql, OrderTravelConstant.STATUS_WAIT_CANCEL, reason, date, orderId, OrderTravelConstant.STATUS_WAIT_INITIAL, OrderTravelConstant.STATUS_WAIT_TRAVEL,OrderTravelConstant.STATUS_WAIT_PAY);
		if(result != 1){
			logger.info("[旅游订单 订单取消]-[操作失败]-[更新订单状态失败]-orderId:" + orderId + ",payStatus:" + payStatus + ",orderTravelStatus:" + orderTravelStatus + ",operId:" + operId + ",operName:" + operName);
			return false;
		}
		return true;
	}

	/**
	 *  订单完成
	 * 
	 * @param orderId		订单id
	 * @param remark		备注
	 * @param operId		操作人id
	 * @param operName		操作人姓名
	 * @return
	 */
	@Log(operateName = "订单完成")
	@Transactional
	public RecordBean<String> orderSuccess(String orderId, String remark, int operId, String operName){
		String oper = remark + " 操作员：" + operId + "(" + operName + "), 操作时间:" + DateUtil.getDateTimeStr();
		String orderTravelSql = "update `order_travel` set `order_travel_status` = ?, `remark` = CONCAT(`remark`, ?),order_success_time = now()  where `order_id` = ? and `order_travel_status` = ? ";
		int result = BaseDao.dao.update(orderTravelSql, OrderTravelConstant.STATUS_WAIT_FINISH, oper, orderId, OrderTravelConstant.STATUS_WAIT_TRAVEL);
		if (result != 1) {
			logger.error("更新订单完成状态失败,订单号【"+orderId+"】");
			return RecordBean.error("更新订单完成状态失败！");
		}
		PayOrder payOrder = payOrderService.getPayOrderByOrderId(orderId);
		DataBean<String> dataBean = payOrderService.paySuccess(payOrder.getPayOrderId(),new Date(),oper);
		if (!dataBean.isOk()) {
			return RecordBean.error(dataBean.getMsg());
		}
		return RecordBean.success(dataBean.getMsg());
	}

	/**
	 * 根据orderId获取支付单信息
	 *
	 * @param orderId
	 * @return
	 */
	@Override
	public List<PayInfo> getPayOrderByOrderId(String orderId) {
		return payOrderService.getPayOrderDetailByOrderId(orderId);
	}

	@Override
	public Order getOrderByOrderId(String orderId) {
		return BaseDao.dao.queryForEntity(Order.class,new Conditions("order_id",SqlExpr.EQUAL,orderId));
	}

	@Override
	public RecordBean<PayRefundOrder> reduceAndRefundOrder(PayRefundOrder payRefundOrder, int operId, String operName,String IP,boolean isRefundCoupon) {
		// 执行退款操作
		RecordBean<String> recordBean = iTravelRefundService.refund(payRefundOrder.getOrderId(),payRefundOrder.getAmount(),payRefundOrder.getApplyReason(),payRefundOrder.getRemark(),operId,operName,isRefundCoupon);
		if (!recordBean.isSuccessCode()) {
			return RecordBean.error(recordBean.getMsg());
		}
		String orderId = payRefundOrder.getOrderId();
		String reason = payRefundOrder.getApplyReason();
		boolean result = orderCancel(orderId,reason,operId,operName,IP);
		if (result) {
			return RecordBean.error(recordBean.getMsg());
		}
		return RecordBean.success("成功！",payRefundOrder);
	}

	@Override
	public OrderTravelPersonnelModel getOrderTravelPersonnelModel(Integer id) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT otp.id,otp.cn_name,otp.en_name,otp.type,otp.type_name,otp.sex,otp.sex_name,otp.mobile,otp.credentials,otp.credentials_name,otp.credentials_id,otp.issue_at,otp.issue_time,otp.valid_time,otp.refer_price ");
		sql.append("FROM order_travel_personnel otp ");
		sql.append("WHERE otp.id = ? ");
		return BaseDao.dao.queryForEntity(OrderTravelPersonnelModel.class, sql.toString(),id);
	}

	@Override
	public RecordBean<OrderTravelPersonnel> editOrderTravelPersonnel(OrderTravelPersonnel orderTravelPersonnel) {
		int num = BaseDao.dao.update(orderTravelPersonnel,new Conditions("id",SqlExpr.EQUAL,orderTravelPersonnel.getId()));
		if (num < 1) {
			return RecordBean.error("修改失败！");
		}
		return RecordBean.success("成功！");
	}

	@Override
	public List<OrderTravelSummary> orderTravelSummaryList(Map<String, Object> map) {
		StringBuffer sql = new StringBuffer("select ");
		sql.append("a.order_id");
		sql.append(",a.buy_uid");
		sql.append(",a.buy_name");
		sql.append(",a.buy_mobile");
		sql.append(",a.order_name");
		sql.append(",a.pid");
		sql.append(",a.product_name");
		sql.append(",a.pay_status");
		sql.append(",a.product_price");
		sql.append(",a.total_amount");
		sql.append(",a.buy_count");
		sql.append(",a.create_time");
		sql.append(",a.supplier_name");

		sql.append(",b.order_travel_status");
		sql.append(",b.travel_place");
		sql.append(",b.travel_time");
		sql.append(",b.travel_days");
		sql.append(",b.rooms");
		sql.append(",b.explain");

		sql.append(" from `order` as a inner join `order_travel` as b on a.order_id = b.order_id");
		sql.append(" where 1 = 1 ");
		sql.append(" AND a.cat_id = "+ProductConstant.TRAVEL_LIVE+" ");
		List<Object> params = new ArrayList<Object>();
		String bookTime = (String) map.get("bookTime");
		String pid = (String) map.get("pid");
		String orderId = (String) map.get("orderId");
		Integer supplierId = (Integer) map.get("supplierId");
		Integer status  = (Integer) map.get("status");
		String mobile  = (String) map.get("mobile");

		if (bookTime != null && StringUtils.isNotEmpty(bookTime)) {
			sql.append("AND a.create_time BETWEEN ? AND ? ");
			String[] time = bookTime.split(" - ");
			params.add(time[0]);
			params.add(time[1]);
		}
		if (status != null) {
			sql.append("AND b.order_travel_status =  ? ");
			params.add(status);
		}
		if (pid != null) {
			sql.append("AND a.pid = ? ");
			params.add(pid);
		}
		if (orderId != null) {
			sql.append("AND a.order_id = ? ");
			params.add(orderId);
		}
		if (supplierId != null) {
			sql.append("AND a.supplier_id = ? ");
			params.add(supplierId);
		}
		if (mobile != null && StringUtils.isNotEmpty(mobile)) {
			sql.append("AND a.buy_mobile = ? ");
			params.add(mobile.trim());
		}
		Sort sort = new Sort("a.create_time",SqlSort.DESC);
		return BaseDao.dao.queryForListEntity(OrderTravelSummary.class,sql.toString(),params);
	}

	@Override
	public OrderTravelSummary getOrderTravelSummary(String orderId) {
		OrderTravelSummary orderTravelSummary = new OrderTravelSummary();
		orderTravelSummary.setOrderId(orderId);
		List<PayInfo> payInfoList = payOrderService.getPaySuccessCoupon(orderId);
		if (payInfoList != null && payInfoList.size() > 0) {
			PayInfo payInfo = payInfoList.get(0);
			System.out.println(JSON.toJSON(payInfo));
			String mcid = payInfo.getMcid();
			MemberCoupon memberCoupon = couponMemberService.getCouponMember(mcid);
			orderTravelSummary.setMcid(mcid);
			orderTravelSummary.setCouponCid(memberCoupon.getCouponCid());
			orderTravelSummary.setCouponAmount(memberCoupon.getCouponAmount());
			orderTravelSummary.setCouponName(memberCoupon.getCouponName());
		}
		System.out.println(JSON.toJSON(orderTravelSummary));
		return orderTravelSummary;
	}

	@Override
	public List<MemberCouponModel> getMemberCouponModel(String orderId) {
		List<MemberCouponModel> couponList = new ArrayList<MemberCouponModel>();
		List<PayInfo> payInfoList = payOrderService.getPayOrderDetailByOrderId(orderId);
		if (payInfoList != null && payInfoList.size() > 0) {
			for (PayInfo payInfo:payInfoList)
			if (payInfo.getPayWay() == PayOrderConstant.PAY_WAY_DAIJQ) {
				MemberCoupon memberCoupon = couponMemberService.getCouponMember(payInfo.getMcid());
				MemberCouponModel memberCouponModel = new MemberCouponModel();
				memberCouponModel.setCouponType(memberCoupon.getCouponType());
				memberCouponModel.setCouponCid(memberCoupon.getCouponCid());
				memberCouponModel.setCouponName(memberCoupon.getCouponName());
				memberCouponModel.setCouponAmount(memberCoupon.getCouponAmount());
				memberCouponModel.setStartTime(memberCoupon.getStartTime());
				memberCouponModel.setEndTime(memberCoupon.getEndTime());
				memberCouponModel.setMcid(memberCoupon.getMcid());
				memberCouponModel.setPayStatus(payInfo.getStatus());
				couponList.add(memberCouponModel);
			}
		}
		return couponList;
	}

	@Override
	public RecordBean<OrderTravelModel> addOrderTravelPerson(List<OrderTravelPersonnel> orderTravelPersonnels, String orderId) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("INSERT INTO order_travel_personnel(order_id,type,type_name,refer_price,cn_name,en_name,mobile,sex,sex_name,credentials,credentials_name,credentials_id,issue_at,valid_time,issue_time,create_time) VALUES ");
		for (int i = 0; i < orderTravelPersonnels.size(); i++){
			if (orderTravelPersonnels.get(i) != null) { //防止里面有空对象
				OrderTravelPersonnel orderTravelPersonnel = orderTravelPersonnels.get(i);
				sql.append("(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now()),");
				params.add(orderId);
				params.add(orderTravelPersonnel.getType());
				params.add(orderTravelPersonnel.getTypeName());
				params.add(orderTravelPersonnel.getReferPrice());
				params.add(orderTravelPersonnel.getCnName());
				params.add(orderTravelPersonnel.getEnName());
				params.add(orderTravelPersonnel.getMobile());
				params.add(orderTravelPersonnel.getSex());
				params.add(orderTravelPersonnel.getSexName());
				params.add(orderTravelPersonnel.getCredentials());
				params.add(orderTravelPersonnel.getCredentialsName());
				params.add(orderTravelPersonnel.getCredentialsId());
				params.add(orderTravelPersonnel.getIssueAt());
				params.add(orderTravelPersonnel.getValidTime());
				params.add(orderTravelPersonnel.getIssueTime());
			}
		}
		int personnelResult = BaseDao.dao.insert(sql.deleteCharAt(sql.length()-1).toString(),params.toArray());
		if (personnelResult < 1) {
			return RecordBean.error("添加旅游携带人员信息失败！");
		}
		return RecordBean.success("添加旅游携带人员信息成功！");
	}

	/**
	 *  订单支付完成
	 * 
	 * @param  orderTravel
	 * @param payAmount		实际支付金额
	 * @param remark		备注
	 * @param operId		操作人id
	 * @param operName		操作人姓名
	 * @return
	 */
	@Log(operateName = "订单支付完成")
	@Transactional
	private boolean orderPaySuccess(OrderTravelSummary orderTravel, double payAmount, String remark, int operId, String operName){
		String orderId = orderTravel.getOrderId();
		String oper = remark + " 操作员：" + operId + "(" + operName + "), 操作时间:" + DateUtil.getDateTimeStr();
		try {
			int payStatus = orderTravel.getPayStatus();
			if(payStatus != OrderConstant.ORDER_PAY_STATUS_SUCCESS){
				//订单
				String orderSql = "update `order` set `pay_status` = ?, `pay_cash` = `pay_cash` + ?, `total_amount` = `total_amount` + ?, `remark` = CONCAT(`remark`, ?)  where `order_id` = ? and `pay_status` = ?";
				String orderTravelSql = "update `order_travel` set `order_travel_status` = ?, `remark` = CONCAT(`remark`, ?)  where `order_id` = ? and `order_travel_status` = ?";
				
				int result = BaseDao.dao.update(orderSql, OrderConstant.ORDER_PAY_STATUS_SUCCESS, payAmount, payAmount, remark, orderId, OrderConstant.ORDER_PAY_STATUS_NOPAY);
				if(result != 1){
					throw new RuntimeException("[更新订单状态为支付成功]失败 ， orderId:" + orderId + ", remark:" + oper + ",operId:" + operId + ",operName:" + operName);
				}
				result = BaseDao.dao.update(orderTravelSql, OrderTravelConstant.STATUS_WAIT_TRAVEL, remark, orderId, OrderTravelConstant.STATUS_WAIT_PAY);
				if(result != 1){
					throw new RuntimeException("[更新订单状态为支付成功]失败 ， orderId:" + orderId + ", remark:" + oper + ",operId:" + operId + ",operName:" + operName);
				}
			}else{
				String orderSql = "update `order` set `pay_cash` = `pay_cash` + ?,`total_amount` = `total_amount` + ?  where `order_id` = ? ";
				BaseDao.dao.update(orderSql, payAmount,payAmount, orderId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动回滚事务
			logger.error("[订单支付完成]-[更新信息失败--事务回滚]: " + 
					" \n \t [Exception:]" + e +
					" \n \t [params:]orderId:" + orderId + ", remark:" + oper + ",operId:" + operId + ",operName:" + operName);
			return false;
		}
		return true;
	}

}
