package com.logistics.controller.imageText;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.common.SqlExpr;
import cn.assist.easydao.common.SqlJoin;
import cn.assist.easydao.pojo.PagePojo;
import com.alibaba.fastjson.JSONObject;
import com.logistics.base.utils.*;
import com.logistics.controller.BaseController;
import com.logistics.service.imageText.IImageTextGroupService;
import com.logistics.service.vo.imageText.ImageTextGroup;
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
 * 图文链接 组管理
 * @author caibin
 *
 */
@Controller
@RequestMapping(value = "/imageTextGroup")
public class ImageTextGroupController extends BaseController{

	@Autowired
	private IImageTextGroupService iImageTextGroupService;
	
	/**
	 * 激活码列表
	 * @param request
	 * @param model
	 * @return
	 *
	 * */
	@RequestMapping(value = "/index")
	public String cdk(HttpServletRequest request, HttpServletResponse response, Model model){
		return "modules/image-text/group_index";
	}
	
	/**
	 * 激活码列表分页数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public JSONObject cdkList(HttpServletRequest request){
		//参数获取
		int pageNo = ReqUtils.getParamToInt(request, "pageNo", 1);
		int pageSize = ReqUtils.getParamToInt(request, "pageSize", 5);
		String code = ReqUtils.getParam(request, "code",null);
		Integer status = ReqUtils.getParamToInteger(request, "status", null);
		Conditions conn = new Conditions("code", SqlExpr.EQUAL,code);
		conn.add(new Conditions("status",SqlExpr.EQUAL,status), SqlJoin.AND);
		PagePojo<ImageTextGroup> page = iImageTextGroupService.getImageTextGroupPage(conn, pageNo, pageSize);
		return JsonBean.success("ok", page);
	}

	/**
	 * 
	 * 添加激活码
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/add")
	@ResponseBody
	public JSONObject add(HttpServletRequest request,ImageTextGroup imageTextGroup){
		if(StringUtils.isBlank(imageTextGroup.getName())){
			return JsonBean.error("组名字不能为空！");
		}
		if(StringUtils.isBlank(imageTextGroup.getCode())){
			return JsonBean.error("组编码不能为空！");
		}
		if(StringUtils.isBlank(imageTextGroup.getPurpose())){
			return JsonBean.error("用途不能为空！");
		}
		RecordBean<ImageTextGroup> result = iImageTextGroupService.addImageTextGroup(imageTextGroup);
		if(result.isSuccessCode()){
			return JsonBean.success("添加图文组成功");
		}
		return JsonBean.error("添加图文组失败");
 	}

	/**
	 *
	 * 添加激活码
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edit")
	@ResponseBody
	public JSONObject edit(HttpServletRequest request,ImageTextGroup imageTextGroup){
		if(StringUtils.isBlank(imageTextGroup.getName())){
			return JsonBean.error("组名字不能为空！");
		}
		if(StringUtils.isBlank(imageTextGroup.getCode())){
			return JsonBean.error("组编码不能为空！");
		}
		if(StringUtils.isBlank(imageTextGroup.getPurpose())){
			return JsonBean.error("用途不能为空！");
		}
		RecordBean<ImageTextGroup> result = iImageTextGroupService.updateImageTextGroup(imageTextGroup);
		if(result.isSuccessCode()){
			return JsonBean.success("修改图文组成功");
		}
		return JsonBean.error(result.getMsg());
	}


	/**
	 *
	 * 获取 组信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getView")
	public String getView(HttpServletRequest request,Model model){
		Integer id = ReqUtils.getParamToInteger(request,"id",null);
		ImageTextGroup imageTextGroup = new ImageTextGroup();
		if (id != null) {
			imageTextGroup = iImageTextGroupService.getImageTextGroup(id);
		}
		model.addAttribute("data",imageTextGroup);
		return "modules/image-text/_add_group";
	}

	/**
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/startOrStop")
	@ResponseBody
	public JSONObject getView (HttpServletRequest request){
		Integer id = ReqUtils.getParamToInteger(request,"id",null);
		Integer status = ReqUtils.getParamToInteger(request,"status",null);
		RecordBean recordBean = iImageTextGroupService.startOrStopGroup(id,status);
		if (recordBean.isSuccessCode()) {
			return JsonBean.success(recordBean.getMsg());
		}
		return JsonBean.error(recordBean.getMsg());
	}

	/**
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/checkCode")
	@ResponseBody
	public JSONObject checkCode (HttpServletRequest request){
		Integer groupId = ReqUtils.getParamToInteger(request,"groupId",null);
		String code = ReqUtils.getParam(request,"code",null);
		RecordBean recordBean = iImageTextGroupService.checkCode(code,groupId);
		if (recordBean.isSuccessCode()) {
			return JsonBean.success(recordBean.getMsg());
		}
		return JsonBean.error(recordBean.getMsg());
	}

}
