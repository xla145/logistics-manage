
package com.logistics.service.vo;

import cn.assist.easydao.annotation.Id;

import cn.assist.easydao.annotation.Temporary;

import cn.assist.easydao.pojo.BasePojo;
import java.util.Date;

/** footprints
	MFID	VARCHAR(64)
	UID	INT(11)
	TITLE	VARCHAR(100)
	DESCRIBE	VARCHAR(256)
	STATUS	INT(5)
	COVER_IMG	VARCHAR(512)
	BACKGROUND_IMG	VARCHAR(512)
	CREATE_TIME	DATETIME(19)
	UPDATE_TIME	DATETIME(19)
	REMARK	VARCHAR(255)
*/
public class Footprints extends BasePojo {
	@Temporary
	private static final long serialVersionUID = 1L;
	@Id
	private String mfid;
	private Integer uid;
	private String title;
	private String describe;
	private Integer status;
	private String coverImg;
	private String backgroundImg;
	private Date createTime;
	private Date updateTime;
	private String remark;

	public void setMfid(String mfid){
		this.mfid=mfid;
	}
	public String getMfid(){
		return mfid;
	}
	public void setUid(Integer uid){
		this.uid=uid;
	}
	public Integer getUid(){
		return uid;
	}
	public void setTitle(String title){
		this.title=title;
	}
	public String getTitle(){
		return title;
	}
	public void setDescribe(String describe){
		this.describe=describe;
	}
	public String getDescribe(){
		return describe;
	}
	public void setStatus(Integer status){
		this.status=status;
	}
	public Integer getStatus(){
		return status;
	}
	public void setCoverImg(String coverImg){
		this.coverImg=coverImg;
	}
	public String getCoverImg(){
		return coverImg;
	}
	public void setBackgroundImg(String backgroundImg){
		this.backgroundImg=backgroundImg;
	}
	public String getBackgroundImg(){
		return backgroundImg;
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

