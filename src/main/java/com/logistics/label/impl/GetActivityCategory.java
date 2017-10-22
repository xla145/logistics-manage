package com.logistics.label.impl;

import com.logistics.base.constant.CouponTypeConstant;
import com.logistics.base.helper.SpringFactory;
import com.logistics.label.ModuleData;
import com.logistics.service.product.api.IProductCategoryService;
import com.logistics.service.product.vo.CategorySummary;
import com.logistics.service.product.vo.ProductCategory;
import com.logistics.service.vo.Option;
import freemarker.template.TemplateModel;
import org.springframework.beans.factory.annotation.Autowired;

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
public class GetActivityCategory extends ModuleData{

	@Override
	public Map<String, Object> getModelData(Map<String, Object> params) throws Exception {
		IProductCategoryService iProductCategoryService = SpringFactory.getBean("IProductCategoryService");
		TemplateModel type = (TemplateModel) params.get("type");
		TemplateModel checked = (TemplateModel) params.get("checked");
		List<CategorySummary> productCategoryList = iProductCategoryService.getProductCategoryListByValid(Integer.valueOf(type.toString()));
 		List<Option> options = new ArrayList<Option>();
 		for (CategorySummary categorySummary:productCategoryList) {
			Option option = new Option();
			option.setId(categorySummary.getCatId()+"");
			option.setName(categorySummary.getName());
			options.add(option);
		}
		if (checked != null) {
			root.put("checked", checked.toString());
		}
		root.put("options", options);
		return root;
	}
}
