package com.logistics.service.vo.card;
import java.util.Date;
import cn.assist.easydao.annotation.Id;
import cn.assist.easydao.annotation.Temporary;
import cn.assist.easydao.pojo.BasePojo;

/**
 * 激活码
 * 
 * @author caixb
 */
public class CardCdk extends BasePojo {
	@Temporary
	private static final long serialVersionUID = 1L;
	
	@Id
	private Integer id;
	private Integer cardId;
	private String cardName;
	private String cdk;
	private Integer status;
	private Date createTime;
	private String batchNo;
	private Date activateTime;
	private Integer uid;
	private String ip;
	private String remark;

	public void setId(Integer id){
		this.id=id;
	}
	public Integer getId(){
		return id;
	}
	public void setCardId(Integer cardId){
		this.cardId=cardId;
	}
	public Integer getCardId(){
		return cardId;
	}
	public String getCardName() {
		return cardName;
	}
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	public void setCdk(String cdk){
		this.cdk=cdk;
	}
	public String getCdk(){
		return cdk;
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
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public void setActivateTime(Date activateTime){
		this.activateTime=activateTime;
	}
	public Date getActivateTime(){
		return activateTime;
	}
	public void setUid(Integer uid){
		this.uid=uid;
	}
	public Integer getUid(){
		return uid;
	}
	public void setIp(String ip){
		this.ip=ip;
	}
	public String getIp(){
		return ip;
	}
	public void setRemark(String remark){
		this.remark=remark;
	}
	public String getRemark(){
		return remark;
	}
}

