package com.logistics.controller.coupon;

import cn.assist.easydao.pojo.PagePojo;
import com.alibaba.fastjson.JSONObject;
import com.logistics.base.constant.BaseConstant;
import com.logistics.base.utils.*;
import com.logistics.controller.BaseController;
import com.logistics.service.coupon.CouponMemberService;
import com.logistics.service.model.MemberCouponModel;
import com.logistics.service.vo.coupon.Coupon;
import com.logistics.service.vo.member.Member;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/memberCoupon")
public class CouponMemberController extends BaseController{

	@Autowired
	private CouponMemberService couponMemberService;
	
	/**
	 * 显示代金券列表
	 * @param request
	 * @param model
	 * @return
	 * 
	 * */
	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request, Model model){	
		return "modules/coupon/release/index";
	}
	
	/**
	 * 获取代金券数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public JSONObject list(HttpServletRequest request){
		//参数获取
		int pageNo = ReqUtils.getParamToInt(request, "pageNo", 1);
		int pageSize = ReqUtils.getParamToInt(request, "pageSize", 15);
		String mcid = ReqUtils.getParam(request, "mcid", null);
		String couponName = ReqUtils.getParam(request, "name", null);
		String createTime = ReqUtils.getParam(request, "createTime", null);
		Integer type = ReqUtils.getParamToInteger(request, "type", null);
		Integer source = ReqUtils.getParamToInteger(request,"source",null);
		//查询条件
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("name", couponName);
		map.put("createTime",createTime);
		map.put("mcid",mcid);
		map.put("type",type);
		map.put("source",source);
		PagePojo<MemberCouponModel> page = couponMemberService.getCouponMemberPage(map,pageNo,pageSize);
		//render结果
		return JsonBean.success("success",page);
	}
	
	/**
	 * 
	 * 发放代金券
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/add")
	@ResponseBody
	public JSONObject add(HttpServletRequest request){
		String uid = ReqUtils.getParam(request,"uid",null);
		String cid = ReqUtils.getParam(request,"couponCid",null);
		Integer number = ReqUtils.getParamToInteger(request,"number",null);
		String remark = ReqUtils.getParam(request,"remark",null);
		if(StringUtils.isBlank(cid)){
			return JsonBean.error("优惠券名称不能为空");
		}
		if(StringUtils.isBlank(uid)){
			return JsonBean.error("用户uid不能为空");
		}
		if(number == null || number == 0){
			return JsonBean.error("优惠券发放张数不能为空或不能为0");
		}
		if(StringUtils.isBlank(remark)){
			return JsonBean.error("优惠券发放原因不能为空");
		}
		Integer operatorId = (Integer) request.getSession().getAttribute(BaseConstant.SYS_UID);
		String operatorName = (String) CommonUtil.getContentFromJSON(request.getSession().getAttribute(BaseConstant.SYS_USER),"name");
		DataObj<MemberCouponModel> result = couponMemberService.addCouponMember(cid,uid,number,remark,operatorId,operatorName);
		if (result.isSuccessCode()) {
			return JsonBean.success(result.getMsg());
		}
		return JsonBean.error(result.getMsg());
	}

	@RequestMapping(value = "/couponList")
	@ResponseBody
	public JSONObject couponList(HttpServletRequest request){
		//参数获取
		int pageNo = ReqUtils.getParamToInt(request, "pageNo", 1);
		int pageSize = ReqUtils.getParamToInt(request, "pageSize", 15);
		String couponName = ReqUtils.getParam(request, "name", null);
		String couponType = ReqUtils.getParam(request, "type", null);
		//查询条件
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("name", couponName);
		map.put("type",couponType);
		PagePojo<Coupon> page = couponMemberService.couponPage(map,pageNo,pageSize);
		//render结果
		return JsonBean.success("success!",page);
	}

	@RequestMapping(value = "/memberList")
	@ResponseBody
	public JSONObject memberList(HttpServletRequest request){
		//参数获取
		int pageNo = ReqUtils.getParamToInt(request, "pageNo", 1);
		int pageSize = ReqUtils.getParamToInt(request, "pageSize", 15);
		String mobile = ReqUtils.getParam(request, "mobile", null);
		//查询条件
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("mobile", mobile);
		PagePojo<Member> page = couponMemberService.memberPage(map,pageNo,pageSize);
		//render结果
		return JsonBean.success("success!",page);
	}

	/**
	 *
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/couponIndex")
	public String couponIndex(HttpServletRequest request, Model model){
		return "modules/coupon/release/coupon";
	}

	/**
	 *
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/memberIndex")
	public String memberIndex(HttpServletRequest request, Model model){
		return "modules/coupon/release/member";
	}
}
