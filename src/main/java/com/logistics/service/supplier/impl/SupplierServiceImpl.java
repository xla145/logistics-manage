package com.yuelinghui.service.supplier.impl;
import cn.assist.easydao.common.*;
import cn.assist.easydao.dao.BaseDao;
import cn.assist.easydao.pojo.PagePojo;
import com.alibaba.fastjson.JSON;
import com.yuelinghui.base.constant.ProductConstant;
import com.yuelinghui.base.constant.ProductGroupTypeConstant;
import com.yuelinghui.base.constant.SupplierConstant;
import com.yuelinghui.base.utils.DataObj;
import com.yuelinghui.service.supplier.SupplierService;
import com.yuelinghui.service.vo.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * test
 * 
 * @author caibin
 *
 */
@Service("SupplierService")
public class SupplierServiceImpl implements SupplierService{

	Logger logger = LoggerFactory.getLogger(this.getClass());
	/**
	 * 分页查询
	 * @param pageNo 查询页码
	 * @param pageSize 每页数量
	 * @return
	 */
	public PagePojo<Supplier> getSupplierPage(Map<String,Object> map, int pageNo, int pageSize){
		return null;
	}
	
	/**
	 * 获取供应商信息
	 * @return
	 * */
	public PagePojo<Supplier> getSupplierPage(Conditions conn, int pageNo, int pageSize) {
		Sort sort = new Sort("create_time", SqlSort.DESC);
		return BaseDao.dao.queryForListPage(Supplier.class, conn, sort, pageNo, pageSize);
	}

	@Override
	public Supplier getSupplier(Integer id) {
		Conditions con = new Conditions("id", SqlExpr.EQUAL,id);
		return BaseDao.dao.queryForEntity(Supplier.class,id);
	}

	@Override
	public DataObj<Supplier> addSupplier(Supplier supplier) {
		logger.info("添加供货商信息"+ JSON.toJSON(supplier));
		try {
			int result = 0;//结果
			supplier.setCreateTime(new Date());
			supplier.setUpdateTime(new Date());
			supplier.setStatus(SupplierConstant.STATUS_START);//添加供应商默认为启用
			result = BaseDao.dao.insert(supplier);
			if (result < 0) {
				return new DataObj<>("添加供货商信息失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("添加供货商信息异常原因是（"+e.getMessage()+")");
		}
		return DataObj.getSuccessData(supplier);
	}

	@Override
	public DataObj<Supplier> updateSupplier(Supplier supplier) {
		logger.info("修改供应商信息"+JSON.toJSON(supplier));
		try {
			supplier.setUpdateTime(new Date());
			if (supplier.getStatus() == SupplierConstant.STATUS_STOP) { //停用供应商
				supplier.setStopTime(new Date());
			}
			int result = BaseDao.dao.update(supplier);
			if (result < 0) {
				return new DataObj<>("修改供应商信息失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("修改供应商信息异常原因是（"+e.getMessage()+")");
			return new DataObj<>("修改供应商信息异常");
		}
		return DataObj.getSuccessData(supplier);
	}

	@Override
	public DataObj<String> delSupplier(String ids) {
		StringBuffer sql = new StringBuffer();
		sql.append("update supplier set status = 0, update_time = now() where id in (");
		sql.append(ids);
		sql.append(")");
		int result = BaseDao.dao.delete(sql.toString());
		if (result < 0) {
			return new DataObj<>("修改信息失败");
		}
		return DataObj.getSuccessData("成功！");
	}
	@Override
	public DataObj<String> changeStatus(String ids,Integer status) {
		Date stopTime = null;
		if(status == SupplierConstant.STATUS_START) {
			status = SupplierConstant.STATUS_STOP;
			stopTime = new Date();
		} else {
			status = SupplierConstant.STATUS_START;
		}
		StringBuffer sql = new StringBuffer();
		sql.append("update supplier set status = ?, update_time = now(), stop_time = ? where id in (");
		sql.append(ids);
		sql.append(")");
		int result= BaseDao.dao.update(sql.toString(),status,stopTime);
		if (result < 0) {
			return new DataObj<>("修改信息失败");
		}
		return DataObj.getSuccessData("成功！");
	}

	@Override
	public List<Supplier> supplierListByType(Integer type) {
		Conditions con = new Conditions("product_group_type",SqlExpr.EQUAL,type);
		con.add(new Conditions("status",SqlExpr.EQUAL,SupplierConstant.STATUS_START), SqlJoin.AND);
		return BaseDao.dao.queryForListEntity(Supplier.class,con);
	}
}
