package com.logistics.service.purchase;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.pojo.PagePojo;
import com.logistics.base.utils.RecordBean;
import com.logistics.service.model.PurchaseModel;
import com.logistics.service.vo.Product;
import com.logistics.service.vo.ProductCategory;
import com.logistics.service.vo.Purchase;

import java.util.List;
import java.util.Map;

/**
 * 会员活动
 */
public interface IPurchaseyService {

    /**
     * 获取采购单
     * @param map
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PagePojo<Purchase> getPurchasePage(Map<String, Object> map, int pageNo, int pageSize);


    /**
     * 获取会员活动
     * @param conn
     * @return
     */
    public List<Purchase> getPurchaseList(Conditions conn);

    /**
     * 添加采购单信息
     * @param purchaseModel
     * @return
     */
    public RecordBean<PurchaseModel> addPurchaseModel(PurchaseModel purchaseModel);

    /**
     * 修改会员活动
     * @param purchaseModel
     * @return
     */
    public RecordBean<PurchaseModel> updatePurchaseModel(PurchaseModel purchaseModel);


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
     * 批量添加活动商品信息
     * @param vipActivityProductList
     * @param activityId
     * @return
     */
    public RecordBean<String> batchAddPurchaseProduct(List<Product> vipActivityProductList, Integer activityId);


    /**
     * 获取活动下的商品
     * @param conn
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PagePojo<Product> getVipActivityProductPage(Conditions conn, int pageNo, int pageSize);

    /**
     * 获取活动下的商品
     * @param conn
     * @return
     */
    public List<Product> getVipActivityProductList(Conditions conn);

    /**
     * 获取活动信息
     * @param activityId
     * @return
     */
    public PurchaseModel getVipActivityModel(Integer activityId);

    /**
     * 活动的停用或启用
     * @param activityId
     * @param status
     * @return
     */
    public RecordBean<String> changeStatus(String[] activityId, Integer status);



}
