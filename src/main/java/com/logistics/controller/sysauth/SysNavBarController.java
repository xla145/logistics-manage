package com.logistics.controller.sysauth;

import com.logistics.base.constant.BaseConstant;
import com.logistics.service.auth.IAuthService;
import com.logistics.service.auth.ISysActionService;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 导航菜单
 * 
 * @author caibin
 *
 */
@Controller
@RequestMapping(value = "/sysNavbar")
public class SysNavBarController {

	@Autowired
	private ISysActionService sysActionService;
	@Autowired
	private IAuthService authService;
	
	@RequestMapping(value = "/getNavbar")
	@ResponseBody
	public JSONArray index(HttpServletRequest request, Model model){
		int uid = (Integer)request.getSession().getAttribute(BaseConstant.SYS_UID);
		if(uid == 1){
			return authService.getAllMenus();
		}else{
			return authService.getMenu(uid);
		}
	}
}
