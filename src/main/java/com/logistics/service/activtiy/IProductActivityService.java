package com.logistics.service.activtiy;

import cn.assist.easydao.pojo.PagePojo;
import com.logistics.base.utils.DataObj;
import com.logistics.base.utils.RecordBean;
import com.logistics.service.model.ProductActivityModel;

import java.util.Map;

/**
 * 
* @ClassName: IProductActivityService
* @Description: TODO(这里用一句话描述这个类的作用)
* @author Administrator
* @date 2017年7月18日
*
 */
public interface IProductActivityService {
	
	/**
	 * 
	* @Title: getProductActivity
	* @Description: (获取好玩活动列表)
	* @param @param map
	* @param @param pageNo
	* @param @param pageSize
	* @param @return    参数
	* @return PagePojo<ProductActivity>    返回类型
	* @throws
	 */
	public PagePojo<ProductActivityModel> getProductActivity(Map<String,Object> map, int pageNo, int pageSize); 
	
	/**
	 * 
	* @Title: addProductActivity
	* @Description: (添加活动信息)
	* @param @param productActivity
	* @param @return    参数
	* @return DataObj<ProductActivity>    返回类型
	* @throws
	 */
	public RecordBean<ProductActivityModel> addProductActivity(ProductActivityModel productActivity);
	
	/**
	 * 
	* @Title: delProductActivity
	* @Description: (这里用一句话描述这个方法的作用)
	* @param @param ids
	* @param @return    参数
	* @return DataObj<ProductActivity>    返回类型
	* @throws
	 */
	public RecordBean<String> delProductActivity(String[] ids);
	
	/**
	 * 
	* @Title: updateProductActivity
	* @Description: (修改活动信息)
	* @param @param productActivity
	* @param @return    参数
	* @return DataObj<ProductActivity>    返回类型
	* @throws
	 */
	public RecordBean<ProductActivityModel> updateProductActivity(ProductActivityModel productActivity);
	/**
	 * 
	* @Title: getProductActivity
	* @Description: (获取好玩活动信息通过产品编号)
	* @param @param productId
	* @param @return    参数
	* @return ProductActivityModel    返回类型
	* @throws
	 */
	public ProductActivityModel getProductActivity(String productId);
	
	/**
	 * 
	* @Title: upOrDownProduct
	* @Description: (批量上下架)
	* @param @param ids
	* @param @param status
	* @param @return    参数
	* @return DataObj<String>    返回类型
	* @throws
	 */
	public RecordBean<String> upOrDownProduct(String[] ids,Integer status);

	/**
	 * 复制 活动产品信息
	 * @param pid
	 * @return
	 */
	public RecordBean<String> copyProductActivity(String pid);
}
