package com.logistics.service.model;

import com.logistics.service.vo.ProductAccessLog;
import com.logistics.service.vo.ProductStock;

public class ProductAccessLogModel extends ProductAccessLog {

    private Integer totalProduct;

    public Integer getTotalProduct() {
        return totalProduct;
    }

    public void setTotalProduct(Integer totalProduct) {
        this.totalProduct = totalProduct;
    }
}
