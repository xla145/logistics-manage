package com.logistics.service.product;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.logistics.base.utils.DataObj;
import com.logistics.service.model.ProductActivityModel;
import com.logistics.service.model.ProductGoodsModel;
import com.logistics.service.model.ProductTravelModel;
import com.logistics.service.vo.product.Product;
import com.logistics.service.vo.product.ProductImage;
import com.logistics.service.vo.Supplier;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.pojo.PagePojo;
import com.logistics.service.vo.travel.ProductTravel;

/**
 * 产品管理
 * */
public interface ProductService {
	/**
	 * 获取产品信息
	 * */
	public PagePojo<Product> getProductPage(Conditions conn, int pageNo, int pageSize);
	
	/**
	 * 获取产品信息
	 * @param
	 * */
	public JSONObject getProductPage(Map<String,Object> map,HttpServletRequest request, int pageNo, int pageSize);
	
	/**
	 * 根据供应商编号获取好物商品
	 * */
	public PagePojo<ProductGoodsModel> getProductGoodsBySupplierId(Integer id, int pageNo, int pageSize);

	/**
	 * 根据供应商编号获取好玩活动
	 * */
	public PagePojo<ProductActivityModel> getProductActivityBySupplierId(Integer id, int pageNo, int pageSize);

	/**
	 * 根据供应商编号获取好玩活动
	 * */
	public PagePojo<ProductTravelModel> getProductTravelBySupplierId(Integer id, int pageNo, int pageSize);

	
	/**
	 * 获取好活动信息
	 * */
	public PagePojo<ProductActivityModel> getProductActivity(Map<String,Object> map, int pageNo, int pageSize);
	
	/**
	 * 获取好物产品信息
	 * */
	public PagePojo<ProductGoodsModel> getProductGoods(Map<String,Object> map, int pageNo, int pageSize);
	
	/**
	 * 保存产品轮播图
	 * */
	public DataObj<String> batchAddProductImgs(List<ProductImage> listImages,String productId);
	
	/**
	 * 保存产品供应商关系
	 * */
	public DataObj<String> batchAddproductSupplier(List<Supplier> supplierList,Integer productId);

	/**
	 * 根据 catId 获取商品信息
	 * @param catId
	 * @return
	 */
	public List<Product> getProductListByCatId(Integer catId);

	/**
	 * 根据 catId 获取商品信息
	 * @param catId
	 * @return
	 */
	public List<Product> getProductList(Integer catId,String condition);
	
}
