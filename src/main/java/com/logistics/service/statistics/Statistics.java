package com.logistics.service.statistics;


import com.logistics.base.utils.RecordBean;

/**
 * 统计接口
 */
public interface Statistics {

    /**
     * 统计销售单，采购单总数及产品库存，总收入
     * @return
     */
    public RecordBean statisticsSumMessage();

    /**
     * 统计各个仓库产品的库存
     * @return
     */
    public RecordBean statisticsWarehouseProduct();

    /**
     * 统计采购单
     * @return
     */
    public RecordBean statistics();


}
