package com.logistics.controller.product;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.common.SqlExpr;
import cn.assist.easydao.common.SqlJoin;
import cn.assist.easydao.pojo.PagePojo;
import com.alibaba.fastjson.JSONObject;
import com.logistics.base.constant.ProductConstant;
import com.logistics.base.utils.JsonBean;
import com.logistics.base.utils.RecordBean;
import com.logistics.base.utils.ReqUtils;
import com.logistics.controller.BaseController;
import com.logistics.service.product.IProductCategoryService;
import com.logistics.service.vo.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *
 * 产品分类管理
 * @author
 *
 */
@Controller
@RequestMapping(value = "/product/category")
public class ProductCategoryController extends BaseController {

	@Autowired
	private IProductCategoryService iProductCategoryService;

	/**
	 * 跳转产品页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request, Model model){
		return "modules/product/category/index";
	}
	
	@RequestMapping(value = "list")
	@ResponseBody
	public JSONObject list(HttpServletRequest request){
		//参数获取
		int pageNo = ReqUtils.getParamToInt(request, "pageNo", 1);
		int pageSize = ReqUtils.getParamToInt(request, "pageSize", 15);
		Integer catId = ReqUtils.getParamToInteger(request, "catId", 0);

		//查询条件
		Integer[] catIds = iProductCategoryService.getAllParents(catId);
		Conditions conn = new Conditions("parent_id", SqlExpr.IN,catIds);
		PagePojo<ProductCategory> page = iProductCategoryService.getProductCategoryPage(conn, pageNo, pageSize);
		return JsonBean.success(page);
	}
	/**
	 * 
	 * 添加数据
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/add")
	@ResponseBody
	public JSONObject add(HttpServletRequest request, ProductCategory productCategory){
		Integer isShow = ReqUtils.getParamToInteger(request,"isShow",0);
		productCategory.setIsShow(isShow);
		RecordBean<ProductCategory> result = iProductCategoryService.addProductCategory(productCategory);
		if (result.isSuccessCode()) {
			return JsonBean.success(result.getMsg());
		}
		return JsonBean.error(result.getMsg());
	}
	
	/**
	 * 
	 * 编辑页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getView")
	public String getView(HttpServletRequest request, Model model) {
		Integer catId = ReqUtils.getParamToInteger(request, "catId", null);
		ProductCategory productCategory = new ProductCategory();
		model.addAttribute("data", productCategory);
		if (catId != null) {
			productCategory = iProductCategoryService.getProductCategory(catId);
			model.addAttribute("data", productCategory);
		}
		return "modules/product/category/_add";
	}

	/**
	 * 
	 * 编辑数据
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edit")
	@ResponseBody
	public JSONObject edit(HttpServletRequest request, ProductCategory productCategory){
		Integer isShow = ReqUtils.getParamToInteger(request,"isShow",0);
		productCategory.setIsShow(isShow);
		RecordBean<ProductCategory> result = iProductCategoryService.updateProductCategory(productCategory);
		if (result.isSuccessCode()) {
			return JsonBean.success(result.getMsg());
		}
		return JsonBean.error(result.getMsg());
	}

	/**
	 *
	 * 删除数据
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/del")
	@ResponseBody
	public JSONObject del(HttpServletRequest request){
		String[] ids = request.getParameterValues("ids");
 		RecordBean<String> result = iProductCategoryService.delProductCategory(ids);
		if (result.isSuccessCode()) {
			return JsonBean.success(result.getMsg());
		}
		return JsonBean.error(result.getMsg());
	}

	/**
	 *
	 * 删除数据
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getItem")
	@ResponseBody
	public JSONObject getItem(HttpServletRequest request){
		Integer id = ReqUtils.getParamToInteger(request,"id",0);
		List<ProductCategory> productCategoryItems = iProductCategoryService.getProductCategoryList(id);
		return JsonBean.success("success",productCategoryItems);
	}

}
