package com.logistics.controller.member;

import cn.assist.easydao.pojo.PagePojo;
import com.alibaba.fastjson.JSONObject;
import com.logistics.base.utils.JsonBean;
import com.logistics.base.utils.PageUtils;
import com.logistics.base.utils.ReqUtils;
import com.logistics.controller.BaseController;
import com.logistics.service.member.MemberOpinionService;
import com.logistics.service.model.MemberOpinionModel;
import com.logistics.service.vo.member.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
@RequestMapping(value = "/memberOpinion")
public class MemberOpinionController extends BaseController{

	@Autowired
	private MemberOpinionService memberOpinionService;
	
	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request, Model model){
		return "modules/member/opinion/index";
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
		String createTime = ReqUtils.getParam(request,"createTime",null);//反馈时间
		Integer uid  = ReqUtils.getParamToInteger(request,"uid",null);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("mobile",mobile);
		map.put("createTime",createTime);
		map.put("uid",uid);
		PagePojo<MemberOpinionModel> page = memberOpinionService.memberOpinionPage(map,pageNo,pageSize);
		//render结果
		return JsonBean.success("success",page);
	}
	/**
	 * 
	 * 编辑页面
	 * 
	 * @param request
	 * @return
	 */
//	@Auth(id = 4)
	@RequestMapping(value = "/info")
	public String info(HttpServletRequest request,Model model){
		Integer id = ReqUtils.getParamToInteger(request, "id", null);
		if(id != null){
			Member member = memberOpinionService.getMemberOpinion(id);
			model.addAttribute("data",member);
			return "/modules/member/opinion/_info";
		}
		return null;
	}
}
