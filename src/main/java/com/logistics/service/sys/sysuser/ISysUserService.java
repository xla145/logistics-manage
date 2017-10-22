package com.logistics.service.sys.sysuser;
import com.logistics.base.utils.RecordBean;

import cn.assist.easydao.pojo.PagePojo;
import com.logistics.service.vo.sys.SysAction;

import java.util.Map;

/**
 * 系统用户service
 * 
 * @author caibin
 *
 */
public interface ISysUserService {

	/**系统用户**/
	public static final int USET_TYPE_ROOT = 1;
	
	/**客户**/
	public static final int USET_TYPE_LOWER = 2;

	/**用户无效**/
	public static final int USET_DALID_NO = 0;
	/**用户有效**/
	public static final int USET_DALID_YES = 1;
	
	
	/**
	 * 登录
	 * 
	 * @param username
	 * @param pswd
	 * @return
	 */
	public RecordBean<SysAction.SysUser> login(String username, String pswd);
		
	
	/**
	 * 根据用户名查询用户
	 * 
	 * @param username
	 * @return
	 */
	public boolean getSysUser(String username);
	
	
	/**
	 * 根据uid查询系统用户信息
	 * 
	 * @param username
	 * @param pswd
	 * @return
	 */
	public SysAction.SysUser getSysUser(int uid);
	
	/**
	 * 分页查询系统用户
	 * 
	 * @param sysUser
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PagePojo<SysAction.SysUser> getSysUsers(Map<String,Object> map, int pageNo, int pageSize);
	
	
	/**
	 * 添加系统用户
	 * 
	 * @param sysUser
	 * @return
	 */
	public boolean addSysUsers(SysAction.SysUser sysUser);
	
	/**
	 * 编辑系统用户
	 * 
	 * @param sysUser
	 * @return
	 */
	public boolean editSysUsers(SysAction.SysUser sysUser);

	/**
	 * 删除用户数据
	 * @param ids
	 * @return
	 */
	public int delSysUsers(String[] ids);

	/**
	 * 编辑系统用户
	 *
	 * @param sysUser
	 * @return
	 */
	public boolean updatePwd(SysAction.SysUser sysUser);
}
