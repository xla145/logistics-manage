package com.logistics.label.impl;
import com.logistics.base.constant.OrderGoodsStatusConstant;
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
public class GetOrderGoodsStatusNode extends ModuleData{

	@Override
	public Map<String, Object> getModelData(Map<String, Object> params) throws Exception {

		List<OrderGoodsStatusConstant> list = Arrays.asList(OrderGoodsStatusConstant.values());
		List<Option> options = new ArrayList<Option>();
		if(list != null){
			for (OrderGoodsStatusConstant type : list) {
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
