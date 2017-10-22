package com.logistics.service.travel.impl;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.pojo.PagePojo;
import com.logistics.base.constant.ProductConstant;
import com.logistics.base.utils.RecordBean;
import com.logistics.service.helper.DataBean;
import com.logistics.service.product.api.IProductCategoryService;
import com.logistics.service.product.vo.ProductCategory;
import com.logistics.service.travel.IProductTravelCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/10/10/010.
 * 产品类型
 */
@Service("IProductTravelCategoryService")
public class ProductTravelCategoryServiceImpl implements IProductTravelCategoryService {

    @Autowired
    private IProductCategoryService iProductCategoryService;

    /**
     * 获取产品类型
     * @param conn      the conn
     * @param pageNo   the page no
     * @param pageSize the page size
     * @return
     */
    @Override
    public PagePojo<ProductCategory> productCategoryPage(Conditions conn, int pageNo, int pageSize) {
        return iProductCategoryService.getProductCategoryPage(conn,pageNo,pageSize);
    }

    /**
     * 添加产品类型
     * @param productCategory
     * @return
     */
    @Override
    public RecordBean<ProductCategory> addProductCategory(ProductCategory productCategory) {
        productCategory.setParentId(ProductConstant.TRAVEL_THEME);
        DataBean<ProductCategory> result = iProductCategoryService.addProductCategory(productCategory);
        if (result.isOk()) {
            return RecordBean.success(result.getMsg());
        }
        return RecordBean.error(result.getMsg());
    }

    /**
     * 删除产品类型信息
     * @param ids the ids
     * @return
     */
    @Override
    public RecordBean<String> delProductCategory(String[] ids) {
        DataBean result = iProductCategoryService.delProductCategory(ids);
        if (result.isOk()) {
            return RecordBean.success(result.getMsg());
        }
        return RecordBean.error(result.getMsg());
    }

    /**
     * 更新产品类型信息
     * @param productCategory
     * @return
     */
    @Override
    public RecordBean<ProductCategory> updateProductCategory(ProductCategory productCategory) {
        DataBean<ProductCategory> result = iProductCategoryService.updateProductCategory(productCategory);
        if (result.isOk()) {
            return RecordBean.success(result.getMsg());
        }
        return RecordBean.error(result.getMsg());
    }

    /**
     * 获取产品类型信息
     * @param id
     * @return
     */
    @Override
    public ProductCategory getProductCategory(Integer id) {
        return iProductCategoryService.getProductCategory(id);
    }

    /**
     * 根据父类编号获取子类信息
     * @param parentId
     * @return
     */
    @Override
    public List<ProductCategory> productCategoryList(Integer parentId) {
        return iProductCategoryService.getProductCategoryList(parentId);
    }
}
