package com.logistics.service.coupon;
import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.pojo.PagePojo;
import com.logistics.base.utils.DataObj;
import com.logistics.service.model.CouponModel;
import com.logistics.service.vo.coupon.Coupon;
import com.logistics.service.vo.coupon.CouponRule;
import com.logistics.service.vo.coupon.CouponType;

import java.util.List;
import java.util.Map;

/**
 * The interface Coupon service.
 */
public interface CouponService {

	/**
	 * 分页查询 代金券
	 *
	 * @param map      the map
	 * @param pageNo   查询页码
	 * @param pageSize 每页数量
	 * @return coupon page
	 */
	public PagePojo<CouponModel> getCouponPage(Map<String,Object> map, int pageNo, int pageSize);

	/**
	 * 分页查询 代金券
	 *
	 * @param conn     the conn
	 * @param pageNo   查询页码
	 * @param pageSize 每页数量
	 * @return coupon page
	 */
	public PagePojo<Coupon> getCouponPage(Conditions conn, int pageNo, int pageSize);

	/**
	 * 获取代金券信息
	 *
	 * @param cid 代金券信息
	 * @return coupon
	 */
	public Coupon getCouponModel(String cid);

	/**
	 * 添加 或修改代金券信息
	 *
	 * @param couponModel 代金券信息
	 * @return int
	 */
	public DataObj<CouponModel> addCouponModel(CouponModel couponModel);

	/**
	 * 删除代金券信息
	 *
	 * @param ids 代金券信息
	 * @return int
	 */
	public DataObj<String> delCoupon(String[] ids);

	/**
	 * 获取代金券类型
	 *
	 * @return the coupon type
	 */
	public List<CouponType> getCouponType();

	/**
	 * 获取代金券规则
	 * @return
	 */
	public List<CouponRule> getCouponRule();

	/**
	 * 获取代金券
	 * @return
	 */
	public List<Coupon> getCouponByUseRange(Integer useRange);


	/**
	 * 根据卡包的编号
	 * 获取代金券列表
	 * @param cardId
	 * @return
	 */
	public List<CouponModel> getCouponListByCardId(Integer cardId);
	
}
