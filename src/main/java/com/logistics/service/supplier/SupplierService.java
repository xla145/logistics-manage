package com.logistics.service.supplier;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.pojo.PagePojo;
import com.logistics.base.utils.DataObj;
import com.logistics.service.vo.Supplier;

import java.util.List;
import java.util.Map;

/**
 * test
 * 
 * @author caibin
 *
 */
//@Service("CouponService")
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
	 * 
	 * @param pageNo 查询页码
	 * @param pageSize 每页数量
	 * @return
	 */
	public PagePojo<Supplier> getSupplierPage(Conditions conn, int pageNo, int pageSize);

	/**
	 *
	 * @param id
	 * @return
	 */
	
	public Supplier getSupplier(Integer id);
	
	/**
	 * 添加代金券信息
	 * 
	 * @param supplier 代金券信息
	 * @return
	 */
	public DataObj<Supplier> addSupplier(Supplier supplier);


	/**
	 * 修改代金券信息
	 *
	 * @param supplier 代金券信息
	 * @return
	 */
	public DataObj<Supplier> updateSupplier(Supplier supplier);
	/**
	 * 删除代金券信息
	 * 
	 * @param ids 代金券信息
	 * @return
	 */
	public DataObj<String> delSupplier(String ids);


	/**
	 *
	 * @param ids
	 * @param status
	 * @return
	 */
	public DataObj<String> changeStatus(String ids,Integer status);

	/**
	 *
	 * @return
	 */
	public List<Supplier> supplierListByType(Integer type);
	
}
