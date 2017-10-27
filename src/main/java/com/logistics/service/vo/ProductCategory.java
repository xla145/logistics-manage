
package com.logistics.service.vo;

import cn.assist.easydao.annotation.Id;

import cn.assist.easydao.annotation.Temporary;

import cn.assist.easydao.pojo.BasePojo;
import java.util.Date;
import java.math.*;


/** product_category
	CAT_ID	INT UNSIGNED(5)
	NAME	VARCHAR(90)
	PARENT_ID	SMALLINT UNSIGNED(5)
	PARENT_ID_PATH	VARCHAR(128)
	IS_SHOW	TINYINT UNSIGNED(4)
	IMG_URL	VARCHAR(512)
	STATUS	TINYINT(4)
	CREATE_TIME	DATETIME(19)
	UPDATE_TIME	DATETIME(19)
	WEIGHT	INT UNSIGNED(11)
	REMARK	VARCHAR(512)
*/
public class ProductCategory extends BasePojo {
	@Temporary
	private static final long serialVersionUID = 1L;
	@Id
	private Integer catId;
	private String name;
	private Integer parentId;
	private String parentIdPath;
	private Integer isShow;
	private String imgUrl;
	private boolean status;
	private Date createTime;
	private Date updateTime;
	private Integer weight;
	private String remark;

	public void setCatId(Integer catId){
		this.catId=catId;
	}
	public Integer getCatId(){
		return catId;
	}
	public void setName(String name){
		this.name=name;
	}
	public String getName(){
		return name;
	}
	public void setParentId(Integer parentId){
		this.parentId=parentId;
	}
	public Integer getParentId(){
		return parentId;
	}
	public void setParentIdPath(String parentIdPath){
		this.parentIdPath=parentIdPath;
	}
	public String getParentIdPath(){
		return parentIdPath;
	}
	public void setIsShow(Integer isShow){
		this.isShow=isShow;
	}
	public Integer getIsShow(){
		return isShow;
	}
	public void setImgUrl(String imgUrl){
		this.imgUrl=imgUrl;
	}
	public String getImgUrl(){
		return imgUrl;
	}
	public void setStatus(boolean status){
		this.status=status;
	}
	public boolean getStatus(){
		return status;
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
	public void setWeight(Integer weight){
		this.weight=weight;
	}
	public Integer getWeight(){
		return weight;
	}
	public void setRemark(String remark){
		this.remark=remark;
	}
	public String getRemark(){
		return remark;
	}
}

