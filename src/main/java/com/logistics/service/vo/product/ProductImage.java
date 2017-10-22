package com.logistics.service.vo.product;
import cn.assist.easydao.annotation.Temporary;

import cn.assist.easydao.pojo.BasePojo;

import cn.assist.easydao.annotation.Id;
import java.util.Date;

/**
 * 
* @ClassName: ProductImage
* @Description: TODO(这里用一句话描述这个类的作用)
* @author Administrator
* @date 2017年7月18日
*
 */
public class ProductImage extends BasePojo {
	@Temporary
	private static final long serialVersionUID = 1L;
	@Id
	private Integer id;
	private String pid;
	private String url;
	private Date createTime;
	private Integer status;
	private String remark;
	private Integer weight;

	public void setId(Integer id){
		this.id=id;
	}
	public Integer getId(){
		return id;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public void setUrl(String url){
		this.url=url;
	}
	public String getUrl(){
		return url;
	}
	public void setCreateTime(Date createTime){
		this.createTime=createTime;
	}
	public Date getCreateTime(){
		return createTime;
	}
	public void setStatus(Integer status){
		this.status=status;
	}
	public Integer getStatus(){
		return status;
	}
	public void setRemark(String remark){
		this.remark=remark;
	}

	public String getRemark() {
		return remark;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}
}

