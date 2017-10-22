package com.logistics.controller.travel;

import cn.assist.easydao.pojo.PagePojo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.logistics.base.constant.*;
import com.logistics.base.utils.*;
import com.logistics.controller.BaseController;
import com.logistics.service.member.MemberService;
import com.logistics.service.model.MemberCouponModel;
import com.logistics.service.model.OrderTravelModel;
import com.logistics.service.model.OrderTravelPersonnelModel;
import com.logistics.service.travel.order.ITravelOrderService;
import com.logistics.service.travel.order.ITravelRefundService;
import com.logistics.service.pay.vo.PayInfo;
import com.logistics.service.pay.vo.PayRefundOrder;
import com.logistics.service.travel.IProductTravelService;
import com.logistics.service.vo.member.Member;
import com.logistics.service.vo.travel.OrderTravelPersonnel;
import com.logistics.service.vo.travel.OrderTravelSummary;
import com.logistics.service.vo.travel.ProductTravelSummary;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *	旅游临时订单 
 *
 * @author caixb
 */
@Controller
@RequestMapping("/travel/order")
public class TravelOrderController extends BaseController{

	@Autowired
	private ITravelOrderService travelOrderService;
	@Autowired
	private ITravelRefundService iTravelRefundService;
	@Autowired
	private IProductTravelService productTravelService;
	@Autowired
	private MemberService memberService;

	/**
	 * 临时订单列表页面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String tempView(HttpServletRequest request, Model model){
		List<ProductTravelSummary> list = productTravelService.getTravelSummaryList(ProductConstant.PRODUCT_STATUS_ONOFFER);
		model.addAttribute("travelSummary", list);
		return "/modules/travel/order/index";
	}
	
	
	/**
	 *	获取订单数据 
	 */
	@RequestMapping("/list")
	@ResponseBody
	public JSONObject tempList(HttpServletRequest request, OrderTravelSummary orderTravelTemp){
		int pageNo = ReqUtils.getParamToInt(request, "pageNo", 1);
		int pageSize = ReqUtils.getParamToInt(request, "pageSize", 10);
		String pid = ReqUtils.getParam(request,"pid",null);
		String orderId = ReqUtils.getParam(request,"orderId",null);
		String bookTime = ReqUtils.getParam(request,"bookTime", null);
		String mobile = ReqUtils.getParam(request,"mobile",null);
		Integer status = ReqUtils.getParamToInteger(request,"status",null);
		Integer supplierId = ReqUtils.getParamToInteger(request,"supplierId",null);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("pid",pid);
		map.put("orderId",orderId);
		map.put("bookTime",bookTime);
		map.put("mobile",mobile);
		map.put("status",status);
		map.put("supplierId",supplierId);
		PagePojo<OrderTravelSummary> page = travelOrderService.getOrderTravel(map, pageNo, pageSize);
		List<OrderTravelSummary> list = page.getPgaeData();
		return JsonBean.success("ok", page);
	}
	
	/**
	 *	草稿订单数据保存 状态-99
	 */
	@RequestMapping("/addTemp")
	@ResponseBody
	public JSONObject tempAdd(HttpServletRequest request, OrderTravelModel orderTravelModel){
		String pid = request.getParameter("pid");
		if (pid == null) {
			return JsonBean.error("旅游产品信息不能为空！");
		}
		if (StringUtils.isBlank(orderTravelModel.getTravelPlace())) {
			return JsonBean.error("预定用户名字不能为空！");
		}
		if (StringUtils.isBlank(orderTravelModel.getBuyMobile())) {
			return JsonBean.error("预定人手机号不能为空！");
		}
		if (StringUtils.isBlank(orderTravelModel.getTravelPlace())) {
			return JsonBean.error("出发地点不能为空!");
		}
		if (orderTravelModel.getTravelTime() == null) {
			return JsonBean.error("出发时间不能为空!");
		}
		String personnels = request.getParameter("personnels");
		List<OrderTravelPersonnel> orderTravelPersonnels = JSON.parseArray(personnels, OrderTravelPersonnel.class);
		orderTravelModel.setOrderTravelStatus(OrderTravelConstant.STATUS_DRAFT);//订单草稿
		HttpSession session = request.getSession();
		String operName = CommonUtil.getContentFromJSON(session.getAttribute(BaseConstant.SYS_USER),"name");
		Integer operId = (Integer) session.getAttribute(BaseConstant.SYS_UID);
		RecordBean<OrderTravelModel> recordBean = travelOrderService.addOrderTravel(pid,orderTravelPersonnels,orderTravelModel,operName,operId);
		if (recordBean.isSuccessCode()) {
			return JsonBean.success("创建草稿订单成功！");
		}
		return JsonBean.error("创建草稿订单失败！");
	}


	/**
	 *	订单数据保存
	 */
	@RequestMapping("/add")
	@ResponseBody
	public JSONObject add(HttpServletRequest request, OrderTravelModel orderTravelModel){
		String pid = request.getParameter("pid");
		if (pid == null) {
			return JsonBean.error("旅游产品信息不能为空！");
		}
		if (StringUtils.isBlank(orderTravelModel.getTravelPlace())) {
			return JsonBean.error("预定用户名字不能为空！");
		}
		if (StringUtils.isBlank(orderTravelModel.getBuyMobile())) {
			return JsonBean.error("预定人手机号不能为空！");
		}
		if (StringUtils.isBlank(orderTravelModel.getTravelPlace())) {
			return JsonBean.error("出发地点不能为空!");
		}
		if (orderTravelModel.getTravelTime() == null) {
			return JsonBean.error("出发时间不能为空!");
		}
		String personnels = request.getParameter("personnels");
		orderTravelModel.setOrderTravelStatus(OrderTravelConstant.STATUS_WAIT_PAY);//订单初始状态
		List<OrderTravelPersonnel> orderTravelPersonnels = CommonUtil.removeNullFromList(JSON.parseArray(personnels, OrderTravelPersonnel.class));
		HttpSession session = request.getSession();
		String operName = CommonUtil.getContentFromJSON(session.getAttribute(BaseConstant.SYS_USER),"name");
		Integer operId = (Integer) session.getAttribute(BaseConstant.SYS_UID);
		RecordBean<OrderTravelModel> recordBean = travelOrderService.addOrderTravel(pid,orderTravelPersonnels,orderTravelModel,operName,operId);
		if (recordBean.isSuccessCode()) {
			return JsonBean.success("创建订单成功！");
		}
		return JsonBean.error("创建订单失败！");
	}
	
	/**
	 * 订单编辑
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/editView")
	public String editView(HttpServletRequest request, Model model){
		String orderId = request.getParameter("orderId");
		OrderTravelSummary orderTravelSummary = travelOrderService.getOrderTravel(orderId);
		List<ProductTravelSummary> list = productTravelService.getTravelSummaryList(null); // 传null代表筛选全部
		model.addAttribute("orderId", orderId);
		model.addAttribute("travelSummary", list);
		model.addAttribute("orderTravelSummary", orderTravelSummary);
		if (orderTravelSummary.getOrderTravelStatus() == OrderTravelStatusConstant.REDUCE.getId() || orderTravelSummary.getOrderTravelStatus() == OrderTravelStatusConstant.WAIT_FINISH.getId() ) { //如果是取消的订单，跳转订单详情页
			return "/modules/travel/order/_info";
		}
		return "/modules/travel/order/_edit";
	}
	
	/**
	 * 获取订单出行人员信息
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/getPersonnels")
	@ResponseBody
	public JSONObject getPersonnels(HttpServletRequest request, Model model){
		String orderId = request.getParameter("orderId");
		List<OrderTravelPersonnelModel>  orderTravelPersonnel = travelOrderService.getOrderTravelPersonnelModel(orderId);
		return JsonBean.success("ok", orderTravelPersonnel);
	}


	/**
	 * 获取订单出行人员信息
	 *
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/getPersonView")
	public String getPersonView(HttpServletRequest request, OrderTravelPersonnelModel orderTravelPersonnel,Model model){
		Integer id = orderTravelPersonnel.getId();
		if (id != null) {
			model.addAttribute("data",travelOrderService.getOrderTravelPersonnelModel(id));
		} else {
			model.addAttribute("data",orderTravelPersonnel);
		}
		return "/modules/travel/order/_editPersonnel";
	}

	/**
	 * 修改订单出行人员信息
	 *
	 * @param request
	 * @param orderTravelPersonnel
	 * @return
	 */
	@RequestMapping("/editPerson")
	@ResponseBody
	public JSONObject editPerson(HttpServletRequest request, OrderTravelPersonnel orderTravelPersonnel){
		RecordBean<OrderTravelPersonnel> result = travelOrderService.editOrderTravelPersonnel(orderTravelPersonnel);
		if (result.isSuccessCode()) {
			return JsonBean.success(result.getMsg());
		}
		return JsonBean.error(result.getMsg());
	}
	/**
	 * 草稿订单编辑
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/editTemp")
	@ResponseBody
	public JSONObject editTemp(HttpServletRequest request, Model model, OrderTravelModel orderTravelModel){
		String pid = request.getParameter("pid");
		String personnels = request.getParameter("personnels");
		if (pid == null) {
			return JsonBean.error("旅游产品信息不能为空！");
		}
		if (StringUtils.isBlank(orderTravelModel.getTravelPlace())) {
			return JsonBean.error("预定用户名字不能为空！");
		}
		if (StringUtils.isBlank(orderTravelModel.getBuyMobile())) {
			return JsonBean.error("预定人手机号不能为空！");
		}
		if (StringUtils.isBlank(orderTravelModel.getTravelPlace())) {
			return JsonBean.error("出发地点不能为空!");
		}
		if (orderTravelModel.getTravelTime() == null) {
			return JsonBean.error("出发时间不能为空!");
		}
		if (Math.abs(orderTravelModel.getOrderTravelStatus()) != 99) {
			return JsonBean.error("非草稿状态下的订单不能保存为草稿订单！");
		}
		List<OrderTravelPersonnel> orderTravelPersonnels = JSON.parseArray(personnels, OrderTravelPersonnel.class);
		RecordBean<OrderTravelModel> recordBean = travelOrderService.updateOrderTravel(pid,orderTravelPersonnels,orderTravelModel);
		if (recordBean.isSuccessCode()) {
			return JsonBean.success("修改订单成功！");
		}
		return JsonBean.error(recordBean.getMsg());
	}

	/**
	 * 订单编辑保存
	 *
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public JSONObject edit(HttpServletRequest request, Model model, OrderTravelModel orderTravelModel){
		String pid = request.getParameter("pid");
		if (pid == null) {
			return JsonBean.error("旅游产品信息不能为空！");
		}
		if (StringUtils.isBlank(orderTravelModel.getTravelPlace())) {
			return JsonBean.error("预定用户名字不能为空！");
		}
		if (StringUtils.isBlank(orderTravelModel.getBuyMobile())) {
			return JsonBean.error("预定人手机号不能为空！");
		}
		if (StringUtils.isBlank(orderTravelModel.getTravelPlace())) {
			return JsonBean.error("出发地点不能为空!");
		}
		if (orderTravelModel.getTravelTime() == null) {
			return JsonBean.error("出发时间不能为空!");
		}
		String personnels = request.getParameter("personnels");
		List<OrderTravelPersonnel> orderTravelPersonnels = JSON.parseArray(personnels, OrderTravelPersonnel.class);
		Integer status = Math.abs(orderTravelModel.getOrderTravelStatus());
		if (status == Math.abs(OrderTravelConstant.STATUS_DRAFT) || status == OrderTravelConstant.STATUS_WAIT_INITIAL) {
			orderTravelModel.setOrderTravelStatus(OrderTravelConstant.STATUS_WAIT_PAY);
		}
		RecordBean<OrderTravelModel> recordBean = travelOrderService.updateOrderTravel(pid,orderTravelPersonnels,orderTravelModel);
		if (recordBean.isSuccessCode()) {
			return JsonBean.success("修改订单成功！");
		}
		return JsonBean.error("修改订单失败！"+recordBean.getMsg());
	}


	/**
	 *	获取支付订单数据
	 */
	@RequestMapping("/payList")
	@ResponseBody
	public JSONObject payList(HttpServletRequest request){
		String orderId = ReqUtils.getParam(request,"orderId",null);
		List<PayInfo> payOrderList = travelOrderService.getPayOrderByOrderId(orderId);
		return JsonBean.success("ok",payOrderList);
	}

	/**
	 * 获取订单退款信息
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/getPayRefundOrderList")
	@ResponseBody
	public JSONObject getPayRefundOrderList(HttpServletRequest request){
		String orderId = request.getParameter("orderId");
		List<PayRefundOrder>  payRefundOrderList = iTravelRefundService.getPayRefundByOrderId(orderId);
		return JsonBean.success("",payRefundOrderList);
	}

	/**
	 * 获取用户的uid
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/getUid")
	@ResponseBody
	public JSONObject getUid(HttpServletRequest request){
		String mobile = request.getParameter("mobile");
		if (StringUtils.isBlank(mobile)) {
			return JsonBean.error("请输入手机号！");
		}
		if (!CommonUtil.isPhoneNumber(mobile)) {
			return JsonBean.error("请输入正确的手机号！");
		}
		Member member = memberService.getMemberByMobile(mobile);
		if (member == null) {
			return JsonBean.error("该手机号用户不是悦龄会会员！");
		}
		return JsonBean.success("成功",member);
	}

	/**
	 * 支付操作
	 * @param request
	 * @return
	 */
	@RequestMapping("/payOrder")
	@ResponseBody
	public JSONObject payOrder(HttpServletRequest request){
		String orderId = ReqUtils.getParam(request,"orderId",null);
		double payAmount = ReqUtils.getParamToDouble(request,"payAmount",0.00);
		String remark = ReqUtils.getParam(request,"remark",null);
		Integer payType = ReqUtils.getParamToInteger(request,"payType",null);
		Integer payWay = ReqUtils.getParamToInteger(request,"payWay",null);
		if (payAmount < 0) {
			return JsonBean.error("收款金额不能是负数！");
		}
		if (StringUtils.isBlank(remark)){
			return JsonBean.error("备注不能是为空！");
		}
		HttpSession session = request.getSession();
		String operName = CommonUtil.getContentFromJSON(session.getAttribute(BaseConstant.SYS_USER),"name");
		Integer operId = (Integer) session.getAttribute(BaseConstant.SYS_UID);
		RecordBean<String> recordBean = travelOrderService.proceeds(orderId,payAmount,payWay,remark,operId,operName);
		if (recordBean.isSuccessCode()) {
			return JsonBean.success(recordBean.getMsg());
		}
		return JsonBean.error(recordBean.getMsg());
	}

	/**
	 * 退款操作
	 * @param request
	 * @return
	 */
	@RequestMapping("/refundOrder")
	@ResponseBody
	public JSONObject refundOrder(HttpServletRequest request,PayRefundOrder payRefundOrder){
		if (payRefundOrder.getAmount().doubleValue() < 0) {
			return JsonBean.error("退款金额不能是负数！");
		}
		String isRefund =ReqUtils.getParam(request,"isRefund",null);
		boolean isRefundCoupon = false;
		if (!StringUtils.isBlank(isRefund)) { // 不退回优惠券
			isRefundCoupon = true;
		}
		HttpSession session = request.getSession();
		String operName = CommonUtil.getContentFromJSON(session.getAttribute(BaseConstant.SYS_USER),"name");
		Integer operId = (Integer) session.getAttribute(BaseConstant.SYS_UID);
		RecordBean<String> result = iTravelRefundService.refund(payRefundOrder.getOrderId(),payRefundOrder.getAmount(),payRefundOrder.getApplyReason(),payRefundOrder.getRemark(),operId,operName,isRefundCoupon);
		if (result.isSuccessCode()) {
			return JsonBean.success(result.getMsg());
		}
		return JsonBean.error(result.getMsg());
	}
	/**
	 * 完成订单
	 * @param request
	 * @return
	 */
	@RequestMapping("/finish")
	@ResponseBody
	public JSONObject finish(HttpServletRequest request){
		HttpSession session = request.getSession();
		String operName = CommonUtil.getContentFromJSON(session.getAttribute(BaseConstant.SYS_USER),"name");
		Integer operId = (Integer) session.getAttribute(BaseConstant.SYS_UID);
		String orderId = ReqUtils.getParam(request,"orderId",null);
//		String remark = ReqUtils.getParam(request,"remark",null);
		RecordBean<String> result = travelOrderService.orderSuccess(orderId,null,operId,operName);
		if (result.isSuccessCode()) {
			return JsonBean.success("成功！");
		}
		return JsonBean.error("操作订单完成失败！");
	}


	/**
	 * 取消订单
	 * @param request
	 * @return
	 */
	@RequestMapping("/reduce")
	@ResponseBody
	public JSONObject reduce(HttpServletRequest request){
		String orderId = ReqUtils.getParam(request,"orderId",null);
		String reason= ReqUtils.getParam(request,"reason",null);
		if (reason == null) {
			return JsonBean.error("请输入取消订单原因！");
		}
		if (reason.length() < 6) {
			return JsonBean.error("取消订单必须大于5哥字符！");
		}
		HttpSession session = request.getSession();
		String operName = CommonUtil.getContentFromJSON(session.getAttribute(BaseConstant.SYS_USER),"name");
		Integer operId = (Integer) session.getAttribute(BaseConstant.SYS_UID);
		String ip = CommonUtil.getIp(request);
		boolean result = travelOrderService.orderCancel(orderId,reason,operId,operName,ip);
		if (result) {
			return JsonBean.success("成功！");
		}
		return JsonBean.error("取消订单失败！");
	}

	/**
	 * 取消订单并退款
	 * @param request
	 * @return
	 */
	@RequestMapping("/reduceAndRefundOrder")
	@ResponseBody
	public JSONObject reduceAndRefundOrder(HttpServletRequest request,PayRefundOrder payRefundOrder){
		if (payRefundOrder.getAmount() == null) {
			return JsonBean.error("请输入退款金额！");
		}
		if (StringUtils.isBlank(payRefundOrder.getApplyReason())) {
			return JsonBean.error("请输入退款原因！");
		}
		String isRefund = ReqUtils.getParam(request,"isRefund",null);
		boolean isRefundCoupon = false;
		if (!StringUtils.isBlank(isRefund)) { // 不退回优惠券
			isRefundCoupon = true;
		}
		HttpSession session = request.getSession();
		String operName = CommonUtil.getContentFromJSON(session.getAttribute(BaseConstant.SYS_USER),"name");
		Integer operId = (Integer) session.getAttribute(BaseConstant.SYS_UID);
		String IP = CommonUtil.getIp(request);
		RecordBean<PayRefundOrder> result = travelOrderService.reduceAndRefundOrder(payRefundOrder,operId,operName,IP,isRefundCoupon);
		if (result.isSuccessCode()) {
			return JsonBean.success("成功！");
		}
		return JsonBean.error(result.getMsg());
	}

	/**
	 * 导出订单
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/export")
	@ResponseBody
	public JSONObject export(HttpServletResponse response, HttpServletRequest request) {
		String pid = ReqUtils.getParam(request,"pid",null);
		String orderId = ReqUtils.getParam(request,"orderId",null);
		String bookTime = ReqUtils.getParam(request,"bookTime", null);
		String mobile = ReqUtils.getParam(request,"mobile",null);
		Integer status = ReqUtils.getParamToInteger(request,"status",null);
		Integer supplierId = ReqUtils.getParamToInteger(request,"supplierId",null);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("pid",pid);
		map.put("orderId",orderId);
		map.put("bookTime",bookTime);
		map.put("mobile",mobile);
		map.put("status",status);
		map.put("supplierId",supplierId);
		response.setContentType("application/binary;charset=utf-8");
		try {
			ServletOutputStream outputStream = response.getOutputStream();
			response.setHeader("Content-disposition", "attachment; filename=TRA_" + DateUtil.formatDateToYYYYMDHMS(new Date()) + ".xlsx");// 组装附件名称和格式
			String[] titles = {"订单编号","产品标题","合作商家","预订人手机号","订单总额", "预定时间", "状态"};
			String[] names = {"orderId", "productName", "supplierName","buyMobile", "totalAmount","createTime","orderTravelStatus"};
			List<OrderTravelSummary> list = travelOrderService.orderTravelSummaryList(map);
			Map<String,Map<Integer,String>> maps = new HashMap<String,Map<Integer,String>>();
			Map<Integer,String> statusMap = new HashMap<Integer,String>();
			OrderTravelStatusConstant[] travelStatusConstants = OrderTravelStatusConstant.values();
			for (int i = 0; i< travelStatusConstants.length; i++ ) {
				statusMap.put(travelStatusConstants[i].getId(),travelStatusConstants[i].getStatus());
			}
			maps.put("orderTravelStatus",statusMap);
			ExportUtil.exportExcel(maps, titles, names, list, outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * （获取订单优惠券信息）
	 * @param request
	 * @return
	 */
	@RequestMapping("/orderCouponInfo")
	public String orderCouponInfo(HttpServletRequest request,Model model){
		String orderId = ReqUtils.getParam(request,"orderId",null);
		OrderTravelSummary orderTravelSummary = travelOrderService.getOrderTravelSummary(orderId);
		model.addAttribute("data",orderTravelSummary);
		return "/modules/travel/order/_addRefund";
	}


	/**
	 * （获取订单优惠券信息）
	 * @param request
	 * @return
	 */
	@RequestMapping("/getCoupon")
	@ResponseBody
	public JSONObject getCoupon(HttpServletRequest request){
		String orderId = ReqUtils.getParam(request,"orderId",null);
		List<MemberCouponModel> memberCouponList = travelOrderService.getMemberCouponModel(orderId);
		return JsonBean.success("success!",memberCouponList);
	}
}
