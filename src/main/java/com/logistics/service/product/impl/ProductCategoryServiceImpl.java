package com.logistics.service.product.impl;

import cn.assist.easydao.common.*;
import cn.assist.easydao.dao.BaseDao;
import cn.assist.easydao.pojo.PagePojo;
import com.alibaba.fastjson.JSON;
import com.logistics.base.constant.ProductCategoryConstant;
import com.logistics.base.utils.CommonUtil;
import com.logistics.base.utils.RecordBean;
import com.logistics.service.product.IProductCategoryService;
import com.logistics.service.vo.CategoryTreeNode;
import com.logistics.service.vo.ProductCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("IProductCategoryService")
public class ProductCategoryServiceImpl implements IProductCategoryService {
    Logger logger = LoggerFactory.getLogger(ProductCategoryServiceImpl.class);


    /**
     * 查询子级分类列表(下级列表)
     *
     * @param catId
     * @return
     */
    public List<ProductCategory> getProductCategoryListByValid(int catId){
        Conditions conn = new Conditions("parent_id", SqlExpr.EQUAL, catId);
        conn.add(new Conditions("status", SqlExpr.EQUAL, ProductCategoryConstant.CATEGORY_STATUS_ENABLED), SqlJoin.AND);
        conn.add(new Conditions("is_show", SqlExpr.EQUAL, 1), SqlJoin.AND);

        Sort sort = new Sort("weight", SqlSort.ASC);
        return BaseDao.dao.queryForListEntity(ProductCategory.class, conn, sort);
    }

    /**
     * 根据父id获取子类信息
     * @param parentId
     * @return
     */
    @Override
    public List<ProductCategory> getProductCategoryList(int parentId) {
        Conditions conn = new Conditions("parent_id",SqlExpr.EQUAL,parentId);
        conn.add(new Conditions("status", SqlExpr.UNEQUAL, ProductCategoryConstant.CATEGORY_STATUS_DEL), SqlJoin.AND);
        return BaseDao.dao.queryForListEntity(ProductCategory.class,conn);
    }

    /**
     * 查询所有子级id数组（有效的 ）
     *
     * @param catId
     * @return
     */
    public Integer[] getValidSubclass(int catId){
        Conditions conn = new Conditions("cat_id", SqlExpr.EQUAL, catId);
        conn.add(new Conditions("is_show", SqlExpr.EQUAL, 1), SqlJoin.AND);
        conn.add(new Conditions("status", SqlExpr.EQUAL, ProductCategoryConstant.CATEGORY_STATUS_ENABLED), SqlJoin.AND);
        ProductCategory category = BaseDao.dao.queryForEntity(ProductCategory.class, conn);
        if(category == null){
            return null;
        }

        List<CategoryTreeNode> categoryTreeNode = new ArrayList<CategoryTreeNode>();
        CategoryTreeNode treeNode = getCategoryTree(category, 1, ProductCategoryConstant.CATEGORY_STATUS_ENABLED);
        categoryTreeNode.add(treeNode);

        Set<Integer> idSet = new HashSet<Integer>();
        getCategoryTreeIds(treeNode, idSet);
        if(idSet == null || idSet.size() < 1){
            return null;
        }
        Integer[] ids = new Integer[idSet.size()];
        idSet.toArray(ids);

        return ids;

    }

    /**
     * 查询所有子级id数组
     *
     * @param catId
     * @return
     */
    public Integer[] getAllSubclass(int catId){
        Conditions conn = new Conditions("cat_id", SqlExpr.EQUAL, catId);
        ProductCategory category = BaseDao.dao.queryForEntity(ProductCategory.class, conn);
        if(category == null){
            return null;
        }

        List<CategoryTreeNode> categoryTreeNode = new ArrayList<CategoryTreeNode>();
        CategoryTreeNode treeNode = getCategoryTree(category, null, null);
        categoryTreeNode.add(treeNode);

        Set<Integer> idSet = new HashSet<Integer>();
        getCategoryTreeIds(treeNode, idSet);
        if(idSet == null || idSet.size() < 1){
            return null;
        }
        Integer[] ids = new Integer[idSet.size()];
        idSet.toArray(ids);

        return ids;
    }

    /**
     * 查询所有父级id数组
     *
     * @param catId
     * @return 返回数组包含本身caiId
     */
    public Integer[] getAllParents(int catId){
        Conditions conn = new Conditions("cat_id", SqlExpr.EQUAL, catId);
        conn.add(new Conditions("is_show", SqlExpr.EQUAL, 1), SqlJoin.AND);
        conn.add(new Conditions("status", SqlExpr.EQUAL, ProductCategoryConstant.CATEGORY_STATUS_ENABLED), SqlJoin.AND);
        ProductCategory category = BaseDao.dao.queryForEntity(ProductCategory.class, conn);
        if(category == null){
            return null;
        }

        List<CategoryTreeNode> categoryTreeNode = new ArrayList<CategoryTreeNode>();
        CategoryTreeNode treeNode = getReverseCategoryTree(category, null, null);
        categoryTreeNode.add(treeNode);

        Set<Integer> idSet = new HashSet<Integer>();
        getCategoryTreeIds(treeNode, idSet);
        if(idSet == null || idSet.size() < 1){
            return null;
        }
        Integer[] ids = new Integer[idSet.size()];
        idSet.toArray(ids);

        return ids;
    }

    /**
     * 分页获取
     * @param conn      the conn
     * @param pageNo   the page no
     * @param pageSize the page size
     * @return
     */
    @Override
    public PagePojo<ProductCategory> getProductCategoryPage(Conditions conn, int pageNo, int pageSize) {
        conn.add(new Conditions("status", SqlExpr.UNEQUAL, ProductCategoryConstant.CATEGORY_STATUS_DEL), SqlJoin.AND);
        Sort sort = new Sort("create_time",SqlSort.DESC);
        return BaseDao.dao.queryForListPage(ProductCategory.class,conn,sort,pageNo,pageSize);
    }

    @Override
    public RecordBean<ProductCategory> addProductCategory(ProductCategory productCategory) {
        logger.info("添加分类信息"+ JSON.toJSON(productCategory));
        Date time = new Date();
        productCategory.setCreateTime(time);
        productCategory.setUpdateTime(time);
        Integer result = BaseDao.dao.insert(productCategory);
        if (result != 1) {
            return RecordBean.error("添加分类失败！");
        }
        return RecordBean.success("",productCategory);
    }

    @Override
    public RecordBean<ProductCategory> updateProductCategory(ProductCategory productCategory) {
        logger.info("修改分类信息"+ JSON.toJSON(productCategory));
        Date time = new Date();
        productCategory.setUpdateTime(time);
        Integer result = BaseDao.dao.update(productCategory);
        if (result != 1) {
            return RecordBean.error("修改分类失败！");
        }
        return RecordBean.success("",productCategory);
    }

    /**
     * 获取分类信息
     * @param id
     * @return
     */
    @Override
    public ProductCategory getProductCategory(Integer id) {
        return BaseDao.dao.queryForEntity(ProductCategory.class,new Conditions("cat_id",SqlExpr.EQUAL,id));
    }

    /**
     * 删除产品类型信息
     * @param id
     * @return
     */
    @Override
    public RecordBean<String> delProductCategory(String[] id) {
        StringBuffer sql = new StringBuffer();
        sql.append("UPDATE product_category SET status = ? ,update_time = now()");
        sql.append("WHERE cat_id IN (");
        sql.append(CommonUtil.arrayToSqlIn(id));
        sql.append(")");
        Integer result = BaseDao.dao.update(sql.toString(),ProductCategoryConstant.CATEGORY_STATUS_DEL);
        if (result != 1) {
            return RecordBean.error("删除失败！");
        }
        return RecordBean.success("删除成功！");
    }

    /**
     * 获取treeid集合
     *
     * @param treeNode
     * @param idSet
     */
    private void getCategoryTreeIds(CategoryTreeNode treeNode, Set<Integer> idSet){
        if(treeNode == null){
            return;
        }
        idSet.add(treeNode.getCatId());

        List<CategoryTreeNode> children = treeNode.getChildren();
        if(children != null && children.size() > 0){
            for (CategoryTreeNode ctn : children) {
                idSet.add(ctn.getCatId());
                getCategoryTreeIds(ctn, idSet);
            }
        }
    }

    /**
     * 获取产品分类树
     *
     * @param ProductCategory   父级对象
     * @param isShow			是否显示
     * @param status			状态
     * @return
     */
    private CategoryTreeNode getCategoryTree(ProductCategory ProductCategory, Integer isShow, Integer status) {
        int catId = ProductCategory.getCatId();
        CategoryTreeNode treeNode = new CategoryTreeNode();
        treeNode.setCatId(catId);
        treeNode.setName(ProductCategory.getName());
        treeNode.setParentId(ProductCategory.getParentId());
        treeNode.setIsShow(ProductCategory.getIsShow());
        treeNode.setImgUrl(ProductCategory.getImgUrl());

        Conditions conn = new Conditions("parent_id", SqlExpr.EQUAL, catId);
        if(isShow != null){
            conn.add(new Conditions("is_show", SqlExpr.EQUAL, isShow), SqlJoin.AND);
        }
        if(status != null){
            conn.add(new Conditions("status", SqlExpr.EQUAL, status), SqlJoin.AND);
        }

        List<ProductCategory> categoryList =  BaseDao.dao.queryForListEntity(ProductCategory.class, conn);
        for (ProductCategory cs : categoryList) {
            CategoryTreeNode tn = getCategoryTree(cs, isShow, status);
            treeNode.getChildren().add(tn);
        }
        return treeNode;
    }

    /**
     * 获取产品分类树
     *
     * @param ProductCategory   子级对象
     * @param isShow			是否显示
     * @param status			状态
     * @return
     */
    private CategoryTreeNode getReverseCategoryTree(ProductCategory ProductCategory, Integer isShow, Integer status) {
        int catId = ProductCategory.getCatId();
        int parentId = ProductCategory.getParentId();
        CategoryTreeNode treeNode = new CategoryTreeNode();
        treeNode.setCatId(catId);
        treeNode.setName(ProductCategory.getName());
        treeNode.setParentId(ProductCategory.getParentId());
        treeNode.setIsShow(ProductCategory.getIsShow());
        treeNode.setImgUrl(ProductCategory.getImgUrl());

        Conditions conn = new Conditions("cat_id", SqlExpr.EQUAL, parentId);
        if(isShow != null){
            conn.add(new Conditions("is_show", SqlExpr.EQUAL, isShow), SqlJoin.AND);
        }
        if(status != null){
            conn.add(new Conditions("status", SqlExpr.EQUAL, status), SqlJoin.AND);
        }

        List<ProductCategory> categoryList =  BaseDao.dao.queryForListEntity(ProductCategory.class, conn);
        for (ProductCategory cs : categoryList) {
            CategoryTreeNode tn = getReverseCategoryTree(cs, isShow, status);
            treeNode.getChildren().add(tn);
        }
        return treeNode;
    }

    /**
     * 获取顶级父类的catId
     * @param childrenCatId  传入的子类catId
     * @return				  顶级父类catId
     */
    public Integer getTopParentCatId(int childrenCatId){
        Integer[] allParents = getAllParents(childrenCatId);
        System.out.println(JSON.toJSON(allParents));
        List<ProductCategory> productCategoryList = getProductCategoryList(0);
        System.out.println(JSON.toJSON(productCategoryList));
        for (ProductCategory productCategory:productCategoryList) {
            System.out.println(productCategory.getCatId());
            for (Integer catId:allParents) {
                if (productCategory.getCatId().compareTo(catId) == 0) {
                    return catId;
                }
            }

        }
        return 0;
    }

}
