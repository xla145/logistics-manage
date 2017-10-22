
package com.logistics.service.vo.member;

import cn.assist.easydao.annotation.Id;

import cn.assist.easydao.annotation.Temporary;

import cn.assist.easydao.pojo.BasePojo;
import java.util.Date;

/** member
	UID	INT(11)
	MOBILE	VARCHAR(11)
	STATUS	INT(5)
	PASSWORD	VARCHAR(128)
	NICKNAME	VARCHAR(128)
	AVATAR	VARCHAR(256)
	SEX	INT(2)
	FR	VARCHAR(64)
	PLATFORM	VARCHAR(64)
	IP	VARCHAR(64)
	REMARK	VARCHAR(128)
	CREATE_TIME	DATETIME(19)
	UPDATE_TIME	DATETIME(19)
	LAST_TIME	DATETIME(19)
*/
public class Member extends BasePojo {
	@Temporary
	private static final long serialVersionUID = 1L;
	@Id
	private Integer uid;
	private String mobile;
	private Integer status;
	private String password;
	private String nickname;
	private String avatar;
	private Integer sex;
	private String fr;
	private String platform;
	private String ip;
	private String remark;
	private Date createTime;
	private Date updateTime;
	private Date lastTime;

	public void setUid(Integer uid){
		this.uid=uid;
	}
	public Integer getUid(){
		return uid;
	}
	public void setMobile(String mobile){
		this.mobile=mobile;
	}
	public String getMobile(){
		return mobile;
	}
	public void setStatus(Integer status){
		this.status=status;
	}
	public Integer getStatus(){
		return status;
	}
	public void setPassword(String password){
		this.password=password;
	}
	public String getPassword(){
		return password;
	}
	public void setNickname(String nickname){
		this.nickname=nickname;
	}
	public String getNickname(){
		return nickname;
	}
	public void setAvatar(String avatar){
		this.avatar=avatar;
	}
	public String getAvatar(){
		return avatar;
	}
	public void setSex(Integer sex){
		this.sex=sex;
	}
	public Integer getSex(){
		return sex;
	}
	public void setFr(String fr){
		this.fr=fr;
	}
	public String getFr(){
		return fr;
	}
	public void setPlatform(String platform){
		this.platform=platform;
	}
	public String getPlatform(){
		return platform;
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
	public void setLastTime(Date lastTime){
		this.lastTime=lastTime;
	}
	public Date getLastTime(){
		return lastTime;
	}
}

