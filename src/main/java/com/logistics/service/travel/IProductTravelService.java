package com.logistics.service.travel;

import java.util.List;
import java.util.Map;

import cn.assist.easydao.pojo.PagePojo;

import com.logistics.base.utils.DataObj;
import com.logistics.base.utils.RecordBean;
import com.logistics.service.model.ProductTravelModel;
import com.logistics.service.vo.travel.ProductTravelArrange;
import com.logistics.service.vo.Supplier;
import com.logistics.service.vo.travel.ProductTravelSummary;


/**
 * 旅游产品
 */
public interface IProductTravelService {

    /**
     * Product goods page page pojo.
     *
     * @param map      the map
     * @param pageNo   the page no
     * @param pageSize the page size
     * @return PagePojo<ProductGoodsModel>      返回类型
     * @throws
     * @Title: productGoodsPage
     * @Description: TODO(获取旅游产品信息)
     */
    public PagePojo<ProductTravelModel> productTravelPage(Map<String,Object> map, int pageNo, int pageSize);

    /**
     * Add product goods data obj.
     *
     * @param productTravelModel the product goods model
     * @return DataObj<ProductGoodsModel>      返回类型
     * @throws
     * @Title: addProductGoods
     * @Description: TODO(添加旅游产品)
     */
    public RecordBean<String> addProductTravel(ProductTravelModel productTravelModel);

    /**
     * Del product goods data obj.
     *
     * @param ids the ids
     * @return DataObj<ProductActivity>      返回类型
     * @throws
     * @Title: delProductGoods
     * @Description: TODO(删除旅游产品)
     */
    public DataObj<String> delProductTravel(String[] ids);

    /**
     * Update product goods data obj.
     *
     * @param productTravelModel the product goods model
     * @return DataObj<ProductActivity>      返回类型
     * @throws
     * @Title: updateProductActivity
     * @Description: TODO(添加旅游产品)
     */
    public DataObj<ProductTravelModel> updateProductTravel(ProductTravelModel productTravelModel);

    /**
     * Gets product goods.
     *
     * @param productId the product id
     * @return ProductActivityModel 返回类型
     * @throws
     * @Title: getProductActivity
     * @Description: TODO(获取旅游信息通过产品编号)
     */
    public ProductTravelModel getProductTravel(String productId);

    /**
     * Up or down product goods data obj.
     *
     * @param ids    the ids
     * @param status the status
     * @return DataObj<String>      返回类型
     * @throws
     * @Title: upOrDownProductGoods
     * @Description: TODO(批量上下架)
     */
    public DataObj<String> upOrDownProductTravel(String[] ids, Integer status);

    /**
     * Suppiler list list.
     * @Description: TODO(获取供应商数据)
     * @param map the map
     * @return the list
     */
    public PagePojo<Supplier> supplierPage(Map<String,Object> map, int pageNo, int pageSize);


    /**
     * 添加行程安排
     * @param productTravelArrangeList
     * @param productId
     * @return
     */
    public DataObj<String> batchAddProductTravelArrange(List<ProductTravelArrange> productTravelArrangeList, String productId);

    /**
     * 获取有效的旅游产品
     * 
     * @return
     */
    public List<ProductTravelSummary> getTravelSummaryList(Integer status);

    /**
     * 复制旅游商品
     *
     * @return
     */
    public RecordBean<String> copyProduct(String pid);
    
}
