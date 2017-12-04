package com.yuelinghui.controller.travel;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.common.SqlExpr;
import cn.assist.easydao.common.SqlJoin;
import cn.assist.easydao.pojo.PagePojo;
import com.alibaba.fastjson.JSONObject;
import com.yuelinghui.base.constant.ProductConstant;
import com.yuelinghui.base.utils.JsonBean;
import com.yuelinghui.base.utils.RecordBean;
import com.yuelinghui.base.utils.ReqUtils;
import com.yuelinghui.controller.BaseController;
import com.yuelinghui.service.product.vo.ProductCategory;
import com.yuelinghui.service.travel.IProductTravelCategoryService;
import com.yuelinghui.service.travel.impl.ProductTravelCategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *
 * 定制旅游信息
 * @author caibin
 *
 */
@Controller
@RequestMapping(value = "/travel/category")
public class TravelCategoryController extends BaseController {

	@Autowired
	private IProductTravelCategoryService iProductCategoryService;
	
	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request, Model model){
		return "modules/travel/category/index";
	}
	
	@RequestMapping(value = "list")
	@ResponseBody
	public JSONObject list(HttpServletRequest request){
		//参数获取
		int pageNo = ReqUtils.getParamToInt(request, "pageNo", 1);
		int pageSize = ReqUtils.getParamToInt(request, "pageSize", 15);
		String name = ReqUtils.getParam(request, "name", null);

		//查询条件
		Conditions conn = new Conditions("name", SqlExpr.EQUAL,name);
		conn.add(new Conditions("parent_id",SqlExpr.EQUAL, ProductConstant.TRAVEL_THEME), SqlJoin.AND);
		PagePojo<ProductCategory> page = iProductCategoryService.productCategoryPage(conn, pageNo, pageSize);
		return JsonBean.success("", page);
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
		return "modules/travel/category/_add";
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
		List<ProductCategory> items = iProductCategoryService.productCategoryList(id);
		return JsonBean.success("success",items);
	}

}
