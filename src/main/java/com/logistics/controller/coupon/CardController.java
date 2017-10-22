package com.logistics.controller.coupon;

import javax.servlet.http.HttpServletRequest;

import com.logistics.base.utils.RecordBean;
import com.logistics.service.model.CardModel;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.assist.easydao.pojo.PagePojo;

import com.alibaba.fastjson.JSONObject;
import com.logistics.base.utils.JsonBean;
import com.logistics.base.utils.ReqUtils;
import com.logistics.controller.BaseController;
import com.logistics.service.coupon.ICardService;

/**
 * 
 * 代金券--卡包管理
 * @author caibin
 *
 */
@Controller
@RequestMapping(value = "/card")
public class CardController extends BaseController{

	@Autowired
	private ICardService cardService;
	
	/**
	 * 显示代金券列表
	 * @param request
	 * @param model
	 * @return
	 * 
	 * */
	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request, Model model){	
		return "modules/card/index";
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
		PagePojo<CardModel> page = cardService.getCardPage(null, pageNo, pageSize);
		return JsonBean.success("ok", page);
	}
	
	
	/**
	 * 
	 * 添加卡
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/add")
	@ResponseBody
	public JSONObject add(HttpServletRequest request){
		String name = request.getParameter("name");
		String[] couponIds = request.getParameterValues("couponId[]");
		String expireTime = ReqUtils.getParam(request, "expireTime", null);
		String info = request.getParameter("info");
		String remark = request.getParameter("remark");
		
		if(StringUtils.isBlank(name)){
			return JsonBean.error("卡名必填");
		}
		if(StringUtils.isBlank(info)){
			return JsonBean.error("卡信息必填");
		}
		if(couponIds == null || couponIds.length < 1){
			return JsonBean.error("卡代金券必选");
		}
		if(expireTime == null){
			return JsonBean.error("卡过期时间必填");
		}
		boolean result = cardService.addCard(name, info, expireTime, remark, couponIds);
		if(result){
			return JsonBean.success("保存成功");
		}
		return JsonBean.error("添加卡失败");
 	}


	/**
	 *
	 * 添加卡
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edit")
	@ResponseBody
	public JSONObject edit(HttpServletRequest request){
		Integer id = ReqUtils.getParamToInteger(request,"id",null);
		String name = request.getParameter("name");
		String[] couponIds = request.getParameterValues("couponId[]");
		String expireTime = ReqUtils.getParam(request, "expireTime", null);
		String info = request.getParameter("impl");
		String remark = request.getParameter("remark");

		if(StringUtils.isBlank(name)){
			return JsonBean.error("卡名必填");
		}
		if(StringUtils.isBlank(info)){
			return JsonBean.error("卡信息必填");
		}
		if(couponIds == null || couponIds.length < 1){
			return JsonBean.error("卡代金券必选");
		}
		if(expireTime == null){
			return JsonBean.error("卡过期时间必填");
		}
		boolean result = cardService.updateCard(id,name, info, expireTime, remark, couponIds);
		if(result){
			return JsonBean.success("保存成功");
		}
		return JsonBean.error("修改卡失败");
	}

	/**
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getView")
	public String getView (HttpServletRequest request,Model model){
		Integer id = ReqUtils.getParamToInteger(request,"id",null);
		CardModel cardModel = new CardModel();
		if (id != null) {
			cardModel = cardService.getCard(id);
			model.addAttribute("data",cardModel);
		}
		return "modules/card/_edit";
	}
	/**
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/startOrStop")
	@ResponseBody
	public JSONObject getView (HttpServletRequest request){
		String[] ids = request.getParameterValues("ids");
		Integer status = ReqUtils.getParamToInteger(request,"status",null);
		RecordBean recordBean = cardService.startOrStopCard(ids,status);
		if (recordBean.isSuccessCode()) {
			return JsonBean.success(recordBean.getMsg());
		}
		return JsonBean.error(recordBean.getMsg());
	}
}
