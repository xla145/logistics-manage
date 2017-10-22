package com.logistics.controller.supplier;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.common.SqlExpr;
import cn.assist.easydao.common.SqlJoin;
import cn.assist.easydao.pojo.PagePojo;
import com.alibaba.fastjson.JSONObject;
import com.logistics.base.constant.ProductGroupTypeConstant;
import com.logistics.base.constant.SettlementTypeConstant;
import com.logistics.base.utils.DataObj;
import com.logistics.base.utils.JsonBean;
import com.logistics.base.utils.PageUtils;
import com.logistics.base.utils.ReqUtils;
import com.logistics.controller.BaseController;
import com.logistics.service.model.ProductGoodsModel;
import com.logistics.service.product.ProductService;
import com.logistics.service.supplier.SupplierService;
import com.logistics.service.vo.Supplier;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * 供应商管理
 * 
 * @author caibin
 *
 */
@Controller
@RequestMapping(value = "/supplier/goods")
public class SupplierGoodsController extends BaseController{

	@Autowired
	private SupplierService supplierService;
	
	@Autowired
	private ProductService productService;
	
	
	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request, Model model){
		model.addAttribute("settlementTypeList", SettlementTypeConstant.values());
		return "modules/supplier/goods/index";
	}
	
	/**
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
		String name = ReqUtils.getParam(request, "name", null);
		Integer status = ReqUtils.getParamToInteger(request, "status", null);
//		Integer type = ReqUtils.getParamToInteger(request, "type", null);

		//查询条件
		Conditions conn = new Conditions("name", SqlExpr.EQUAL, name);
		conn.add(new Conditions("status", SqlExpr.EQUAL, status), SqlJoin.AND);
		conn.add(new Conditions("product_group_type", SqlExpr.EQUAL, ProductGroupTypeConstant.GOODS.getId()), SqlJoin.AND);

		PagePojo<Supplier> page = supplierService.getSupplierPage(conn, pageNo, pageSize);
		//render结果
		return PageUtils.render(request, "WEB-INF/view/modules/supplier/goods", "_list.html", page);
	}
	
	/**
	 * 
	 * 添加数据
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/add")
	@ResponseBody
	public JSONObject save(HttpServletRequest request, Supplier supplier){
		if(StringUtils.isBlank(supplier.getName())){
			return JsonBean.error("合作商名称必填");
		}
		if(StringUtils.isBlank(supplier.getPeople())){
			return JsonBean.error("联系人必填");
		}
		if(StringUtils.isBlank(supplier.getAddress())){
			return JsonBean.error("供货商地址必填");
		}
		if(supplier.getSettType() == null){
			return JsonBean.error("结算方式必填");
		}
//		if(supplier.getProductType() == null){
//			return JsonBean.error("供应商类型必填");
//		}
		supplier.setProductGroupType(ProductGroupTypeConstant.GOODS.getId());
		DataObj<Supplier> result = supplierService.addSupplier(supplier);
		if (result.isSuccessCode()) {
			return JsonBean.success("保存成功！");
		}
		return JsonBean.error("保存失败！");
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
		Integer id = ReqUtils.getParamToInteger(request, "id", null);
		if (id != null) {
			Supplier supplier = supplierService.getSupplier(id);
			PageUtils.render(request, response, "WEB-INF/view/modules/supplier/goods", "_edit.html", supplier);
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
//	@Auth(id = 5)
	@RequestMapping(value = "/edit")
	@ResponseBody
	public JSONObject edit(HttpServletRequest request, Supplier supplier){
		if(StringUtils.isBlank(supplier.getName())){
			return JsonBean.error("合作商名称必填");
		}
		if(StringUtils.isBlank(supplier.getPeople())){
			return JsonBean.error("联系人必填");
		}
		if(StringUtils.isBlank(supplier.getAddress())){
			return JsonBean.error("供货商地址必填");
		}
		if(supplier.getSettType() == null){
			return JsonBean.error("结算方式必填");
		}
//		if(supplier.getProductType() == null){
//			return JsonBean.error("供应商类型必填");
//		}
		DataObj<Supplier> result = supplierService.updateSupplier(supplier);
		if (result.isSuccessCode()) {
			return JsonBean.success("更新成功！");
		}
		return JsonBean.error("更新失败！");
	}
	
//	/**
//	 *
//	 * 获取供应商下的好玩产品
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping(value = "/productPlayList")
//	@ResponseBody
//	public JSONObject productPlayList(HttpServletRequest request){
//		//参数获取
//		int pageNo = ReqUtils.getParamToInt(request, "pageNo", 1);
//		int pageSize = ReqUtils.getParamToInt(request, "pageSize", 15);
//		Integer supplierId = ReqUtils.getParamToInteger(request, "supplierId", null);
//
//		//查询条件
//		PagePojo<ProductTourisnModel> page = productService.getProductTourisnBySuppilerId(supplierId, pageNo, pageSize);
//		//render结果
//		return PageUtils.render(request, "WEB-INF/view/modules/supplier/", "travel_list.html", page);
//	}
	
	/**
	 * 
	 * 获取供应商下的好物产品
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/productGoodsList")
	@ResponseBody
	public JSONObject productGoodsList(HttpServletRequest request){
		//参数获取
		int pageNo = ReqUtils.getParamToInt(request, "pageNo", 1);
		int pageSize = ReqUtils.getParamToInt(request, "pageSize", 15);
		Integer supplierId = ReqUtils.getParamToInteger(request, "supplierId", null);
		PagePojo<ProductGoodsModel> page = productService.getProductGoodsBySupplierId(supplierId, pageNo, pageSize);
		//render结果
		return PageUtils.render(request, "WEB-INF/view/modules/supplier/goods", "goods_list.html", page);
	}
	/**
	 * 
	 * 获取供应商下的产品
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/productIndex")
	public String productIndex(HttpServletRequest request,Model model){
		//参数获取请求的产品类型名称
		Integer supplierId = ReqUtils.getParamToInt(request, "id", 1);
		//render结果
		model.addAttribute("supplierId", supplierId);
		return "modules/supplier/goods/goods_index";
	} 
	
//	@Auth(id = 5)
	@RequestMapping(value = "/del")
	@ResponseBody
	public JSONObject del(HttpServletRequest request){
		String ids = request.getParameter("ids");
		DataObj<String> result= supplierService.delSupplier(ids);
		if (result.isSuccessCode()) {
			return JsonBean.success("删除成功");
		}
		return JsonBean.error("删除失败");
	}
	
//	@Auth(id = 5)
	@RequestMapping(value = "/changeStatus")
	@ResponseBody
	public JSONObject changeStatus(HttpServletRequest request){
		String ids = ReqUtils.getParam(request, "ids", "");
		Integer status = ReqUtils.getParamToInteger(request, "status", 0);
		DataObj<String> result = supplierService.changeStatus(ids,status);
		if (result.isSuccessCode()) {
			return JsonBean.success("状态修改成功");
		}
		return JsonBean.error("状态修改失败");
	}
}
