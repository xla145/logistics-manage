package com.logistics.service.goods.impl;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.common.Sort;
import cn.assist.easydao.common.SqlExpr;
import cn.assist.easydao.common.SqlSort;
import cn.assist.easydao.dao.BaseDao;
import cn.assist.easydao.pojo.PagePojo;
import com.alibaba.fastjson.JSON;
import com.logistics.base.constant.ProductConstant;
import com.logistics.base.constant.ProductGroupTypeConstant;
import com.logistics.base.constant.SupplierConstant;
import com.logistics.base.utils.CommonUtil;
import com.logistics.base.utils.DataObj;
import com.logistics.service.goods.ProductGoodsService;
import com.logistics.service.model.ProductGoodsModel;
import com.logistics.service.product.ProductService;
import com.logistics.service.vo.product.Product;
import com.logistics.service.vo.goods.product.ProductGoods;
import com.logistics.service.vo.product.ProductImage;
import com.logistics.service.vo.Supplier;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * The type Product goods service.
 */
@Service("ProductGoodsService")
public class ProductGoodsServiceImpl implements ProductGoodsService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ProductService productService;

	@Override
	public PagePojo<ProductGoodsModel> productGoodsPage(Map<String, Object> map, int pageNo, int pageSize) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("SELECT p.pid,p.name,p.title,pg.goods_type_id,pg.goods_type_name,p.price,pg.sales,p.release_time,p.`status`,pg.sp_name,pg.unit,p.supplier_name,p.original_price,p.stock,p.remaining,p.weight ");
		sql.append("FROM product p JOIN product_goods pg ON(p.pid = pg.pid) ");
		sql.append("WHERE 1 = 1 AND p.status != -1 ");
		String releaseStartTime = (String) map.get("startTime");
		String releaseEndTime = (String) map.get("endTime");
		String pid = (String) map.get("pid");
		Integer status  = (Integer) map.get("status");
		String title  = (String) map.get("title");
		if (releaseStartTime != null && StringUtils.isNotEmpty(releaseStartTime) && releaseEndTime == null) {
			releaseStartTime = releaseStartTime +" 00:00:00";
			sql.append("AND p.release_time >= ? ");
			params.add(releaseStartTime);
		}
		if (releaseStartTime == null && releaseEndTime != null && StringUtils.isNotEmpty(releaseEndTime)) {
			releaseEndTime = releaseEndTime + " 23:59:59";
			sql.append("AND p.release_time <= ? ");
			params.add(releaseEndTime);
		}
		if (releaseStartTime != null && StringUtils.isNotEmpty(releaseStartTime) && releaseEndTime != null && StringUtils.isNotEmpty(releaseEndTime)) {
			releaseStartTime = releaseStartTime +" 00:00:00";
			releaseEndTime = releaseEndTime + " 23:59:59";
			sql.append("AND p.release_time BETWEEN ? AND ? ");
			params.add(releaseStartTime);
			params.add(releaseEndTime);
		}
		if (status != null) {
			sql.append("AND p.status =  ? ");
			params.add(status);
		}
		if (title != null && StringUtils.isNotEmpty(title)) {
			sql.append("AND p.title like ? ");
			params.add(CommonUtil.queryLike(title));
		}
		if (pid != null && StringUtils.isNotEmpty(pid)) {
			sql.append("AND p.pid = ? ");
			params.add(pid);
		}
		Sort sort = new Sort("p.create_time",SqlSort.DESC);
		return BaseDao.dao.queryForListPage(ProductGoodsModel.class, sql.toString(), params, sort, pageNo, pageSize);	
	}

	@Transactional
	@Override
	public DataObj<ProductGoodsModel> addProductGoods(ProductGoodsModel productGoodsModel) {
		String info = productGoodsModel.getInfo();
		productGoodsModel.setInfo("");
		logger.info("更新好物信息"+JSON.toJSON(productGoodsModel));
        /**
		 * 保存产品信息
		 */
		try {
			String productId = CommonUtil.getId("PGD");
			Product product = new Product();
			product.setPid(productId);
			product.setTitle(productGoodsModel.getTitle());
			product.setName(productGoodsModel.getName());
			product.setOriginalPrice(productGoodsModel.getOriginalPrice());
			product.setPrice(productGoodsModel.getPrice());
			product.setImageUrl(productGoodsModel.getImageUrl());
			product.setSupplierId(productGoodsModel.getSupplierId());
			product.setUpdateTime(new Date());
			product.setCreateTime(new Date());
			product.setProductGroupType(ProductGroupTypeConstant.GOODS.getId());
			product.setCatId(ProductGroupTypeConstant.GOODS.getId());
			product.setProductGroupName(ProductGroupTypeConstant.GOODS.getName());
			product.setReleaseTime(new Date());
			product.setStatus(ProductConstant.PRODUCT_STATUS_INITIAL);
			product.setStock(productGoodsModel.getStock());
			product.setRemaining(productGoodsModel.getStock());
			product.setSupplierName(productGoodsModel.getSupplierName());
			product.setRemark(productGoodsModel.getRemark());
			product.setWeight(productGoodsModel.getWeight());
			product.setInfo(info);
			int result = BaseDao.dao.insert(product);
			if(result < 1) {
				return new DataObj<>("添加产品信息失败！");
			}
			/**
			 * 保存商品信息
			 * */
			ProductGoods pGoods = new ProductGoods();
			pGoods.setGoodsTypeId(productGoodsModel.getGoodsTypeId());
			pGoods.setUnit(productGoodsModel.getUnit());
			pGoods.setDelivery(productGoodsModel.getDelivery());
			pGoods.setFreight(productGoodsModel.getFreight());
			pGoods.setSales(productGoodsModel.getSales());
			pGoods.setPid(productId);
			int num = BaseDao.dao.insert(pGoods);
			if(num < 1) {
				return new DataObj<>("添加好物产品信息失败！");
			}
			/**
			 * 保存轮播图片
			 * */
			if(productGoodsModel.getImageList() != null && productGoodsModel.getImageList().size() > 0 ) {
				productService.batchAddProductImgs(productGoodsModel.getImageList(), productId);
			}
		} catch (Exception e) {
		   e.printStackTrace();
		   logger.error("添加好物信息失败，原因是"+e.getMessage());
		   TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return new DataObj<>("添加好物产品信息异常！");
		}
		return DataObj.getSuccessData(productGoodsModel);
	}

	@Override
	public DataObj<String> delProductGoods(String[] ids) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE product set status = ?, update_time = now() ");
		sql.append("WHERE pid IN (");
		sql.append(CommonUtil.arrayToSqlIn(ids));
		sql.append(")");
		int result = BaseDao.dao.update(sql.toString(),ProductConstant.PRODUCT_STATUS_DEL);
		if (result < 1) {
			return new DataObj<>("删除好玩商品失败！");
		}
		return DataObj.getSuccessData("删除成功！");
	}

	@Transactional
	@Override
	public DataObj<ProductGoodsModel> updateProductGoods(ProductGoodsModel productGoodsModel) {
		String info = productGoodsModel.getInfo();
		productGoodsModel.setInfo("");
		logger.info("更新好物信息"+JSON.toJSON(productGoodsModel));
		try {
			Conditions conn = new Conditions("pid",SqlExpr.EQUAL,productGoodsModel.getPid());
			Product oldProduct  = BaseDao.dao.queryForEntity(Product.class,conn);
			/**
			* 修改产品信息
		 	*/
			Product product = new Product();
			product.setPid(productGoodsModel.getPid());
			product.setName(productGoodsModel.getName());
			product.setOriginalPrice(productGoodsModel.getOriginalPrice());
			product.setRemark(productGoodsModel.getRemark());
			product.setTitle(productGoodsModel.getTitle());
			product.setPrice(productGoodsModel.getPrice());
			product.setImageUrl(productGoodsModel.getImageUrl());
			product.setSupplierId(productGoodsModel.getSupplierId());
			product.setUpdateTime(new Date());
			Integer remaining = oldProduct.getRemaining() - (oldProduct.getStock() - productGoodsModel.getStock());//剩余库存由库存数确定
			product.setRemaining(remaining);
			product.setStock(productGoodsModel.getStock());
			product.setInfo(info);
			product.setSupplierName(productGoodsModel.getSupplierName());
			product.setWeight(productGoodsModel.getWeight());
			int result = BaseDao.dao.update(product);
			if(result < 1) {
				return new DataObj<>("修改产品信息失败！");
			}
			/**
			 * 修改商品信息
			 * */
			List<Object> list = new ArrayList<Object>();
			StringBuffer sql = new StringBuffer();
			sql.append("UPDATE product_goods SET ");
			sql.append("goods_type_id = ?,unit = ?,delivery = ?,freight = ?,sales = ? ");
			sql.append("WHERE pid = ?");
			list.add(productGoodsModel.getGoodsTypeId());
			list.add(productGoodsModel.getUnit());
			list.add(productGoodsModel.getDelivery());
			list.add(productGoodsModel.getFreight());
			System.out.println(productGoodsModel.getSales());
			list.add(productGoodsModel.getSales());
			list.add(productGoodsModel.getPid());
			int num = BaseDao.dao.update(sql.toString(),list.toArray());
			if(num < 1) {
				return new DataObj<>("修改好物产品信息失败！");
			}
			/**
			 * 保存轮播图片
			 * */
//			productService.batchAddProductImgs(productGoodsModel.getImageList(), productGoodsModel.getPid());
			if(productGoodsModel.getImageList() != null && productGoodsModel.getImageList().size() > 0 ) {
				productService.batchAddProductImgs(productGoodsModel.getImageList(), product.getPid());
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("修改好物产品信息异常"+e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return new DataObj<>("修改好物产品信息失败！");
		}
		return DataObj.getSuccessData(productGoodsModel);
	}

	@Override
	public ProductGoodsModel getProductGoods(String productId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT p.pid,p.title,pg.goods_type_id,p.`name`,p.price,p.original_price,pg.unit,pg.delivery, pg.sales, pg.freight,p.stock,p.remaining,p.image_url,p.supplier_id,p.info,p.supplier_name,p.remark,p.weight ");
		sql.append("FROM product p JOIN product_goods pg ON(p.pid = pg.pid) ");
		sql.append("WHERE p.pid =? ");
		ProductGoodsModel product = BaseDao.dao.queryForEntity(ProductGoodsModel.class, sql.toString(), productId);	
		Conditions con = new Conditions("pid", SqlExpr.EQUAL,productId);
		List<ProductImage> imgList = BaseDao.dao.queryForListEntity(ProductImage.class, con);
		product.setImageList(imgList);
		return product;
	}

	@Override
	public DataObj<String> upOrDownProductGoods(String[] ids, Integer status) {
		logger.info("上下架活动信息，ids="+JSON.toJSON(ids)+",status"+status);
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("UPDATE product SET status = ?,update_time = now() ");
			sql.append("WHERE pid in (");
			sql.append(CommonUtil.arrayToSqlIn(ids));
			sql.append(")");
			status = (status == ProductConstant.PRODUCT_STATUS_ONOFFER)? ProductConstant.PRODUCT_STATUS_ADMINOUT:ProductConstant.PRODUCT_STATUS_ONOFFER;
			int result = BaseDao.dao.update(sql.toString(),status);
			if (result < 1) {
				return new DataObj<>("失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("上下架好物商品失败"+e.getMessage());
			return new DataObj<>("上下架好物商品异常");
		}
		return DataObj.getSuccessData("成功！");
	}

	/**
	 * 获取供应商信息
	 * @param map the map
	 * @return
	 */
	@Override
	public PagePojo<Supplier> supplierPage(Map<String, Object> map,int pageNo, int pageSize) {
		List<Object> params = new ArrayList<Object>();
 		String name = (String) map.get("name");
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT id,name,people,mobile,create_time,status,stop_time ");
		sql.append("FROM supplier WHERE 1=1 AND status = " + SupplierConstant.STATUS_START+" ");
		sql.append("AND product_group_type = "+ ProductGroupTypeConstant.GOODS.getId()+" ");
		if (name != null && StringUtils.isNotEmpty(name)) {
			sql.append("AND name like = ?");
			params.add(CommonUtil.queryLike(name));
		}
		Sort sort = new Sort("create_time",SqlSort.DESC);
		return BaseDao.dao.queryForListPage(Supplier.class,sql.toString(),params,sort,pageNo,pageSize);

	}
}
