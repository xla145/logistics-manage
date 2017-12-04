package com.logistics.service.warehouse;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.pojo.PagePojo;
import com.logistics.base.utils.DataObj;
import com.logistics.base.utils.RecordBean;
import com.logistics.service.vo.Supplier;
import com.logistics.service.vo.Warehouse;

import java.util.List;
import java.util.Map;

/**
 * 仓库管理
 */
public interface IWarehouseService {

	/**
	 * 分页查询 仓库
	 * @param conn
	 * @param pageNo 查询页码
	 * @param pageSize 每页数量
	 * @return
	 */
	public PagePojo<Warehouse> getWarehousePage(Conditions conn, int pageNo, int pageSize);

	/**
	 * 分页查询 仓库
	 * @param conn 查询条件
	 * @return
	 */
	public List<Warehouse> getWarehouseList(Conditions conn);

	/**
	 * 获取供应商信息
	 * @param wid
	 * @return
	 */

	public Warehouse getWarehouse(String wid);

	/**
	 * 添加仓库信息
	 *
	 * @param warehouse
	 * @return
	 */
	public RecordBean<Warehouse> addWarehouse(Warehouse warehouse);


	/**
	 * 修改仓库信息
	 *
	 * @param warehouse 仓库信息
	 * @return
	 */
	public RecordBean<Warehouse> updateWarehouse(Warehouse warehouse);

	/**
	 * 删除仓库信息
	 *
	 * @param ids 仓库信息
	 * @return
	 */
	public RecordBean<String> delWarehouse(String[] ids);


	/**
	 * 仓库信息状态修改
	 * @param ids
	 * @param status
	 * @return
	 */
	public RecordBean<String> changeStatus(String[] ids, Integer status);

}
