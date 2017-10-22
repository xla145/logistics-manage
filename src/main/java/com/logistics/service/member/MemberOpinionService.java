package com.logistics.service.member;

import cn.assist.easydao.pojo.PagePojo;
import com.logistics.service.model.MemberOpinionModel;

import java.util.Map;

/**
 * 
* @ClassName: MemberOpinionService
* @Description: TODO(用户意见管理)
* @author Administrator
* @date 2017年7月21日
*
 */
public interface MemberOpinionService {
	
	/**
	 * 
	* @Title: memberOpinionPage
	* @Description: TODO(获取用户意见信息)
	* @param @param map
	* @param @param pageNo
	* @param @param pageSize
	* @param @return    参数
	* @return PagePojo<MemberOpinion>    返回类型
	* @throws
	 */
 	public PagePojo<MemberOpinionModel> memberOpinionPage(Map<String,Object> map, int pageNo, int pageSize);
 	
 	/**
 	 * 
 	* @Title: getMemberOpinion
 	* @Description: TODO(获取用户意见)
 	* @param @param id
 	* @param @return    参数
 	* @return MemberOpinion    返回类型
 	* @throws
 	 */
 	public MemberOpinionModel getMemberOpinion(Integer id);
 	
}
