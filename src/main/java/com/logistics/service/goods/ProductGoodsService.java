package com.logistics.service.goods;

import cn.assist.easydao.pojo.PagePojo;
import com.logistics.base.utils.DataObj;
import com.logistics.service.model.ProductGoodsModel;
import com.logistics.service.vo.Supplier;

import java.util.Map;

/**
 * The interface Product goods service.
 *
 * @author Administrator
 * @ClassName: ProductGoodsService
 * @Description: TODO(商品业务层)
 * @date 2017年7月19日
 */
public interface ProductGoodsService {
	/**
	 * Product goods page page pojo.
	 *
	 * @param map      the map
	 * @param pageNo   the page no
	 * @param pageSize the page size
	 * @return PagePojo<ProductGoodsModel>      返回类型
	 * @throws
	 * @Title: productGoodsPage
	 * @Description: TODO(获取好物产品信息)
	 */
	public PagePojo<ProductGoodsModel> productGoodsPage(Map<String,Object> map, int pageNo, int pageSize);

	/**
	 * Add product goods data obj.
	 *
	 * @param productGoodsModel the product goods model
	 * @return DataObj<ProductActivity>      返回类型
	 * @throws
	 * @Title: addProductGoods
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	public DataObj<ProductGoodsModel> addProductGoods(ProductGoodsModel productGoodsModel);

	/**
	 * Del product goods data obj.
	 *
	 * @param ids the ids
	 * @return DataObj<ProductActivity>      返回类型
	 * @throws
	 * @Title: delProductGoods
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	public DataObj<String> delProductGoods(String[] ids);

	/**
	 * Update product goods data obj.
	 *
	 * @param productGoodsModel the product goods model
	 * @return DataObj<ProductActivity>      返回类型
	 * @throws
	 * @Title: updateProductActivity
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	public DataObj<ProductGoodsModel> updateProductGoods(ProductGoodsModel productGoodsModel);

	/**
	 * Gets product goods.
	 *
	 * @param productId the product id
	 * @return ProductActivityModel 返回类型
	 * @throws
	 * @Title: getProductActivity
	 * @Description: TODO(获取好玩活动信息通过产品编号)
	 */
	public ProductGoodsModel getProductGoods(String productId);

	/**
	 * Up or down product goods data obj.
	 *
	 * @param ids    the ids
	 * @param status the status
	 * @return DataObj<String>      返回类型
	 * @throws
	 * @Title: upOrDownProductGoods
	 * @Description: TODO(批量上下架)
	 */
	public DataObj<String> upOrDownProductGoods(String[] ids,Integer status);

	/**
	 * Suppiler list list.
	 * @Description: TODO(获取供应商数据)
	 * @param map the map
	 * @return the list
	 */
	public PagePojo<Supplier> supplierPage(Map<String,Object> map,int pageNo, int pageSize);

}
