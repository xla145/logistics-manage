package com.logistics.service.auth.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.common.Sort;
import cn.assist.easydao.common.SqlExpr;
import cn.assist.easydao.common.SqlJoin;
import cn.assist.easydao.common.SqlSort;
import cn.assist.easydao.dao.BaseDao;
import cn.assist.easydao.pojo.PagePojo;

import com.logistics.base.utils.CommonUtil;
import com.logistics.base.utils.DateUtil;
import com.logistics.service.auth.IAuthService;
import com.logistics.service.auth.ISysActionService;
import com.logistics.service.vo.sys.SysAction;
import com.logistics.service.vo.sys.SysUserAction;
import com.logistics.service.vo.sys.SysUserRole;
import com.logistics.service.vo.sys.TreeData;
import com.logistics.service.vo.sys.TreeNode;
import org.springframework.transaction.annotation.Transactional;


/**
 * 系统权限
 * 
 * @author gull
 *
 */
@Service("ISysActionService")
public class SysActionService implements ISysActionService {
	
	private Logger logger = LoggerFactory.getLogger(SysActionService.class);

	@Autowired
	private IAuthService authService;
	
	/**
	 * 获取功能树
	 * 
	 */
	public List<TreeNode> getActionTrees() {
		Conditions conn = new Conditions("parent_id", SqlExpr.LT, 1);
		List<SysAction> list = BaseDao.dao.queryForListEntity(SysAction.class, conn);
		List<TreeNode> treeNodes = new ArrayList<TreeNode>();
		if(list != null){
			for (SysAction sysAction : list) {
				TreeNode treeNode = getActionTree(sysAction);
				treeNodes.add(treeNode);
			}
		}
		if(treeNodes.size() > 0){
			treeNodes.get(0).setSpread(true);
		}
		return treeNodes;
	}
	
	/**
	 * 获取功能树
	 * 
	 */
	private TreeNode getActionTree(SysAction sysAction) {
		int id = sysAction.getId();
		TreeNode treeNode = new TreeNode();
		treeNode.setId(id);
		treeNode.setName(sysAction.getName());
		treeNode.setParentId(sysAction.getParentId());
		treeNode.setCheckboxValue(String.valueOf(id));
		TreeData treeData = new TreeData();
		treeData.setId(id);
		treeData.setOrigin(sysAction);
		treeNode.setData(treeData);
		Conditions childrenConn = new Conditions("parent_id", SqlExpr.EQUAL, id);
		childrenConn.add(new Conditions("status",SqlExpr.EQUAL,1),SqlJoin.AND);
		List<SysAction> childrenSysActionList =  BaseDao.dao.queryForListEntity(SysAction.class, childrenConn);
		for (SysAction childrenSysAction : childrenSysActionList) {
			//递归查询所有子节点
			TreeNode tn = getActionTree(childrenSysAction); 
			treeNode.getChildren().add(tn);
		}
		return treeNode;
	}
	
	
	public SysAction getSysAction(int id) {
		return BaseDao.dao.queryForEntity(SysAction.class, id);
	}
	
	/**
	 * 获取父级菜单
	 * 
	 */
	public List<SysAction> getParentMenus() {
		Conditions conn = new Conditions("type", SqlExpr.EQUAL, AuthServiceImpl.ACTION_TYPE_MENU);
		conn.add(new Conditions("parent_id", SqlExpr.LT, 1), SqlJoin.AND);
		return BaseDao.dao.queryForListEntity(SysAction.class, conn);
	}
	
	/**
	 * 查询用户角色权限列表
	 * 
	 * @param
	 * @return
	 */
	public List<SysUserAction> getSysUserAction(int uid) {
		Conditions conn = new Conditions("uid", SqlExpr.EQUAL, uid);
		return BaseDao.dao.queryForListEntity(SysUserAction.class, conn);
	}

	/**
	 * 更新用户权限
	 * 
	 * @param operateUid
	 * @param uid
	 * @param roleIds
	 * @return
	 */
	@Transactional
	public boolean reloadSysUserAction(int operateUid, int uid, List<Integer> roleIds) {
		//清除用户权限
		BaseDao.dao.delete("DELETE FROM `sys_user_action` WHERE uid = " + uid);
		//清除用户角色
		BaseDao.dao.delete("DELETE FROM `sys_user_role` WHERE uid = " + uid); 
		if(roleIds != null && roleIds.size() > 0){
			String remark = "更新角色权限：" +operateUid;
			Date date = new Date();
			List<SysUserRole> list = new ArrayList<SysUserRole>();
			for (int i = 0; i < roleIds.size(); i++) {
				SysUserRole sur = new SysUserRole();
				sur.setRemark(remark);
				sur.setCreateTime(date);
				sur.setRoleId(roleIds.get(i));
				sur.setUid(uid);
				list.add(sur);
			}
			//保存用户角色
			int result = BaseDao.dao.insert(list); 
			if(result < 1){
				logger.warn("用户赋权->保存用户角色失败!operateUid:" + operateUid + ",uid:" + uid + ",roleIds:" + StringUtils.join(roleIds, ","));
				return false;
			}
			
			//保存用户权限
			StringBuffer sql = new StringBuffer("INSERT INTO `sys_user_action`(uid, action_id, role_id, create_time,create_uid,remark)");
			sql.append(" SELECT DISTINCT b.uid AS uid, a.action_id AS action_id, a.role_id as role_id, '" + DateUtil.formatDateToString(date) + "' AS create_time , "+operateUid+" AS create_uid, '"+remark+"' AS remark");
			sql.append(" FROM `sys_role_action` AS a INNER JOIN `sys_user_role` AS b ON a.role_id = b.role_id WHERE b.uid = ? GROUP BY b.uid,a.action_id");

			result = BaseDao.dao.insert(sql.toString(), uid);
			if(result < 1){
				logger.warn("用户赋权->保存用户权限失败!operateUid:" + operateUid + ",uid:" + uid + ",roleIds:" + StringUtils.join(roleIds, ","));
				return false;
			}
		}
		//更新用户权限
		authService.reload(uid);
		return true;
	}
	
	/**
	 * 分页查询系统功能列表
	 * 
	 * @param
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PagePojo<SysAction> getSysAction(Conditions conn, int pageNo, int pageSize){
		conn.add(new Conditions("status",SqlExpr.UNEQUAL,-1),SqlJoin.AND);
		//查询条件
		Sort sort = new Sort("weight", SqlSort.ASC);
		return BaseDao.dao.queryForListPage(SysAction.class, conn, sort, pageNo, pageSize);
	}
	
	
	/**
	 * 添加系统功能
	 * 
	 * @param
	 * @return
	 */
	public boolean addSysAction(SysAction sysAction){
		sysAction.setCreateTime(new Date());
		sysAction.setUpdateTime(new Date());
		sysAction.setWeight(99);
		int i = BaseDao.dao.insert(sysAction);
		return i == 1;
	}
	
	/**
	 * 编辑系统功能
	 * 
	 * @param
	 * @return
	 */
	public boolean editSysAction(SysAction sysAction){
		sysAction.setUpdateTime(new Date());
		int i = BaseDao.dao.update(sysAction);
		return i == 1;
	}

	@Override
	public boolean delSysAction(String[] ids) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE sys_action SET status = -1 ,update_time = now() WHERE id IN (");
		sql.append(CommonUtil.arrayToSqlIn(ids));
		sql.append(")");
		int result = BaseDao.dao.update(sql.toString());
		if (result > 0) {
			return true;
		}
		return false;
	}
}
