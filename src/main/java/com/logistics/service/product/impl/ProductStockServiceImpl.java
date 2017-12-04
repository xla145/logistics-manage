package com.logistics.service.product.impl;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.common.Sort;
import cn.assist.easydao.common.SqlExpr;
import cn.assist.easydao.common.SqlSort;
import cn.assist.easydao.dao.BaseDao;
import cn.assist.easydao.pojo.PagePojo;
import com.alibaba.fastjson.JSON;
import com.logistics.base.constant.ProductAccessLogConstant;
import com.logistics.base.utils.CommonUtil;
import com.logistics.service.model.ProductAccessLogModel;
import com.logistics.service.model.ProductStockModel;
import com.logistics.service.product.IProductCategoryService;
import com.logistics.service.product.IProductStockService;
import com.logistics.service.vo.ProductAccessLog;
import com.logistics.service.vo.ProductStock;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("IProductStockService")
public class ProductStockServiceImpl implements IProductStockService {

    @Autowired
    private IProductCategoryService iProductCategoryService;
    /**
     * 获取商品库存信息
     * @param conn
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public PagePojo<ProductStockModel> getProductStockModelPage(Conditions conn, int pageNo, int pageSize) {
        StringBuffer sql = new StringBuffer();
        List<Object> params = null;
        sql.append("SELECT ps.pid,ps.cat_id,ps.wid,ps.stock,ps.remaining,p.price product_price,p.name product_name ");
        sql.append("FROM product p JOIN product_stock ps ON(p.pid = ps.pid) ");
        sql.append("WHERE 1=1 ");
        if (StringUtils.isNotBlank(conn.getConnSql()) && conn.getConnParams().size() > 0) {
            sql.append( "AND"+ conn.getConnSql());
            params = conn.getConnParams();
        }
        Sort sort = new Sort("ps.create_time", SqlSort.DESC);
        return BaseDao.dao.queryForListPage(ProductStockModel.class,sql.toString(),params,sort,pageNo,pageSize);
    }


    /**
     * 获取商品库存信息
     * @param map
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public PagePojo<ProductStockModel> getProductStockModelPage(Map<String,Object> map, int pageNo, int pageSize) {
        StringBuffer sql = new StringBuffer();
        List<Object> params = new ArrayList<Object>();
        sql.append("SELECT ps.pid,ps.cat_id,ps.wid,ps.stock,ps.remaining,p.price product_price,p.name product_name ");
        sql.append("FROM product p JOIN product_stock ps ON(p.pid = ps.pid) ");
        sql.append("WHERE 1=1 ");
        String wid = (String) map.get("wid");
        String pid = (String) map.get("pid");
        Integer catId = (Integer) map.get("catId");
        if (StringUtils.isNotBlank(wid)) {
            sql.append("AND p.wid = ?");
            params.add(wid);
        }
        if (StringUtils.isNotBlank(pid)) {
            sql.append("AND p.pid = ?");
            params.add(pid);
        }
        if (catId != null) {
            sql.append("AND ps.cat_id IN (");
            sql.append(CommonUtil.arrayToSqlIn(iProductCategoryService.getValidSubclass(catId)));
            sql.append(")");
        }
        Sort sort = new Sort("ps.create_time", SqlSort.DESC);
        return BaseDao.dao.queryForListPage(ProductStockModel.class,sql.toString(),params,sort,pageNo,pageSize);
    }

    /**
     * 获取商品的库存数
     * @param conn
     * @return
     */
    @Override
    public List<ProductStock> getProductStockList(Conditions conn) {
        return BaseDao.dao.queryForListEntity(ProductStock.class,conn);
    }

    /**
     * 库存增加或减少统一更新库存
     */

    public void updateProductStock() {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT wid,pid,SUM(product_num) total_product,cat_id FROM product_access_log ");
        sql.append("WHERE symbol = ? ");
        sql.append("GROUP BY wid,pid ");
        List<ProductAccessLogModel> productStockModelList = BaseDao.dao.queryForListEntity(ProductAccessLogModel.class,sql.toString(), ProductAccessLogConstant.PRODUCT_SYMBOL_ADD);
        List<ProductAccessLogModel> productStockModelList1 = BaseDao.dao.queryForListEntity(ProductAccessLogModel.class,sql.toString(), ProductAccessLogConstant.PRODUCT_SYMBOL_REDUCE);
        System.out.println(JSON.toJSON(productStockModelList));
        BaseDao.dao.delete("DELETE FROM product_stock");
        StringBuffer sql_1 = new StringBuffer();
        List<Object> params = new ArrayList<Object>();
        sql_1.append("INSERT INTO product_stock(pid,wid,cat_id,stock,remaining,create_time,update_time) VALUES");
        for (ProductAccessLogModel productAccessLog:productStockModelList) {
            Integer remaining = productAccessLog.getTotalProduct();
            for (ProductAccessLogModel productAccessLog1:productStockModelList1) {
                if (productAccessLog.getPid().equalsIgnoreCase(productAccessLog1.getPid()) && productAccessLog.getWid().equalsIgnoreCase(productAccessLog1.getWid())) {
                    remaining = productAccessLog.getTotalProduct() - productAccessLog1.getTotalProduct();
                    break;
                }
            }
            sql_1.append("(?,?,?,?,?,now(),now()),");
            params.add(productAccessLog.getPid());
            params.add(productAccessLog.getWid());
            params.add(productAccessLog.getCatId());
            params.add(productAccessLog.getTotalProduct());
            params.add(remaining);

        }
        System.out.println(JSON.toJSON(params));
        BaseDao.dao.insert(sql_1.deleteCharAt(sql_1.length()-1).toString(),params.toArray());
    }

    @Override
    public long getProductRemaining() {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT SUM(remaining) sum_remaining FROM product_stock");
        Map<String,Object> map = BaseDao.dao.queryForMap(sql.toString());
        int sumRemaining = (int) map.get("sumRemaining");
        return sumRemaining;
    }

    /**
     * 获取商品的库存数
     * @param pid
     * @return
     */
    @Override
    public ProductStock getProductStock(String pid) {
        Conditions conn = new Conditions("pid", SqlExpr.EQUAL,pid);
        return BaseDao.dao.queryForEntity(ProductStock.class,conn);
    }


}
