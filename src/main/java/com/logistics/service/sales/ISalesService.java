package com.logistics.service.sales;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.pojo.PagePojo;
import com.logistics.base.utils.RecordBean;
import com.logistics.service.model.SalesModel;
import com.logistics.service.vo.ProductCategory;
import com.logistics.service.vo.Sales;
import com.logistics.service.vo.SalesProduct;

import java.util.List;
import java.util.Map;

/**
 * 销售单信息
 */
public interface ISalesService {

    /**
     * 获取销售单
     * @param map
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PagePojo<Sales> getSalesPage(Map<String, Object> map, int pageNo, int pageSize);


    /**
     * 获取销售单信息
     * @param conn
     * @return
     */
    public List<Sales> getSalesList(Conditions conn);

    /**
     * 添加销售单信息
     * @param SalesModel
     * @return
     */
    public RecordBean<SalesModel> addSalesModel(SalesModel SalesModel,Integer operatorId,String operatorName);



    /**
     * 批量添加销售单商品
     * @param salesProducts
     * @param peId
     * @return
     */
    public RecordBean<String> batchAddSalesProduct(List<SalesProduct> salesProducts, String peId);


    /**
     * 获取销售单商品
     * @param conn
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PagePojo<SalesProduct> getSalesProductPage(Conditions conn, int pageNo, int pageSize);

    /**
     * 获取销售单的商品
     * @param conn
     * @return
     */
    public List<SalesProduct> getSalesProductList(Conditions conn);

    /**
     * 获取销售单信息
     * @param peId
     * @return
     */
    public SalesModel getSalesModel(String peId);






}
