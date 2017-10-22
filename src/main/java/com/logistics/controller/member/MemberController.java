package com.logistics.controller.member;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.common.SqlExpr;
import cn.assist.easydao.pojo.PagePojo;
import com.alibaba.fastjson.JSONObject;
import com.logistics.base.utils.JsonBean;
import com.logistics.base.utils.PageUtils;
import com.logistics.base.utils.RecordBean;
import com.logistics.base.utils.ReqUtils;
import com.logistics.controller.BaseController;
import com.logistics.service.member.MemberService;
import com.logistics.service.model.MemberCouponModel;
import com.logistics.service.model.MemberModel;
import com.logistics.service.vo.member.Member;
import com.logistics.service.vo.member.MemberAddr;
import com.logistics.service.vo.member.MemberFootprints;
import com.logistics.service.vo.member.MemberFootprintsImages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 测试
 * 
 * @author caibin
 *
 */
@Controller
@RequestMapping(value = "/member")
public class MemberController extends BaseController{

	@Autowired
	private MemberService memberService;
	
	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request, Model model){
		return "modules/member/index";
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
		int pageSize = ReqUtils.getParamToInt(request, "pageSize", 15);
		String mobile = ReqUtils.getParam(request, "mobile", null);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("mobile",mobile);
		PagePojo<Member> page = memberService.memberPage(map,pageNo,pageSize);
		//render结果
		return JsonBean.success("success!",page);
	}
	/**
	 * 
	 * 编辑页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/info")
	public String info(HttpServletRequest request, ModelMap modelMap){
		Integer uid = ReqUtils.getParamToInteger(request, "uid", null);
		if (uid == null) {
			return "";
		}
		Member member = memberService.getMember(uid);
		modelMap.addAttribute("data",member);
		return "modules/member/_info";
	}
	
	/**
	 * 
	 * 用户收货地址信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addressList")
	@ResponseBody
	public JSONObject footprintsList(HttpServletRequest request){
		Integer uid = ReqUtils.getParamToInteger(request,"uid",null);
		Conditions conn = new Conditions("uid", SqlExpr.EQUAL,uid);
		List<MemberAddr> memberAddrs = memberService.memberAddrList(conn);
		//render结果
		return JsonBean.success("success!",memberAddrs);
	}

	/**
	 *
	 * 优惠券信息
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/couponList")
	@ResponseBody
	public JSONObject couponList(HttpServletRequest request){
		int pageNo = ReqUtils.getParamToInt(request, "pageNo", 1);
		int pageSize = ReqUtils.getParamToInt(request, "pageSize", 15);
		Integer uid = ReqUtils.getParamToInteger(request,"uid",0);
		Conditions conn = new Conditions("uid",SqlExpr.EQUAL,uid);
		PagePojo<MemberCouponModel> page = memberService.memberCouponPage(conn,pageNo,pageSize);
		//render结果
		return JsonBean.success("success!",page);
	}

	/**
	 *
	 * 优惠券信息
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/editAvatar")
	@ResponseBody
	public JSONObject editAvatar(HttpServletRequest request){
		String avatarPath = "http://yuelinghui.oss-cn-shenzhen.aliyuncs.com/yue-product/IMG-108612-9DC130.png";
		Integer uid = ReqUtils.getParamToInteger(request,"uid",null);
		RecordBean<String> recordBean = memberService.updateAvatar(uid,avatarPath);
		if (!recordBean.isSuccessCode()) {
			return JsonBean.error(recordBean.getMsg());
		}
		return JsonBean.success(recordBean.getMsg());
	}
}
