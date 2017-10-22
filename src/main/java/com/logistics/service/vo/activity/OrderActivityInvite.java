package com.logistics.service.vo.activity;
import java.util.Date;
import cn.assist.easydao.annotation.Id;
import cn.assist.easydao.annotation.Temporary;
import cn.assist.easydao.pojo.BasePojo;

/**
 * 活动邀请列表
 *
 * @author caixb
 */
public class OrderActivityInvite extends BasePojo{

	@Temporary
	private static final long serialVersionUID = 1L;
	
	@Id
	private Integer id;
	private String orderId;		//订单id
	private String name;		//活动地点 
	private String mobile;		//活动详细信息
	private Date createTime;	//活动订单状态 0：初始状态   10：待开始 15：已结束  20：已取消
	private Date updateTime;	//陪同人数 为0表示不可免费邀请同行人 
	private String remark;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	
	
}
