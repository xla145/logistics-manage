
package com.logistics.service.vo.product;

import cn.assist.easydao.annotation.Id;

import cn.assist.easydao.annotation.Temporary;

import cn.assist.easydao.pojo.BasePojo;

/** product_group
	GID	INT(11)
	NAME	VARCHAR(255)
	INFO	VARCHAR(255)
	REMARK	VARCHAR(255)
	WEIGHT	INT(5)
*/
public class ProductGroup extends BasePojo {
	@Temporary
	private static final long serialVersionUID = 1L;
	@Id
	private Integer gid;
	private String name;
	private String info;
	private String remark;
	private Integer weight;

	public void setGid(Integer gid){
		this.gid=gid;
	}
	public Integer getGid(){
		return gid;
	}
	public void setName(String name){
		this.name=name;
	}
	public String getName(){
		return name;
	}
	public void setInfo(String info){
		this.info=info;
	}
	public String getInfo(){
		return info;
	}
	public void setRemark(String remark){
		this.remark=remark;
	}
	public String getRemark(){
		return remark;
	}
	public void setWeight(Integer weight){
		this.weight=weight;
	}
	public Integer getWeight(){
		return weight;
	}
}

