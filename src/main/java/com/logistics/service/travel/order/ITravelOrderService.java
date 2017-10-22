package com.logistics.service.travel.order;
import java.util.List;
import java.util.Map;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.pojo.PagePojo;

import com.logistics.base.utils.RecordBean;
import com.logistics.service.model.MemberCouponModel;
import com.logistics.service.model.OrderTravelModel;
import com.logistics.service.model.OrderTravelPersonnelModel;
import com.logistics.service.pay.vo.PayInfo;
import com.logistics.service.pay.vo.PayRefundOrder;
import com.logistics.service.vo.order.Order;
import com.logistics.service.vo.travel.OrderTravelPersonnel;
import com.logistics.service.vo.travel.OrderTravelSummary;

/**
 * 
 *	旅游订单操作
 *
 * @author caixb
 */
public interface ITravelOrderService {

	/**
	 * 分页查询旅游订单
	 * 
	 * @param conn
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PagePojo<OrderTravelSummary> getOrderTravel(Conditions conn, int pageNo,int pageSize);


	/**
	 * 分页查询旅游订单
	 *
	 * @param map
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PagePojo<OrderTravelSummary> getOrderTravel(Map<String,Object> map, int pageNo, int pageSize);

	/**
	 * 添加旅游订单信息
	 * @param pid
	 * @param orderTravelPersonnels
	 * @param orderTravelModel
	 * @return
	 */
	public RecordBean<OrderTravelModel> addOrderTravel(String pid, List<OrderTravelPersonnel> orderTravelPersonnels, OrderTravelModel orderTravelModel,String operName,Integer operId);


	/**
	 * 修改旅游订单信息
	 * @param pid
	 * @param orderTravelPersonnels
	 * @param orderTravelModel
	 * @return
	 */
	public RecordBean<OrderTravelModel> updateOrderTravel(String pid,List<OrderTravelPersonnel> orderTravelPersonnels,OrderTravelModel orderTravelModel);


	/**
	 * 根据id查询旅游申请订单
	 * 
	 * @param orderId
	 * @return
	 */
	public OrderTravelSummary getOrderTravel(String orderId);
	
	
	/**
	 * 根据订单id 查询人员信息
	 * 
	 * 
	 * @param orderId
	 * @return
	 */
	public List<OrderTravelPersonnel> getOrderTravelPersonnel(String orderId);

	/**
	 * 根据订单id 查询人员信息
	 *
	 *
	 * @param orderId
	 * @return
	 */
	public List<OrderTravelPersonnelModel> getOrderTravelPersonnelModel(String orderId);

	/**
	 * 订单确认收款
	 * 
	 * @param orderId		订单id
	 * @param payAmount		实际支付金额
	 * @param payWay		支付方式		参考com.logistics.base.constant.PayOrderConstant
	 * @param remark		备注
	 * @param operId		操作人id
	 * @param operName		操作人姓名
	 * @return
	 */
	public RecordBean<String> proceeds(String orderId, double payAmount, int payWay, String remark, int operId, String operName);
	
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
	public boolean orderCancel(String orderId, String reason, int operId, String operName,String ip);

	/**
	 *  订单完成
	 * 
	 * @param orderId		订单id
	 * @param remark		备注
	 * @param operId		操作人id
	 * @param operName		操作人姓名
	 * @return
	 */
	public RecordBean<String> orderSuccess(String orderId, String remark, int operId, String operName);


	/**
	 * 根据orderId获取支付单信息
	 *
	 * @param orderId
	 * @return
	 */
	public List<PayInfo> getPayOrderByOrderId(String orderId);

	/**
	 * 获取订单信息
	 * @param orderId
	 * @return
	 */
	public Order getOrderByOrderId(String orderId);

	/**
	 * 取消并退款
	 * @param payRefundOrder
	 * @return
	 */
	public RecordBean<PayRefundOrder> reduceAndRefundOrder(PayRefundOrder payRefundOrder,int operId, String operName,String ip,boolean isRefundCoupon);

	/**
	 * 根据人员信息编号获取人员信息
	 * @param id
	 * @return
	 */
	public OrderTravelPersonnelModel getOrderTravelPersonnelModel(Integer id);

	/**
	 * 修改人员信息
	 * @param orderTravelPersonnel
	 * @return
	 */
	public RecordBean<OrderTravelPersonnel> editOrderTravelPersonnel(OrderTravelPersonnel orderTravelPersonnel);

	/**
	 * 获取订单信息
	 * @param map
	 * @return
	 */
	public List<OrderTravelSummary> orderTravelSummaryList(Map<String, Object> map);

	/**
	 * 获取订单使用优惠券的信息
	 * @param orderId
	 * @return
	 */
	public OrderTravelSummary getOrderTravelSummary(String orderId);

	/**
	 * 获取用户优惠劵信息
	 * @param orderId
	 * @return
	 */
	public List<MemberCouponModel> getMemberCouponModel(String orderId);

}
