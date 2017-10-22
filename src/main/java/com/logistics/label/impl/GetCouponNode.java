package com.logistics.label.impl;

import com.logistics.base.constant.CouponConstant;
import com.logistics.base.constant.CouponTypeConstant;
import com.logistics.base.helper.SpringFactory;
import com.logistics.label.ModuleData;
import com.logistics.service.coupon.CouponService;
import com.logistics.service.supplier.SupplierService;
import com.logistics.service.vo.Option;
import com.logistics.service.vo.Supplier;
import com.logistics.service.vo.coupon.Coupon;
import com.logistics.service.vo.coupon.CouponType;
import freemarker.template.TemplateModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 
 * 获取代金券数据
 * 
 * @author caibin
 *
 */
public class GetCouponNode extends ModuleData{

	@Override
	public Map<String, Object> getModelData(Map<String, Object> params) throws Exception {
		TemplateModel type = (TemplateModel) params.get("type");
		TemplateModel checked = (TemplateModel) params.get("checked");
		CouponService couponService = SpringFactory.getBean("CouponService");
		if (type == null) {
			return null;
		}
		List<Coupon> list = couponService.getCouponByUseRange(Integer.valueOf(type.toString()));
		List<Option> options = new ArrayList<Option>();
		if(list != null){
			for (Coupon coupon : list) {
				Option option = new Option();
				option.setId(coupon.getCid()+"");
				option.setName(CouponTypeConstant.getCouponNameById(coupon.getType())+"  "+coupon.getName()+"      ("+coupon.getPrice()+"元)");
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
