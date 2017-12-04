package com.logistics.service.product;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.common.Sort;
import cn.assist.easydao.common.SqlSort;
import cn.assist.easydao.dao.BaseDao;
import cn.assist.easydao.pojo.PagePojo;
import com.logistics.base.utils.RecordBean;
import com.logistics.service.model.ProductStockModel;
import com.logistics.service.vo.Product;
import com.logistics.service.vo.ProductStock;
import com.logistics.service.vo.PurchaseProduct;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Map;

public interface IProductStockService {

    /**
     * 查询商品库存信息
     * @param conn
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PagePojo<ProductStockModel> getProductStockModelPage(Conditions conn, int pageNo, int pageSize);

    /**
     * 获取商品库存信息
     * @param map
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PagePojo<ProductStockModel> getProductStockModelPage(Map<String,Object> map, int pageNo, int pageSize);


    /**
     * 获取商品库存数
     * @param conn
     * @return
     */
    public List<ProductStock> getProductStockList(Conditions conn);

    /**
     * 获取当前商品的库存数
     * @param pid
     * @return
     */
    public ProductStock getProductStock(String pid);

    /**
     * 更新商品库存
     */
    public void updateProductStock();

    public long getProductRemaining();

}
