package com.logistics.service.coupon;

import cn.assist.easydao.pojo.PagePojo;
import com.logistics.base.utils.DataObj;
import com.logistics.service.model.MemberCouponModel;
import com.logistics.service.vo.coupon.Coupon;
import com.logistics.service.vo.member.Member;
import com.logistics.service.vo.member.MemberCoupon;

import java.util.Map;

/**
 * test
 *
 * @author caibin
 */
public interface CouponMemberService {

	/**
	 * 分页查询 发放优惠券信息
	 *
	 * @param map      the map
	 * @param pageNo   查询页码
	 * @param pageSize 每页数量
	 * @return coupon member page
	 */
	public PagePojo<MemberCouponModel> getCouponMemberPage(Map<String,Object> map, int pageNo, int pageSize);

	/**
	 * 获取 发放优惠券信息
	 *
	 * @param mcid 用户优惠券信息
	 * @return coupon member
	 */
	public MemberCoupon getCouponMember(String mcid);

	/**
	 * 发放优惠券
	 * @param cid
	 * @param uid
	 * @param number
	 * @param remark
	 * @param operatorId
	 * @param operatorName
	 * @return
	 */
	public DataObj<MemberCouponModel> addCouponMember(String cid,String uid,Integer number,String remark,Integer operatorId,String operatorName);


	/**
	 *
	 * @param map
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PagePojo<Member> memberPage(Map<String,Object> map, int pageNo, int pageSize);

	/**
	 *
	 * @param map
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PagePojo<Coupon> couponPage(Map<String,Object> map, int pageNo, int pageSize);
	
	
}
