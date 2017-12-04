package com.logistics.label.impl;
import com.logistics.base.helper.SpringFactory;
import com.logistics.label.ModuleData;
import com.logistics.service.auth.ISysActionService;
import com.logistics.service.vo.Option;
import com.logistics.service.vo.sys.SysAction;
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
public class GetActionNode extends ModuleData{

	@Override
	public Map<String, Object> getModelData(Map<String, Object> params) throws Exception {
		TemplateModel checked = (TemplateModel) params.get("checked");
		ISysActionService sysActionService = SpringFactory.getBean("ISysActionService");
		List<SysAction> list = sysActionService.getParentMenus();
		List<Option> options = new ArrayList<Option>();
		if(list != null){
			for (SysAction sysAction : list) {
				Option option = new Option();
				option.setId(sysAction.getId()+"");
				option.setName(sysAction.getName());
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
