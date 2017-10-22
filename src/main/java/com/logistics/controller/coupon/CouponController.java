package com.logistics.controller.coupon;

import cn.assist.easydao.pojo.PagePojo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.logistics.base.auth.Auth;
import com.logistics.base.utils.DataObj;
import com.logistics.base.utils.JsonBean;
import com.logistics.base.utils.PageUtils;
import com.logistics.base.utils.ReqUtils;
import com.logistics.controller.BaseController;
import com.logistics.service.coupon.CouponService;
import com.logistics.service.model.CouponModel;
import com.logistics.service.product.ProductService;
import com.logistics.service.product.api.IProductCategoryService;
import com.logistics.service.product.vo.CategorySummary;
import com.logistics.service.product.vo.ProductCategory;
import com.logistics.service.vo.coupon.Coupon;
import com.logistics.service.vo.product.Product;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 代金券管理
 * @author caibin
 *
 */
@Controller
@RequestMapping(value = "/coupon")
public class CouponController extends BaseController{

	@Autowired
	private CouponService couponService;
	@Autowired
	private IProductCategoryService iProductCategoryService;
	@Autowired
	private ProductService productService;

	/**
	 * 显示代金券列表
	 * @param request
	 * @param model
	 * @return
	 * 
	 * */
	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request, Model model){	
		return "modules/coupon/generate/index";
	}
	
	/**
	 * 获取代金券数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public JSONObject data(HttpServletRequest request){
		//参数获取
		int pageNo = ReqUtils.getParamToInt(request, "pageNo", 1);
		int pageSize = ReqUtils.getParamToInt(request, "pageSize", 15);
		String createTime = ReqUtils.getParam(request, "createTime", null);
		String couponName = ReqUtils.getParam(request, "name", null);
		Integer useRange = ReqUtils.getParamToInteger(request, "useRange", null);
		Integer couponStatus = ReqUtils.getParamToInteger(request, "status", null);
		//查询条件
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("createTime", createTime);
		map.put("name", couponName);
		map.put("status", couponStatus);
		map.put("useRange", useRange);
		PagePojo<CouponModel> page = couponService.getCouponPage(map,pageNo, pageSize);
		//render结果
		return JsonBean.success("",page);
	}
	
	/**
	 * 
	 * 添加代金券数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/add")
	@ResponseBody
	public JSONObject add(HttpServletRequest request, CouponModel couponModel){
		if(StringUtils.isBlank(couponModel.getName())){
			return JsonBean.error("优惠券名称必填");
		}
		if(couponModel.getType() == null){
			return JsonBean.error("优惠券类型不能为空");
		}
		if(couponModel.getPrice() == null || couponModel.getPrice().doubleValue() <= 0){
			return JsonBean.error("金额不能为空,且不能为负数");
		}
		if(couponModel.getUseRange() == null){
			return JsonBean.error("优惠券使用范围不能为空");
		}
		if(couponModel.getUseRange() == 0 && couponModel.getStock() == null || couponModel.getStock() < 1){ // 非卡包不需要验证张数
			return JsonBean.error("生成张数不能为空且不能是负数");
		}
		if(couponModel.getUseRange() == 0 && StringUtils.isBlank(couponModel.getValidPeriod())){ // 非卡包不需要有效期
			return JsonBean.error("生成张数不能为空且不能是负数");
		}
		DataObj<CouponModel> result = couponService.addCouponModel(couponModel);
		if (result.isSuccessCode()) {
			return JsonBean.success(result.getMsg());
		}
		return JsonBean.error(result.getMsg());
 	}
	/**
	 * 
	 * 删除数据
	 * @param request
	 * @return
	 */
	@Auth(id = 5)
	@RequestMapping(value = "/del")
	@ResponseBody
	public JSONObject del(HttpServletRequest request){
		String[] ids = request.getParameterValues("ids");
		DataObj<String> result = couponService.delCoupon(ids);
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
		List<CategorySummary> categorySummaryList= iProductCategoryService.getProductCategoryListByValid(type);
		return JsonBean.success("success",categorySummaryList);
	}

	/**
	 *
	 * 获取产品的下一级类型
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getProductList")
	@ResponseBody
	public JSONObject getProductList(HttpServletRequest request){
		Integer catId = ReqUtils.getParamToInteger(request,"catId",null);
		String condition = ReqUtils.getParam(request,"condition",null);
		List<Product> productList = productService.getProductList(catId,condition);
		return JsonBean.success("success",productList);
	}


}
