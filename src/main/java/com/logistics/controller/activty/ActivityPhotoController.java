package com.logistics.controller.activty;

import cn.assist.easydao.pojo.PagePojo;
import com.alibaba.fastjson.JSONObject;
import com.logistics.base.constant.BaseConstant;
import com.logistics.base.utils.*;
import com.logistics.controller.BaseController;
import com.logistics.service.model.ActivityDisplayModel;
import com.logistics.service.activtiy.IActivityDisplayService;
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
import java.util.Map;

/**
 * 
 * 好玩活动图片管理
 * 
 * @author caibin
 *
 */
@Controller
@RequestMapping(value = "/play/activity/photo")
public class ActivityPhotoController extends BaseController{

	@Autowired
	private IActivityDisplayService iActivityDisplayService;

	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request, Model model){
		return "modules/activity/photo/index";
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
		String startTime = ReqUtils.getParam(request, "startTime", null);
		String endTime = ReqUtils.getParam(request, "endTime", null);
		Integer status = ReqUtils.getParamToInteger(request, "status", null);
				
		//查询条件
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("status", status);
		PagePojo<ActivityDisplayModel> page = iActivityDisplayService.getProductActivityPhoto(map, pageNo, pageSize);
		
		//render结果
		return PageUtils.render(request, "WEB-INF/view/modules/activity/photo", "_list.html", page);
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
		String id = ReqUtils.getParam(request, "id", null);
		ActivityDisplayModel activityDisplayModel = new ActivityDisplayModel();
		modelMap.addAttribute("data",activityDisplayModel);
		if(id != null){
			activityDisplayModel = iActivityDisplayService.getProductActivityPhoto(id);
			modelMap.addAttribute("data",activityDisplayModel);
		}
		return "modules/activity/photo/_add";
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
	public JSONObject add(HttpServletRequest request, ActivityDisplayModel activityDisplayModel){
		if(StringUtils.isBlank(activityDisplayModel.getTitle())){
			return JsonBean.error("商品标题必填");
		}
		if(activityDisplayModel.getActivityTime() == null){
			return JsonBean.error("活动时间不能为空");
		}
		if(StringUtils.isBlank(activityDisplayModel.getAddr())){
			return JsonBean.error("活动地点必填");
		}
		if(activityDisplayModel.getExpireTime() == null){
			return JsonBean.error("链接有效期不能为空");
		}
		if(activityDisplayModel.getActivityDisplayImgsList() == null || activityDisplayModel.getActivityDisplayImgsList().size() == 0){
			return JsonBean.error("照片不能为空");
		}
		Integer uid = (Integer) request.getSession().getAttribute(BaseConstant.SYS_UID);
		String userName = CommonUtil.getContentFromJSON((String) request.getSession().getAttribute(BaseConstant.SYS_USER),"name");
        activityDisplayModel.setOperatorId(uid);
        activityDisplayModel.setAuthor(userName);
		DataObj<ActivityDisplayModel> data = iActivityDisplayService.addProductActivityPhoto(activityDisplayModel);
		if(data.isSuccessCode()) {
			return JsonBean.success("保存成功");
		}
		return JsonBean.error(data.getMsg());
	}

	/**
	 *
	 * 编辑数据
	 *
	 * @param request
	 * @return
	 */
//	@Auth(id = 5)
	@RequestMapping(value = "/edit",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject edit(HttpServletRequest request, ActivityDisplayModel activityDisplayModel){
		if (activityDisplayModel.getStatus() != null && activityDisplayModel.getStatus() == 0 ){
			return JsonBean.error("状态为无效下不可编辑！");
		}
		if(StringUtils.isBlank(activityDisplayModel.getTitle())){
			return JsonBean.error("商品标题必填");
		}
		if(activityDisplayModel.getActivityTime() == null){
			return JsonBean.error("活动时间不能为空");
		}
		if(StringUtils.isBlank(activityDisplayModel.getAddr())){
			return JsonBean.error("活动地点必填");
		}
		if(activityDisplayModel.getExpireTime() == null){
			return JsonBean.error("链接有效期不能为空");
		}
		if(activityDisplayModel.getActivityDisplayImgsList() == null || activityDisplayModel.getActivityDisplayImgsList().size() == 0){
			return JsonBean.error("照片不能为空");
		}
		Integer uid = (Integer) request.getSession().getAttribute(BaseConstant.SYS_UID);
		String userName = CommonUtil.getContentFromJSON((String) request.getSession().getAttribute(BaseConstant.SYS_USER),"name");
		activityDisplayModel.setOperatorId(uid);
		activityDisplayModel.setAuthor(userName);
		DataObj<ActivityDisplayModel> result = iActivityDisplayService.updateProductActivityPhoto(activityDisplayModel);
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
		DataObj<String> result = iActivityDisplayService.delProductActivityPhoto(ids);
		if (result.isSuccessCode()) {
			return JsonBean.success(result.getMsg());
		}
		return JsonBean.error(result.getMsg());
	}

	/**
	 * 发布活动图片信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/upOrDown")
	@ResponseBody
	public JSONObject upOrDown(HttpServletRequest request){
		String[] ids = request.getParameterValues("ids");
		Integer status = ReqUtils.getParamToInteger(request,"status",null);
		DataObj<String> result = iActivityDisplayService.upOrDownActivtiyDisplay(ids,status);
		if (result.isSuccessCode()) {
			return JsonBean.success(result.getMsg());
		}
		return JsonBean.error(result.getMsg());
	}
}
