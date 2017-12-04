package com.yuelinghui.controller.vipActivity;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.common.SqlExpr;
import cn.assist.easydao.common.SqlJoin;
import cn.assist.easydao.pojo.PagePojo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import com.yuelinghui.base.utils.CommonUtil;
import com.yuelinghui.base.utils.JsonBean;
import com.yuelinghui.base.utils.RecordBean;
import com.yuelinghui.base.utils.ReqUtils;
import com.yuelinghui.controller.BaseController;
import com.yuelinghui.service.activtiy.IProductActivityService;
import com.yuelinghui.service.model.ProductActivityModel;
import com.yuelinghui.service.model.ProductModel;
import com.yuelinghui.service.model.VipActivityModel;
import com.yuelinghui.service.product.ProductService;
import com.yuelinghui.service.product.vo.CategorySummary;
import com.yuelinghui.service.supplier.SupplierService;
import com.yuelinghui.service.vipActivity.IVipActivityService;
import com.yuelinghui.service.vo.VipActivityProduct;
import com.yuelinghui.service.vo.product.Product;
import com.yuelinghui.service.vo.travel.OrderTravelPersonnel;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 
 * 会员活动
 * @author caibin
 *
 */
@Controller
@RequestMapping(value = "/vip/activity")
public class VipActivityController extends BaseController{

	@Autowired
	private IVipActivityService iVipActivityService;
	@Autowired
	private SupplierService supplierService;
	@Autowired
	private ProductService productService;
	
	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request, Model model){
		return "modules/vip-activity/index";
	}

	@RequestMapping(value = "/productIndex")
	public String productIndex(HttpServletRequest request, Model model){
		Integer activityId = ReqUtils.getParamToInteger(request,"activityId",null);
		model.addAttribute("activityId",activityId);
		return "modules/vip-activity/product_index";
	}
	
	/**
	 *
	 * 获取活动列表
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
		String releaseTime = ReqUtils.getParam(request, "releaseTime", null);
		String activityTime = ReqUtils.getParam(request, "activityTime", null);
		String title = ReqUtils.getParam(request, "title", null);
		Integer status = ReqUtils.getParamToInteger(request, "status", null);
				
		//查询条件
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("releaseTime", releaseTime);
		map.put("title", title);
		map.put("status", status);
		map.put("activityTime", activityTime);
		PagePojo<VipActivityModel> page = iVipActivityService.getVipActivityPage(map, pageNo, pageSize);
		
		//render结果
		return JsonBean.success(page);
	}

	/**
	 *
	 * 获取会员活动商品列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/activityProductList")
	@ResponseBody
	public JSONObject activityProductList(HttpServletRequest request){
		/**
		 * 获取参数
		 * */
		int pageNo = ReqUtils.getParamToInt(request, "pageNo", 1);
		int pageSize = ReqUtils.getParamToInt(request, "pageSize", 15);
		int activityId = ReqUtils.getParamToInt(request, "activityId", 0);

		//查询条件
		Conditions conn = new Conditions("activityId", SqlExpr.EQUAL,activityId);
		PagePojo<VipActivityProduct> page = iVipActivityService.getVipActivityProductPage(conn, pageNo, pageSize);

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

//		Integer[] catIds = iVipActivityService.getProductCategory(catId);
		//查询条件
//		Conditions conn = new Conditions("catId", SqlExpr.IN,catIds);
//		conn.add(new Conditions("pid",SqlExpr.EQUAL,pid), SqlJoin.AND);
		PagePojo<ProductModel> page = productService.getProductModelPage(pid,catId, pageNo, pageSize);

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
	public JSONObject add(HttpServletRequest request, VipActivityModel vipActivityModel){
		if(StringUtils.isBlank(vipActivityModel.getTitle())){
			return JsonBean.error("会员活动标题必填");
		}
		if(StringUtils.isBlank(vipActivityModel.getActivityTime())){
			return JsonBean.error("会员活动时间必填");
		}
		if(StringUtils.isBlank(vipActivityModel.getVipName())){
			return JsonBean.error("会员等级必填");
		}
		String productData = ReqUtils.getParam(request,"productData",null);
		List<VipActivityProduct> vipActivityProducts = JSON.parseArray(productData, VipActivityProduct.class);
		if (vipActivityProducts == null || vipActivityProducts.size() == 0) {
			return JsonBean.error("商品信息不能为空");
		}
		List<VipActivityProduct> vipActivityProductList= CommonUtil.removeNullFromList(vipActivityProducts);
		List<Object> pidList = new ArrayList<Object>();
		for (VipActivityProduct vipActivityProduct:vipActivityProductList){
			if (pidList.contains(vipActivityProduct.getPid())) {
				return JsonBean.error("商品编号+【"+vipActivityProduct.getPid()+"】的商品重复");
			}
			pidList.add(vipActivityProduct.getPid());
		}
		vipActivityModel.setVipActivityProductList(vipActivityProductList);
		vipActivityModel.setOperatorId(CommonUtil.getSysUid(request));
		vipActivityModel.setOperatorName(CommonUtil.getSysName(request));
		RecordBean<VipActivityModel> data = iVipActivityService.addVipActivityModel(vipActivityModel);
		if(data.isSuccessCode()) {
			return JsonBean.success("保存成功");
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
		Integer activityId = ReqUtils.getParamToInteger(request, "id", null);
		VipActivityModel vipActivityModel = new VipActivityModel();
		if(activityId != null){
			vipActivityModel = iVipActivityService.getVipActivityModel(activityId);
		}
		model.addAttribute("data",vipActivityModel);
		return "modules/vip-activity/_add";
	}


	/**
	 * 获取活动详情页
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/info")
	public String info(HttpServletRequest request, Model model){
		Integer activityId = ReqUtils.getParamToInteger(request, "id", null);
		VipActivityModel vipActivityModel = new VipActivityModel();
		if(activityId != null){
			vipActivityModel = iVipActivityService.getVipActivityModel(activityId);
		}
		model.addAttribute("data",vipActivityModel);
		return "modules/vip-activity/_info";
	}
	
	/**
	 * 
	 * 编辑数据
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edit",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject edit(HttpServletRequest request, VipActivityModel vipActivityModel){
		if(StringUtils.isBlank(vipActivityModel.getTitle())){
			return JsonBean.error("会员活动标题必填");
		}
		if(StringUtils.isBlank(vipActivityModel.getActivityTime())){
			return JsonBean.error("会员活动时间必填");
		}
		if(StringUtils.isBlank(vipActivityModel.getVipName())){
			return JsonBean.error("会员等级必填");
		}
		String productData = ReqUtils.getParam(request,"productData",null);
		System.out.println(productData);
		List<VipActivityProduct> vipActivityProducts = JSON.parseArray(productData, VipActivityProduct.class);
		if (vipActivityProducts == null || vipActivityProducts.size() == 0) {
			return JsonBean.error("商品信息不能为空");
		}
		List<VipActivityProduct> vipActivityProductList = CommonUtil.removeNullFromList(vipActivityProducts);
		List<Object> pidList = new ArrayList<Object>();
		for (VipActivityProduct vipActivityProduct:vipActivityProductList){
			if (pidList.contains(vipActivityProduct.getPid())) {
				return JsonBean.error("商品编号【"+vipActivityProduct.getPid()+"】的商品重复");
			}
			pidList.add(vipActivityProduct.getPid());
		}
		vipActivityModel.setVipActivityProductList(vipActivityProductList);
		vipActivityModel.setOperatorId(CommonUtil.getSysUid(request));
		vipActivityModel.setOperatorName(CommonUtil.getSysName(request));
		RecordBean<VipActivityModel> data = iVipActivityService.updateVipActivityModel(vipActivityModel);
		if(data.isSuccessCode()) {
			return JsonBean.success("修改成功");
		}
		return JsonBean.error(data.getMsg());
	}

	/**
	 * 
	 * 活动启用或停用
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/changeStatus")
	@ResponseBody
	public JSONObject changeStatus(HttpServletRequest request){
		String[] ids = request.getParameterValues("ids");
		Integer status = ReqUtils.getParamToInteger(request, "status",null);
		RecordBean<String> result = iVipActivityService.changeStatus(ids, status);
		if (result.isSuccessCode()) {
			return JsonBean.success(result.getMsg());
		}
		return JsonBean.error(result.getMsg());
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
		List<CategorySummary> categorySummaryList= iVipActivityService.getProductCategoryList(type);
		return JsonBean.success("success",categorySummaryList);
	}

}
