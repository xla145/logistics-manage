package com.logistics.service.model;

import com.logistics.service.vo.Sales;
import com.logistics.service.vo.SalesProduct;

import java.util.List;

public class SalesModel extends Sales {

    private List<SalesProduct> salesProductList;

    public List<SalesProduct> getSalesProductList() {
        return salesProductList;
    }

    public void setSalesProductList(List<SalesProduct> salesProductList) {
        this.salesProductList = salesProductList;
    }
}
