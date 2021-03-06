package com.logistics.controller.sysauth;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.logistics.service.vo.sys.SysAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.logistics.base.constant.BaseConstant;
import com.logistics.base.utils.JsonBean;
import com.logistics.base.utils.ReqUtils;
import com.logistics.controller.BaseController;
import com.logistics.service.auth.ISysActionService;
import com.logistics.service.auth.ISysRoleService;
import com.logistics.service.sys.sysuser.ISysUserService;
import com.logistics.service.vo.sys.SysRole;
import com.logistics.service.vo.sys.SysUserRole;
import com.logistics.service.vo.sys.TreeNode;

/**
 * 用户权限管理
 * 
 * @author caibin
 *
 */
@Controller
@RequestMapping(value = "/sysUserAuth")
public class SysUserAuthController extends BaseController{

	@Autowired
	private ISysActionService sysActionService;
	@Autowired
	private ISysRoleService sysRoleService;
	@Autowired
	private ISysUserService sysUserService;
	
	
	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request, Model model){
		int targetUid = ReqUtils.getParamToInt(request, "targetUid", 0);
		SysAction.SysUser sysUser = sysUserService.getSysUser(targetUid);
		if(sysUser == null){
			return renderTips(model, "用户不存在");
		}
		model.addAttribute("sysUser", sysUser);
		List<SysUserRole> userRolelist = sysRoleService.getSysUserRole(targetUid);
		List<SysRole> roleList = sysRoleService.getSysRoles();
		if(userRolelist != null && roleList != null){
			for (SysUserRole sur : userRolelist) {
				for (int i = 0; i < roleList.size(); i++) {
					if(sur.getRoleId() == roleList.get(i).getId()){
						roleList.get(i).setChecked(true);
					}
				}
			}
		}
		
		model.addAttribute("roleList", roleList);
		return "modules/sys-auth/user-auth/index";
	}
	
	//获取角色权限树
	@RequestMapping(value = "/getRoleActionTree")
	@ResponseBody
	public JSONObject getRoleAction(HttpServletRequest request,@RequestParam("roleIds[]") List<Integer> roleIds){
		if(roleIds == null || roleIds.size() < 1){
			return JsonBean.error("未选择角色!");
		}
		List<TreeNode> tn = sysRoleService.getRoleActionTree(roleIds);
		return JsonBean.success("ok", JSONArray.toJSONString(tn));
	}
	
	//获取角色权限树
	@RequestMapping(value = "/authSave")
	@ResponseBody
	public JSONObject authSave(HttpServletRequest request,@RequestParam("roleIds[]") List<Integer> roleIds){
		int operateUid = (Integer)request.getSession().getAttribute(BaseConstant.SYS_UID);
		int targetUid = ReqUtils.getParamToInt(request, "targetUid", 0);
		boolean result = sysActionService.reloadSysUserAction(operateUid,targetUid, roleIds);
		return result ? JsonBean.success("保存用户角色成功") : JsonBean.error("保存用户角色失败");
	}
}
