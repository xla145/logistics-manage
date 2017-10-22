package com.logistics.base.freemarker;
import java.io.IOException;
import java.util.Map;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateNumberModel;

/**
 * 权限节点拦截处理指令
 * 
 * @author caibin
 *
 */
public class NodeDirectiveModel implements TemplateDirectiveModel {

	@SuppressWarnings("rawtypes") 
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		TemplateModel paramValue = (TemplateModel) params.get("id");
	    int nodeId = ((TemplateNumberModel) paramValue).getAsNumber().intValue();
	        
		if(body != null){
			if(nodeId == 999){ 
				body.render(env.getOut());
			}
		}
	}

}
