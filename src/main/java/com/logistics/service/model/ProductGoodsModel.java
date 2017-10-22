package com.logistics.service.model;

import cn.assist.easydao.annotation.Id;
import cn.assist.easydao.annotation.Temporary;
import com.logistics.service.vo.product.Product;
import com.logistics.service.vo.product.ProductImage;
import com.logistics.service.vo.Supplier;

import java.math.BigDecimal;
import java.util.List;
/**
 * 业务模型 好物商品
 * @author Administrator
 * */
public class ProductGoodsModel extends Product{
	@Temporary
	private static final long serialVersionUID = 1L;
	@Id
	private Integer id;//编号
	private String delivery;//配送方式
	private BigDecimal freight;//运费
	private String speName;//规格名称
	private String unit;//包装单位
	private Integer goodsTypeId;//所属商品编号
	private String goodsTypeName;//所属商品分类
	private Integer goodsStatus;//状态
	private Integer sales;//销量
	private List<ProductImage> imageList;//轮播图
	private List<Supplier> supplierList;//供应商

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDelivery() {
		return delivery;
	}

	public void setDelivery(String delivery) {
		this.delivery = delivery;
	}

	public BigDecimal getFreight() {
		return freight;
	}

	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}

	public String getSpeName() {
		return speName;
	}

	public void setSpeName(String speName) {
		this.speName = speName;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Integer getGoodsTypeId() {
		return goodsTypeId;
	}

	public void setGoodsTypeId(Integer goodsTypeId) {
		this.goodsTypeId = goodsTypeId;
	}

	public String getGoodsTypeName() {
		return goodsTypeName;
	}

	public void setGoodsTypeName(String goodsTypeName) {
		this.goodsTypeName = goodsTypeName;
	}

	public Integer getGoodsStatus() {
		return goodsStatus;
	}

	public void setGoodsStatus(Integer goodsStatus) {
		this.goodsStatus = goodsStatus;
	}

	public List<ProductImage> getImageList() {
		return imageList;
	}

	public void setImageList(List<ProductImage> imageList) {
		this.imageList = imageList;
	}

	public List<Supplier> getSupplierList() {
		return supplierList;
	}

	public void setSupplierList(List<Supplier> supplierList) {
		this.supplierList = supplierList;
	}

	public Integer getSales() {
		return sales;
	}

	public void setSales(Integer sales) {
		this.sales = sales;
	}
}
