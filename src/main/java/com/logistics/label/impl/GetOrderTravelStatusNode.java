package com.logistics.label.impl;
import com.logistics.base.constant.OrderActivityStatusConstant;
import com.logistics.base.constant.OrderTravelStatusConstant;
import com.logistics.label.ModuleData;
import com.logistics.service.vo.Option;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 
 * 获取下拉框数据
 * 
 * @author caibin
 *
 */
public class GetOrderTravelStatusNode extends ModuleData{

	@Override
	public Map<String, Object> getModelData(Map<String, Object> params) throws Exception {
		List<OrderTravelStatusConstant> list = Arrays.asList(OrderTravelStatusConstant.values());
		List<Option> options = new ArrayList<Option>();
		if(list != null){
			for (OrderTravelStatusConstant type : list) {
				Option option = new Option();
				option.setId(type.getId()+"");
				option.setName(type.getStatus());
				options.add(option);
			}
		}
		root.put("checked", null);
		root.put("options", options);
		return root;
	}

}
