
package com.logistics.service.vo.member;

import cn.assist.easydao.annotation.Id;

import cn.assist.easydao.annotation.Temporary;

import cn.assist.easydao.pojo.BasePojo;
import java.util.Date;

/** member_addr
	ID	INT(11)
	UID	INT(11)
	NAME	VARCHAR(11)
	MOBILE	VARCHAR(11)
	PROVINCE	VARCHAR(128)
	PROVINCE_ID	VARCHAR(128)
	CITY	VARCHAR(128)
	CITY_ID	VARCHAR(128)
	TOWN	VARCHAR(128)
	TOWN_ID	VARCHAR(128)
	ADDR	VARCHAR(256)
	STATUS	INT(5)
	TAG	VARCHAR(128)
	IS_DEFAULT	INT(2)
	CREATE_TIME	DATETIME(19)
	UPDATE_TIME	DATETIME(19)
*/
public class MemberAddr extends BasePojo {
	@Temporary
	private static final long serialVersionUID = 1L;
	@Id
	private Integer id;
	private Integer uid;
	private String name;
	private String mobile;
	private String province;
	private String provinceId;
	private String city;
	private String cityId;
	private String town;
	private String townId;
	private String addr;
	private Integer status;
	private String tag;
	private Integer isDefault;
	private Date createTime;
	private Date updateTime;

	public void setId(Integer id){
		this.id=id;
	}
	public Integer getId(){
		return id;
	}
	public void setUid(Integer uid){
		this.uid=uid;
	}
	public Integer getUid(){
		return uid;
	}
	public void setName(String name){
		this.name=name;
	}
	public String getName(){
		return name;
	}
	public void setMobile(String mobile){
		this.mobile=mobile;
	}
	public String getMobile(){
		return mobile;
	}
	public void setProvince(String province){
		this.province=province;
	}
	public String getProvince(){
		return province;
	}
	public void setProvinceId(String provinceId){
		this.provinceId=provinceId;
	}
	public String getProvinceId(){
		return provinceId;
	}
	public void setCity(String city){
		this.city=city;
	}
	public String getCity(){
		return city;
	}
	public void setCityId(String cityId){
		this.cityId=cityId;
	}
	public String getCityId(){
		return cityId;
	}
	public void setTown(String town){
		this.town=town;
	}
	public String getTown(){
		return town;
	}
	public void setTownId(String townId){
		this.townId=townId;
	}
	public String getTownId(){
		return townId;
	}
	public void setAddr(String addr){
		this.addr=addr;
	}
	public String getAddr(){
		return addr;
	}
	public void setStatus(Integer status){
		this.status=status;
	}
	public Integer getStatus(){
		return status;
	}
	public void setTag(String tag){
		this.tag=tag;
	}
	public String getTag(){
		return tag;
	}
	public void setIsDefault(Integer isDefault){
		this.isDefault=isDefault;
	}
	public Integer getIsDefault(){
		return isDefault;
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
}

