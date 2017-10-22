package com.logistics.service.model;

import cn.assist.easydao.annotation.Temporary;
import com.logistics.service.pay.vo.PayOrderDetail;
import com.logistics.service.vo.order.Order;
import com.logistics.service.vo.activity.OrderActivityInvite;

import java.util.List;

/**
 * 
* @ClassName: ProductOrderActivityModel
* @Description: TODO(产品活动的业务模型)
* @author Administrator
* @date 2017年7月19日
*
 */
public class OrderActivityModel extends Order{
	@Temporary
	private static final long serialVersionUID = 1L;
	private String supplierName;//合作商家
	private String activityAddr;//活动地点
	private String activityInfo;
	private String activityTime;//活动时间区间
	private Integer orderActivityStatus;//订单状态 0：初始状态   10：待开始 15：已结束  20：已取消
	private String orderStatus;
	private Integer inviteCount;//陪同人数 为0表示不可免费邀请同行人
	private List<PayOrderDetail> detailList;
	private List<OrderActivityInvite> memberList;//携带人数

	public String getActivityAddr() {
		return activityAddr;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
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

	public List<OrderActivityInvite> getMemberList() {
		return memberList;
	}

	public void setMemberList(List<OrderActivityInvite> memberList) {
		this.memberList = memberList;
	}

	public String getActivityTime() {
		return activityTime;
	}

	public void setActivityTime(String activityTime) {
		this.activityTime = activityTime;
	}

	public List<PayOrderDetail> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<PayOrderDetail> detailList) {
		this.detailList = detailList;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

}
