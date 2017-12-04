
package com.logistics.service.vo;

import cn.assist.easydao.annotation.Id;

import cn.assist.easydao.annotation.Temporary;

import cn.assist.easydao.pojo.BasePojo;
import java.util.Date;
import java.math.*;


/** purchase
	PE_ID	VARCHAR(20)
	NAME	VARCHAR(256)
	WID	VARCHAR(30)
	BUY_COUNT	INT(11)
	TOTAL_AMOUNT	DECIMAL(10,2)
	CREATE_TIME	DATETIME(19)
	UPDATE_TIME	DATETIME(19)
	REMARK	VARCHAR(256)
	OPEAR_ID	INT(11)
	OPEAR_NAME	VARCHAR(20)
	TYPE	INT(11)
*/
public class Purchase extends BasePojo {
	@Temporary
	private static final long serialVersionUID = 1L;
	@Id
	private String peId;
	private Integer buyCount; // 采购单总数
	private BigDecimal totalAmount;// 采购单总价
	private Date createTime;
	private Date updateTime;
	private String remark;
	private Integer opearId; // 系统操作人信息
	private String opearName;// 操作人
	private Integer type;// 采购单类型
	private Integer status;// 采购单状态
	private Date peDate;// 单据时间
	private String purchaseName;// 采购人员

	public void setPeId(String peId){
		this.peId=peId;
	}
	public String getPeId(){
		return peId;
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
	public void setOpearId(Integer opearId){
		this.opearId=opearId;
	}
	public Integer getOpearId(){
		return opearId;
	}
	public void setOpearName(String opearName){
		this.opearName=opearName;
	}
	public String getOpearName(){
		return opearName;
	}
	public void setType(Integer type){
		this.type=type;
	}
	public Integer getType(){
		return type;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getPeDate() {
		return peDate;
	}

	public void setPeDate(Date peDate) {
		this.peDate = peDate;
	}

	public String getPurchaseName() {
		return purchaseName;
	}

	public void setPurchaseName(String purchaseName) {
		this.purchaseName = purchaseName;
	}
}

