package com.logistics.service.product;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.pojo.PagePojo;
import com.logistics.base.utils.RecordBean;
import com.logistics.service.vo.ProductCategory;

import java.util.List;

public interface IProductCategoryService {

    /**
     * 查询子级分类列表(下级列表)
     *
     * @param parentId 父级id
     * @return
     */
    public List<ProductCategory> getProductCategoryListByValid(int parentId);

    /**
     * 查询子级分类列表 (下级列表)
     *
     * @param parentId
     * @return
     */
    public List<ProductCategory> getProductCategoryList(int parentId);

    /**
     * 查询所有子级id数组（有效的 ）
     *
     * @param catId
     * @return 返回数组包含本身caiId
     */
    public Integer[] getValidSubclass(int catId);

    /**
     * 查询所有子级id数组
     *
     * @param catId
     * @return 返回数组包含本身caiId
     */
    public Integer[] getAllSubclass(int catId);

    /**
     * 查询所有父级id数组
     *
     * @param catId
     * @return 返回数组包含本身caiId
     */
    public Integer[] getAllParents(int catId);

    /**
     * Product goods page page pojo.
     *
     * @param conn     the conn
     * @param pageNo   the page no
     * @param pageSize the page size
     * @return PagePojo<TravelCustom>      返回类型
     * @Title: productGoodsPage
     * @Description: (获取产品类型)
     */
    public PagePojo<ProductCategory> getProductCategoryPage(Conditions conn, int pageNo, int pageSize);

    /**
     * Add product TravelCustoms data obj.
     *
     * @param productCategory
     * @return RecordBean<ProductCategory>      返回类型
     * @Title: addProductTravelCategory
     * @Description: (添加产品类型)
     */
    public RecordBean<ProductCategory> addProductCategory(ProductCategory productCategory);

    /**
     * Update
     *
     * @param productCategory
     * @return RecordBean<ProductCategory>      返回类型
     * @throws
     * @Title: updateProductTravelCustom
     * @Description: (更新产品类型)
     */
    public RecordBean<ProductCategory> updateProductCategory(ProductCategory productCategory);

    /**
     * @param id
     * @return ProductCategory 返回类型
     * @throws
     * @Title: getProductCategory
     * @Description: (获取产品类型)
     */
    public ProductCategory getProductCategory(Integer id);

    /**
     * @param id
     * @return DataBean<String> 返回类型
     * @throws
     * @Title: delProductCategory
     * @Description: (删除产品类型信息)
     */
    public RecordBean<String> delProductCategory(String[] id);


    /**
     * 获取顶级父类的catId
     *
     * @param childrenCatId 传入的子类catId
     * @return 顶级父类catId
     */
    public Integer getTopParentCatId(int childrenCatId);
}
