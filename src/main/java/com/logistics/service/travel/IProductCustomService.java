package com.logistics.service.travel;

import cn.assist.easydao.pojo.PagePojo;
import com.logistics.base.utils.DataObj;
import com.logistics.service.vo.travel.TravelCustom;

import java.util.Map;

/**
 * Created by Administrator on 2017/8/29/029.
 * 定制旅游
 */
public interface IProductCustomService {

    /**
     * Product goods page page pojo.
     *
     * @param map      the map
     * @param pageNo   the page no
     * @param pageSize the page size
     * @return PagePojo<TravelCustom>      返回类型
     * @throws
     * @Title: productGoodsPage
     * @Description: TODO(获取定制旅游产品信息)
     */
    public PagePojo<TravelCustom> productTravelCustomPage(Map<String,Object> map, int pageNo, int pageSize);

    /**
     * Add product TravelCustoms data obj.
     *
     * @param productTravelCustom
     * @return DataObj<ProductGoodsModel>      返回类型
     * @throws
     * @Title: addProductGoods
     * @Description: TODO(添加定制旅游信息)
     */
    public DataObj<TravelCustom> addProductTravelCustom(TravelCustom productTravelCustom);

    /**
     * @param ids the ids
     * @return DataObj<ProductActivity>      返回类型
     * @throws
     * @Title: delProductTravelCustom
     * @Description: TODO(删除定制旅游信息)
     */
    public DataObj<String> delProductTravelCustom(String[] ids);


    /**
     * @param ids the ids
     * @return DataObj<ProductActivity>      返回类型
     * @throws
     * @Title: delProductTravelCustom
     * @Description: TODO(处理制旅游信息)
     */
    public DataObj<String> dealProductTravelCustom(String[] ids,Integer operationId,String operationName);

    /**
     * Update
     * @param productTravelCustom
     * @return DataObj<ProductActivity>      返回类型
     * @throws
     * @Title: updateProductTravelCustom
     * @Description: TODO(更新定制旅游信息)
     */
    public DataObj<TravelCustom> updateProductTravelCustom(TravelCustom productTravelCustom);

    /**
     *
     * @param mid
     * @return TravelCustom 返回类型
     * @throws
     * @Title: getProductActivity
     * @Description: TODO(获取好玩活动信息通过产品编号)
     */
    public TravelCustom getProductTravelCustom(String mid);

}
