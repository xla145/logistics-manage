package com.logistics.controller.travel;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.common.SqlExpr;
import cn.assist.easydao.common.SqlJoin;
import cn.assist.easydao.pojo.PagePojo;
import com.alibaba.fastjson.JSONObject;
import com.logistics.base.constant.BaseConstant;
import com.logistics.base.constant.ProductConstant;
import com.logistics.base.utils.CommonUtil;
import com.logistics.base.utils.JsonBean;
import com.logistics.base.utils.RecordBean;
import com.logistics.base.utils.ReqUtils;
import com.logistics.controller.BaseController;
import com.logistics.service.pay.vo.PayRefundOrder;
import com.logistics.service.travel.order.ITravelLiveRefundService;
import com.logistics.service.vo.travel.OrderTravelSummary;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *	退款管理
 *
 * @author caixb
 */
@Controller
@RequestMapping("/travel/live/order/refund")
public class TravelLiveRefundOrderController extends BaseController{

	@Autowired
	private ITravelLiveRefundService iTravelLiveRefundService;


	/**
	 * 获取退款信息页面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String tempView(HttpServletRequest request, Model model){
		return "/modules/travel/travel-live/order/refund/index";
	}
	
	
	/**
	 *	获取订单数据 
	 */
	@RequestMapping("/list")
	@ResponseBody
	public JSONObject tempList(HttpServletRequest request, OrderTravelSummary orderTravelTemp){
		int pageNo = ReqUtils.getParamToInt(request, "page", 1);
		int pageSize = ReqUtils.getParamToInt(request, "limit", 10);
		String orderId = ReqUtils.getParam(request,"orderId",null);
		String mobile = ReqUtils.getParam(request,"mobile",null);
		Integer status = ReqUtils.getParamToInteger(request,"status",null);
		Conditions conn = new Conditions("order_id", SqlExpr.EQUAL,orderId);
		conn.add(new Conditions("mobile",SqlExpr.EQUAL,mobile), SqlJoin.AND);
		conn.add(new Conditions("status",SqlExpr.EQUAL,status), SqlJoin.AND);
		conn.add(new Conditions("cat_id",SqlExpr.EQUAL, ProductConstant.TRAVEL_LIVE), SqlJoin.AND);
		PagePojo<PayRefundOrder> page = iTravelLiveRefundService.refundOrderTravelPage(conn, pageNo, pageSize);
		return JsonBean.success("ok",page);
	}

	/**
	 * 退款操作
	 * @param request
	 * @return
	 */
	@RequestMapping("/audit")
	@ResponseBody
	public JSONObject audit(HttpServletRequest request,PayRefundOrder payRefundOrder){
		if (payRefundOrder.getStatus() == 10 && StringUtils.isBlank(payRefundOrder.getFailReason())) {
			return JsonBean.error("退款失败原因不能为空！");
		}
		HttpSession session = request.getSession();
		String operName = CommonUtil.getContentFromJSON(session.getAttribute(BaseConstant.SYS_USER),"name");
		Integer operId = (Integer) session.getAttribute(BaseConstant.SYS_UID);
		String IP = CommonUtil.getIp(request);
		RecordBean<PayRefundOrder> result = iTravelLiveRefundService.auditRefundOrder(payRefundOrder,operId,operName);
		if (result.isSuccessCode()) {
			return JsonBean.success(result.getMsg());
		}
		return JsonBean.error(result.getMsg());
	}

	/**
	 * 退款操作
	 * @param request
	 * @return
	 */
	@RequestMapping("/editView")
	public String editView(HttpServletRequest request,Model model){
		String proid = ReqUtils.getParam(request,"proid",null);
		PayRefundOrder payRefundOrder = iTravelLiveRefundService.getPayRefundOrder(proid);
		model.addAttribute("data",payRefundOrder);
		return "/modules/travel/order/refund/_auditRefund";
	}
}
