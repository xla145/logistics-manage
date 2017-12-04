package com.logistics.service.sales.impl;

import cn.assist.easydao.common.*;
import cn.assist.easydao.dao.BaseDao;
import cn.assist.easydao.pojo.PagePojo;
import com.alibaba.fastjson.JSON;
import com.logistics.base.constant.ProductAccessLogConstant;
import com.logistics.base.constant.SalesConstant;
import com.logistics.base.log.Log;
import com.logistics.base.utils.CommonUtil;
import com.logistics.base.utils.RecordBean;
import com.logistics.service.member.IMemberService;
import com.logistics.service.model.SalesModel;
import com.logistics.service.product.IProductCategoryService;
import com.logistics.service.product.IProductStockService;
import com.logistics.service.sales.ISalesService;
import com.logistics.service.vo.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 销售管理
 */
@Service("ISalesService")
public class SalesServiceImpl implements ISalesService {

    private static Logger logger = LoggerFactory.getLogger(SalesServiceImpl.class);

    @Autowired
    private IMemberService iMemberService;
    @Autowired
    private IProductStockService iProductStockService;

    @Override
    public PagePojo<Sales> getSalesPage(Map<String, Object> map, int pageNo, int pageSize) {
        String createTime = (String) map.get("createTime");
        Conditions conditions = new Conditions();
        if (StringUtils.isNotBlank(createTime) && createTime.contains(" - ")) {
            String[] time = createTime.split(" - ");
            conditions = new Conditions("create_time", SqlExpr.GT_AND_EQUAL,time[0]);
            conditions.add(new Conditions("create_time", SqlExpr.LT_AND_EQUAL,time[1]), SqlJoin.AND);
        }
        Sort sort = new Sort("create_time", SqlSort.DESC);
        return BaseDao.dao.queryForListPage(Sales.class,conditions,sort,pageNo,pageSize);
    }

    @Override
    public List<Sales> getSalesList(Conditions conn) {
        return BaseDao.dao.queryForListEntity(Sales.class,conn);
    }

    @Transactional
    @Override
    @Log(operateName = "addSalesModel")
    public RecordBean<SalesModel> addSalesModel(SalesModel salesModel,Integer operatorId,String operatorName) {
        logger.info("添加销售单信息【"+ JSON.toJSON(salesModel)+"】");
        try {
            Sales sales = new Sales();
            String sid = CommonUtil.getId("SAL");
            sales.setsId(sid);
            sales.setBuyUid(salesModel.getBuyUid());
            Member member = iMemberService.getMember(sales.getBuyUid());
            sales.setBuyMobile(member.getMobile());
            sales.setBuyName(member.getName());
            sales.setBuyCount(salesModel.getBuyCount());
            sales.setSalesName(salesModel.getSalesName());
            sales.setStatus(SalesConstant.SALES_STATUS_ENABLE);
            double totalAmount = 0.00;
            Integer buyCount = 0;
            for (SalesProduct SalesProduct:salesModel.getSalesProductList()) {
                buyCount += SalesProduct.getBuyCount();
                totalAmount += SalesProduct.getBuyCount()*SalesProduct.getProductPrice().doubleValue();
            }
            sales.setCreateTime(new Date());
            sales.setUpdateTime(new Date());
            sales.setBuyCount(buyCount);
            sales.setTotalAmount(BigDecimal.valueOf(totalAmount));
            int result = BaseDao.dao.insert(sales);
            if (result < 0) {
                return RecordBean.error("添加销售单信息失败！");
            }
            batchAddSalesProduct(salesModel.getSalesProductList(),sid);
            addProductAccessLog(salesModel.getSalesProductList(),operatorId,operatorName);
        } catch (Exception e) {
            logger.error("添加销售单异常【"+e.getMessage()+"】");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return RecordBean.error("添加销售单异常！");
        }
        return RecordBean.success("添加销售单成功！");

    }
    /**
     * 添加销售商品信息
     * @param salesProducts
     * @param sId
     * @return
     */
    @Override
    public RecordBean<String> batchAddSalesProduct(List<SalesProduct> salesProducts, String sId) {
        BaseDao.dao.update("DELETE FROM sales_product WHERE s_id = ?",sId);
        if (salesProducts != null && salesProducts.size() > 0) {
            StringBuffer sql = new StringBuffer();
            List<Object> params =new ArrayList<Object>();
            sql.append("INSERT INTO sales_product(s_id,cat_id,pid,wid,buy_count,sales_price,product_price,create_time,update_time) VALUES");
            for (SalesProduct salesProduct:salesProducts){
                sql.append("(?,?,?,?,?,?,?,now(),now()),");
                params.add(sId);
                params.add(salesProduct.getCatId());
                params.add(salesProduct.getPid());
                params.add(salesProduct.getWid());
                params.add(salesProduct.getBuyCount());
                params.add(salesProduct.getSalesPrice());
                params.add(salesProduct.getProductPrice());
            }
            int result = BaseDao.dao.insert(sql.deleteCharAt(sql.length()-1).toString(), params.toArray());
            if (result < 1) {
                return RecordBean.error("添加销售商品信息失败！");
            }
        }
        return RecordBean.success("添加销售商品信息成功！");
    }

    @Override
    public PagePojo<SalesProduct> getSalesProductPage(Conditions conn, int pageNo, int pageSize) {
        Sort sort = new Sort("create_time",SqlSort.DESC);
        return BaseDao.dao.queryForListPage(SalesProduct.class,conn,sort,pageNo,pageSize);
    }

    /**
     * 获取销售商品信息
     * @param conn
     * @return
     */
    @Override
    public List<SalesProduct> getSalesProductList(Conditions conn) {
        return BaseDao.dao.queryForListEntity(SalesProduct.class,conn);
    }

    /**
     * 获取销售单信息
     * @param sId
     * @return
     */
    @Override
    public SalesModel getSalesModel(String sId) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT s_id,sales_name,buy_uid,buy_name,buy_mobile,buy_count,total_amount,status FROM sales ");
        sql.append("WHERE s_id = ?");
        return BaseDao.dao.queryForEntity(SalesModel.class,sql.toString(),sId);
    }

    /**
     * 添加出出库信息
     * @param salesProducts
     * @param operatorId
     * @param operatorName
     */
    public void addProductAccessLog(List<SalesProduct> salesProducts, Integer operatorId, String operatorName) {
        if (salesProducts != null && salesProducts.size() > 0) {
            StringBuffer sql = new StringBuffer();
            List<Object> params =new ArrayList<Object>();
            sql.append("INSERT INTO product_access_log(pid,cat_id,product_num,wid,symbol,operator_id,operator_name) VALUES");
            for (SalesProduct salesProduct:salesProducts){
                sql.append("(?,?,?,?,?,?,?),");
                params.add(salesProduct.getPid());
                params.add(salesProduct.getCatId());
                params.add(salesProduct.getBuyCount());
                params.add(salesProduct.getWid());
                params.add(ProductAccessLogConstant.PRODUCT_SYMBOL_REDUCE);
                params.add(operatorId);
                params.add(operatorName);
            }
            BaseDao.dao.insert(sql.deleteCharAt(sql.length()-1).toString(), params.toArray());
        }
        iProductStockService.updateProductStock();
    }
}
