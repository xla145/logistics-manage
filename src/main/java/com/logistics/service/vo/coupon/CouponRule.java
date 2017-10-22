
package com.logistics.service.vo.coupon;

import cn.assist.easydao.annotation.Id;

import cn.assist.easydao.annotation.Temporary;

import cn.assist.easydao.pojo.BasePojo;
import java.util.Date;

/**
 * 
* @ClassName: CouponRule
* @Description: TODO(优惠券规则)
* @author Administrator
* @date 2017年7月21日
*
 */
public class CouponRule extends BasePojo {
	@Temporary
	private static final long serialVersionUID = 1L;
	@Id
	private Integer id;
	private String couponId;//优惠券编号
	private Date createTime;
	private Date updateTime;
	private Integer status;
	private String name;//规则名称

	public void setId(Integer id){
		this.id=id;
	}
	public Integer getId(){
		return id;
	}
	public void setCouponId(String couponId){
		this.couponId=couponId;
	}
	public String getCouponId(){
		return couponId;
	}
	public void setCreateTime(Date createTime){
		this.createTime=createTime;
	}
	public Date getCreateTime(){
		return createTime;
	}
	public void setUpdateTime(Date updateTime){
		this.updateTime=updateTime;
	}
	public Date getUpdateTime(){
		return updateTime;
	}
	public void setStatus(Integer status){
		this.status=status;
	}
	public Integer getStatus(){
		return status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

