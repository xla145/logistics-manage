
package com.logistics.service.vo;

import cn.assist.easydao.annotation.Id;

import cn.assist.easydao.annotation.Temporary;

import cn.assist.easydao.pojo.BasePojo;
import java.util.Date;

/**
 * 供应商信息
 */
public class Supplier extends BasePojo {
	@Temporary
	private static final long serialVersionUID = 1L;
	@Id
	private Integer id;
	private String name;
	private String people;
	private String mobile;
	private String fax;
	private String url;
	private String qq;
	private String address;
	private Integer status;
	private Date stopTime;
	private String email;
	private Integer settType;
	private Integer productGroupType;
	private String remark;
	private Date updateTime;
	private Date createTime;

	public void setId(Integer id){
		this.id=id;
	}
	public Integer getId(){
		return id;
	}
	public void setName(String name){
		this.name=name;
	}
	public String getName(){
		return name;
	}
	public void setPeople(String people){
		this.people=people;
	}
	public String getPeople(){
		return people;
	}
	public void setMobile(String mobile){
		this.mobile=mobile;
	}
	public String getMobile(){
		return mobile;
	}
	public void setFax(String fax){
		this.fax=fax;
	}
	public String getFax(){
		return fax;
	}
	public void setUrl(String url){
		this.url=url;
	}
	public String getUrl(){
		return url;
	}
	public void setQq(String qq){
		this.qq=qq;
	}
	public String getQq(){
		return qq;
	}
	public void setAddress(String address){
		this.address=address;
	}
	public String getAddress(){
		return address;
	}
	public void setStatus(Integer status){
		this.status=status;
	}
	public Integer getStatus(){
		return status;
	}
	public void setStopTime(Date stopTime){
		this.stopTime=stopTime;
	}
	public Date getStopTime(){
		return stopTime;
	}
	public void setEmail(String email){
		this.email=email;
	}
	public String getEmail(){
		return email;
	}

	public Integer getSettType() {
		return settType;
	}

	public void setSettType(Integer settType) {
		this.settType = settType;
	}

	public Integer getProductGroupType() {
		return productGroupType;
	}

	public void setProductGroupType(Integer productGroupType) {
		this.productGroupType = productGroupType;
	}

	public void setRemark(String remark){
		this.remark=remark;
	}
	public String getRemark(){
		return remark;
	}
	public void setUpdateTime(Date updateTime){
		this.updateTime=updateTime;
	}
	public Date getUpdateTime(){
		return updateTime;
	}
	public void setCreateTime(Date createTime){
		this.createTime=createTime;
	}
	public Date getCreateTime(){
		return createTime;
	}
}

