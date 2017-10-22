package com.logistics.controller.activty;

import cn.assist.easydao.pojo.PagePojo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.logistics.base.auth.Auth;
import com.logistics.base.constant.OrderActivityStatusConstant;
import com.logistics.base.utils.*;
import com.logistics.controller.BaseController;
import com.logistics.service.model.OrderActivityModel;
import com.logistics.service.activtiy.IOrderActivityService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 测试
 * @author caibin
 *
 */
@Controller
@RequestMapping(value = "play/activity/order")
public class OrderActivityController extends BaseController{

	@Autowired
	private IOrderActivityService orderActivityService;

	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request, Model model){
		return "modules/activity/order/index";
	}
	
	/**
	 * 
	 * datagrid 数据测试
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public JSONObject data(HttpServletRequest request){
		//参数获取
		int pageNo = ReqUtils.getParamToInt(request, "pageNo", 1);
		int pageSize = ReqUtils.getParamToInt(request, "pageSize", 15);
		String mobile = ReqUtils.getParam(request, "mobile", null);
		String title = ReqUtils.getParam(request,"title",null);
		Integer status =ReqUtils.getParamToInteger(request,"status",null);
		Integer supplierId = ReqUtils.getParamToInteger(request,"supplierId",null);
		String startTime = ReqUtils.getParam(request, "startTime", null);
		String endTime = ReqUtils.getParam(request, "endTime", null);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("mobile",mobile);
		map.put("title",title);
		map.put("status",status);
		map.put("supplierId",supplierId);
		map.put("startTime",startTime);
		map.put("endTime",endTime);
		PagePojo<OrderActivityModel> page = orderActivityService.orderActivityPage(map, pageNo, pageSize);
		//render结果
		return PageUtils.render(request, "WEB-INF/view/modules/activity/order/", "_list.html", page);
	}
	/**
	 * 
	 * 编辑页面
	 * 
	 * @param request
	 * @return
	 */
//	@Auth(id = 4)
	@RequestMapping(value = "/editView")
	@ResponseBody
	public String editView(HttpServletRequest request, HttpServletResponse response){
		String orderId = ReqUtils.getParam(request, "orderId", null);
		if(orderId != null && StringUtils.isNotEmpty(orderId)){
			OrderActivityModel orderActivityModel = orderActivityService.getOrderActivity(orderId);
			PageUtils.render(request, response, "WEB-INF/view/modules/activity/order", "_info.html", orderActivityModel);
		}
		return null;
	}
	
	/**
	 * 
	 * 编辑活动携带人员信息
	 * 
	 * @param request
	 * @return
	 */
	@Auth(id = 5)
	@RequestMapping(value = "/edit")
	@ResponseBody
	public JSONObject edit(HttpServletRequest request, OrderActivityModel orderActivityModel){
		DataObj<OrderActivityModel> result =  orderActivityService.updateOrderActivity(orderActivityModel);
		if (result.isSuccessCode()) {
			return JsonBean.success(result.getMsg());
		}
		return JsonBean.error(result.getMsg());
	}

	/**
	 *
	 * 活动订单退款
	 * @param request
	 * @return
	 */
	@Auth(id = 5)
	@RequestMapping(value = "/refunds")
	@ResponseBody
	public JSONObject refunds(HttpServletRequest request, OrderActivityModel orderActivityModel){
		DataObj<OrderActivityModel> result =  orderActivityService.refundsOrderActivity(orderActivityModel);
		if (result.isSuccessCode()) {
			return JsonBean.success(result.getMsg());
		}
		return JsonBean.error(result.getMsg());
	}

	/**
	 * 导出活动订单信息
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/export")
	public String export(HttpServletResponse response,HttpServletRequest request){
		String mobile = ReqUtils.getParam(request, "mobile", null);
		String title = ReqUtils.getParam(request,"title",null);
		Integer status =ReqUtils.getParamToInteger(request,"status",null);
		Integer supplierId = ReqUtils.getParamToInteger(request,"supplierId",null);
		String startTime = ReqUtils.getParam(request, "startTime", null);
		String endTime = ReqUtils.getParam(request, "endTime", null);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("mobile",mobile);
		map.put("title",title);
		map.put("status",status);
		map.put("supplierId",supplierId);
		map.put("startTime",startTime);
		map.put("endTime",endTime);
		response.setContentType("application/binary;charset=utf-8");
		try
		{
			ServletOutputStream outputStream = response.getOutputStream();
			response.setHeader("Content-disposition", "attachment; filename=ORA_" + DateUtil.formatDateToYYYYMDHMS(new Date()) + ".xlsx");// 组装附件名称和格式
			String[] titles = { "订单编号", "活动标题", "合作商家","下单日期","下单账户","商品金额","订单状态"};
			String[] names = { "orderId", "orderName", "supplierName","createTime","buyMobile","productPrice","orderActivityStatus"};
			List<OrderActivityModel> list = orderActivityService.orderActivityList(map);
			Map<String,Map<Integer,String>> maps = new HashMap<String,Map<Integer,String>>();
			Map<Integer,String> statusMap = new HashMap<Integer,String>();
			OrderActivityStatusConstant[] activityStatus = OrderActivityStatusConstant.values();
			for (int i = 0; i< activityStatus.length; i++ ) {
				statusMap.put(activityStatus[i].getId(),activityStatus[i].getStatus());
			}
			maps.put("orderActivityStatus",statusMap);
			ExportUtil.exportExcel(maps, titles, names, list, outputStream);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
