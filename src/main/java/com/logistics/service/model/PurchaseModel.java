package com.logistics.service.model;

import com.logistics.service.vo.Product;
import com.logistics.service.vo.Purchase;
import com.logistics.service.vo.PurchaseProduct;

import java.util.List;

public class PurchaseModel extends Purchase{

    private List<PurchaseProduct> productList;

    public List<PurchaseProduct> getProductList() {
        return productList;
    }

    public void setProductList(List<PurchaseProduct> productList) {
        this.productList = productList;
    }
}
