package com.logistics.service.activtiy;

import cn.assist.easydao.pojo.PagePojo;
import com.logistics.base.utils.DataObj;
import com.logistics.service.model.OrderActivityModel;

import java.util.List;
import java.util.Map;

/**
 * 
* @ClassName: ProductActivityOrderService
* @Description: TODO(好玩活动订单)
* @author xla
* @date 2017年7月18日
*
 */
public interface IOrderActivityService {
	/**
	 * 
	* @Title: getProductActivity
	* @Description: TODO(获取产品活动订单列表)
	* @param @param map
	* @param @param pageNo
	* @param @param pageSize
	* @param @return    参数
	* @return PagePojo<ProductActivity>    返回类型
	* @throws
	 */
	public PagePojo<OrderActivityModel> orderActivityPage(Map<String,Object> map, int pageNo, int pageSize); 
	
	/**
	 * 
	* @Title: delProductActivity
	* @Description: TODO(删除产品活动订单信息)
	* @param @param ids
	* @param @return    参数
	* @return DataObj<ProductActivity>    返回类型
	* @throws
	 */
	public DataObj<String> delOrderActivity(String[] ids);
	
	/**
	 * 
	* @Title: updateProductActivity
	* @Description: TODO(更新产品活动订单信息)
	* @param @param productActivity
	* @param @return    参数
	* @return DataObj<ProductActivity>    返回类型
	* @throws
	 */
	public DataObj<OrderActivityModel> updateOrderActivity(OrderActivityModel productOrderActivity);
	/**
	 * 
	* @Title: getProductActivity
	* @Description: TODO(获取产品活动订单 通过订单编号)
	* @param @param productId
	* @param @return    参数
	* @return ProductActivityModel    返回类型
	* @throws
	 */
	public OrderActivityModel getOrderActivity(String orderId);
	
	/***
	 * 
	* @Title: refundsOrderActivity
	* @Description: TODO(退款操作处理)
	* @param @param productOrderActivity
	* @param @return    参数
	* @return DataObj<OrderActivityModel>    返回类型
	* @throws
	 */
	public DataObj<OrderActivityModel> refundsOrderActivity(OrderActivityModel productOrderActivity);

	/**
	 * 获取活动订单
	 * @param map
	 * @return
	 */
	public List<OrderActivityModel> orderActivityList(Map<String, Object> map);
	
}
