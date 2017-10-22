package com.logistics.service.goods;

import cn.assist.easydao.pojo.PagePojo;
import com.logistics.base.utils.DataObj;
import com.logistics.service.model.OrderGoodsModel;

import java.util.List;
import java.util.Map;

/**
 * 
* @ClassName: OrderGoodsService
* @Description: TODO(好物订单管理)
* @author Administrator
* @date 2017年7月20日
*
 */
public interface OrderGoodsService {
	/**
	 * 
	* @Title: orderActivityPage
	* @Description: TODO(获取好物商品订单)
	* @param @param map
	* @param @param pageNo
	* @param @param pageSize
	* @param @return    参数
	* @return PagePojo<OrderActivityModel>    返回类型
	* @throws
	 */
	public PagePojo<OrderGoodsModel> orderGoodsPage(Map<String, Object> map, int pageNo,int pageSize);
	
	/**
	 * 
	* @Title: updateOrderLogistics
	* @Description: TODO(更新订单物流信息)
	* @param @param orderGoodsModel
	* @param @return    参数
	* @return DataObj<OrderGoodsModel>    返回类型
	* @throws
	 */
	public DataObj<OrderGoodsModel> updateOrderLogistics(OrderGoodsModel orderGoodsModel);
	

	/**
	 * 
	* @Title: getOrderGoodsModel
	* @Description: TODO(获取好物订单信息)
	* @param @param orderId
	* @param @return    参数
	* @return OrderGoodsModel    返回类型
	* @throws
	 */
	public OrderGoodsModel getOrderGoodsModel(String orderId);

	/**
	 *
	 * @param map
	 * @return
	 */
	public List<OrderGoodsModel> orderGoodsList(Map<String, Object> map);

	/**
	 * 添加物流信息
	 * @param courier
	 * @return
	 */
	public DataObj<String> addCourier(String[] courier);

	/**
	 * 根据订单编号找到订单信息
	 * @param ids
	 * @return
	 */
	public List<OrderGoodsModel> orderGoodsList(String ids);

}
