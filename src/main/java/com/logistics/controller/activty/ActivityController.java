package com.logistics.controller.activty;

import cn.assist.easydao.pojo.PagePojo;
import com.alibaba.fastjson.JSONObject;
import com.logistics.base.constant.ProductGroupTypeConstant;
import com.logistics.base.utils.*;
import com.logistics.controller.BaseController;
import com.logistics.service.model.ProductActivityModel;
import com.logistics.service.activtiy.IProductActivityService;
import com.logistics.service.supplier.SupplierService;
import com.logistics.service.vo.Supplier;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 好玩活动
 * 
 * @author caibin
 *
 */
@Controller
@RequestMapping(value = "/activity")
public class ActivityController extends BaseController{

	@Autowired
	private IProductActivityService iProductActivityService;
	@Autowired
	private SupplierService supplierService;
	
	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request, Model model){
		return "modules/activity/index";
	}
	
	/**
	 * 
	 * datagrid 数据测试
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public JSONObject data(HttpServletRequest request){
		/**
		 * 获取参数
		 * */
		int pageNo = ReqUtils.getParamToInt(request, "pageNo", 1);
		int pageSize = ReqUtils.getParamToInt(request, "pageSize", 15);
		String releaseTime = ReqUtils.getParam(request, "releaseTime", null);
		String title = ReqUtils.getParam(request, "title", null);
		Integer status = ReqUtils.getParamToInteger(request, "status", null);
				
		//查询条件
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("releaseTime", releaseTime);
		map.put("title", title);
		map.put("status", status);
		PagePojo<ProductActivityModel> page = iProductActivityService.getProductActivity(map, pageNo, pageSize);
		
		//render结果
		return JsonBean.success("success",page);
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
	public JSONObject add(HttpServletRequest request, ProductActivityModel productActivity){
		if(StringUtils.isBlank(productActivity.getTitle())){
			return JsonBean.error("商品标题必填");
		}
		if(productActivity.getPrice() == null){
			return JsonBean.error("活动票价不能为空");
		}
		if(productActivity.getActivityEndTime() != null && productActivity.getActivityStartTime() != null && productActivity.getActivityStartTime().after(productActivity.getActivityEndTime())){
			return JsonBean.error("活动时间开始不能大于活动时间结束时间！");
		}
		if(productActivity.getApplyStartTime() == null){
			return JsonBean.error("报名开始时间不能为空");
		}
		if (productActivity.getApplyEndTime() != null && productActivity.getApplyEndTime().before(productActivity.getApplyStartTime())){
			return JsonBean.error("报名结束时间不能在报名开始时间之前");
		}
		if (productActivity.getApplyEndTime() != null && productActivity.getApplyEndTime().after(productActivity.getActivityStartTime())){
			return JsonBean.error("报名开始时间不能在活动开始之后");
		}
		if(productActivity.getWeight() == null){
			return JsonBean.error("商品权重不能为空");
		}
		if(StringUtils.isBlank(productActivity.getAddress())){
			return JsonBean.error("活动地点必填");
		}
		if(productActivity.getActivityType() == null){
			return JsonBean.error("活动类型必填");
		}
		if(productActivity.getImageList() == null || productActivity.getImageList().size() == 0){
			return JsonBean.error("活动轮播图不能为空");
		}
		RecordBean<ProductActivityModel> data = iProductActivityService.addProductActivity(productActivity);
		if(data.isSuccessCode()) {
			return JsonBean.success("保存成功");
		}
		return JsonBean.error(data.getMsg());
	}
	
	/**
	 * 
	 * 编辑页面
	 * 
	 * @param request
	 * @return
	 */
//	@Auth(id = 4)
	@RequestMapping(value = "/getView")
	public String editView(HttpServletRequest request, ModelMap modelMap){
		String pid = ReqUtils.getParam(request, "pid", null);
		ProductActivityModel product = new ProductActivityModel();
		modelMap.addAttribute("data",product);
		if(pid != null){
			product = iProductActivityService.getProductActivity(pid);
			modelMap.addAttribute("data",product);
		}
		return "modules/activity/_add";
	}


	@RequestMapping(value = "/info")
	public String info(HttpServletRequest request, ModelMap modelMap){
		String pid = ReqUtils.getParam(request, "pid", null);
		ProductActivityModel product = iProductActivityService.getProductActivity(pid);
		modelMap.addAttribute("data",product);
		return "modules/activity/_info";
	}
	
	/**
	 * 
	 * 编辑数据
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edit",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject edit(HttpServletRequest request, ProductActivityModel product){
		if(StringUtils.isBlank(product.getTitle())){
			return JsonBean.error("商品标题必填");
		}
		if(product.getPrice() == null){
			return JsonBean.error("活动票价不能为空");
		}
		if(product.getActivityEndTime() != null && product.getActivityStartTime().after(product.getActivityEndTime())){
			return JsonBean.error("活动时间开始不能大于活动时间结束时间！");
		}
		if(product.getApplyStartTime() == null){
			return JsonBean.error("报名开始时间不能为空");
		}
		if (product.getApplyEndTime() != null && product.getApplyEndTime().before(product.getApplyStartTime())){
			return JsonBean.error("报名结束时间不能在报名开始时间之前");
		}
		if (product.getApplyEndTime() != null &&  product.getApplyEndTime().after(product.getActivityStartTime())){
			return JsonBean.error("报名开始时间不能在活动开始之后");
		}
		if(product.getWeight() == null){
			return JsonBean.error("商品权重不能为空");
		}
		if(StringUtils.isBlank(product.getAddress())){
			return JsonBean.error("活动地点必填");
		}
		if(product.getImageList() == null || product.getImageList().size() == 0){
			return JsonBean.error("活动轮播图不能为空");
		}
		RecordBean<ProductActivityModel> result = iProductActivityService.updateProductActivity(product);
		if (result.isSuccessCode()) {
			return JsonBean.success(result.getMsg());
		}
		return JsonBean.error(result.getMsg());
	}
	
	/**
	 * 
	 * 删除数据
	 * @param request
	 * @return
	 */
//	@Auth(id = 5)
	@RequestMapping(value = "/del")
	@ResponseBody
	public JSONObject del(HttpServletRequest request){
		String[] ids = request.getParameterValues("ids");
		RecordBean<String> result = iProductActivityService.delProductActivity(ids);
		if (result.isSuccessCode()) {
			return JsonBean.success(result.getMsg());
		}
		return JsonBean.error(result.getMsg());
	}
	
	/**
	 * 
	 * 活动上下架
	 * @param request
	 * @return
	 */
//	@Auth(id = 5)
	@RequestMapping(value = "/changeStatus")
	@ResponseBody
	public JSONObject changeStatus(HttpServletRequest request){
		String[] ids = request.getParameterValues("ids");
		Integer status = ReqUtils.getParamToInteger(request, "status",null);
		RecordBean<String> result = iProductActivityService.upOrDownProduct(ids, status);
		if (result.isSuccessCode()) {
			return JsonBean.success(result.getMsg());
		}
		return JsonBean.error(result.getMsg());
	}

	/**
	 * 复制活动产品
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/copyProduct")
	@ResponseBody
	public JSONObject copyProduct(HttpServletRequest request){
		String pid = ReqUtils.getParam(request, "pid",null);
		RecordBean<String> result = iProductActivityService.copyProductActivity(pid);
		if (result.isSuccessCode()) {
			return JsonBean.success(result.getMsg());
		}
		return JsonBean.error(result.getMsg());
	}

}
