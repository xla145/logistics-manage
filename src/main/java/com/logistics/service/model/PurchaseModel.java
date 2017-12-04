package com.logistics.service.model;

import com.logistics.service.vo.Product;

import java.util.List;

public class PurchaseModel {

    private List<Product> productList;


    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
