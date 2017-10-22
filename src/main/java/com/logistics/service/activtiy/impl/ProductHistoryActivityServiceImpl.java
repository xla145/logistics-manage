package com.logistics.service.activtiy.impl;

import cn.assist.easydao.common.Sort;
import cn.assist.easydao.common.SqlSort;
import cn.assist.easydao.dao.BaseDao;
import cn.assist.easydao.pojo.PagePojo;
import com.alibaba.fastjson.JSON;
import com.logistics.base.constant.ProductConstant;
import com.logistics.base.utils.CommonUtil;
import com.logistics.base.utils.DataObj;
import com.logistics.service.model.ProductActivityModel;
import com.logistics.service.model.ProductHistoryActivityModel;
import com.logistics.service.activtiy.IProductHistoryActivityService;
import com.logistics.service.vo.activity.ProductHistoryActivity;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* @ClassName: ProductActivityServiceImpl
* @Description: TODO(活动历史信息管理)
* @author Administrator
* @date 2017年7月18日
*
*/
@Service("ProductHistoryActivityService")
public class ProductHistoryActivityServiceImpl implements IProductHistoryActivityService {

	private final  Logger logger = LoggerFactory.getLogger(this.getClass());
	private final Integer ACTIVITY_APPLY_STATUS = 10;//活动结束状态
	private final Integer ACTIVITY_RUNNING_STATUS = 20;//活动结束状态
	private final Integer ACTIVITY_END_STATUS = 30;//活动结束状态
	/**
	 * 获取活动历史信息
	 */
	@Override
	public PagePojo<ProductHistoryActivityModel> productHistoryActivityPage(Map<String,Object> map, int pageNo, int pageSize){
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("SELECT pha.id,p.pid, p.title ,p.release_time, CONCAT_WS('~',pa.activity_start_time,pa.activity_end_time) activity_time,pha.title history_title,pha.browse,pa.address,pha.`status` ");
		sql.append("FROM product p JOIN product_activity pa ON (p.pid = pa.pid) JOIN product_history_activity pha ON (pa.pid = pha.pid) ");
		sql.append("WHERE 1 = 1 ");
		String releaseStartTime = (String) map.get("startTime");
		String releaseEndTime = (String) map.get("endTime");
		Integer status  = (Integer) map.get("status");
		if (releaseStartTime != null && StringUtils.isNotEmpty(releaseStartTime) && releaseEndTime == null) {
			sql.append("AND pha.release_time >= ? ");
			params.add(releaseStartTime);
		}
		if (releaseStartTime == null && releaseEndTime != null && StringUtils.isNotEmpty(releaseEndTime)) {
			sql.append("AND pha.release_time <= ? ");
			params.add(releaseEndTime);
		}
		if (releaseStartTime != null && StringUtils.isNotEmpty(releaseStartTime) && releaseEndTime != null && StringUtils.isNotEmpty(releaseEndTime)) {
			sql.append("AND pha.release_time BETWEEN ? AND ? ");
			params.add(releaseStartTime);
			params.add(releaseEndTime);
		}
		if (status != null) {
			sql.append("AND pha.status =  ? ");
			params.add(status);
		}
		Sort sort = new Sort("pha.create_time",SqlSort.DESC);
		return BaseDao.dao.queryForListPage(ProductHistoryActivityModel.class, sql.toString(), params, sort, pageNo, pageSize);	
	}
	/**
	 * 保存历史活动信息
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public DataObj<ProductHistoryActivityModel> addProductHistoryActivity(ProductHistoryActivityModel productActivity) {
		logger.info("添加历史活动信息 "+ JSON.toJSON(productActivity));
		try {
			String productId = productActivity.getPid();
			/**
			 * 保存历史活动信息
			 * */
			ProductHistoryActivity productHistoryActivity = new ProductHistoryActivity();
			productHistoryActivity.setTitle(productActivity.getHistoryTitle());
			productHistoryActivity.setImgUrl(productActivity.getHistoryImgUrl());
			productHistoryActivity.setPid(productId);
			productHistoryActivity.setCreateTime(new Date());
			productHistoryActivity.setUpdateTime(new Date());
			productHistoryActivity.setReleaseTime(new Date());
			productHistoryActivity.setInfo(productActivity.getInfo());
			productHistoryActivity.setStatus(ProductConstant.PRODUCT_STATUS_ONOFFER);
			int num = BaseDao.dao.insert(productHistoryActivity);
			if(num < 1) {
				return new DataObj<>("添加历史活动产品信息失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("添加历史活动信息异常原因（"+e.getMessage()+")");
			return new DataObj<>("添加历史活动产品信息异常！");
		}
		return DataObj.getSuccessData(productActivity);
	}
	/**
	 * 删除好玩活动信息
	 */
	@Override
	public DataObj<String> delProductHistoryActivity(String[] ids) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE product_history_activity set status = ?, update_time = now() ");
		sql.append("WHERE id IN (");
		sql.append(CommonUtil.arrayToSqlIn(ids));
		sql.append(")");
		int result = BaseDao.dao.update(sql.toString(),ProductConstant.PRODUCT_STATUS_DEL);
		if (result < 1) {
			return new DataObj<>("删除好玩历史活动失败！");
		}
		return DataObj.getSuccessData("删除好玩历史活动成功！");
	};
	
	/**
	 * 更新活动历史信息
	 * */
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public DataObj<ProductHistoryActivityModel> updateProductHistoryActivity(ProductHistoryActivityModel productHistoryActivityModel) {
		logger.info("更新活动历史信息"+JSON.toJSON(productHistoryActivityModel));
		try {
			/**
			 * 修改历史活动信息
			 * */
			ProductHistoryActivity productHistoryActivity = new ProductHistoryActivity();
			productHistoryActivity.setImgUrl(productHistoryActivityModel.getHistoryImgUrl());
			productHistoryActivity.setUpdateTime(new Date());
			productHistoryActivity.setInfo(productHistoryActivityModel.getInfo());
			productHistoryActivity.setId(productHistoryActivityModel.getId());
			int result = BaseDao.dao.update(productHistoryActivity);
			if(result < 1) {
				return new DataObj<>("修改历史活动产品信息失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("修改历史活动产品异常原因（"+e.getMessage()+")");
		}
		return DataObj.getSuccessData(productHistoryActivityModel);
	}
	
	/**
	 * 
	 * 通过编号获取活动历史信息
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public ProductHistoryActivityModel getProductHistoryActivity(Integer id) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT pha.id, p.title , CONCAT_WS('~',pa.activity_start_time,pa.activity_end_time) activity_time,pha.title history_title,pa.address,pha.impl,pha.img_url image_url ");
		sql.append("FROM product p JOIN product_activity pa ON (p.pid = pa.pid) JOIN product_history_activity pha ON (pa.pid = pha.pid) ");
		sql.append("WHERE pha.id = ?");
//		Conditions con = new Conditions("id",SqlExpr.EQUAL,id);
		ProductHistoryActivityModel product = BaseDao.dao.queryForEntity(ProductHistoryActivityModel.class,sql.toString(),id);
		return product;
	}

	/**
	 * 上下架好玩活动产品
	 * */
	@Override
	public DataObj<String> upOrDownProduct(String[] ids, Integer status) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE product_history_activity SET status = ?,update_time = now() ");
		sql.append("WHERE id in (");
		sql.append(CommonUtil.arrayToSqlIn(ids));
		sql.append(")");
		status = (status == ProductConstant.PRODUCT_STATUS_ONOFFER)? ProductConstant.PRODUCT_STATUS_ADMINOUT:ProductConstant.PRODUCT_STATUS_ONOFFER;
		int result = BaseDao.dao.update(sql.toString(),status);
		if (result > 1) {
			return DataObj.getSuccessData("成功！");
		}
		return new DataObj<>("失败");
	}

	@Override
	public PagePojo<ProductActivityModel> productActivityPage(Map<String, Object> map, int pageNo, int pageSize) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("SELECT p.pid, p.title,p.release_time, CONCAT_WS('~',pa.activity_start_time,pa.activity_end_time) activity_time,pa.address,pa.browse,COUNT(o.pid) sum_order,COUNT(pl.pid) want_number ");
		sql.append("FROM product p JOIN product_activity pa ON (p.pid = pa.pid) LEFT JOIN `order` o ON(o.pid = pa.pid) LEFT JOIN product_love pl ON (pl.pid = pa.pid)  ");
		sql.append("WHERE 1 = 1 AND pa.activity_status = "+ProductConstant.PRODUCT_STATUS_ADMINOUT+" ");
		sql.append("OR pa.activity_status = "+ACTIVITY_END_STATUS+" ");
		String title  = (String) map.get("title");
		if (title != null &&  StringUtils.isNotEmpty(title)) {
			sql.append("AND p.title like  ? ");
			params.add(CommonUtil.queryLike(title));
		}
//		Sort sort = new Sort("p.create_time",SqlSort.DESC);
		sql.append("GROUP BY p.pid ");
		sql.append("ORDER BY p.create_time DESC");
		return BaseDao.dao.queryForListPage(ProductActivityModel.class, sql.toString(), params, null, pageNo, pageSize);
	}

}
