package com.logistics.service.auth;

import com.logistics.service.vo.sys.SysRole;
import com.logistics.service.vo.sys.SysRoleAction;
import com.logistics.service.vo.sys.SysUserRole;
import com.logistics.service.vo.sys.TreeNode;

import java.util.List;



/**
 * 系统权限
 * 
 * @author gull
 *
 */
public interface ISysRoleService {
	
	/**
	 * 查询角色列表
	 * 
	 * @param roleId
	 * @return
	 */
	public List<SysRole> getSysRoles();
	
	/**
	 * 查询角色权限
	 * 
	 * @param roleId
	 * @return
	 */
	public List<SysRoleAction> getSysRoleAction(int roleId);
	
	/**
	 * 查询用户角色列表
	 * 
	 * @param uid
	 * @return
	 */
	public List<SysUserRole> getSysUserRole(int uid);
	
	/**
	 * 查询角色权限树
	 * 
	 * @param roleIds
	 * @return
	 */
	public List<TreeNode> getRoleActionTree(List<Integer> roleIds);
	
	/**
	 * 更新角色权限
	 * 
	 * @param roleId
	 * @param sysRoleActions
	 * @return
	 */
	public boolean reloadSysRoleAction(int roleId, int operateUid, List<SysRoleAction> sysRoleActions);

}
