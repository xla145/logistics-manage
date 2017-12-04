package com.logistics.job;

import cn.assist.easydao.common.Conditions;
import com.alibaba.fastjson.JSONObject;
import com.logistics.base.constant.BaseConstant;
import com.logistics.service.product.IProductStockService;
import com.logistics.service.purchase.IPurchaseService;
import com.logistics.service.sales.ISalesService;
import com.logistics.service.vo.Purchase;
import com.logistics.service.vo.Sales;
import com.logistics.sorket.SocketMessageHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 活动商品状态变更任务
 * 活动商品状态 [报名中---> 进行中 ---> 已结束]
 *
 * @author caixb
 */
public class ActivityStatusJob {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private IProductStockService iProductStockService;
	@Autowired
	private ISalesService iSalesService;
	@Autowired
	private IPurchaseService iPurchaseService;
	@Autowired
	private SocketMessageHandle socketMessageHandle;
	
	public void run(){
		if(BaseConstant.isDev){
			logger.info("============================>库存统计start...");
		}

		JSONObject json = new JSONObject();

		long sumRemaining = iProductStockService.getProductRemaining();

		List<Sales> salesList = iSalesService.getSalesList(new Conditions());

		List<Purchase> purchaseList = iPurchaseService.getPurchaseList(new Conditions());

		json.put("sumRemaining",sumRemaining);
		json.put("sumSales",salesList.size());
		json.put("sumPurchase",purchaseList.size());

		socketMessageHandle.sendMessageToUsers(json.toJSONString());
	}
}
