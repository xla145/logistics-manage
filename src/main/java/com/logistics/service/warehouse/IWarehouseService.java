package com.logistics.service.supplier;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.pojo.PagePojo;
import com.logistics.base.utils.DataObj;
import com.logistics.service.vo.Supplier;

import java.util.List;
import java.util.Map;

/**
 * 供应商管理
 */
public interface SupplierService {

	/**
	 * 分页查询 供应商
	 * 
	 * @param pageNo 查询页码
	 * @param pageSize 每页数量
	 * @return
	 */
	public PagePojo<Supplier> getSupplierPage(Map<String,Object> map, int pageNo, int pageSize);
	
	/**
	 * 分页查询 供应商
	 * @param conn 查询条件
	 * @return
	 */
	public List<Supplier> getSupplierList(Conditions conn);

	/**
	 * 获取供应商信息
	 * @param id
	 * @return
	 */
	
	public Supplier getSupplier(Integer id);
	
	/**
	 * 添加供应商信息
	 * 
	 * @param supplier 添加供应商信息
	 * @return
	 */
	public DataObj<Supplier> addSupplier(Supplier supplier);


	/**
	 * 修改供应商信息
	 *
	 * @param supplier 供应商信息
	 * @return
	 */
	public DataObj<Supplier> updateSupplier(Supplier supplier);

	/**
	 * 删除供应商信息
	 * 
	 * @param ids 供应商信息
	 * @return
	 */
	public DataObj<String> delSupplier(String[] ids);


	/**
	 * 供应商状态修改
	 * @param ids
	 * @param status
	 * @return
	 */
	public DataObj<String> changeStatus(String[] ids,Integer status);

}
