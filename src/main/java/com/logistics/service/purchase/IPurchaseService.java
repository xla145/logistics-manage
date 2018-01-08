package com.logistics.service.purchase;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.pojo.PagePojo;
import com.logistics.base.utils.RecordBean;
import com.logistics.service.model.PurchaseModel;
import com.logistics.service.vo.ProductCategory;
import com.logistics.service.vo.Purchase;
import com.logistics.service.vo.PurchaseProduct;

import java.util.List;
import java.util.Map;

/**
 * 会员活动
 */
public interface IPurchaseService {

    /**
     * 获取采购单
     * @param map
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PagePojo<Purchase> getPurchasePage(Map<String, Object> map, int pageNo, int pageSize);


    /**
     * 获取采购单信息
     * @param conn
     * @return
     */
    public List<Purchase> getPurchaseList(Conditions conn);

    /**
     * 添加采购单信息
     * @param purchaseModel
     * @return
     */
    public RecordBean<PurchaseModel> addPurchaseModel(PurchaseModel purchaseModel,Integer operatorId,String operatorName);


    /**
     * 添加采购单信息
     * @param purchaseModel
     * @return
     */
    public RecordBean<PurchaseModel> editPurchaseModel(PurchaseModel purchaseModel,Integer operatorId,String operatorName);



    /**
     * 获取catId 下属的所有有用的类型
     * @param catId
     * @return
     */
    public Integer[] getProductCategory(Integer catId);

    /**
     * 获取catId 下属的所有有用的类型
     * @param catId
     * @return
     */
    public List<ProductCategory> getProductCategoryList(Integer catId);


    /**
     * 批量添加采购单商品
     * @param purchaseProducts
     * @param peId
     * @return
     */
    public RecordBean<String> batchAddPurchaseProduct(List<PurchaseProduct> purchaseProducts, String peId);


    /**
     * 获取采购单商品
     * @param conn
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PagePojo<PurchaseProduct> getPurchaseProductPage(Conditions conn, int pageNo, int pageSize);

    /**
     * 获取采购单的商品
     * @param conn
     * @return
     */
    public List<PurchaseProduct> getPurchaseProductList(Conditions conn);

    /**
     * 获取采购单信息
     * @param peId
     * @return
     */
    public PurchaseModel getPurchaseModel(String peId);

    /**
     * 批量添加采购单商品
     * @param status
     * @param peId
     * @param reason
     * @return
     */
    public RecordBean<String> auditPurchase(String peId,Integer status,String reason);


    /**
     * 添加审核单
     * @param peId
     * @return
     */
    public RecordBean<String> addAuditOrder(String peId);


}
