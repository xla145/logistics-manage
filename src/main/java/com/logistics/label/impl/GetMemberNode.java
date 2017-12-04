package com.logistics.label.impl;


import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.common.SqlExpr;
import com.logistics.base.constant.SupplierConstant;
import com.logistics.base.helper.SpringFactory;
import com.logistics.label.ModuleData;
import com.logistics.service.member.IMemberService;
import com.logistics.service.supplier.ISupplierService;
import com.logistics.service.vo.Member;
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
public class GetMemberNode extends ModuleData {

	@Override
	public Map<String, Object> getModelData(Map<String, Object> params) throws Exception {
		TemplateModel checked = (TemplateModel) params.get("checked");
		IMemberService iMemberService = SpringFactory.getBean("IMemberService");
		List<Member> list = iMemberService.getMemberList(new Conditions());
		List<Option> options = new ArrayList<Option>();
		if(list != null){
			for (Member member : list) {
				Option option = new Option();
				option.setId(member.getUid()+"");
				option.setName(member.getName());
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
