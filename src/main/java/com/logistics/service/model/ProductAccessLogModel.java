package com.logistics.service.model;

import com.logistics.service.vo.ProductStock;

public class ProductStockModel extends ProductStock {

    private Integer total_product;

    public Integer getTotal_product() {
        return total_product;
    }

    public void setTotal_product(Integer total_product) {
        this.total_product = total_product;
    }
}
