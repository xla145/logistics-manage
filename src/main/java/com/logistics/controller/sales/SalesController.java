package com.logistics.controller.sales;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.common.SqlExpr;
import cn.assist.easydao.common.SqlJoin;
import cn.assist.easydao.pojo.PagePojo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.logistics.base.utils.CommonUtil;
import com.logistics.base.utils.JsonBean;
import com.logistics.base.utils.RecordBean;
import com.logistics.base.utils.ReqUtils;
import com.logistics.controller.BaseController;
import com.logistics.service.model.ProductModel;
import com.logistics.service.model.ProductStockModel;
import com.logistics.service.model.PurchaseModel;
import com.logistics.service.model.SalesModel;
import com.logistics.service.product.IProductCategoryService;
import com.logistics.service.product.IProductService;
import com.logistics.service.product.IProductStockService;
import com.logistics.service.purchase.IPurchaseService;
import com.logistics.service.sales.ISalesService;
import com.logistics.service.supplier.ISupplierService;
import com.logistics.service.vo.*;
import com.sun.org.apache.xalan.internal.xslt.Process;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 销售管理
 * @author caibin
 *
 */
@Controller
@RequestMapping(value = "/sales")
public class SalesController extends BaseController {

	@Autowired
	private IProductCategoryService iProductCategoryService;
	@Autowired
	private IProductStockService iProductStockService;
	@Autowired
	private ISalesService iSalesService;
	
	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request, Model model){
		return "modules/sales/index";
	}

	/**
	 * 获取销售单商品页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/productIndex")
	public String productIndex(HttpServletRequest request, Model model){
		String sid = ReqUtils.getParam(request,"sid",null);
		model.addAttribute("sid",sid);
		return "modules/sales/product_index";
	}
	
	/**
	 *
	 * 获取销售单列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public JSONObject list(HttpServletRequest request){
		/**
		 * 获取参数
		 * */
		int pageNo = ReqUtils.getParamToInt(request, "pageNo", 1);
		int pageSize = ReqUtils.getParamToInt(request, "pageSize", 15);
		String createTime = ReqUtils.getParam(request, "createTime", null);
				
		//查询条件
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("createTime", createTime);
		PagePojo<Sales> page = iSalesService.getSalesPage(map, pageNo, pageSize);
		
		//render结果
		return JsonBean.success(page);
	}

	/**
	 *
	 * 获取销售单的商品列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/salesProductList")
	@ResponseBody
	public JSONObject salesProductList(HttpServletRequest request){
		/**
		 * 获取参数
		 * */
		int pageNo = ReqUtils.getParamToInt(request, "pageNo", 1);
		int pageSize = ReqUtils.getParamToInt(request, "pageSize", 15);
		String sId = ReqUtils.getParam(request, "sId", null);

		PagePojo<SalesProduct> page = new PagePojo<SalesProduct>();
		//查询条件
		if (StringUtils.isNotBlank(sId)) {
			Conditions conn = new Conditions("s_id", SqlExpr.EQUAL,sId);
			page = iSalesService.getSalesProductPage(conn, pageNo, pageSize);
		}
		//render结果
		return JsonBean.success(page);
	}

	@RequestMapping(value = "/getView")
	public String getView(HttpServletRequest request, Model model){
		String sId = ReqUtils.getParam(request, "sId", null);
		SalesModel salesProduct = new SalesModel();
		if(StringUtils.isNotBlank(sId)){
			salesProduct = iSalesService.getSalesModel(sId);
		}
		model.addAttribute("data",salesProduct);
		return "modules/sales/_add";
	}

	/**
	 *
	 * 获取商品列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/productStockList")
	@ResponseBody
	public JSONObject productStockList(HttpServletRequest request){
		/**
		 * 获取参数
		 * */
		int pageNo = ReqUtils.getParamToInt(request, "pageNo", 1);
		int pageSize = ReqUtils.getParamToInt(request, "pageSize", 15);
		Integer catId = ReqUtils.getParamToInteger(request, "catId", null);
		String pid = ReqUtils.getParam(request, "pid", null);
		String wid = ReqUtils.getParam(request, "wid", null);
		Conditions conn = new Conditions("pid",SqlExpr.EQUAL,pid);
		if (catId != null) {
			conn.add(new Conditions("catId",SqlExpr.IN,iProductCategoryService.getValidSubclass(catId)), SqlJoin.AND);
		}
		conn.add(new Conditions("wid",SqlExpr.EQUAL,wid),SqlJoin.AND);
		PagePojo<ProductStockModel> page = iProductStockService.getProductStockModelPage(conn, pageNo, pageSize);
		//render结果
		return JsonBean.success(page);
	}
	
	/**
	 * 
	 * 添加数据
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject add(HttpServletRequest request, SalesModel salesModel){
		if(salesModel.getBuyUid() == null){
			return JsonBean.error("客户信息必填");
		}
		if(StringUtils.isBlank(salesModel.getSalesName())){
			return JsonBean.error("销售员信息必填");
		}
		String productData = ReqUtils.getParam(request,"productData",null);
		List<SalesProduct> salesProductList = JSON.parseArray(productData, SalesProduct.class);
		if (salesProductList == null || salesProductList.size() == 0) {
			return JsonBean.error("商品信息不能为空");
		}
		salesProductList = CommonUtil.removeNullFromList(salesProductList);
		List<Object> pidList = new ArrayList<Object>();
		for (SalesProduct salesProduct:salesProductList){
			if (pidList.contains(salesProduct.getPid())) {
				return JsonBean.error("商品编号【"+salesProduct.getPid()+"】的商品重复");
			}
			pidList.add(salesProduct.getPid());
		}
		salesModel.setSalesProductList(salesProductList);
		Integer operatorId = ReqUtils.getSysUid(request);
		String operatorName = ReqUtils.getSysName(request);
		RecordBean<SalesModel> data = iSalesService.addSalesModel(salesModel,operatorId,operatorName);
		if(data.isSuccessCode()) {
			return JsonBean.success("修改成功");
		}
		return JsonBean.error(data.getMsg());
	}


	/**
	 * 获取销售单信息
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/info")
	public String info(HttpServletRequest request, Model model){
		String sId = ReqUtils.getParam(request, "sId", null);
		SalesModel salesModel = new SalesModel();
		if(StringUtils.isNotBlank(sId)){
			salesModel = iSalesService.getSalesModel(sId);
		}
		System.out.println(JSON.toJSON(salesModel));
		model.addAttribute("data",salesModel);
		return "modules/sales/_info";
	}


}
