package com.logistics.service.activtiy;

import cn.assist.easydao.pojo.PagePojo;
import com.logistics.base.utils.DataObj;
import com.logistics.service.model.ProductActivityModel;
import com.logistics.service.model.ProductHistoryActivityModel;

import java.util.Map;

/**
 * 
* @ClassName: IProductActivityService
* @Description: TODO(历史活动)
* @author Administrator
* @date 2017年7月18日
*
 */
public interface IProductHistoryActivityService {
	
	/**
	 * 
	* @Title: getProductActivity
	* @Description: TODO(获取好玩活动列表)
	* @param @param map
	* @param @param pageNo
	* @param @param pageSize
	* @param @return    参数
	* @return PagePojo<ProductActivity>    返回类型
	* @throws
	 */
	public PagePojo<ProductHistoryActivityModel> productHistoryActivityPage(Map<String,Object> map, int pageNo, int pageSize);
	
	/**
	 * 
	* @Title: addProductActivity
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param @param productActivity
	* @param @return    参数
	* @return DataObj<ProductActivity>    返回类型
	* @throws
	 */
	public DataObj<ProductHistoryActivityModel> addProductHistoryActivity(ProductHistoryActivityModel productHistoryActivity);
	
	/**
	 * 
	* @Title: delProductActivity
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param @param ids
	* @param @return    参数
	* @return DataObj<ProductActivity>    返回类型
	* @throws
	 */
	public DataObj<String> delProductHistoryActivity(String[] ids);
	
	/**
	 * 
	* @Title: updateProductActivity
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param @param productActivity
	* @param @return    参数
	* @return DataObj<ProductActivity>    返回类型
	* @throws
	 */
	public DataObj<ProductHistoryActivityModel> updateProductHistoryActivity(ProductHistoryActivityModel productHistoryActivity);
	/**
	 * 
	* @Title: getProductActivity
	* @Description: TODO(获取好玩活动信息通过产品编号)
	* @param @param productId
	* @param @return    参数
	* @return ProductActivityModel    返回类型
	* @throws
	 */
	public ProductHistoryActivityModel getProductHistoryActivity(Integer id);
	
	/**
	 * 
	* @Title: upOrDownProduct
	* @Description: TODO(批量上下架)
	* @param @param ids
	* @param @param status
	* @param @return    参数
	* @return DataObj<String>    返回类型
	* @throws
	 */
	public DataObj<String> upOrDownProduct(String[] ids,Integer status);

	/**
	 *
	 * @Title: getProductActivity
	 * @Description: TODO(获取好玩活动列表)
	 * @param @param map
	 * @param @param pageNo
	 * @param @param pageSize
	 * @param @return    参数
	 * @return PagePojo<ProductActivity>    返回类型
	 * @throws
	 */
	public PagePojo<ProductActivityModel> productActivityPage(Map<String,Object> map, int pageNo, int pageSize);
}
