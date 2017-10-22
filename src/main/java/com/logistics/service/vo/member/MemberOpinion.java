
package com.logistics.service.vo.member;

import cn.assist.easydao.annotation.Id;
import cn.assist.easydao.annotation.Temporary;

import java.util.Date;

/**
 * 
* @ClassName: MemberOpinion
* @Description: TODO(这里用一句话描述这个类的作用)
* @author Administrator
* @date 2017年7月21日
*
 */
public class MemberOpinion extends Member {
	@Temporary
	private static final long serialVersionUID = 1L;
	@Id
	private Integer id;
	private Integer uid;
	private String feedback;//反馈内容
	private Integer status;//状态 0 未处理， 1已处理
	private String remark;//备注
	private Date createTime;
	private Date updateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public Integer getUid() {
		return uid;
	}

	@Override
	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	@Override
	public Integer getStatus() {
		return status;
	}

	@Override
	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public String getRemark() {
		return remark;
	}

	@Override
	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public Date getCreateTime() {
		return createTime;
	}

	@Override
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public Date getUpdateTime() {
		return updateTime;
	}

	@Override
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}

