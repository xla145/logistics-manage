package com.logistics.service.purchase.impl;

import cn.assist.easydao.common.*;
import cn.assist.easydao.dao.BaseDao;
import cn.assist.easydao.pojo.PagePojo;
import com.alibaba.fastjson.JSON;
import com.logistics.base.constant.ProductAccessLogConstant;
import com.logistics.base.constant.PurchaseConstant;
import com.logistics.base.utils.CommonUtil;
import com.logistics.base.utils.JsonBean;
import com.logistics.base.utils.RecordBean;
import com.logistics.service.model.PurchaseModel;
import com.logistics.service.product.IProductCategoryService;
import com.logistics.service.product.IProductStockService;
import com.logistics.service.purchase.IPurchaseService;
import com.logistics.service.vo.ProductCategory;
import com.logistics.service.vo.Purchase;
import com.logistics.service.vo.PurchaseProduct;
import com.logistics.service.warehouse.IWarehouseService;
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
 * 会员活动管理
 */
@Service("IVipActivityService")
public class PurchaseServiceImpl implements IPurchaseService {

    private static Logger logger = LoggerFactory.getLogger(PurchaseServiceImpl.class);
    @Autowired
    private IProductCategoryService iProductCategoryService;
    @Autowired
    private IProductStockService iProductStockService;
    @Autowired
    private IWarehouseService iWarehouseService;


    @Override
    public PagePojo<Purchase> getPurchasePage(Map<String, Object> map, int pageNo, int pageSize) {
        String createTime = (String) map.get("createTime");
        Conditions conditions = new Conditions();
        if (StringUtils.isNotBlank(createTime) && createTime.contains(" - ")) {
            String[] time = createTime.split(" - ");
            conditions = new Conditions("create_time", SqlExpr.GT_AND_EQUAL,time[0]);
            conditions.add(new Conditions("create_time", SqlExpr.LT_AND_EQUAL,time[1]), SqlJoin.AND);
        }
        Sort sort = new Sort("create_time", SqlSort.DESC);
        return BaseDao.dao.queryForListPage(Purchase.class,conditions,sort,pageNo,pageSize);
    }

    @Override
    public List<Purchase> getPurchaseList(Conditions conn) {
        return BaseDao.dao.queryForListEntity(Purchase.class,conn);
    }

    @Transactional
    @Override
    public RecordBean<PurchaseModel> addPurchaseModel(PurchaseModel purchaseModel,Integer operatorId,String operatorName) {
        logger.info("添加采购单信息【"+ JSON.toJSON(purchaseModel)+"】");
        try {
            Purchase purchase = new Purchase();
            String peId = CommonUtil.getId("PUR");
            purchase.setPeId(peId);
            purchase.setType(1);
            purchase.setStatus(PurchaseConstant.PURCHASE_STATUS_ENABLE);
            purchase.setPeDate(purchaseModel.getPeDate());
            purchase.setPurchaseName(purchaseModel.getPurchaseName());
            double totalAmount = 0.00;
            Integer buyCount = 0;
            for (PurchaseProduct purchaseProduct:purchaseModel.getProductList()) {
                buyCount += purchaseProduct.getBuyCount();
                totalAmount += purchaseProduct.getBuyCount()*purchaseProduct.getProductPrice().doubleValue();
            }
            purchase.setCreateTime(new Date());
            purchase.setUpdateTime(new Date());
            purchase.setBuyCount(buyCount);
            purchase.setTotalAmount(BigDecimal.valueOf(totalAmount));
            int result = BaseDao.dao.insert(purchase);
            if (result < 0) {
                return RecordBean.error("添加采购单信息失败！");
            }
            batchAddPurchaseProduct(purchaseModel.getProductList(),peId);
            addProductAccessLog(purchaseModel.getProductList(),operatorId,operatorName);
        } catch (Exception e) {
            logger.error("添加采购单异常【"+e.getMessage()+"】");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return RecordBean.error("添加采购单异常！");
        }
        return RecordBean.success("添加采购单成功！");

    }

    @Override
    public Integer[] getProductCategory(Integer catId) {
        return new Integer[0];
    }

    @Override
    public List<ProductCategory> getProductCategoryList(Integer catId) {
        return iProductCategoryService.getProductCategoryList(catId);
    }

    /**
     * 添加采购商品信息
     * @param purchaseProducts
     * @param peId
     * @return
     */
    @Override
    public RecordBean<String> batchAddPurchaseProduct(List<PurchaseProduct> purchaseProducts, String peId) {
        BaseDao.dao.update("DELETE FROM purchase_product WHERE pe_id = ?",peId);
        if (purchaseProducts != null && purchaseProducts.size() > 0) {
            StringBuffer sql = new StringBuffer();
            List<Object> params =new ArrayList<Object>();
            sql.append("INSERT INTO purchase_product(pid,wid,w_name,cat_id,cat_name,product_name,product_price,pe_id,buy_count,total_amount,create_time,update_time) VALUES");
            for (PurchaseProduct purchaseProduct:purchaseProducts){
                sql.append("(?,?,?,?,?,?,?,?,?,?,now(),now()),");
                params.add(purchaseProduct.getPid());
                params.add(purchaseProduct.getWid());
                params.add(iWarehouseService.getWarehouse(purchaseProduct.getWid()).getName());
                params.add(purchaseProduct.getCatId());
                params.add(purchaseProduct.getCatName());
                params.add(purchaseProduct.getProductName());
                params.add(purchaseProduct.getProductPrice());
                params.add(peId);
                params.add(purchaseProduct.getBuyCount());
                params.add(purchaseProduct.getBuyCount()*purchaseProduct.getProductPrice().doubleValue());
            }
            int result = BaseDao.dao.insert(sql.deleteCharAt(sql.length()-1).toString(), params.toArray());
            if (result < 1) {
                return RecordBean.error("添加采购商品信息失败！");
            }
        }
        return RecordBean.success("添加采购商品信息成功！");
    }

    @Override
    public PagePojo<PurchaseProduct> getPurchaseProductPage(Conditions conn, int pageNo, int pageSize) {
        Sort sort = new Sort("create_time",SqlSort.DESC);
        return BaseDao.dao.queryForListPage(PurchaseProduct.class,conn,sort,pageNo,pageSize);
    }

    /**
     * 获取采购商品信息
     * @param conn
     * @return
     */
    @Override
    public List<PurchaseProduct> getPurchaseProductList(Conditions conn) {
        return BaseDao.dao.queryForListEntity(PurchaseProduct.class,conn);
    }

    /**
     * 获取采购单信息
     * @param peId
     * @return
     */
    @Override
    public PurchaseModel getPurchaseModel(String peId) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT pe_id,buy_count,total_amount,pe_date,purchase_name FROM purchase ");
        sql.append("WHERE pe_id = ?");
        PurchaseModel purchaseModel = BaseDao.dao.queryForEntity(PurchaseModel.class,sql.toString(),peId);
        List<PurchaseProduct> purchaseProducts = getPurchaseProductList(new Conditions("pe_id",SqlExpr.EQUAL,peId));
        purchaseModel.setProductList(purchaseProducts);
        return purchaseModel;

    }

    /**
     * 添加进出库日志信息
     * @param purchaseProducts
     * @return
     */
    public void addProductAccessLog(List<PurchaseProduct> purchaseProducts,Integer operatorId,String operatorName) {
        if (purchaseProducts != null && purchaseProducts.size() > 0) {
            StringBuffer sql = new StringBuffer();
            List<Object> params =new ArrayList<Object>();
            sql.append("INSERT INTO product_access_log(pid,cat_id,product_num,wid,symbol,operator_id,operator_name) VALUES");
            for (PurchaseProduct purchaseProduct:purchaseProducts){
                sql.append("(?,?,?,?,?,?,?),");
                params.add(purchaseProduct.getPid());
                params.add(purchaseProduct.getCatId());
                params.add(purchaseProduct.getBuyCount());
                params.add(purchaseProduct.getWid());
                params.add(ProductAccessLogConstant.PRODUCT_SYMBOL_ADD);
                params.add(operatorId);
                params.add(operatorName);
            }
            BaseDao.dao.insert(sql.deleteCharAt(sql.length()-1).toString(), params.toArray());
        }
        iProductStockService.updateProductStock();
    }
}
