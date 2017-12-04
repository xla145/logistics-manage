package com.logistics.base.constant;

/**
 * 
* @ClassName: ProductConstant
* @Description: TODO(产品常量信息)
* @author Administrator
* @date 2017年7月18日
*
 */
public class ProductConstant {


	/**
	 * 旅游的二级分类
	 */
	public static final Integer TRAVEL_THEME = 101;// 主题旅游
	public static final Integer TRAVEL_LIVE = 102;// 悦龄旅居


	public static final Integer PRODUCT_STATUS_INITIAL = 0;//初始状态
	public static final Integer PRODUCT_STATUS_ONOFFER = 10;//上架
	public static final Integer PRODUCT_STATUS_SELLOUT = 14;//售完下架
	public static final Integer PRODUCT_STATUS_ADMINOUT = 15;//管理员下架
	public static final Integer PRODUCT_STATUS_DEL = -1;//删除 


	//定制旅游状态 5:待处理15：已处理
	public static final Integer PRODUCT_CUSTOM_PENDING = 5;//待处理
	public static final Integer PRODUCT_CUSTOM_PENDED = 15;//已处理

	//活动状态 10：报名中， 20：进行中， 30已结束

	public static final Integer PRODUCT_ACTIVITY_APPLYING = 10;//报名中
	public static final Integer PRODUCT_ACTIVITY_HAVING = 20;//进行中
	public static final Integer PRODUCT_AVTIVITY_END = 30;//已结束
	
}
