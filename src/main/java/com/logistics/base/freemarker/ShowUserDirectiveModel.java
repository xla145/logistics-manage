package com.logistics.base.freemarker;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.logistics.service.vo.sys.SysAction;
import org.springframework.beans.factory.annotation.Autowired;

import com.logistics.base.constant.BaseConstant;
import com.logistics.service.sys.sysuser.ISysUserService;

import freemarker.core.Environment;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 显示用户登录名
 * 
 * @author caibin
 *
 */
public class ShowUserDirectiveModel implements TemplateDirectiveModel {

	@Autowired
	private ISysUserService sysUserService;
	@Autowired
	private HttpServletRequest request;
	
	
	@SuppressWarnings("rawtypes") 
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		int uid = (Integer)request.getSession().getAttribute(BaseConstant.SYS_UID);
		SysAction.SysUser sysUser = sysUserService.getSysUser(uid);
		if(sysUser == null){
			return;
		}
		
		if(body != null){
			env.setVariable("sysUser", new BeansWrapper().wrap(sysUser));
	        body.render(env.getOut());
		}else{
			env.getOut().write(sysUser.getRealName());
		}
	}
}
