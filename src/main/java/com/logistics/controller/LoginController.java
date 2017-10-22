package com.logistics.controller;

import com.alibaba.fastjson.JSONObject;
import com.logistics.base.constant.BaseConstant;
import com.logistics.base.utils.JsonBean;
import com.logistics.base.utils.RecordBean;
import com.logistics.base.utils.ReqUtils;
import com.logistics.event.EventModel;
import com.logistics.event.LoginEvent;
import com.logistics.service.sys.sysuser.ISysUserService;
import com.logistics.service.vo.sys.SysAction;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录
 * @author caibin
 *
 */
@Controller
public class LoginController {

	private Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private ISysUserService sysUserService;
	@Autowired
	private ApplicationContext applicationContext;
	
	@RequestMapping(value = "/login", method = {RequestMethod.GET})
	public String loginGet(HttpServletRequest request, Model model){
		return "login";
	}

	/**
	 * 登录post
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/login", method = {RequestMethod.POST})
	@ResponseBody
	public JSONObject loginPost(HttpServletRequest request, HttpServletResponse response, Model model){
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String code = request.getParameter("code");
		if(StringUtils.isBlank(userName)){
			return JsonBean.error("请输入用户名");
		}
		if(StringUtils.isBlank(password)){
			return JsonBean.error("请输入密码");
		}
		if (StringUtils.isBlank(code)) {
			return JsonBean.error("请输入验证码");
		}
		if (!code.equalsIgnoreCase((String) request.getSession().getAttribute("code"))) {
			return JsonBean.error("验证码不正确");
		}
		RecordBean<SysAction.SysUser> recordBean = sysUserService.login(userName, password);
		if(!recordBean.isSuccessCode()){
			return JsonBean.error(recordBean.getMsg());
		}
		//登录成功
		loginSuccess(request, response, recordBean.getData());
		return JsonBean.success("登录成功");
	}
	
	/**
	 * 退出登录
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/loginOut")
	@ResponseBody
	public JSONObject loginOut(HttpServletRequest request, HttpServletResponse response, Model model){
		Object uidObj = request.getSession().getAttribute(BaseConstant.SYS_UID);
		logger.info("用户退出操作........ uid:" + uidObj);
		// 清除session
		request.getSession().removeAttribute(BaseConstant.SYS_UID);
		request.getSession().invalidate();
		return JsonBean.success("退出成功！");
	}
	
	private void loginSuccess(HttpServletRequest request, HttpServletResponse response, SysAction.SysUser sysUser){
		int uid = sysUser.getUid();
		int cid = -1;
		int userType = sysUser.getType();
		//登录成功,存入登录用户UID
		request.getSession().setAttribute(BaseConstant.SYS_UID, uid);
		request.getSession().setMaxInactiveInterval(60*60);
		JSONObject sysUserJSON = (JSONObject) JSONObject.toJSON(sysUser);
		request.getSession().setAttribute(BaseConstant.SYS_USER, sysUserJSON.toJSONString());
		
		String ip = ReqUtils.getUserIp(request);
		String userAgent = request.getHeader("user-agent");
		String referer =  request.getHeader("Referer");
		
		logger.info("用户登录成功,userType：" + userType + ",cid:"+cid+",uid:" + uid + ",username:" + sysUser.getName() + ",ip:" + ip + ",userAgent:" + userAgent);
		
		EventModel eventModel = new EventModel();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("referer",referer);
		param.put("userAgent", userAgent);
		param.put("ip", ip);
		param.put("lastLoginTime", new Date());
		eventModel.setParam(param);
		eventModel.setSysUser(sysUser);
		applicationContext.publishEvent(new LoginEvent(eventModel));  //发布登录事件
	}
	
}
