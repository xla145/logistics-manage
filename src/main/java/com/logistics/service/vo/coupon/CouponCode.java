package com.logistics.service.vo.coupon;

import cn.assist.easydao.annotation.Temporary;
import cn.assist.easydao.annotation.Id;
import cn.assist.easydao.pojo.BasePojo;
import java.util.Date;

public class CouponCode extends BasePojo {
	@Temporary
	private static final long serialVersionUID = 1L;
	@Id
	private Integer id;
	private String couponId;
	private String code;
	private Integer status;
	private Date createTime;
	private Date updateTime;
	private String remark;

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
	public void setCode(String code){
		this.code=code;
	}
	public String getCode(){
		return code;
	}
	public void setStatus(Integer status){
		this.status=status;
	}
	public Integer getStatus(){
		return status;
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
	public void setRemark(String remark){
		this.remark=remark;
	}
	public String getRemark(){
		return remark;
	}
}

