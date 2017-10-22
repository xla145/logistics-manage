package com.logistics.controller.travel;

import cn.assist.easydao.pojo.PagePojo;
import com.alibaba.fastjson.JSONObject;
import com.logistics.base.utils.*;
import com.logistics.controller.BaseController;
import com.logistics.service.model.ProductTravelModel;
import com.logistics.service.travel.IProductTravelLiveService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 测试
 * 
 * @author caibin
 *
 */
@Controller
@RequestMapping(value = "/travel/live")
public class TravelLiveProductController extends BaseController {

	@Autowired
	private IProductTravelLiveService iProductTravelLiveService;
	
	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request, Model model){
		return "modules/travel/travel-live/index";
	}
	
	@RequestMapping(value = "list")
	@ResponseBody
	public JSONObject list(HttpServletRequest request){
		//参数获取
		int pageNo = ReqUtils.getParamToInt(request, "pageNo", 1);
		int pageSize = ReqUtils.getParamToInt(request, "pageSize", 2);
		String releaseTime = ReqUtils.getParam(request, "releaseTime", null);
		String pid = ReqUtils.getParam(request, "pid", null);
		Integer status = ReqUtils.getParamToInteger(request, "status", null);
		Integer supplierId = ReqUtils.getParamToInteger(request, "supplierId", null);
				
		//查询条件
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("releaseTime", releaseTime);
		map.put("pid", pid);
		map.put("status", status);
		map.put("supplierId", supplierId);
		PagePojo<ProductTravelModel> page = iProductTravelLiveService.productTravelPage(map, pageNo, pageSize);
		return JsonBean.success("", page);
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
	public JSONObject add(HttpServletRequest request, ProductTravelModel productTravelModel){
		String[] departTimes = request.getParameterValues("departTime");
		if (StringUtils.isBlank(productTravelModel.getTitle())) {
			return JsonBean.error("旅游标题不能为空！");
		}
		if (StringUtils.isBlank(productTravelModel.getSubTitle())) {
			return JsonBean.error("旅游副标题不能为空！");
		}
		if ((departTimes == null || StringUtils.isEmpty(departTimes[0])) && StringUtils.isBlank(productTravelModel.getSuggestTime())) {
			return JsonBean.error("出发时间和建议出发时间不能同时为空！");
		}
		if (productTravelModel.getPrice() == null) {
			return JsonBean.error("旅游价格不能为空！");
		}
		if (StringUtils.isNotBlank(productTravelModel.getRemark()) && productTravelModel.getRemark().length() > 250) {
			return JsonBean.error("旅游备注信息长度不能大于250的字符！");
		}
		if (departTimes != null && !StringUtils.isEmpty(departTimes[0])) {
			String departTime = CommonUtil.arrayTimeToString(departTimes);
			productTravelModel.setDepartTime(departTime);
		}
		RecordBean<String> productTravelModelDataObj = iProductTravelLiveService.addProductTravel(productTravelModel);
		if (productTravelModelDataObj.isSuccessCode()) {
			return JsonBean.success(productTravelModelDataObj.getMsg());
		}
		return JsonBean.error(productTravelModelDataObj.getMsg());
	}
	
	/**
	 * 
	 * 编辑页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getView")
	public String editView(HttpServletRequest request, Model model) {
		String pid = ReqUtils.getParam(request, "pid", null);
		ProductTravelModel travelModel = new ProductTravelModel();
		model.addAttribute("data", travelModel);
		if (pid != null) {
			travelModel = iProductTravelLiveService.getProductTravel(pid);
			model.addAttribute("data", travelModel);
			return "modules/travel/travel-live/_edit";
		} else {
			String id = "YL-" + DateUtil.formatDate(new Date(), "YYMM") + CommonUtil.getNumberRandom(4);
			model.addAttribute("pid", id);
			return "modules/travel/travel-live/_add";
		}
	}

	/**
	 *
	 * 查看详情
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/info")
	public String info(HttpServletRequest request, Model model) {
		String pid = ReqUtils.getParam(request, "pid", null);
		ProductTravelModel travelModel = new ProductTravelModel();
//		model.addAttribute("data", travelModel);
		travelModel = iProductTravelLiveService.getProductTravel(pid);
		model.addAttribute("data", travelModel);
		return "modules/travel/travel-live/_info";
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
	public JSONObject edit(HttpServletRequest request, ProductTravelModel productTravelModel){
		String[] departTimes = request.getParameterValues("departTime");
		if (StringUtils.isBlank(productTravelModel.getTitle())) {
			return JsonBean.error("旅游标题不能为空！");
		}
		if ((departTimes == null || StringUtils.isEmpty(departTimes[0])) && StringUtils.isBlank(productTravelModel.getSuggestTime())) {
			return JsonBean.error("出发时间和建议出发时间不能同时为空！");
		}
		if (productTravelModel.getPrice() == null) {
			return JsonBean.error("旅游价格不能为空！");
		}
		if (departTimes != null && !StringUtils.isEmpty(departTimes[0])) {
			String departTime = CommonUtil.arrayTimeToString(departTimes);
			productTravelModel.setDepartTime(departTime);
		}
		DataObj<ProductTravelModel> productTravelModelDataObj = iProductTravelLiveService.updateProductTravel(productTravelModel);
		if (productTravelModelDataObj.isSuccessCode()) {
			return JsonBean.success(productTravelModelDataObj.getMsg());
		}
		return JsonBean.error(productTravelModelDataObj.getMsg());
	}

	/**
	 * 导出旅游模板
	 * @param request
	 */
	@RequestMapping(value = "/importFtl")
	public void importFtl(HttpServletRequest request){}

	/**
	 *
	 * 编辑数据
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/upOrDownProduct")
	@ResponseBody
	public JSONObject upOrDownProduct(HttpServletRequest request){
		Integer status = ReqUtils.getParamToInteger(request,"status",null);
		String[] ids = request.getParameterValues("ids");
		DataObj<String> result = iProductTravelLiveService.upOrDownProductTravel(ids,status);
		if (result.isSuccessCode()) {
			return JsonBean.success(result.getMsg());
		}
		return JsonBean.error(result.getMsg());
	}


	/**
	 *
	 * 编辑数据
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/del")
	@ResponseBody
	public JSONObject del(HttpServletRequest request){
		String[] ids = request.getParameterValues("ids");
		DataObj<String> result = iProductTravelLiveService.delProductTravel(ids);
		if (result.isSuccessCode()) {
			return JsonBean.success(result.getMsg());
		}
		return JsonBean.error(result.getMsg());
	}

	/**
	 * 导出旅游模板
	 * @param request
	 */
	@RequestMapping(value = "/copyProduct")
	@ResponseBody
	public JSONObject copyProduct(HttpServletRequest request){
		String pid = ReqUtils.getParam(request,"pid",null);
		RecordBean recordBean = iProductTravelLiveService.copyProduct(pid);
		if (recordBean.isSuccessCode()) {
			return JsonBean.success("success!");
		}
		return JsonBean.error(recordBean.getMsg());
	}
}
