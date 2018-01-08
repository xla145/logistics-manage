package com.logistics.controller.audit;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.common.SqlExpr;
import cn.assist.easydao.common.SqlJoin;
import cn.assist.easydao.pojo.PagePojo;
import com.alibaba.fastjson.JSONObject;
import com.logistics.base.utils.JsonBean;
import com.logistics.base.utils.RecordBean;
import com.logistics.base.utils.ReqUtils;
import com.logistics.controller.BaseController;
import com.logistics.service.audit.IAuditService;
import com.logistics.service.vo.AuditOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * 审核管理
 * 
 * @author caibin
 *
 */
@Controller
@RequestMapping(value = "/audit")
public class AuditController extends BaseController {

	@Autowired
	private IAuditService iAuditService;

	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request, Model model){
		String orderId = ReqUtils.getParam(request,"orderId",null);
		model.addAttribute("orderId",orderId);
		return "modules/audit/index";
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public JSONObject data(HttpServletRequest request){
		//参数获取
		int pageNo = ReqUtils.getParamToInt(request, "pageNo", 1);
		int pageSize = ReqUtils.getParamToInt(request, "pageSize", 15);
		String  orderId = ReqUtils.getParam(request, "orderId", null);
		Integer type = ReqUtils.getParamToInteger(request, "type", null);

		//查询条件
		Conditions conn = new Conditions("type", SqlExpr.EQUAL, type);
		conn.add(new Conditions("order_id",SqlExpr.EQUAL,orderId), SqlJoin.AND);

		PagePojo<AuditOrder> page = iAuditService.getAuditOrderPage(conn, pageNo, pageSize);
		//render结果
		return JsonBean.success(page);
	}


    /**
	 *
	 * 审核采购单
	 * @param request
	 * @return
	 * */
	@RequestMapping(value = "/audit")
	@ResponseBody
	public JSONObject audit(HttpServletRequest request,  @RequestParam("orderId") String orderId,@RequestParam("auditId") Integer auditId, @RequestParam("reason") String reason, @RequestParam("status") Integer status){
		RecordBean<String> result = iAuditService.auditOrder(orderId,auditId,status,reason);
		if (result.isSuccessCode()) {
			return JsonBean.success(result.getMsg());
		}
		return JsonBean.error(result.getMsg());
	}
}
