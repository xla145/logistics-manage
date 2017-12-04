package com.logistics.controller.product;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.common.SqlExpr;
import cn.assist.easydao.common.SqlJoin;
import cn.assist.easydao.pojo.PagePojo;
import com.alibaba.fastjson.JSONObject;
import com.logistics.base.utils.JsonBean;
import com.logistics.base.utils.ReqUtils;
import com.logistics.controller.BaseController;
import com.logistics.service.model.ProductStockModel;
import com.logistics.service.product.IProductCategoryService;
import com.logistics.service.product.IProductStockService;
import com.logistics.service.vo.ProductCategory;
import com.logistics.service.vo.ProductStock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/product/stock")
@Controller
public class ProductStockController extends BaseController{

    @Autowired
    private IProductStockService iProductStockService;

    /**
     * 跳转产品页面
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/index")
    public String index(HttpServletRequest request, Model model){
        return "modules/product/stock/index";
    }

    @RequestMapping(value = "list")
    @ResponseBody
    public JSONObject list(HttpServletRequest request){
        //参数获取
        int pageNo = ReqUtils.getParamToInt(request, "pageNo", 1);
        int pageSize = ReqUtils.getParamToInt(request, "pageSize", 15);
        String pid = ReqUtils.getParam(request, "pid", null);
        String wid = ReqUtils.getParam(request, "wid", null);
        Integer catId = ReqUtils.getParamToInteger(request, "catId", null);

        //查询条件
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("pid",pid);
        map.put("wid",wid);
        map.put("catId",catId);
        PagePojo<ProductStockModel> page = iProductStockService.getProductStockModelPage(map, pageNo, pageSize);
        return JsonBean.success(page);
    }
}
