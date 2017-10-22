package com.logistics.service.member;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.pojo.PagePojo;
import com.logistics.base.utils.RecordBean;
import com.logistics.service.model.MemberCouponModel;
import com.logistics.service.model.MemberModel;
import com.logistics.service.vo.member.Member;
import com.logistics.service.vo.member.MemberAddr;
import com.logistics.service.vo.member.MemberFootprints;
import com.logistics.service.vo.member.MemberFootprintsImages;

import java.util.List;
import java.util.Map;

/**
 * 
* @ClassName: MemberService
* @Description: TODO(这里用一句话描述这个类的作用)
* @author Administrator
* @date 2017年7月21日
*
 */
public interface MemberService {

	/**
	 * 
	* @Title: memberPage
	* @Description: TODO(获取用户信息)
	* @param @param map
	* @param @param pageNo
	* @param @param pageSize
	* @param @return    参数
	* @return PagePojo<Member>    返回类型
	* @throws
	 */
	public PagePojo<Member> memberPage(Map<String,Object> map, int pageNo, int pageSize);
	
	/**
	 * 
	* @Title: meberPage
	* @Description: TODO(获取用户优惠券信息)
	* @param @param map
	* @param @param pageNo
	* @param @param pageSize
	* @param @return    参数
	* @return PagePojo<MemberCoupon>    返回类型
	* @throws
	 */
	public PagePojo<MemberCouponModel> memberCouponPage(Conditions conn, int pageNo, int pageSize);

	/**
	 *
	 * @Title: meberPage
	 * @Description: TODO(获取用户地址)
	 * @param @param map
	 * @param @param pageNo
	 * @param @param pageSize
	 * @param @return    参数
	 * @return PagePojo<MemberCoupon>    返回类型
	 * @throws
	 */
	public List<MemberAddr> memberAddrList(Conditions conn);

	/**
	 * 获取用户数据
	 * @param uid
	 * @return
	 */
	public Member getMember(Integer uid);

	/**
	 * 获取用户数据
	 * @param con
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PagePojo<Member> memberPage(Conditions con, int pageNo, int pageSize);

	/**
	 * 根据手机号获取用户的uid
	 * @param mobile
	 * @return
	 */
	public Member getMemberByMobile(String mobile);

	/**
	 * 修改头像
	 * @param uid
	 * @param avatar
	 * @return
	 */
	public RecordBean<String> updateAvatar(Integer uid,String avatar);

}
