package com.logistics.service.product.impl;

import cn.assist.easydao.common.*;
import cn.assist.easydao.dao.BaseDao;
import cn.assist.easydao.pojo.PagePojo;
import com.alibaba.fastjson.JSONObject;
import com.logistics.base.utils.CommonUtil;
import com.logistics.base.utils.DataObj;
import com.logistics.service.model.ProductActivityModel;
import com.logistics.service.model.ProductGoodsModel;
import com.logistics.service.product.ProductService;
import com.logistics.service.model.ProductTravelModel;
import com.logistics.service.product.api.IProductCategoryService;
import com.logistics.service.vo.product.Product;
import com.logistics.service.vo.product.ProductImage;
import com.logistics.service.vo.Supplier;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("ProductService")
public class ProductServiceImpl implements ProductService {

	@Autowired
	private IProductCategoryService iProductCategoryService;

	@Override
	public PagePojo<Product> getProductPage(Conditions conn, int pageNo, int pageSize) {
		return null;
	}

	@Override
	public PagePojo<ProductGoodsModel> getProductGoodsBySupplierId(Integer id, int pageNo, int pageSize) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		params.add(id);
		sql.append("SELECT p.`name`,p.pid,p.title,p.original_price,p.price,pg.unit,pg.goods_type_name,p.`status`  ");
		sql.append("FROM product p JOIN product_goods pg ON(p.pid = pg.pid) ");
		sql.append("WHERE p.supplier_id = ?");
		Sort sort = new Sort("p.create_time",SqlSort.DESC);
		PagePojo<ProductGoodsModel> productGoodsPage = BaseDao.dao.queryForListPage(ProductGoodsModel.class, sql.toString(), params, sort, pageNo, pageSize);
		return productGoodsPage;
	}

	/**
	 * 根据供应商编号 和 产品类型 
	 * 获取活动信息
	 * */
	@Override
	public PagePojo<ProductActivityModel> getProductActivityBySupplierId(Integer id, int pageNo, int pageSize) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		params.add(id);
		sql.append("SELECT p.pid , p.title ,p.release_time, p.price, p.`status`, CONCAT_WS(' ~ ',pa.activity_start_time ,pa.activity_end_time) activity_time ");
		sql.append("FROM supplier s JOIN product p ON(s.id = p.supplier_id) JOIN product_activity pa ON (p.pid = pa.pid) ");
		sql.append("WHERE s.id = ?");
		Sort sort = new Sort("p.create_time",SqlSort.DESC);
		PagePojo<ProductActivityModel> productActivityPage = BaseDao.dao.queryForListPage(ProductActivityModel.class, sql.toString(), params, sort, pageNo, pageSize);
		return productActivityPage;
	}

	@Override
	public PagePojo<ProductTravelModel> getProductTravelBySupplierId(Integer id, int pageNo, int pageSize) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		params.add(id);
		sql.append("SELECT p.pid , p.title ,p.release_time, p.price, p.`status`");
		sql.append("FROM supplier s JOIN product p ON(s.id = p.supplier_id) JOIN product_travel pt ON (p.pid = pt.pid) ");
		sql.append("WHERE s.id = ?");
		Sort sort = new Sort("p.create_time",SqlSort.DESC);
		return BaseDao.dao.queryForListPage(ProductTravelModel.class, sql.toString(), params, sort, pageNo, pageSize);
	}

	@Override
	public JSONObject getProductPage(Map<String, Object> map, HttpServletRequest request, int pageNo, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PagePojo<ProductActivityModel> getProductActivity(Map<String,Object> map, int pageNo, int pageSize) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("SELECT p.id product_id, p.name, p.title ,p.release_time, p.price, p.`status`, pa.want_number, pa.browse ");
		sql.append("FROM product p JOIN product_activity pa ON (p.id = pa.product_id) ");
		sql.append("WHERE 1 = 1 ");
		String releaseStartTime = (String) map.get("releaseStartTime");
		String releaseEndTime = (String) map.get("releaseEndTime");
		String name = (String) map.get("name");
		Integer status  = (Integer) map.get("status");
		if (releaseStartTime != null && StringUtils.isNotEmpty(releaseStartTime) && releaseEndTime == null) {
			sql.append("AND release_time >= ? ");
			params.add(releaseStartTime);
		}
		if (releaseStartTime == null && releaseEndTime != null && StringUtils.isNotEmpty(releaseEndTime)) {
			sql.append("AND release_time <= ? ");
			params.add(releaseEndTime);
		}
		if (releaseStartTime != null && StringUtils.isNotEmpty(releaseStartTime) && releaseEndTime != null && StringUtils.isNotEmpty(releaseEndTime)) {
			sql.append("AND release_time BETWEEN ? AND ? ");
			params.add(releaseStartTime);
			params.add(releaseEndTime);
		}
		if (status != null) {
			sql.append("AND p.status =  ? ");
			params.add(status);
		}
		if (name != null && StringUtils.isNotEmpty(name)) {
			sql.append("AND name like ? ");
			params.add(CommonUtil.queryLike(name));
		}
		Sort sort = new Sort("p.create_time",SqlSort.DESC);
		return BaseDao.dao.queryForListPage(ProductActivityModel.class, sql.toString(), params, sort, pageNo, pageSize);
	}

	@Override
	public PagePojo<ProductGoodsModel> getProductGoods(Map<String,Object> map, int pageNo, int pageSize) {
		
		return null;
	}

	@Override
	public DataObj<String> batchAddProductImgs(List<ProductImage> listImages, String productId) {
		BaseDao.dao.update("DELETE FROM product_image WHERE pid = ?",productId);
		if (listImages != null && listImages.size() > 0) {
			StringBuffer sql = new StringBuffer();
			List<Object> params =new ArrayList<Object>();
			sql.append("INSERT INTO product_image(pid,url,weight,create_time) VALUES");
			for (ProductImage pImage:listImages){
				sql.append("(?,?,?,now()),");
				params.add(productId);
				params.add(pImage.getUrl());
				Integer weight = pImage.getWeight();
				if (pImage.getWeight() == null) {
					weight = 0;
				}
				params.add(weight);
			}
			int result = BaseDao.dao.insert(sql.deleteCharAt(sql.length()-1).toString(), params.toArray());
			if (result > 1) {
				return DataObj.getSuccessData("插入图片成功！");
			}
		}
		return new DataObj<>("上传图片失败！");
	}

	@Override
	public DataObj<String> batchAddproductSupplier(List<Supplier> supplierlList, Integer productId) {
		BaseDao.dao.update("DELETE FROM product_supplier WHERE product_id = ?",productId);
		StringBuffer sql = new StringBuffer();
		List<Object> parmas =new ArrayList<Object>();
		sql.append("INSERT INTO product_supplier(product_id,supplier_id,update_time,create_time) VALUES");
		for(Supplier s:supplierlList){
			sql.append("(?,?,now(),now()),");
			parmas.add(productId);
			parmas.add(s.getId());
		}
		int result = BaseDao.dao.insert(sql.deleteCharAt(sql.length()-1).toString(), parmas.toArray());
		if (result > 1) {
			return DataObj.getSuccessData("添加产品供应商信息成功！");
		}
		return new DataObj<>("添加产品供应商信息失败！");
	}

	/**
	 * 获取商品信息
	 * @param catId
	 * @return
	 */
	@Override
	public List<Product> getProductListByCatId(Integer catId) {
		Integer[] catIds = iProductCategoryService.getValidSubclass(catId);
		return BaseDao.dao.queryForListEntity(Product.class,new Conditions("catId", SqlExpr.IN,catIds));
	}

	@Override
	public List<Product> getProductList(Integer catId,String condition) {
		Integer[] catIds = iProductCategoryService.getValidSubclass(catId);
		Conditions conn = new Conditions("catId", SqlExpr.IN,catIds);
		conn.add(new Conditions("price",SqlExpr.GT,condition),SqlJoin.AND);
		return BaseDao.dao.queryForListEntity(Product.class,conn);
	}
}
