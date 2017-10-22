package com.logistics.service.vo.activity;
import cn.assist.easydao.annotation.Id;
import cn.assist.easydao.annotation.Temporary;
import cn.assist.easydao.pojo.BasePojo;

/**
 * 活动订单
 *
 * @author caixb
 */
public class OrderActivity extends BasePojo{

	@Temporary
	private static final long serialVersionUID = 1L;
	
	
	@Id
	private String orderId;					//订单id
	private String activityAddr;			//活动地点
	private String activityInfo;			//活动详细信息
	private Integer orderActivityStatus;	//活动订单状态 0：初始状态   10：待开始 15：已结束  20：已取消
	private Integer inviteCount;			//陪同人数 为0表示不可免费邀请同行人
	
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getActivityAddr() {
		return activityAddr;
	}
	public void setActivityAddr(String activityAddr) {
		this.activityAddr = activityAddr;
	}
	public String getActivityInfo() {
		return activityInfo;
	}
	public void setActivityInfo(String activityInfo) {
		this.activityInfo = activityInfo;
	}
	public Integer getOrderActivityStatus() {
		return orderActivityStatus;
	}
	public void setOrderActivityStatus(Integer orderActivityStatus) {
		this.orderActivityStatus = orderActivityStatus;
	}
	public Integer getInviteCount() {
		return inviteCount;
	}
	public void setInviteCount(Integer inviteCount) {
		this.inviteCount = inviteCount;
	}
	
	
	
}
