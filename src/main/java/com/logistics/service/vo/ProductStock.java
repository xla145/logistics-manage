
package com.logistics.service.vo;

import cn.assist.easydao.annotation.Id;

import cn.assist.easydao.annotation.Temporary;

import cn.assist.easydao.pojo.BasePojo;
import java.util.Date;
import java.math.*;


/** product_stock
	ID	INT(11)
	PRODUCT_ID	VARCHAR(30)
	CAT_ID	INT(11)
	STOCK	VARCHAR(255)
	REMAINING	VARCHAR(255)
	CREATE_TIME	DATETIME(19)
	UPDATE_TIME	DATETIME(19)
	REMARK	VARCHAR(255)
*/
public class ProductStock extends BasePojo {
	@Temporary
	private static final long serialVersionUID = 1L;
	@Id
	private Integer id;
	private String productId;
	private Integer catId;
	private String stock;
	private String remaining;
	private Date createTime;
	private Date updateTime;
	private String remark;

	public void setId(Integer id){
		this.id=id;
	}
	public Integer getId(){
		return id;
	}
	public void setProductId(String productId){
		this.productId=productId;
	}
	public String getProductId(){
		return productId;
	}
	public void setCatId(Integer catId){
		this.catId=catId;
	}
	public Integer getCatId(){
		return catId;
	}
	public void setStock(String stock){
		this.stock=stock;
	}
	public String getStock(){
		return stock;
	}
	public void setRemaining(String remaining){
		this.remaining=remaining;
	}
	public String getRemaining(){
		return remaining;
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

