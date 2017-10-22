package com.logistics.controller.goods;

import cn.assist.easydao.pojo.PagePojo;
import com.alibaba.fastjson.JSONObject;
import com.logistics.base.auth.Auth;
import com.logistics.base.constant.OrderGoodsStatusConstant;
import com.logistics.base.utils.*;
import com.logistics.controller.BaseController;
import com.logistics.service.goods.OrderGoodsService;
import com.logistics.service.model.OrderGoodsModel;
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
 * 
 * @author caibin
 *
 */
@Controller
@RequestMapping(value = "/goods/order")
public class OrderGoodsController extends BaseController{

	@Autowired
	private OrderGoodsService orderGoodsService;
	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request, Model model){	
		return "modules/goods/order/index";
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
		int pageSize = ReqUtils.getParamToInt(request, "pageSize", 5);
		String mobile = ReqUtils.getParam(request, "mobile", null);
		String[] status = request.getParameterValues("orderStatus");
		String createStartTime = ReqUtils.getParam(request, "startTime", null);
		String createEndTime = ReqUtils.getParam(request, "endTime", null);
		Integer supplierId = ReqUtils.getParamToInteger(request, "supplierId", null);
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("mobile", mobile);
		map.put("status", status);
		map.put("createStartTime", createStartTime);
		map.put("createEndTime", createEndTime);
		map.put("supplierId", supplierId);
		PagePojo<OrderGoodsModel> page = orderGoodsService.orderGoodsPage(map, pageNo, pageSize);
		//render结果
		return PageUtils.render(request, "WEB-INF/view/modules/goods/order/", "_list.html", page);
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
		String orderId = ReqUtils.getParam(request, "id", null);
		if(orderId != null){
			OrderGoodsModel orderGoodsModel = orderGoodsService.getOrderGoodsModel(orderId);
			PageUtils.render(request, response, "WEB-INF/view/modules/goods/order/", "_info.html", orderGoodsModel);
		}
		return null;
	}
	
	/**
	 * 
	 * 编辑数据
	 * 
	 * @param request
	 * @return
	 */
	@Auth(id = 5)
	@RequestMapping(value = "/edit")
	@ResponseBody
	public JSONObject edit(HttpServletRequest request, OrderGoodsModel orderGoodsModel){
		DataObj<OrderGoodsModel> result = orderGoodsService.updateOrderLogistics(orderGoodsModel);
		if (result.isSuccessCode()) {
			return JsonBean.success(result.getMsg());
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
	public String export(HttpServletResponse response,HttpServletRequest request) {
		String mobile = ReqUtils.getParam(request, "mobile", null);
		String[] status = request.getParameterValues("orderStatus");
		String createStartTime = ReqUtils.getParam(request, "startTime", null);
		String createEndTime = ReqUtils.getParam(request, "endTime", null);
		Integer supplierId = ReqUtils.getParamToInteger(request, "supplierId", null);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mobile", mobile);
		map.put("status", status);
		map.put("createStartTime", createStartTime);
		map.put("createEndTime", createEndTime);
		map.put("supplierId", supplierId);
		response.setContentType("application/binary;charset=utf-8");
		try {
			ServletOutputStream outputStream = response.getOutputStream();
			response.setHeader("Content-disposition", "attachment; filename=ORG_" + DateUtil.formatDateToYYYYMDHMS(new Date()) + ".xlsx");// 组装附件名称和格式
			String[] titles = {"订单编号","商品名称", "商品编码","数量","供货商名称", "下单日期","下单用户", "收货人", "电话","收货地址", "进货价","进货总价","运费","商品售价","订单总价","支付金额","优惠券金额","物流公司","物流单号","订单状态"};
			String[] names = {"orderId", "productName", "pid", "buyCount","supplierName", "createTime","buyMobile", "adresName", "adresMobile","adresAddr","productOriginalPrice","totalOriginalPrice","freight","productPrice","totalAmount","payCash","payNoncash","courierCompany","courierNumber","orderGoodsStatus"};
			List<OrderGoodsModel> list = orderGoodsService.orderGoodsList(map);
			Map<String,Map<Integer,String>> maps = new HashMap<String,Map<Integer,String>>();
			Map<Integer,String> statusMap = new HashMap<Integer,String>();
			OrderGoodsStatusConstant[] goodsStatus = OrderGoodsStatusConstant.values();
			for (int i = 0; i< goodsStatus.length; i++ ) {
				statusMap.put(goodsStatus[i].getId(),goodsStatus[i].getStatus());
			}
			maps.put("orderGoodsStatus",statusMap);
			ExportUtil.exportExcel(maps, titles, names, list, outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 *
	 * 添加物流信息
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/deliverGoods")
	@ResponseBody
	public JSONObject addCourier(HttpServletRequest request){
		String[] courier = request.getParameterValues("courier");
 		DataObj<String> result = orderGoodsService.addCourier(courier);
		if (result.isSuccessCode()) {
			return JsonBean.success(result.getMsg());
		}
		return JsonBean.error(result.getMsg());
	}

	/***
	 * 发货订单页面
	 */
	@RequestMapping(value = "/ship")
	public String shipView(HttpServletRequest request, Model model){
      String ids = ReqUtils.getParam(request,"ids",null);
      List<OrderGoodsModel> orderGoodsModels = orderGoodsService.orderGoodsList(ids);
	  model.addAttribute("data",orderGoodsModels);
      return "modules/goods/order/ship";
	}

}
