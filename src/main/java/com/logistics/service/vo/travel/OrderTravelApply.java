
package com.logistics.service.vo.travel;

import cn.assist.easydao.annotation.Id;

import cn.assist.easydao.annotation.Temporary;

import cn.assist.easydao.pojo.BasePojo;
import java.util.Date;

/** order_travel_apply
	ID	INT(11)
	USER_NAME	VARCHAR(64)
	USER_MOBILE	VARCHAR(64)
	PID	VARCHAR(64)
	PRODUCT_NAME	VARCHAR(254)
	REMARK	VARCHAR(21845)
	CREATE_TIME	DATETIME(19)
	STATUS	INT(5)
*/
public class OrderTravelApply extends BasePojo {
	@Temporary
	private static final long serialVersionUID = 1L;
	
	@Id
	private Integer id;
	private String userName;
	private String userMobile;
	private String pid;
	private String productName;
	private String remark;
	private Date createTime;
	private Integer status;

	public void setId(Integer id){
		this.id=id;
	}
	public Integer getId(){
		return id;
	}
	public void setUserName(String userName){
		this.userName=userName;
	}
	public String getUserName(){
		return userName;
	}
	public void setUserMobile(String userMobile){
		this.userMobile=userMobile;
	}
	public String getUserMobile(){
		return userMobile;
	}
	public void setPid(String pid){
		this.pid=pid;
	}
	public String getPid(){
		return pid;
	}
	public void setProductName(String productName){
		this.productName=productName;
	}
	public String getProductName(){
		return productName;
	}
	public void setRemark(String remark){
		this.remark=remark;
	}
	public String getRemark(){
		return remark;
	}
	public void setCreateTime(Date createTime){
		this.createTime=createTime;
	}
	public Date getCreateTime(){
		return createTime;
	}
	public void setStatus(Integer status){
		this.status=status;
	}
	public Integer getStatus(){
		return status;
	}
}

