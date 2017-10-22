package com.logistics.controller.travel;

import cn.assist.easydao.pojo.PagePojo;
import com.alibaba.fastjson.JSONObject;
import com.logistics.base.constant.BaseConstant;
import com.logistics.base.utils.CommonUtil;
import com.logistics.base.utils.DataObj;
import com.logistics.base.utils.JsonBean;
import com.logistics.base.utils.ReqUtils;
import com.logistics.controller.BaseController;
import com.logistics.service.travel.IProductCustomService;
import com.logistics.service.vo.travel.TravelCustom;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * 定制旅游信息
 * @author caibin
 *
 */
@Controller
@RequestMapping(value = "/travel/custom")
public class TravelCustomController extends BaseController {

	@Autowired
	private IProductCustomService iProductCustomService;
	
	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request, Model model){
		return "modules/travel/custom/index";
	}
	
	@RequestMapping(value = "list")
	@ResponseBody
	public JSONObject list(HttpServletRequest request){
		//参数获取
		int pageNo = ReqUtils.getParamToInt(request, "pageNo", 1);
		int pageSize = ReqUtils.getParamToInt(request, "pageSize", 15);
		String createTime = ReqUtils.getParam(request, "createTime", null);
		String name = ReqUtils.getParam(request, "name", null);
		Integer status = ReqUtils.getParamToInteger(request, "status", null);
		Integer source = ReqUtils.getParamToInteger(request, "source", null);
				
		//查询条件
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("createTime", createTime);
		map.put("name", name);
		map.put("status", status);
		map.put("source", source);
		PagePojo<TravelCustom> page = iProductCustomService.productTravelCustomPage(map, pageNo, pageSize);
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
	public JSONObject add(HttpServletRequest request, TravelCustom productTravelCustom){
		if (StringUtils.isBlank(productTravelCustom.getName())) {
			JsonBean.error("名字不能为空！");
		}
		if (StringUtils.isBlank(productTravelCustom.getContact())) {
			JsonBean.error("账号不能为空！");
		}
		if (StringUtils.isBlank(productTravelCustom.getDeparture())) {
			JsonBean.error("出发地不能为空！");
		}
		if (StringUtils.isBlank(productTravelCustom.getDestination())) {
			JsonBean.error("目的地不能为空！");
		}
		if (StringUtils.isBlank(productTravelCustom.getSpecialNeed())) {
			JsonBean.error("特殊需求不能为空！");
		}
		if (productTravelCustom.getRoomNumber() == null) {
			JsonBean.error("房间数不能为空！");
		}
		if (productTravelCustom.getTravelDay() == null) {
			JsonBean.error("出行天数不能为空！");
		}
		if (productTravelCustom.getBudget() == null) {
			JsonBean.error("人均旅行预算不能为空！");
		}
		if (productTravelCustom.getPlanTime() == null) {
			JsonBean.error("计划出行时间不能为空！");
		}
		if (productTravelCustom.getTravelNumber() == null) {
			JsonBean.error("出行人数不能为空！");
		}
		DataObj<TravelCustom> result = iProductCustomService.addProductTravelCustom(productTravelCustom);
		if (result.isSuccessCode()) {
			return JsonBean.success(result.getMsg());
		}
		return JsonBean.error(result.getMsg());
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
		String mid = ReqUtils.getParam(request, "mid", null);
		TravelCustom productTravelCustom = new TravelCustom();
		model.addAttribute("data", productTravelCustom);
		if (mid != null) {
			productTravelCustom = iProductCustomService.getProductTravelCustom(mid);
			model.addAttribute("data", productTravelCustom);
		}
		return "modules/travel/custom/_add";
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
	public JSONObject edit(HttpServletRequest request, TravelCustom productTravelCustom){
		if (StringUtils.isBlank(productTravelCustom.getName())) {
			JsonBean.error("名字不能为空！");
		}
		if (StringUtils.isBlank(productTravelCustom.getContact())) {
			JsonBean.error("账号不能为空！");
		}
		if (StringUtils.isBlank(productTravelCustom.getDeparture())) {
			JsonBean.error("出发地不能为空！");
		}
		if (StringUtils.isBlank(productTravelCustom.getDestination())) {
			JsonBean.error("目的地不能为空！");
		}
		if (StringUtils.isBlank(productTravelCustom.getSpecialNeed())) {
			JsonBean.error("特殊需求不能为空！");
		}
		if (productTravelCustom.getRoomNumber() == null) {
			JsonBean.error("房间数不能为空！");
		}
		if (productTravelCustom.getTravelDay() == null) {
			JsonBean.error("出行天数不能为空！");
		}
		if (productTravelCustom.getBudget() == null) {
			JsonBean.error("人均旅行预算不能为空！");
		}
		if (productTravelCustom.getPlanTime() == null) {
			JsonBean.error("计划出行时间不能为空！");
		}
		if (productTravelCustom.getTravelNumber() == null) {
			JsonBean.error("出行人数不能为空！");
		}
		DataObj<TravelCustom> result = iProductCustomService.updateProductTravelCustom(productTravelCustom);
		if (result.isSuccessCode()) {
			return JsonBean.success(result.getMsg());
		}
		return JsonBean.error(result.getMsg());
	}

	/**
	 *
	 * 删除数据
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/del")
	@ResponseBody
	public JSONObject del(HttpServletRequest request){
		String[] ids = request.getParameterValues("ids");
 		DataObj<String> result = iProductCustomService.delProductTravelCustom(ids);
		if (result.isSuccessCode()) {
			return JsonBean.success(result.getMsg());
		}
		return JsonBean.error(result.getMsg());
	}


	/**
	 *
	 * 删除数据
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/deal")
	@ResponseBody
	public JSONObject deal(HttpServletRequest request){
		String[] ids = request.getParameterValues("ids");
		HttpSession session = request.getSession();
		String operName = CommonUtil.getContentFromJSON(session.getAttribute(BaseConstant.SYS_USER),"name");
		Integer operId = (Integer) session.getAttribute(BaseConstant.SYS_UID);
		DataObj<String> result = iProductCustomService.dealProductTravelCustom(ids,operId,operName);
		if (result.isSuccessCode()) {
			return JsonBean.success(result.getMsg());
		}
		return JsonBean.error(result.getMsg());
	}
}
