package com.logistics.service.product.impl;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.common.Sort;
import cn.assist.easydao.common.SqlExpr;
import cn.assist.easydao.common.SqlSort;
import cn.assist.easydao.dao.BaseDao;
import cn.assist.easydao.pojo.PagePojo;
import com.alibaba.fastjson.JSON;
import com.logistics.base.constant.ProductConstant;
import com.logistics.base.utils.CommonUtil;
import com.logistics.base.utils.RecordBean;
import com.logistics.service.model.ProductModel;
import com.logistics.service.product.IProductCategoryService;
import com.logistics.service.product.IProductService;
import com.logistics.service.supplier.ISupplierService;
import com.logistics.service.vo.Product;
import com.logistics.service.vo.Supplier;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("IProductService")
public class ProductServiceImpl implements IProductService{

    Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private IProductCategoryService iProductCategoryService;
    @Autowired
    private ISupplierService iSupplierService;
    /**
     * 查询商品信息
     * @param map
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public PagePojo<Product> getProductPage(Map<String,Object> map, int pageNo, int pageSize) {
        StringBuffer sql = new StringBuffer();
        List<Object> params = new ArrayList<Object>();
        sql.append("SELECT * FROM product ");
        String name = (String) map.get("name");
        Integer catId = (Integer) map.get("catId");
        Integer status = (Integer) map.get("status");
        sql.append("WHERE 1=1 ");
        if (name != null && StringUtils.isNotEmpty(name)) {
            sql.append("AND name like ? ");
            params.add(CommonUtil.queryLike(name));
        }
        if (catId != null) {
            sql.append("AND cat_id = ? ");
            params.add(catId);
        }
        if (status != null) {
            sql.append("WHERE status = ? ");
            params.add(status);
        }
        Sort sort = new Sort("create_time", SqlSort.DESC);
        return BaseDao.dao.queryForListPage(Product.class,sql.toString(),params,sort,pageNo,pageSize);
    }

    /**
     * 获取指定分类下的商品
     * @param catId
     * @return
     */
    @Override
    public List<Product> getProductListByCatId(Integer catId) {
        Integer[] catIds = iProductCategoryService.getValidSubclass(catId);
        Conditions conn = new Conditions("cat_id",SqlExpr.IN,catIds);
        return BaseDao.dao.queryForListEntity(Product.class,conn);
    }

    /**
     * 添加商品信息
     * @param product
     * @return
     */
    @Override
    public RecordBean<Product> addProduct(Product product) {
        logger.info("添加商品信息【"+ JSON.toJSON(product)+"】");
        try {
            String pid = CommonUtil.getId("GOD");
            product.setPid(pid);
            product.setCreateTime(new Date());
            product.setUpdateTime(new Date());
            if (product.getReferencePrice() == null) {
                product.setReferencePrice(product.getPrice());
            }
            Supplier supplier = iSupplierService.getSupplier(product.getSupplierId());
            product.setSupplierName(supplier.getName());
            int result = BaseDao.dao.insert(product);
            if (result != 1) {
                return RecordBean.error("添加商品失败！");
            }
        } catch (Exception e) {
            logger.error("添加商品异常，原因是【"+e.getMessage()+"】");
            return RecordBean.error("添加商品异常");
        }
        return RecordBean.success("添加商品信息成功!");
    }

    /**
     * 修改商品信息
     * @param product
     * @return
     */
    @Override
    public RecordBean<Product> updateProduct(Product product) {
        logger.info("修改商品信息【"+ JSON.toJSON(product)+"】");
        try {
            product.setUpdateTime(new Date());
            if (product.getReferencePrice() == null) {
                product.setReferencePrice(product.getPrice());
            }
            Supplier supplier = iSupplierService.getSupplier(product.getSupplierId());
            product.setSupplierName(supplier.getName());
            int result = BaseDao.dao.update(product);
            if (result != 1) {
                return RecordBean.error("修改商品失败！");
            }
        } catch (Exception e) {
            logger.error("修改商品异常，原因是【"+e.getMessage()+"】");
            return RecordBean.error("修改商品异常");
        }
        return RecordBean.success("修改商品信息成功!");
    }

    /**
     * 删除商品信息
     * @param pids
     * @return
     */
    @Override
    public RecordBean<String> delProduct(String[] pids) {
        logger.info("删除商品信息"+JSON.toJSONString(pids));
        try {
            StringBuffer sql = new StringBuffer();
            sql.append("UPDATE product SET status = ? , update_time = now() WHERE pid IN (");
            sql.append(CommonUtil.arrayToSqlIn(pids));
            sql.append(")");
            int result = BaseDao.dao.update(sql.toString(),ProductConstant.PRODUCT_STATUS_DEL);
            if (result != 1) {
                return RecordBean.error("删除商品信息失败！");
            }
        } catch (Exception e) {
            logger.error("删除商品信息异常，原因是【"+e.getMessage()+"】");
            return RecordBean.error("删除商品信息异常");
        }
        return RecordBean.success("删除商品信息成功！");
    }

    /**
     * 获取商品信息
     * @param pid
     * @return
     */
    @Override
    public ProductModel getProductModel(String pid) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT * FROM product ");
        sql.append("WHERE pid = ? ");
        ProductModel productModel = BaseDao.dao.queryForEntity(ProductModel.class,sql.toString(),pid);
        Integer parentCatId =  iProductCategoryService.getTopParentCatId(productModel.getCatId());
        productModel.setParentCatId(parentCatId);
        return productModel;
    }



    @Override
    public PagePojo<Product> getProductGoodsBySupplierId(Integer supplierId,int pageNo, int pageSize) {
        Conditions conn = new Conditions("supplier_id", SqlExpr.EQUAL,supplierId);
        Sort sort = new Sort("create_time",SqlSort.DESC);
        return BaseDao.dao.queryForListPage(Product.class,conn,sort,pageNo,pageSize);
    }

    @Override
    public PagePojo<ProductModel> getProductModelPage(String pid,Integer catId, int pageNo, int pageSize) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT pid,p.name product_name,price product_price,p.cat_id,pc.name cat_name ");
        sql.append("FROM product p JOIN product_category pc ON (p.cat_id = pc.cat_id) ");
        sql.append("WHERE 1=1 ");
        if (StringUtils.isNotBlank(pid)){
            sql.append(" AND p.pid = ?");
            params.add(pid);
        }
        if (catId != null){
            Integer[] catIds = iProductCategoryService.getValidSubclass(catId);
            sql.append(" AND p.cat_id IN (");
            sql.append(CommonUtil.arrayToSqlIn(catIds));
            sql.append(")");
        }
        Sort sort = new Sort("p.create_time",SqlSort.DESC);
        return BaseDao.dao.queryForListPage(ProductModel.class,sql.toString(),params,sort,pageNo,pageSize);
    }
}
