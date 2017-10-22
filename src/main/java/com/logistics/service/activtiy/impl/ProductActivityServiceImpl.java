package com.logistics.service.activtiy.impl;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.common.Sort;
import cn.assist.easydao.common.SqlExpr;
import cn.assist.easydao.common.SqlSort;
import cn.assist.easydao.dao.BaseDao;
import cn.assist.easydao.pojo.PagePojo;
import com.alibaba.fastjson.JSON;
import com.logistics.base.constant.ProductConstant;
import com.logistics.base.constant.ProductGroupTypeConstant;
import com.logistics.base.utils.CommonUtil;
import com.logistics.base.utils.DataObj;
import com.logistics.base.utils.DateUtil;
import com.logistics.base.utils.RecordBean;
import com.logistics.service.model.ProductActivityModel;
import com.logistics.service.activtiy.IProductActivityService;
import com.logistics.service.product.ProductService;
import com.logistics.service.supplier.SupplierService;
import com.logistics.service.vo.*;
import com.logistics.service.vo.activity.ProductActivity;
import com.logistics.service.vo.product.Product;
import com.logistics.service.vo.product.ProductImage;
import com.logistics.service.vo.product.ProductLove;
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

;

/**
* @ClassName: ProductActivityServiceImpl
* @Description: TODO(活动管理)
* @author Administrator
* @date 2017年7月18日
*
*/
@Service("IProductActivityService")
public class ProductActivityServiceImpl implements IProductActivityService {

	private static Logger logger = LoggerFactory.getLogger(ProductActivityServiceImpl.class);

	@Autowired
	private ProductService productService;

	@Autowired
	private SupplierService supplierService;
	
	@Override
	public PagePojo<ProductActivityModel> getProductActivity(Map<String,Object> map, int pageNo, int pageSize){
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("SELECT p.pid,p.title,p.sub_title,p.label,p.release_time, CONCAT_WS('~',pa.activity_start_time,pa.activity_end_time) activity_time,SUM(pl.fever) want_number,pa.browse,p.price,pa.activity_status,p.status,pa.apply_max,(pa.apply_max - p.stock) apply_current,p.weight ");
		sql.append("FROM product p JOIN product_activity pa ON (p.pid = pa.pid) LEFT JOIN product_love pl ON (p.pid = pl.pid) ");
		sql.append("WHERE 1 = 1 AND status != "+ProductConstant.PRODUCT_STATUS_DEL+" ");
		String releaseTime = (String) map.get("releaseTime");
		String name = (String) map.get("title");
		Integer status  = (Integer) map.get("status");
		if (releaseTime != null && StringUtils.isNotEmpty(releaseTime)) {
			String[] time = releaseTime.split(" - ");
 			sql.append("AND p.release_time BETWEEN ? AND ? ");
			params.add(time[0]);
			params.add(time[1]);
		}
		if (status != null) {
			sql.append("AND pa.activity_status =  ? ");
			params.add(status);
		}
		if (name != null && StringUtils.isNotEmpty(name)) {
			sql.append("AND p.title like ? ");
			params.add(CommonUtil.queryLike(name));
		}
		sql.append("GROUP BY p.pid ");
		Sort sort = new Sort("p.create_time", SqlSort.DESC);
		return BaseDao.dao.queryForListPage(ProductActivityModel.class, sql.toString(), params, sort, pageNo, pageSize);
	}
	
	@Transactional
	@Override
	public RecordBean<ProductActivityModel> addProductActivity(ProductActivityModel productActivity) {
		String info = productActivity.getInfo();
		productActivity.setInfo("");
		logger.info("添加活动信息"+JSON.toJSON(productActivity));
		/**
		 * 保存产品信息
		 */
		try {
			int result = 0;
			Supplier supplier = supplierService.getSupplier(productActivity.getSupplierId());
			String pid = CommonUtil.getId("PHW");
			Product product = new Product();
			product.setSubTitle(productActivity.getSubTitle());
			product.setLabel(productActivity.getLabel());
			product.setCatId(productActivity.getCatId());
			product.setTitle(productActivity.getTitle());
			product.setName(productActivity.getTitle());
			product.setOriginalPrice(productActivity.getPrice());
			product.setPrice(productActivity.getPrice());
			product.setImageUrl(productActivity.getImageUrl());
			product.setSupplierId(productActivity.getSupplierId());
			product.setSupplierName(supplier.getName());
			product.setUpdateTime(new Date());
			product.setCreateTime(new Date());
			product.setInfo(info);
			product.setProductGroupType(ProductGroupTypeConstant.ACTIVITY.getId());
			product.setProductGroupName(ProductGroupTypeConstant.ACTIVITY.getName());
			product.setReleaseTime(new Date());
			product.setStatus(ProductConstant.PRODUCT_STATUS_INITIAL);
			product.setStock(productActivity.getStock());
			product.setRemaining(productActivity.getRemaining());
			product.setWeight(productActivity.getWeight());
			product.setPid(pid);
			result = BaseDao.dao.insert(product);
			if (result < 1) {
				return RecordBean.error("添加产品信息失败！");
			}
			/**
			 * 保存活动信息
			 * */
			ProductActivity pActivity = new ProductActivity();
			if (productActivity.getActivityStartTime() == null) {
				pActivity.setActivityStartTime(new Date());
			} else {
				pActivity.setActivityStartTime(productActivity.getActivityStartTime());
			}
			pActivity.setActivityEndTime(productActivity.getActivityEndTime());
			pActivity.setApplyStartTime(productActivity.getApplyStartTime());
			if (productActivity.getApplyEndTime() == null) { //如果不填报名截止时间是活动结束时间的前一个小时
				pActivity.setApplyEndTime(DateUtil.getDateAddOffSet(5,-1,productActivity.getActivityEndTime()));
			} else {
				pActivity.setApplyEndTime(productActivity.getApplyEndTime());
			}
			pActivity.setApplyMax(productActivity.getStock());
			if (productActivity.getBrowse() == null) {
				pActivity.setBrowse(1);
			} else {
				pActivity.setBrowse(productActivity.getBrowse());
			}
			pActivity.setActivityType(productActivity.getActivityType());
			pActivity.setPid(pid);
			pActivity.setUpdateTime(new Date());
			pActivity.setCarryNumber(productActivity.getCarryNumber());
			pActivity.setAddress(productActivity.getAddress());
			pActivity.setActivityStatus(ProductConstant.PRODUCT_STATUS_INITIAL);
			result = BaseDao.dao.insert(pActivity);
			if (result < 1) {
				return RecordBean.error("添加活动产品信息失败！");
			}
			ProductLove productLove = new ProductLove();
			productLove.setIp("0:0:0:0");
			productLove.setFever(productActivity.getWantNumber());
			productLove.setUid(-1);
			productLove.setPid(pid);
			productLove.setRemark("后台添加,想去人数的基数！");
			productLove.setCreateTime(new Date());
			result = BaseDao.dao.insert(productLove);
			if (result < 1) {
				return RecordBean.error("添加想去人数信息失败！");
			}
			/**
			 * 保存轮播图片
			 * */
			if (productActivity.getImageList() != null && productActivity.getImageList().size() > 0) {
				productService.batchAddProductImgs(productActivity.getImageList(), pid);
			}
		}catch (Exception e){
			e.printStackTrace();
			logger.error("添加活动产品异常"+e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return RecordBean.error("添加活动产品信息失败！");
		}
		return RecordBean.success("success",productActivity);
	}
	/**
	 * 删除好玩活动信息
	 */
//	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public RecordBean<String> delProductActivity(String[] ids) {
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("UPDATE product set status = ?, update_time = now() ");
			sql.append("WHERE pid IN (");
			sql.append(CommonUtil.arrayToSqlIn(ids));
			sql.append(")");
			int result = BaseDao.dao.update(sql.toString(),ProductConstant.PRODUCT_STATUS_DEL);
			if (result < 1) {
				return RecordBean.error("删除好玩活动失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("删除好玩活动信息失败！"+e.getMessage());
		}
		return RecordBean.success("删除成功！");
	};
	
	@Transactional
	@Override
	public RecordBean<ProductActivityModel> updateProductActivity(ProductActivityModel productActivity) {
		String info = productActivity.getInfo();
		productActivity.setInfo("");
		logger.info("修改活动信息"+ JSON.toJSON(productActivity));
		/**
		 * 修改产品信息
		 */
		try {
			int result = 0;
			Supplier supplier = supplierService.getSupplier(productActivity.getSupplierId());
			Product product = new Product();
			product.setSubTitle(productActivity.getSubTitle());
			product.setLabel(productActivity.getLabel());
			product.setSubTitle(productActivity.getSubTitle());
			product.setPid(productActivity.getPid());
			product.setCatId(productActivity.getCatId());
			product.setTitle(productActivity.getTitle());
			product.setName(productActivity.getTitle());
			product.setPrice(productActivity.getPrice());
			product.setImageUrl(productActivity.getImageUrl());
			product.setSupplierId(productActivity.getSupplierId());
			product.setSupplierName(supplier.getName());
			product.setUpdateTime(new Date());
			product.setInfo(info);
			product.setReleaseTime(new Date());
			product.setWeight(productActivity.getWeight());
			result = BaseDao.dao.update(product);
			if(result < 1) {
				return RecordBean.error("更新产品信息失败！");
			}
			/**
			 * 修改活动信息
			 * */
			List<Object> list = new ArrayList<Object>();
			StringBuffer sql = new StringBuffer();
			sql.append("UPDATE product_activity SET ");
			sql.append("browse = ?,activity_type = ?,address = ?,apply_max = ?,activity_start_time = ?,activity_end_time = ?,apply_start_time = ?,apply_end_time = ?,carry_number = ?,update_time = now() ");
			sql.append("WHERE pid = ?");
			String productId = productActivity.getPid();
			list.add(productActivity.getBrowse());
			list.add(productActivity.getActivityType());
			list.add(productActivity.getAddress());
			list.add(productActivity.getStock());
			list.add(productActivity.getActivityStartTime());
			list.add(productActivity.getActivityEndTime());
			list.add(productActivity.getApplyStartTime());
			list.add(productActivity.getApplyEndTime());
			list.add(productActivity.getCarryNumber());
			list.add(productId);
			result = BaseDao.dao.update(sql.toString(),list.toArray());
			if(result < 1) {
				return RecordBean.error("修改活动产品信息失败！");
			}
			if (productActivity.getWantNumber() != null) {
				StringBuffer loveSql = new StringBuffer();
				loveSql.append("UPDATE product_love SET fever = ? WHERE uid = -1 AND pid = ?");
				result = BaseDao.dao.update(loveSql.toString(),productActivity.getWantNumber(),productActivity.getPid());
				if (result < 1) {
					return RecordBean.error("修改想去人数信息失败！");
				}
			}
			/**
			 * 保存轮播图片
			 * */
			productService.batchAddProductImgs(productActivity.getImageList(), productId);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("修改活动信息异常 "+e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return RecordBean.error("修改活动产品信息异常！");
		}
		return RecordBean.success("success!",productActivity);
	}

	@Override
	public ProductActivityModel getProductActivity(String productId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT p.pid, p.title, p.price,p.image_url,p.supplier_id,p.info, pa.browse , pa.activity_start_time, pa.activity_end_time, pa.apply_start_time, pa.apply_end_time,pa.activity_type,p.stock, pa.carry_number, pa.address, p.weight ");
		sql.append("FROM product p JOIN product_activity pa ON (p.pid = pa.pid) ");
		sql.append("WHERE p.pid =? ");
		ProductActivityModel product = BaseDao.dao.queryForEntity(ProductActivityModel.class, sql.toString(), productId);	
		Conditions con = new Conditions("pid",SqlExpr.EQUAL,productId);
		List<ProductImage> liImages = BaseDao.dao.queryForListEntity(ProductImage.class, con);
		if (liImages != null && liImages.size() > 0) {
			product.setImageList(liImages);
		}
		StringBuffer loveSql = new StringBuffer();
		loveSql.append("SELECT fever FROM product_love WHERE pid = ? AND uid = -1");
		ProductLove productLove = BaseDao.dao.queryForEntity(ProductLove.class,loveSql.toString(),productId);//返回想要的人数
		if (productLove != null) {
			product.setWantNumber(productLove.getFever());
		}
		return product;
	}
	@Transactional
	@Override
	public RecordBean<String> upOrDownProduct(String[] ids, Integer status) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE product SET status = ?,update_time = now() ");
		sql.append("WHERE pid in (");
		sql.append(CommonUtil.arrayToSqlIn(ids));
		sql.append(")");
		status = (status == ProductConstant.PRODUCT_STATUS_ONOFFER)? ProductConstant.PRODUCT_STATUS_ADMINOUT:ProductConstant.PRODUCT_STATUS_ONOFFER;
		int result = BaseDao.dao.update(sql.toString(),status);
		if (result < 1) {
			return RecordBean.error("活动上下架失败");
		}
		StringBuffer sql_1 = new StringBuffer();
		sql_1.append("UPDATE product_activity SET activity_status = ?,update_time = now() ");
		sql_1.append("WHERE pid in (");
		sql_1.append(CommonUtil.arrayToSqlIn(ids));
		sql_1.append(")");
		ProductActivity productActivity = BaseDao.dao.queryForEntity(ProductActivity.class,new Conditions("pid",SqlExpr.EQUAL,ids[0]));
		if (productActivity.getApplyEndTime().after(new Date()) && productActivity.getApplyStartTime().before(new Date())) { //如果报名开始时间在当前时间之后，更新活动状态为报名中
			int num = BaseDao.dao.update(sql_1.toString(),ProductConstant.PRODUCT_ACTIVITY_APPLYING);
			if (num < 1) {
				return RecordBean.error("活动上下架失败");
			}
		}
		return RecordBean.success("活动上下架成功！");
	}

	/**
	 * 复制 活动信息
	 * @param pid
	 * @return
	 */
	@Override
	public RecordBean<String> copyProductActivity(String pid) {
		ProductActivityModel productActivityModel = getProductActivity(pid);
		productActivityModel.setTitle("【复制活动信息】"+productActivityModel.getTitle());
		productActivityModel.setStatus(ProductConstant.PRODUCT_STATUS_INITIAL);
		RecordBean recordBean = addProductActivity(productActivityModel);
		if (recordBean.isSuccessCode()) {
			return RecordBean.success("复制成功！");
		}
		return RecordBean.error(recordBean.getMsg());
	}

}
