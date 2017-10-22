package com.logistics.controller.imageText;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.common.SqlExpr;
import cn.assist.easydao.pojo.PagePojo;
import com.alibaba.fastjson.JSONObject;
import com.logistics.base.utils.JsonBean;
import com.logistics.base.utils.RecordBean;
import com.logistics.base.utils.ReqUtils;
import com.logistics.controller.BaseController;
import com.logistics.service.imageText.IImageTextGroupService;
import com.logistics.service.imageText.IImageTextItemService;
import com.logistics.service.vo.imageText.ImageTextGroup;
import com.logistics.service.vo.imageText.ImageTextItem;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * 图文链接 - 管理
 * @author caibin
 *
 */
@Controller
@RequestMapping(value = "/imageTextItem")
public class ImageTextItemController extends BaseController{

	@Autowired
	private IImageTextItemService iImageTextItemService;
	@Autowired
	private IImageTextGroupService iImageTextGroupService;
	
	/**
	 * 显示代金券列表
	 * @param request
	 * @param model
	 * @return
	 * 
	 * */
	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request, Model model){	
		Integer groupId = ReqUtils.getParamToInteger(request,"groupId",null);
		ImageTextGroup imageTextGroup = iImageTextGroupService.getImageTextGroup(groupId);
		model.addAttribute("data",imageTextGroup);
		return "modules/image-text/item_index";
	}
	
	/**
	 * 卡列表分页数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public JSONObject data(HttpServletRequest request){
		//参数获取
		int pageNo = ReqUtils.getParamToInt(request, "pageNo", 1);
		int pageSize = ReqUtils.getParamToInt(request, "pageSize", 5);
		Integer groupId = ReqUtils.getParamToInteger(request,"groupId",null);
		Conditions conn = new Conditions("group_id", SqlExpr.EQUAL,groupId);
		PagePojo<ImageTextItem> page = iImageTextItemService.getImageTextItemPage(conn, pageNo, pageSize);
		return JsonBean.success("ok", page);
	}
	
	
	/**
	 * 
	 * 添加图文信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/add")
	@ResponseBody
	public JSONObject add(HttpServletRequest request,ImageTextItem imageTextItem){
		Integer groupId = ReqUtils.getParamToInteger(request,"groupId",null);
		String groupCode = ReqUtils.getParam(request,"groupCode",null);
		if (StringUtils.isBlank(imageTextItem.getTitle()) && StringUtils.isBlank(imageTextItem.getImgUrl())) {
			return JsonBean.error("文字标题和图片不能同时为空！");
		}
		if (imageTextItem.getWeight() == null) {
			return JsonBean.error("权重不能为空！");
		}
		imageTextItem.setGroupCode(groupCode);
		imageTextItem.setGroupId(groupId);
		RecordBean recordBean = iImageTextItemService.addImageTextItem(imageTextItem);
		if (recordBean.isSuccessCode()) {
			return JsonBean.success("success!");
		}
		return JsonBean.error(recordBean.getMsg());
 	}


	/**
	 *
	 * 添加卡
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edit")
	@ResponseBody
	public JSONObject edit(HttpServletRequest request,ImageTextItem imageTextItem){
		Integer groupId = ReqUtils.getParamToInteger(request,"groupId",null);
		String groupCode = ReqUtils.getParam(request,"groupCode",null);
		if (StringUtils.isBlank(imageTextItem.getTitle()) && StringUtils.isBlank(imageTextItem.getImgUrl())) {
			return JsonBean.error("文字标题和图片不能同时为空！");
		}
		if (imageTextItem.getWeight() == null) {
			return JsonBean.error("权重不能为空！");
		}
		imageTextItem.setGroupCode(groupCode);
		imageTextItem.setGroupId(groupId);
		RecordBean recordBean = iImageTextItemService.updateImageTextItem(imageTextItem);
		if (recordBean.isSuccessCode()) {
			return JsonBean.success("success!");
		}
		return JsonBean.error(recordBean.getMsg());
	}

	/**
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getView")
	public String getView (HttpServletRequest request,Model model){
		Integer id = ReqUtils.getParamToInteger(request,"id",null);
		ImageTextItem imageTextItem = new ImageTextItem();
		if (id != null) {
			imageTextItem = iImageTextItemService.getImageTextItem(id);
		}
		model.addAttribute("data",imageTextItem);
		return "modules/image-text/_add_item";
	}

	/**
	 *
	 * 添加卡
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/del")
	@ResponseBody
	public JSONObject del(HttpServletRequest request){
		Integer id = ReqUtils.getParamToInteger(request,"id",null);
		RecordBean recordBean = iImageTextItemService.delImageTextItem(id);
		if (recordBean.isSuccessCode()) {
			return JsonBean.success("success!");
		}
		return JsonBean.error(recordBean.getMsg());
	}
}
