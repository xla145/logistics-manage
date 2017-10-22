package com.logistics.controller.activty;
import cn.assist.easydao.pojo.PagePojo;
import com.alibaba.fastjson.JSONObject;
import com.logistics.base.utils.DataObj;
import com.logistics.base.utils.JsonBean;
import com.logistics.base.utils.PageUtils;
import com.logistics.base.utils.ReqUtils;
import com.logistics.controller.BaseController;
import com.logistics.service.model.ProductActivityModel;
import com.logistics.service.model.ProductHistoryActivityModel;
import com.logistics.service.activtiy.IProductHistoryActivityService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 历史活动
 * @author caibin
 *
 */
@Controller
@RequestMapping(value = "/play/history")
public class HistoryActivityController extends BaseController{

	@Autowired
	private IProductHistoryActivityService productHistoryActivityService;
	
	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request, Model model){	
		return "modules/play/activity/history/index";
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
		String startTime = ReqUtils.getParam(request, "startTime", null);
		String endTime = ReqUtils.getParam(request, "endTime", null);
		Integer status = ReqUtils.getParamToInteger(request, "status", null);
		
		//查询条件
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("status", status);
		
		//page数据
		PagePojo<ProductHistoryActivityModel> page = productHistoryActivityService.productHistoryActivityPage(map, pageNo, pageSize);
		
		//render结果
		return PageUtils.render(request, "WEB-INF/view/modules/activtiy/activity/history/", "_list.html", page);
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
	public JSONObject add(HttpServletRequest request, ProductHistoryActivityModel productHistoryActivityModel){
		if(StringUtils.isBlank(productHistoryActivityModel.getHistoryTitle())){
			return JsonBean.error("历史标题必填");
		}
		if(StringUtils.isBlank(productHistoryActivityModel.getHistoryImgUrl())){
			return JsonBean.error("列表页图片不能为空");
		}
		if(StringUtils.isBlank(productHistoryActivityModel.getInfo())){
			return JsonBean.error("活动详情不能为空");
		}
		DataObj<ProductHistoryActivityModel> result = productHistoryActivityService.addProductHistoryActivity(productHistoryActivityModel);
		if (result.isSuccessCode()){
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
//	@Auth(id = 4)
	@RequestMapping(value = "/getView")
	public String editView(HttpServletRequest request, ModelMap modelMap){
		Integer id = ReqUtils.getParamToInteger(request, "id", null);
		ProductHistoryActivityModel productHistoryActivityModel = new ProductHistoryActivityModel();
		if(id != null){
			productHistoryActivityModel = productHistoryActivityService.getProductHistoryActivity(id);
			modelMap.addAttribute("data",productHistoryActivityModel);
		}
		modelMap.addAttribute("data",productHistoryActivityModel);
		return "modules/play/activity/history/_add";
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
	public JSONObject edit(HttpServletRequest request, ProductHistoryActivityModel productHistoryActivityModel){
		if(StringUtils.isBlank(productHistoryActivityModel.getHistoryTitle())){
			return JsonBean.error("历史标题必填");
		}
		if(StringUtils.isBlank(productHistoryActivityModel.getHistoryImgUrl())){
			return JsonBean.error("列表页图片不能为空");
		}
		if(StringUtils.isBlank(productHistoryActivityModel.getInfo())){
			return JsonBean.error("活动详情不能为空");
		}
		DataObj<ProductHistoryActivityModel> result = productHistoryActivityService.updateProductHistoryActivity(productHistoryActivityModel);
		if (result.isSuccessCode()){
			return JsonBean.success(result.getMsg());
		}
		return JsonBean.error(result.getMsg());
	}

	/**
	 * 显示下架活动页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/activityIndex")
	public String activityIndex(HttpServletRequest request, Model model){
		return "modules/play/activity/history/activity_index";
	}

	/**
	 * 显示下架活动页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/activityList")
	@ResponseBody
	public JSONObject activityList(HttpServletRequest request){
		/**
		 * 获取参数
		 * */
		int pageNo = ReqUtils.getParamToInt(request, "pageNo", 1);
		int pageSize = ReqUtils.getParamToInt(request, "pageSize", 15);
		String startTime = ReqUtils.getParam(request, "startTime", null);
		String title = ReqUtils.getParam(request, "title", null);
		//查询条件
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("title", title);
		PagePojo<ProductActivityModel> page = productHistoryActivityService.productActivityPage(map, pageNo, pageSize);

		//render结果
		return PageUtils.render(request, "WEB-INF/view/modules/activtiy/activity/history", "activity_list.html", page);
	}
}
