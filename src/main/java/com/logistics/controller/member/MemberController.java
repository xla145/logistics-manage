package com.logistics.controller.member;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.common.SqlExpr;
import cn.assist.easydao.pojo.PagePojo;
import com.alibaba.fastjson.JSONObject;
import com.logistics.base.constant.BaseConstant;
import com.logistics.base.utils.JsonBean;
import com.logistics.base.utils.MD5;
import com.logistics.base.utils.RecordBean;
import com.logistics.base.utils.ReqUtils;
import com.logistics.controller.BaseController;
import com.logistics.service.member.IMemberService;
import com.logistics.service.sys.sysuser.ISysUserService;
import com.logistics.service.vo.Member;
import com.logistics.service.vo.sys.SysAction;
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
 * 系统用户管理
 * 
 * @author caibin
 *
 */
@Controller
@RequestMapping(value = "/member")
public class MemberController extends BaseController{

	@Autowired
	private IMemberService imemberService;
	
	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request, Model model){
		return "modules/member/index";
	}
	
	/**
	 * 
	 * 获取客户数据
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public JSONObject data(HttpServletRequest request, SysAction.SysUser sysUser){
		//参数获取
		int pageNo = ReqUtils.getParamToInt(request, "pageNo", 1);
		int pageSize = ReqUtils.getParamToInt(request, "pageSize", 15);

		String mobile = ReqUtils.getParam(request,"mobile",null);

		Conditions conn = new Conditions("mobile", SqlExpr.EQUAL,mobile);

		PagePojo<Member> page = imemberService.getMemberPage(conn, pageNo, pageSize);
		
		//render结果
		return JsonBean.success(page);
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
	public JSONObject add(HttpServletRequest request, Member member){
		if(StringUtils.isBlank(member.getName())){
			return JsonBean.error("用户名必填");
		}
		if(StringUtils.isBlank(member.getMobile())){
			return JsonBean.error("手机号必填");
		}
		RecordBean<Member> memberRecordBean = imemberService.addMember(member);
		if (memberRecordBean.isSuccessCode()) {
			return JsonBean.success(memberRecordBean.getMsg());
		}
		return JsonBean.error(memberRecordBean.getMsg());
	}
	
	/**
	 * 
	 * 编辑页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/editView")
	public String editView(HttpServletRequest request, Model model){
		Integer uid = ReqUtils.getParamToInteger(request, "uid", null);
		Member member = new Member();
		if(uid != null){
			member = imemberService.getMember(uid);
		}
		model.addAttribute("data",member);
		return  "modules/member/_edit";
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
	public JSONObject edit(HttpServletRequest request, Member member){
		if(StringUtils.isBlank(member.getName())){
			return JsonBean.error("用户名必填");
		}
		if(StringUtils.isBlank(member.getMobile())){
			return JsonBean.error("手机号必填");
		}
		RecordBean<Member> memberRecordBean = imemberService.updateMember(member);
		if (memberRecordBean.isSuccessCode()) {
			return JsonBean.success(memberRecordBean.getMsg());
		}
		return JsonBean.error(memberRecordBean.getMsg());
	}

//	/**
//	 *
//	 * 删除用户数据
//	 *
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping(value = "/del")
//	@ResponseBody
//	public JSONObject del(HttpServletRequest request){
//		String[] ids = request.getParameterValues("ids");
// 		int result = imemberService.delSysUsers(ids);
//		return result > 0 ?JsonBean.success("删除成功") : JsonBean.error("删除失败");
//	}
}
