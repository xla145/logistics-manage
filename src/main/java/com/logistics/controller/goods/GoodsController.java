package com.logistics.controller.goods;
import cn.assist.easydao.pojo.PagePojo;
import com.alibaba.fastjson.JSONObject;
import com.logistics.base.auth.Auth;
import com.logistics.base.constant.ProductConstant;
import com.logistics.base.utils.DataObj;
import com.logistics.base.utils.JsonBean;
import com.logistics.base.utils.PageUtils;
import com.logistics.base.utils.ReqUtils;
import com.logistics.controller.BaseController;
import com.logistics.service.goods.ProductGoodsService;
import com.logistics.service.model.ProductGoodsModel;
import com.logistics.service.vo.Supplier;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 测试
 * 
 * @author caibin
 *
 */
@Controller
@RequestMapping(value = "/goods")
public class GoodsController extends BaseController {

	@Autowired
	private ProductGoodsService productGoodsService;
	
	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request, Model model){
		return "modules/goods/goods/index";
	}
	
	/**
	 * 
	 * datagrid 数据测试
	 * 
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
		String releaseStartTime = ReqUtils.getParam(request, "startTime", null);
		String releaseEndTime = ReqUtils.getParam(request, "endTime", null);
		String title = ReqUtils.getParam(request, "title", null);
		String pid = ReqUtils.getParam(request, "pid", null);
		Integer status = ReqUtils.getParamToInteger(request, "status", null);
				
		//查询条件
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("startTime", releaseStartTime);
		map.put("endTime", releaseEndTime);
		map.put("title", title);
		map.put("status", status);
		map.put("pid",pid);
		PagePojo<ProductGoodsModel> page = productGoodsService.productGoodsPage(map, pageNo, pageSize);
		//render结果
		return PageUtils.render(request, "WEB-INF/view/modules/goods/goods/", "_list.html", page);
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
	public JSONObject add(HttpServletRequest request,ProductGoodsModel productGoodsModel){
		if(StringUtils.isBlank(productGoodsModel.getTitle())){
			return JsonBean.error("商品标题必填");
		}
		if(StringUtils.isBlank(productGoodsModel.getName())){
			return JsonBean.error("商品名称不能为空");
		}
		if(productGoodsModel.getPrice() == null){
			return JsonBean.error("商品金额不能为空");
		}
//		//进货价不能大于商品
//		if (productGoodsModel.getOriginalPrice() != null && productGoodsModel.getOriginalPrice().compareTo(productGoodsModel.getPrice()) == 1){
//
//		}
		if(productGoodsModel.getStock() == null){
			return JsonBean.error("商品库存数不能为空");
		}
		if(StringUtils.isBlank(productGoodsModel.getImageUrl())){
			return JsonBean.error("列表页图片不能为空");
		}
		if(productGoodsModel.getSupplierId() == null){
			return JsonBean.error("供货商不能为空");
		}
		if(StringUtils.isBlank(productGoodsModel.getInfo())){
			return JsonBean.error("商品详情不能为空");
		}
		if(productGoodsModel.getWeight() == null){
			return JsonBean.error("商品权重不能为空");
		}
		if(productGoodsModel.getImageList() == null || productGoodsModel.getImageList().size() == 0){
			return JsonBean.error("商品轮播图不能为空");
		}
		DataObj<ProductGoodsModel> result = productGoodsService.addProductGoods(productGoodsModel);
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
//	@Auth(id = 4)
	@RequestMapping(value = "/editView")
	public String editView(HttpServletRequest request, ModelMap model){
		String pid = ReqUtils.getParam(request, "id", null);
		ProductGoodsModel productGoodsModel = new ProductGoodsModel();
		model.addAttribute("data",productGoodsModel);
		model.addAttribute("contextPath",contextPath(request));
		model.addAttribute("isUeditor","ueditor");
		if(pid != null){
			productGoodsModel = productGoodsService.getProductGoods(pid);
			model.addAttribute("data",productGoodsModel);
		}
		return "modules/goods/goods/_add";
	}
	/**
	 * 
	 * 编辑数据
	 * 
	 * @param request
	 * @return
	 */
//	@Auth(id = 5)
	@RequestMapping(value = "/edit")
	@ResponseBody
	public JSONObject edit(HttpServletRequest request, ProductGoodsModel productGoodsModel){
		if(StringUtils.isBlank(productGoodsModel.getTitle())){
			return JsonBean.error("商品标题必填");
		}
		if(StringUtils.isBlank(productGoodsModel.getName())){
			return JsonBean.error("商品名称不能为空");
		}
		if(productGoodsModel.getPrice() == null){
			return JsonBean.error("商品金额不能为空");
		}
		if(productGoodsModel.getStock() == null){
			return JsonBean.error("商品库存数不能为空");
		}
		if(StringUtils.isBlank(productGoodsModel.getImageUrl())){
			return JsonBean.error("列表页图片不能为空");
		}
		if(productGoodsModel.getSupplierId() == null){
			return JsonBean.error("供货商不能为空");
		}
		if(StringUtils.isBlank(productGoodsModel.getInfo())){
			return JsonBean.error("商品详情不能为空");
		}
		if(productGoodsModel.getWeight() == null){
			return JsonBean.error("商品权重不能为空");
		}
		if(productGoodsModel.getImageList() == null || productGoodsModel.getImageList().size() == 0){
			return JsonBean.error("商品轮播图不能为空");
		}
		DataObj<ProductGoodsModel> result = productGoodsService.updateProductGoods(productGoodsModel);
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
	@Auth(id = 5)
	@RequestMapping(value = "/del")
	@ResponseBody
	public JSONObject del(HttpServletRequest request){
		String[] ids = request.getParameterValues("ids");
 		DataObj<String> result = productGoodsService.delProductGoods(ids);
		if (result.isSuccessCode()) {
			return JsonBean.success(result.getMsg());
		}
		return JsonBean.error(result.getMsg());
	}
	
	/**
	 * 
	 * 上下架
	 * 
	 * @param request
	 * @return
	 */
	@Auth(id = 5)
	@RequestMapping(value = "/upOrDownProduct")
	@ResponseBody
	public JSONObject upOrDownProduct(HttpServletRequest request){
		String[] ids = request.getParameterValues("id");
		Integer status = ReqUtils.getParamToInteger(request, "status", ProductConstant.PRODUCT_STATUS_INITIAL);
		DataObj<String> result = productGoodsService.upOrDownProductGoods(ids, status);
		if (result.isSuccessCode()) {
			return JsonBean.success(result.getMsg());
		}
		return JsonBean.error(result.getMsg());
	}

	/**
	 * 添加供应商信息
	 */
//	@Auth(id = 5)
//	@RequestMapping(value = "/getSupplier")
//	@ResponseBody
//	public JSONObject getSupplier(HttpServletRequest request){
//		Integer id = ReqUtils.getParamToInteger(request,"id",0);
//		Map<String,Object> map = new HashMap<String,Object>();
//		map.put("id",id);
//		PagePojo<Supplier> page = productGoodsService.suppilerPage(map);
//		return PageUtils.render(request, "WEB-INF/view/modules/goods/goods/", "supplier.html", page);
//	}

	/**
	 * 获取供应商页面
	 */
	@RequestMapping(value = "/supplierIndex")
	public String couponIndex(HttpServletRequest request, Model model){
		return "modules/goods/goods/supplierIndex";
	}

	/**
	 * 获取供应商数据
	 */
	@RequestMapping(value = "/supplierList")
	@ResponseBody
	public JSONObject supplierList(HttpServletRequest request){
		int pageNo = ReqUtils.getParamToInt(request, "pageNo", 1);
		int pageSize = ReqUtils.getParamToInt(request, "pageSize", 15);
		String name = ReqUtils.getParam(request,"supplierName",null);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("name",name);
		PagePojo<Supplier> page = productGoodsService.supplierPage(map,pageNo,pageSize);
		return PageUtils.render(request, "WEB-INF/view/modules/goods/goods/", "_supplierList.html", page);
	}
}
