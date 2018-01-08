
package com.logistics.service.vo;

import cn.assist.easydao.annotation.Id;

import cn.assist.easydao.annotation.Temporary;

import cn.assist.easydao.pojo.BasePojo;
import java.util.Date;

/** audit_order
	ID	INT(11)
	ORDER_ID	VARCHAR(20)
	TYPE	INT(11)
	STATUS	INT(11)
	REASON	VARCHAR(255)
	CREATE_TIME	DATETIME(19)
*/
public class AuditOrder extends BasePojo {
	@Temporary
	private static final long serialVersionUID = 1L;
	@Id
	private Integer id;
	private String orderId;
	private Integer type;
	private Integer status;
	private String reason;
	private Date createTime;

	public void setId(Integer id){
		this.id=id;
	}
	public Integer getId(){
		return id;
	}
	public void setOrderId(String orderId){
		this.orderId=orderId;
	}
	public String getOrderId(){
		return orderId;
	}
	public void setType(Integer type){
		this.type=type;
	}
	public Integer getType(){
		return type;
	}
	public void setStatus(Integer status){
		this.status=status;
	}
	public Integer getStatus(){
		return status;
	}
	public void setReason(String reason){
		this.reason=reason;
	}
	public String getReason(){
		return reason;
	}
	public void setCreateTime(Date createTime){
		this.createTime=createTime;
	}
	public Date getCreateTime(){
		return createTime;
	}
}

