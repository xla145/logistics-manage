package com.yuelinghui.label.impl;

import com.yuelinghui.base.helper.SpringFactory;
import com.yuelinghui.label.ModuleData;
import com.yuelinghui.service.supplier.SupplierService;
import com.yuelinghui.service.vo.Option;
import com.yuelinghui.service.vo.Supplier;
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
public class GetSupplierNode extends ModuleData{

	@Override
	public Map<String, Object> getModelData(Map<String, Object> params) throws Exception {
		TemplateModel type = (TemplateModel) params.get("type");
		TemplateModel checked = (TemplateModel) params.get("checked");
		SupplierService supplierService = SpringFactory.getBean("SupplierService");
		if (type == null) {
			return null;
		}
		List<Supplier> list = supplierService.supplierListByType(Integer.parseInt(type.toString()));
		List<Option> options = new ArrayList<Option>();
		if(list != null){
			for (Supplier supplier : list) {
				Option option = new Option();
				option.setId(supplier.getId()+"");
				option.setName(supplier.getName());
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
