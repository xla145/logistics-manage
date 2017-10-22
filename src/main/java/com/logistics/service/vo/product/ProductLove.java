
package com.logistics.service.vo.product;

import cn.assist.easydao.annotation.Id;

import cn.assist.easydao.annotation.Temporary;

import cn.assist.easydao.pojo.BasePojo;
import java.util.Date;

/** product_love
	ID	INT(11)
	PID	VARCHAR(128)
	UID	INT(11)
	IP	VARCHAR(128)
	FEVER	INT(5)
	CREATE_TIME	DATETIME(19)
	REMARK	VARCHAR(256)
*/
public class ProductLove extends BasePojo {
	@Temporary
	private static final long serialVersionUID = 1L;
	@Id
	private Integer id;
	private String pid;
	private Integer uid;
	private String ip;
	private Integer fever;
	private Date createTime;
	private String remark;

	public void setId(Integer id){
		this.id=id;
	}
	public Integer getId(){
		return id;
	}
	public void setPid(String pid){
		this.pid=pid;
	}
	public String getPid(){
		return pid;
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
	public void setFever(Integer fever){
		this.fever=fever;
	}
	public Integer getFever(){
		return fever;
	}
	public void setCreateTime(Date createTime){
		this.createTime=createTime;
	}
	public Date getCreateTime(){
		return createTime;
	}
	public void setRemark(String remark){
		this.remark=remark;
	}
	public String getRemark(){
		return remark;
	}
}

