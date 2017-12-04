package com.logistics.controller.warehouse;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.common.SqlExpr;
import cn.assist.easydao.common.SqlJoin;
import cn.assist.easydao.pojo.PagePojo;
import com.alibaba.fastjson.JSONObject;
import com.logistics.base.constant.SettlementTypeConstant;
import com.logistics.base.utils.DataObj;
import com.logistics.base.utils.JsonBean;
import com.logistics.base.utils.RecordBean;
import com.logistics.base.utils.ReqUtils;
import com.logistics.controller.BaseController;
import com.logistics.service.product.IProductService;
import com.logistics.service.supplier.ISupplierService;
import com.logistics.service.vo.Product;
import com.logistics.service.vo.Supplier;
import com.logistics.service.vo.Warehouse;
import com.logistics.service.warehouse.IWarehouseService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * 仓库管理
 * 
 * @author caibin
 *
 */
@Controller
@RequestMapping(value = "/warehouse")
public class WarehouseController extends BaseController {

	@Autowired
	private IWarehouseService iWarehouseService;
	
	@Autowired
	private IProductService iproductService;
	
	
	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request, Model model){
		return "modules/warehouse/index";
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

		//查询条件
		Conditions conn = new Conditions("name", SqlExpr.EQUAL, name);
		conn.add(new Conditions("status", SqlExpr.EQUAL, status), SqlJoin.AND);

		PagePojo<Warehouse> page = iWarehouseService.getWarehousePage(conn, pageNo, pageSize);
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
	@RequestMapping(value = "/add")
	@ResponseBody
	public JSONObject save(HttpServletRequest request, Warehouse warehouse){
		if(StringUtils.isBlank(warehouse.getName())){
			return JsonBean.error("仓库名称必填");
		}
		if(StringUtils.isBlank(warehouse.getPrincipal())){
			return JsonBean.error("负责人必填");
		}
		if(StringUtils.isBlank(warehouse.getAddress())){
			return JsonBean.error("仓库地址必填");
		}
		RecordBean<Warehouse> result = iWarehouseService.addWarehouse(warehouse);
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
	@RequestMapping(value = "/getView")
	public String editView(HttpServletRequest request,Model model){
		String wid = ReqUtils.getParam(request, "wid", null);
		Warehouse warehouse = new Warehouse();
		if (StringUtils.isNotBlank(wid)) {
			warehouse = iWarehouseService.getWarehouse(wid);
		}
		model.addAttribute("data",warehouse);
		return "modules/warehouse/_edit";
	}
	
	/**
	 * 
	 * 编辑数据
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edit")
	@ResponseBody
	public JSONObject edit(HttpServletRequest request, Warehouse warehouse){
		if(StringUtils.isBlank(warehouse.getName())){
			return JsonBean.error("仓库名称必填");
		}
		if(StringUtils.isBlank(warehouse.getPrincipal())){
			return JsonBean.error("负责人必填");
		}
		if(StringUtils.isBlank(warehouse.getAddress())){
			return JsonBean.error("仓库地址必填");
		}
		RecordBean<Warehouse> result = iWarehouseService.updateWarehouse(warehouse);
		if (result.isSuccessCode()) {
			return JsonBean.success("更新成功！");
		}
		return JsonBean.error(result.getMsg());
	}

	/**
	 * 
	 * 获取仓库下的好物产品
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/productList")
	@ResponseBody
	public JSONObject productList(HttpServletRequest request){
		//参数获取
		int pageNo = ReqUtils.getParamToInt(request, "pageNo", 1);
		int pageSize = ReqUtils.getParamToInt(request, "pageSize", 15);
		Integer wid = ReqUtils.getParamToInteger(request, "wid", null);
		PagePojo<Product> page = iproductService.getProductGoodsBySupplierId(wid,pageNo,pageSize);
		//render结果
		return JsonBean.success(page);
	}


	/**
	 *
	 * 仓库详情
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/info")
	public String productIndex(HttpServletRequest request,Model model){
		String wid = ReqUtils.getParam(request, "wid", null);
		Warehouse warehouse = new Warehouse();
		if (StringUtils.isNotBlank(wid)) {
			warehouse = iWarehouseService.getWarehouse(wid);
		}
		model.addAttribute("data",warehouse);
		return "modules/warehouse/_info";
	}

	/**
	 * 删除仓库信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/del")
	@ResponseBody
	public JSONObject del(HttpServletRequest request){
		String[] ids = request.getParameterValues("ids");
		RecordBean<String> result= iWarehouseService.delWarehouse(ids);
		if (result.isSuccessCode()) {
			return JsonBean.success("删除成功");
		}
		return JsonBean.error("删除失败");
	}

	/**
	 * 更新仓库状态
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/changeStatus")
	@ResponseBody
	public JSONObject changeStatus(HttpServletRequest request){
		String[] ids = request.getParameterValues("ids");
		Integer status = ReqUtils.getParamToInteger(request, "status", 0);
		RecordBean<String> result = iWarehouseService.changeStatus(ids,status);
		if (result.isSuccessCode()) {
			return JsonBean.success("状态修改成功");
		}
		return JsonBean.error("状态修改失败");
	}
}
