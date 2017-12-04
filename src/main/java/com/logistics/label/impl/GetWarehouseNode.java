package com.logistics.label.impl;


import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.common.SqlExpr;
import com.logistics.base.constant.WarehouseConstant;
import com.logistics.base.helper.SpringFactory;
import com.logistics.label.ModuleData;
import com.logistics.service.vo.Option;
import com.logistics.service.vo.Warehouse;
import com.logistics.service.warehouse.IWarehouseService;
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
public class GetWarehouseNode extends ModuleData {

	@Override
	public Map<String, Object> getModelData(Map<String, Object> params) throws Exception {
		TemplateModel checked = (TemplateModel) params.get("checked");
		IWarehouseService iWarehouseService = SpringFactory.getBean("IWarehouseService");
		List<Warehouse> list = iWarehouseService.getWarehouseList(new Conditions("status",SqlExpr.EQUAL, WarehouseConstant.PWAREHOUSE_ENABLE));
		List<Option> options = new ArrayList<Option>();
		if(list != null){
			for (Warehouse warehouse : list) {
				Option option = new Option();
				option.setId(warehouse.getWid()+"");
				option.setName(warehouse.getName());
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
