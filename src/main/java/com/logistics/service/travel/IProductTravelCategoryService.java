package com.logistics.service.travel;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.pojo.PagePojo;
import com.logistics.base.utils.RecordBean;
import com.logistics.service.product.vo.ProductCategory;

import java.util.List;

/**
 * Created by Administrator on 2017/8/29/029.
 * 产品的分类
 */
public interface IProductTravelCategoryService {

    /**
     * Product goods page page pojo.
     *
     * @param conn      the conn
     * @param pageNo   the page no
     * @param pageSize the page size
     * @return PagePojo<TravelCustom>      返回类型
     * @throws
     * @Title: productGoodsPage
     * @Description: TODO(获取产品类型)
     */
    public PagePojo<ProductCategory> productCategoryPage(Conditions conn, int pageNo, int pageSize);

    /**
     * Add product TravelCustoms data obj.
     *
     * @param productCategory
     * @return RecordBean<ProductCategory>      返回类型
     * @throws
     * @Title: addProductTravelCategory
     * @Description: TODO(添加产品类型)
     */
    public RecordBean<ProductCategory> addProductCategory(ProductCategory productCategory);

    /**
     * @param ids the ids
     * @return RecordBean<String>      返回类型
     * @throws
     * @Title: delProductTravelCustom
     * @Description: TODO(删除产品类型)
     */
    public RecordBean<String> delProductCategory(String[] ids);

    /**
     * Update
     * @param productCategory
     * @return RecordBean<ProductCategory>      返回类型
     * @throws
     * @Title: updateProductTravelCustom
     * @Description: TODO(更新产品类型)
     */
    public RecordBean<ProductCategory> updateProductCategory(ProductCategory productCategory);

    /**
     *
     * @param id
     * @return ProductCategory 返回类型
     * @throws
     * @Title: getProductCategory
     * @Description: TODO(获取产品类型)
     */
    public ProductCategory getProductCategory(Integer id);

    /**
     * 获取子类信息
     * @param parentId
     * @return
     */
    public List<ProductCategory> productCategoryList(Integer parentId);

}
