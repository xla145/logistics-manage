package com.logistics.label.impl;

import com.logistics.base.constant.CouponTypeConstant;
import com.logistics.label.ModuleData;
import com.logistics.service.vo.Option;

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
public class GetCouponTypeNode extends ModuleData{

	@Override
	public Map<String, Object> getModelData(Map<String, Object> params) throws Exception {
		CouponTypeConstant[] couponType = CouponTypeConstant.values();
 		List<Option> options = new ArrayList<Option>();
 		for (CouponTypeConstant type:couponType) {
			Option option = new Option();
			option.setId(type.getId()+"");
			option.setName(type.getName());
			options.add(option);
		}
		root.put("checked", null);
		root.put("options", options);
		return root;
	}
}
