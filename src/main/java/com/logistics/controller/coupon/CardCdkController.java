package com.logistics.controller.coupon;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.common.SqlExpr;
import cn.assist.easydao.common.SqlJoin;
import com.logistics.base.constant.CardConstant;
import com.logistics.base.utils.DateUtil;
import com.logistics.base.utils.ExportUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.assist.easydao.pojo.PagePojo;

import com.alibaba.fastjson.JSONObject;
import com.logistics.base.utils.JsonBean;
import com.logistics.base.utils.ReqUtils;
import com.logistics.controller.BaseController;
import com.logistics.service.coupon.ICardService;
import com.logistics.service.vo.card.Card;
import com.logistics.service.vo.card.CardCdk;

/**
 * 
 * 代金券--卡包激活码
 * @author caibin
 *
 */
@Controller
@RequestMapping(value = "/card")
public class CardCdkController extends BaseController{

	@Autowired
	private ICardService cardService;
	
	/**
	 * 激活码列表
	 * @param request
	 * @param model
	 * @return
	 * 
	 * */
	@RequestMapping(value = "/cdk")
	public String cdk(HttpServletRequest request, HttpServletResponse response, Model model){
		int cardId = ReqUtils.getParamToInt(request, "cardId", 0);
		if(cardId < 1){
			return renderTips(model, "卡不存在");
		}
		Card card = cardService.getCard(cardId);
		if(card == null){
			return renderTips(model, "卡不存在");
		}
		model.addAttribute("card", card);
		return "modules/card/cdk";
	}
	
	/**
	 * 激活码列表分页数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/cdkList")
	@ResponseBody
	public JSONObject cdkList(HttpServletRequest request){
		//参数获取
		int pageNo = ReqUtils.getParamToInt(request, "pageNo", 1);
		int pageSize = ReqUtils.getParamToInt(request, "pageSize", 5);
		Integer cardId = ReqUtils.getParamToInteger(request, "cardId",null);
		String batchNo = ReqUtils.getParam(request, "batchNo",null);
		Integer status = ReqUtils.getParamToInteger(request, "status", null);
		Integer uid = ReqUtils.getParamToInteger(request, "uid", null);
		Conditions conn = new Conditions("batch_no", SqlExpr.EQUAL,batchNo);
		conn.add(new Conditions("status",SqlExpr.EQUAL,status), SqlJoin.AND);
		conn.add(new Conditions("card_id",SqlExpr.EQUAL,cardId), SqlJoin.AND);
		conn.add(new Conditions("uid",SqlExpr.EQUAL,uid), SqlJoin.AND);
		PagePojo<CardCdk> page = cardService.getCardCdkPage(conn, pageNo, pageSize);
		return JsonBean.success("ok", page);
	}

	/**
	 * 
	 * 添加激活码
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addCdk")
	@ResponseBody
	public JSONObject addCdk(HttpServletRequest request){
		int cardId = ReqUtils.getParamToInt(request, "cardId", 0);
		int count = ReqUtils.getParamToInt(request, "count", 0);
		String batchNo = request.getParameter("batchNo");
		String remark = request.getParameter("remark");
		
		if(cardId < 1){
			return JsonBean.error("卡无效");
		}
		Card card = cardService.getCard(cardId);
		if(card == null){
			return JsonBean.error("卡无效");
		}
		if(card.getExpireTime() != null && card.getExpireTime().before(new Date())){
			return JsonBean.error("卡已过期");
		}
		if(count < 1){
			return JsonBean.error("激活码数量必填");
		}
		if(StringUtils.isBlank(batchNo)){
			return JsonBean.error("批次号必填");
		}
		boolean result = cardService.addCardCdk(cardId, card.getName(), count, batchNo, remark);
		if(result){
			return JsonBean.success("添加卡激活码成功");
		}
		return JsonBean.error("添加卡激活码失败");
 	}

	/**
	 * 导出订单
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/export")
	public String export(HttpServletResponse response,HttpServletRequest request) {
		Integer cardId = ReqUtils.getParamToInteger(request, "cardId",null);
		String batchNo = ReqUtils.getParam(request, "batchNo",null);
		Integer status = ReqUtils.getParamToInteger(request, "status", null);
		Integer uid = ReqUtils.getParamToInteger(request, "uid", null);
		Conditions conn = new Conditions("batch_no", SqlExpr.EQUAL,batchNo);
		conn.add(new Conditions("status",SqlExpr.EQUAL,status), SqlJoin.AND);
		conn.add(new Conditions("card_id",SqlExpr.EQUAL,cardId), SqlJoin.AND);
		conn.add(new Conditions("uid",SqlExpr.EQUAL,uid), SqlJoin.AND);
		response.setContentType("application/binary;charset=utf-8");
		try {
			ServletOutputStream outputStream = response.getOutputStream();
			response.setHeader("Content-disposition", "attachment; filename=CDK_" + DateUtil.formatDateToYYYYMDHMS(new Date()) + ".xlsx");// 组装附件名称和格式
			String[] titles = {"卡包编号","卡包名字", "激活码","批次号","状态", "激活UID","激活时间"};
			String[] names = {"cardId", "cardName", "cdk", "batchNo","status", "uid", "activateTime"};
			List<CardCdk> list = cardService.getCardCdkList(conn);
			Map<String,Map<Integer,String>> maps = new HashMap<String,Map<Integer,String>>();
			Map<Integer,String> statusMap = new HashMap<Integer,String>();
			statusMap.put(CardConstant.CARD_CDK_STATUS_NONACTIVATED,"未激活");
			statusMap.put(CardConstant.CARD_CDK_STATUS_ACTIVATED,"激活");
			maps.put("status",statusMap);
			ExportUtil.exportExcel(maps, titles, names, list, outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
