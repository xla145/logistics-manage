package com.logistics.label.impl;

import com.logistics.base.helper.SpringFactory;
import com.logistics.label.ModuleData;
import com.logistics.service.product.api.IProductCategoryService;
import com.logistics.service.product.vo.ProductCategory;
import com.logistics.service.supplier.SupplierService;
import com.logistics.service.vo.Option;
import com.logistics.service.vo.Supplier;
import freemarker.template.TemplateModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 
 * 获取下拉框数据
 * 
 * @author caibin
 *
 */
public class GetProductCategory extends ModuleData{

	@Override
	public Map<String, Object> getModelData(Map<String, Object> params) throws Exception {
		TemplateModel type = (TemplateModel) params.get("type");
		TemplateModel checked = (TemplateModel) params.get("checked");
		IProductCategoryService iProductCategoryService = SpringFactory.getBean("IProductCategoryService");
		if (type == null) {
			return null;
		}
		List<ProductCategory> productCategories = iProductCategoryService.getProductCategoryList(Integer.parseInt(type.toString()));
		List<Option> options = new ArrayList<Option>();
		if(productCategories != null){
			for (ProductCategory productCategory : productCategories) {
				Option option = new Option();
				option.setId(productCategory.getCatId()+"");
				option.setName(productCategory.getName());
				options.add(option);
			}
		}
		if (checked != null) {
			root.put("checked",checked.toString());
		}
		root.put("options", options);
		return root;
	}

}
