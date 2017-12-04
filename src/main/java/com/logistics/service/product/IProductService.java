package com.logistics.service.product;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.pojo.PagePojo;
import com.logistics.base.utils.RecordBean;
import com.logistics.service.model.ProductModel;
import com.logistics.service.vo.Product;

import java.util.List;
import java.util.Map;

public interface IProductService {

    /**
     * 获取产品信息
     * */
     PagePojo<Product> getProductPage(Map<String,Object> map, int pageNo, int pageSize);


    /**
     * 根据 catId 获取商品信息
     * @param catId
     * @return
     */
     List<Product> getProductListByCatId(Integer catId);

    /**
     *
     * 保存商品信息
     * @return
     */
     RecordBean<Product> addProduct(Product product);

    /**
     * 修改商品信息
     * @param product
     * @return
     */
     RecordBean<Product> updateProduct(Product product);

    /**
     * 删除商品信息
     * @param pids
     * @return
     */
    RecordBean<String> delProduct(String[] pids);

    /**
     * 获取商品信息
     * @param pid
     * @return
     */
    ProductModel getProductModel(String pid);

    /**
     * 获取供应商下的商品
     * @param supplierId
     * @return
     */
    PagePojo<Product> getProductGoodsBySupplierId(Integer supplierId,int pageNo, int pageSize);


    /**
     * 获取产品信息
     * */
    public PagePojo<ProductModel> getProductModelPage(String pid,Integer catId, int pageNo, int pageSize);

}
