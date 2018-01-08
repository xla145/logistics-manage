package com.logistics.controller.purchase;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.common.SqlExpr;
import cn.assist.easydao.pojo.PagePojo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.logistics.base.utils.CommonUtil;
import com.logistics.base.utils.JsonBean;
import com.logistics.base.utils.RecordBean;
import com.logistics.base.utils.ReqUtils;
import com.logistics.controller.BaseController;
import com.logistics.service.model.ProductModel;
import com.logistics.service.model.PurchaseModel;
import com.logistics.service.product.IProductService;
import com.logistics.service.purchase.IPurchaseService;
import com.logistics.service.supplier.ISupplierService;
import com.logistics.service.vo.ProductCategory;
import com.logistics.service.vo.Purchase;
import com.logistics.service.vo.PurchaseProduct;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 会员活动
 * @author caibin
 *
 */
@Controller
@RequestMapping(value = "/purchase")
public class PurchaseController extends BaseController {

	@Autowired
	private ISupplierService isupplierService;
	@Autowired
	private IProductService iproductService;
	@Autowired
	private IPurchaseService iPurchaseyService;
	
	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request, Model model){
		return "modules/purchase/index";
	}

	@RequestMapping(value = "/productIndex")
	public String productIndex(HttpServletRequest request, Model model){
		String peId = ReqUtils.getParam(request,"peId",null);
		model.addAttribute("peId",peId);
		return "modules/purchase/product_index";
	}
	
	/**
	 *
	 * 获取采购单列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public JSONObject data(HttpServletRequest request){
		/**
		 * 获取参数
		 * */
		int pageNo = ReqUtils.getParamToInt(request, "pageNo", 1);
		int pageSize = ReqUtils.getParamToInt(request, "pageSize", 15);
		String createTime = ReqUtils.getParam(request, "createTime", null);
				
		//查询条件
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("createTime", createTime);
		PagePojo<Purchase> page = iPurchaseyService.getPurchasePage(map, pageNo, pageSize);
		
		//render结果
		return JsonBean.success(page);
	}

	/**
	 *
	 * 获取采购单的商品列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/purchaseProductList")
	@ResponseBody
	public JSONObject purchaseProductList(HttpServletRequest request){
		/**
		 * 获取参数
		 * */
		int pageNo = ReqUtils.getParamToInt(request, "pageNo", 1);
		int pageSize = ReqUtils.getParamToInt(request, "pageSize", 15);
		String peId = ReqUtils.getParam(request, "peId", null);

		PagePojo<PurchaseProduct> page = new PagePojo<PurchaseProduct>();
		//查询条件
		if (StringUtils.isNotBlank(peId)) {
			Conditions conn = new Conditions("peId", SqlExpr.EQUAL,peId);
			page = iPurchaseyService.getPurchaseProductPage(conn, pageNo, pageSize);
		}
		//render结果
		return JsonBean.success(page);
	}

	/**
	 *
	 * 获取商品列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/productList")
	@ResponseBody
	public JSONObject productList(HttpServletRequest request){
		/**
		 * 获取参数
		 * */
		int pageNo = ReqUtils.getParamToInt(request, "pageNo", 1);
		int pageSize = ReqUtils.getParamToInt(request, "pageSize", 15);
		Integer catId = ReqUtils.getParamToInteger(request, "catId", null);
		String pid = ReqUtils.getParam(request, "pid", null);

		PagePojo<ProductModel> page = iproductService.getProductModelPage(pid,catId, pageNo, pageSize);

		//render结果
		return JsonBean.success(page);
	}
	
	/**
	 * 
	 * 添加数据
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject add(HttpServletRequest request, PurchaseModel purchaseModel){
		if (StringUtils.isBlank(purchaseModel.getPurchaseName())){
			return JsonBean.error("采购人员信息必填");
		}
		if (purchaseModel.getPeDate() == null) {
			return JsonBean.error("单据日期信息必填");
		}
		String productData = ReqUtils.getParam(request,"productData",null);
		List<PurchaseProduct> purchaseProducts = JSON.parseArray(productData, PurchaseProduct.class);
		if (purchaseProducts == null || purchaseProducts.size() == 0) {
			return JsonBean.error("商品信息不能为空");
		}
		purchaseProducts = CommonUtil.removeNullFromList(purchaseProducts);
		List<Object> pidList = new ArrayList<Object>();
		for (PurchaseProduct purchaseProduct:purchaseProducts){
			if (pidList.contains(purchaseProduct.getPid())) {
				return JsonBean.error("商品编号【"+purchaseProduct.getPid()+"】的商品重复");
			}
			pidList.add(purchaseProduct.getPid());
		}
		purchaseModel.setProductList(purchaseProducts);
		Integer operatorId = ReqUtils.getSysUid(request);
		String operatorName = ReqUtils.getSysName(request);
		RecordBean<PurchaseModel> data = iPurchaseyService.addPurchaseModel(purchaseModel,operatorId,operatorName);
		if(data.isSuccessCode()) {
			return JsonBean.success("修改成功");
		}
		return JsonBean.error(data.getMsg());
	}


	/**
	 *
	 * 添加数据
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edit",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject edit(HttpServletRequest request, PurchaseModel purchaseModel){
		if (StringUtils.isBlank(purchaseModel.getPurchaseName())){
			return JsonBean.error("采购人员信息必填");
		}
		if (purchaseModel.getPeDate() == null) {
			return JsonBean.error("单据日期信息必填");
		}
		String productData = ReqUtils.getParam(request,"productData",null);
		List<PurchaseProduct> purchaseProducts = JSON.parseArray(productData, PurchaseProduct.class);
		if (purchaseProducts == null || purchaseProducts.size() == 0) {
			return JsonBean.error("商品信息不能为空");
		}
		purchaseProducts = CommonUtil.removeNullFromList(purchaseProducts);
		List<Object> pidList = new ArrayList<Object>();
		for (PurchaseProduct purchaseProduct:purchaseProducts){
			if (pidList.contains(purchaseProduct.getPid())) {
				return JsonBean.error("商品编号【"+purchaseProduct.getPid()+"】的商品重复");
			}
			pidList.add(purchaseProduct.getPid());
		}
		purchaseModel.setProductList(purchaseProducts);
		Integer operatorId = ReqUtils.getSysUid(request);
		String operatorName = ReqUtils.getSysName(request);
		RecordBean<PurchaseModel> data = iPurchaseyService.editPurchaseModel(purchaseModel,operatorId,operatorName);
		if(data.isSuccessCode()) {
			return JsonBean.success("修改成功");
		}
		return JsonBean.error(data.getMsg());
	}
	
	/**
	 * 
	 * 编辑页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getView")
	public String editView(HttpServletRequest request, Model model){
		String peId = ReqUtils.getParam(request, "peId", null);
		Purchase purchase = new Purchase();
		if(StringUtils.isNotBlank(peId)){
			purchase = iPurchaseyService.getPurchaseModel(peId);
		}
		model.addAttribute("data",purchase);
		return "modules/purchase/_add";
	}

	/**
	 * 获取活动详情页
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/info")
	public String info(HttpServletRequest request, Model model){
		String peId = ReqUtils.getParam(request, "peId", null);
		Purchase purchase = new Purchase();
		if(StringUtils.isNotBlank(peId)){
			purchase = iPurchaseyService.getPurchaseModel(peId);
		}
		model.addAttribute("data",purchase);
		return "modules/purchase/_info";
	}

	/**
	 *
	 * 获取产品的下一级类型
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getCatList")
	@ResponseBody
	public JSONObject getCatList(HttpServletRequest request){
		Integer type = ReqUtils.getParamToInteger(request,"type",null);
		List<ProductCategory> categorySummaryList= iPurchaseyService.getProductCategoryList(type);
		return JsonBean.success("success",categorySummaryList);
	}

	/**
	 *
	 * 审核采购单
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/audit")
	@ResponseBody
	public JSONObject audit(HttpServletRequest request, @RequestParam("peId") String peId,@RequestParam("reason") String reason,@RequestParam("status") Integer status){
		RecordBean<String> result = iPurchaseyService.auditPurchase(peId,status,reason);
		if (result.isSuccessCode()) {
			return JsonBean.success(result.getMsg());
		}
		return JsonBean.error(result.getMsg());
	}


	/**
	 *
	 * 审核采购单
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addAuditOrder")
	@ResponseBody
	public JSONObject audit(HttpServletRequest request, @RequestParam("peId") String peId){
		RecordBean<String> result = iPurchaseyService.addAuditOrder(peId);
		if (result.isSuccessCode()) {
			return JsonBean.success(result.getMsg());
		}
		return JsonBean.error(result.getMsg());
	}

}
