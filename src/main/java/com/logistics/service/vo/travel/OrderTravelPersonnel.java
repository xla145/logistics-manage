
package com.logistics.service.vo.travel;

import cn.assist.easydao.annotation.Id;

import cn.assist.easydao.annotation.Temporary;

import cn.assist.easydao.pojo.BasePojo;
import java.util.Date;
import java.math.*;

//携带人员信息
public class OrderTravelPersonnel extends BasePojo {
	@Temporary
	private static final long serialVersionUID = 1L;
	
	@Id
	private Integer id;
	private String orderId;
	private Integer type;
	private String typeName;
	private BigDecimal referPrice;
	private String cnName;
	private String enName;
	private String mobile;
	private Integer sex;
	private String  sexName;
	private Integer credentials;
	private String  credentialsName;
	private String credentialsId;
	private String issueAt;
	private Date issueTime;
	private Date validTime;
	private Date birthTime;
	private String birthPlace;
	private Date createTime;
	private Integer operatorId;
	private String operatorName;
	private String remark;

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
	public void setReferPrice(BigDecimal referPrice){
		this.referPrice=referPrice;
	}
	public BigDecimal getReferPrice(){
		return referPrice;
	}
	public void setCnName(String cnName){
		this.cnName=cnName;
	}
	public String getCnName(){
		return cnName;
	}
	public void setEnName(String enName){
		this.enName=enName;
	}
	public String getEnName(){
		return enName;
	}
	public void setMobile(String mobile){
		this.mobile=mobile;
	}
	public String getMobile(){
		return mobile;
	}
	public void setSex(Integer sex){
		this.sex=sex;
	}
	public Integer getSex(){
		return sex;
	}
	public void setCredentials(Integer credentials){
		this.credentials=credentials;
	}
	public Integer getCredentials(){
		return credentials;
	}
	public void setCredentialsId(String credentialsId){
		this.credentialsId=credentialsId;
	}
	public String getCredentialsId(){
		return credentialsId;
	}
	public void setIssueAt(String issueAt){
		this.issueAt=issueAt;
	}
	public String getIssueAt(){
		return issueAt;
	}
	public void setIssueTime(Date issueTime){
		this.issueTime=issueTime;
	}
	public Date getIssueTime(){
		return issueTime;
	}
	public void setValidTime(Date validTime){
		this.validTime=validTime;
	}
	public Date getValidTime(){
		return validTime;
	}
	public void setBirthTime(Date birthTime){
		this.birthTime=birthTime;
	}
	public Date getBirthTime(){
		return birthTime;
	}
	public void setBirthPlace(String birthPlace){
		this.birthPlace=birthPlace;
	}
	public String getBirthPlace(){
		return birthPlace;
	}
	public void setCreateTime(Date createTime){
		this.createTime=createTime;
	}
	public Date getCreateTime(){
		return createTime;
	}
	public void setOperatorId(Integer operatorId){
		this.operatorId=operatorId;
	}
	public Integer getOperatorId(){
		return operatorId;
	}
	public void setOperatorName(String operatorName){
		this.operatorName=operatorName;
	}
	public String getOperatorName(){
		return operatorName;
	}
	public void setRemark(String remark){
		this.remark=remark;
	}
	public String getRemark(){
		return remark;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getSexName() {
		return sexName;
	}

	public void setSexName(String sexName) {
		this.sexName = sexName;
	}

	public String getCredentialsName() {
		return credentialsName;
	}

	public void setCredentialsName(String credentialsName) {
		this.credentialsName = credentialsName;
	}
}

