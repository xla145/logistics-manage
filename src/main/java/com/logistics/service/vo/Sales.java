
package com.logistics.service.vo;

import cn.assist.easydao.annotation.Id;

import cn.assist.easydao.annotation.Temporary;

import cn.assist.easydao.pojo.BasePojo;
import java.util.Date;
import java.math.*;


/** sales
	S_ID	VARCHAR(128)
	NAME	VARCHAR(256)
	BUY_UID	INT(11)
	BUY_NAME	VARCHAR(64)
	BUY_MOBILE	VARCHAR(11)
	BUY_COUNT	INT(11)
	TOTAL_AMOUNT	DECIMAL(10,2)
	CREATE_TIME	DATETIME(19)
	UPDATE_TIME	DATETIME(19)
	REMARK	VARCHAR(256)
	STATUS	INT(5)
*/
public class Sales extends BasePojo {
	@Temporary
	private static final long serialVersionUID = 1L;
	@Id
	private String sId;
	private Integer buyUid;
	private String salesName;
	private String buyName;
	private String buyMobile;
	private Integer buyCount;
	private BigDecimal totalAmount;
	private Date createTime;
	private Date updateTime;
	private String remark;
	private Integer status;

	public String getsId() {
		return sId;
	}

	public void setsId(String sId) {
		this.sId = sId;
	}

	public void setBuyUid(Integer buyUid){
		this.buyUid=buyUid;
	}
	public Integer getBuyUid(){
		return buyUid;
	}
	public void setBuyName(String buyName){
		this.buyName=buyName;
	}
	public String getBuyName(){
		return buyName;
	}
	public void setBuyMobile(String buyMobile){
		this.buyMobile=buyMobile;
	}
	public String getBuyMobile(){
		return buyMobile;
	}
	public void setBuyCount(Integer buyCount){
		this.buyCount=buyCount;
	}
	public Integer getBuyCount(){
		return buyCount;
	}
	public void setTotalAmount(BigDecimal totalAmount){
		this.totalAmount=totalAmount;
	}
	public BigDecimal getTotalAmount(){
		return totalAmount;
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
	public void setStatus(Integer status){
		this.status=status;
	}
	public Integer getStatus(){
		return status;
	}

	public String getSalesName() {
		return salesName;
	}

	public void setSalesName(String salesName) {
		this.salesName = salesName;
	}
}

