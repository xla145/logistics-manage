package com.logistics.controller.product;

import cn.assist.easydao.pojo.PagePojo;
import com.alibaba.fastjson.JSONObject;
import com.logistics.base.auth.Auth;
import com.logistics.base.utils.JsonBean;
import com.logistics.base.utils.RecordBean;
import com.logistics.base.utils.ReqUtils;
import com.logistics.controller.BaseController;
import com.logistics.service.model.ProductModel;
import com.logistics.service.product.IProductCategoryService;
import com.logistics.service.product.IProductService;
import com.logistics.service.supplier.ISupplierService;
import com.logistics.service.vo.Product;
import com.logistics.service.vo.ProductCategory;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 商品管理
 * 
 * @author
 *
 */
@Controller
@RequestMapping(value = "/product")
public class ProductController extends BaseController {

	@Autowired
	private IProductService iProductService;
	@Autowired
	private ISupplierService iSupplierService;
	@Autowired
	private IProductCategoryService iProductCategoryService;
	
	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request, Model model){
		return "modules/product/index";
	}
	
	/**
	 * 商品数据获取
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

		String name = ReqUtils.getParam(request,"name",null);
		Integer status = ReqUtils.getParamToInteger(request, "status", null);
		Integer catId = ReqUtils.getParamToInteger(request, "catId", null);

		//查询条件
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("name", name);
		map.put("catId", catId);
		map.put("status", status);
		PagePojo<Product> page = iProductService.getProductPage(map, pageNo, pageSize);
		
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
	public JSONObject add(HttpServletRequest request, Product product){
		if (StringUtils.isBlank(product.getName())) {
			return JsonBean.error("商品名字不能为空！");
		}
		if (product.getCatId() == null) {
			return JsonBean.error("商品分类不能为空！");
		}
		if (product.getPrice() == null) {
			return JsonBean.error("商品价格不能为空！");
		}
		if (product.getSupplierId() == null) {
			return JsonBean.error("供应商不能为空！");
		}
		if (StringUtils.isBlank(product.getImageUrl())) {
			return JsonBean.error("商品图片不能为空不能为空！");
		}
		RecordBean<Product> data = iProductService.addProduct(product);
		if(data.isSuccessCode()) {
			return JsonBean.success(data.getMsg());
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
//	@Auth(id = 4)
	@RequestMapping(value = "/getView")
	public String editView(HttpServletRequest request, ModelMap modelMap){
		String pid = ReqUtils.getParam(request, "pid", null);
		Product product = new Product();
		modelMap.addAttribute("data",product);
		if(pid != null){
			product = iProductService.getProductModel(pid);
			modelMap.addAttribute("data",product);
		}
		return "modules/product/_add";
	}


	/**
	 * 获取产品的详情
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/info")
	public String info(HttpServletRequest request, ModelMap modelMap){
		String pid = ReqUtils.getParam(request, "pid", null);
		ProductModel productModel = new ProductModel();
		if (pid != null) {
			productModel = iProductService.getProductModel(pid);
		}
		modelMap.addAttribute("data",productModel);
		return "modules/product/_info";
	}
	
	/**
	 * 
	 * 编辑数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edit",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject edit(HttpServletRequest request, Product product){
		if (StringUtils.isBlank(product.getName())) {
			return JsonBean.error("商品名字不能为空！");
		}
		if (product.getCatId() == null) {
			return JsonBean.error("商品分类不能为空！");
		}
		if (product.getPrice() == null) {
			return JsonBean.error("商品价格不能为空！");
		}
		if (product.getSupplierId() == null) {
			return JsonBean.error("供应商不能为空！");
		}
		if (StringUtils.isBlank(product.getImageUrl())) {
			return JsonBean.error("商品图片不能为空不能为空！");
		}
		RecordBean<Product> data = iProductService.updateProduct(product);
		if(data.isSuccessCode()) {
			return JsonBean.success(data.getMsg());
		}
		return JsonBean.error(data.getMsg());
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
		RecordBean<String> result = iProductService.delProduct(ids);
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
		List<ProductCategory> categorySummaryList= iProductCategoryService.getProductCategoryListByValid(type);
		return JsonBean.success("success",categorySummaryList);
	}
}
