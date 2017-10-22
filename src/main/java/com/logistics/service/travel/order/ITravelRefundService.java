package com.logistics.service.travel.order;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.pojo.PagePojo;
import com.logistics.base.utils.RecordBean;
import com.logistics.service.pay.vo.PayRefundOrder;

import java.math.BigDecimal;
import java.util.List;

/**
 * 退款信息管理
 * Created by Administrator on 2017/9/1/001.
 */
public interface ITravelRefundService {

    /**
     * 分页查询退款订单信息
     *
     * @param conn
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PagePojo<PayRefundOrder> refundOrderTravelPage(Conditions conn, int pageNo, int pageSize);

    /**
     * 审核退款订单
     * @param payRefundOrder
     * @param operatorId
     * @param operatorName
     * @return
     */
    public RecordBean<PayRefundOrder> auditRefundOrder(PayRefundOrder payRefundOrder,Integer operatorId,String operatorName);


    /**
     * 获取退款单信息
     * @param proid
     * @return
     */
    public PayRefundOrder getPayRefundOrder(String proid);

    /**
     * 订单申请退款
     *
     * @param orderId		订单id
     * @param amount		退款金额
     * @param reason 		退款原因：reason会显示给用户看  请注意用语标准
     * @param remark		备注
     * @param operId		操作人id
     * @param operName		操作人姓名
     * @return
     */
    public RecordBean<String> refund(String orderId, BigDecimal amount, String reason, String remark, int operId, String operName,boolean isRefundCoupon);

    /**
     * 获取退款单信息
     * @param orderId
     * @return
     */
    public List<PayRefundOrder> getPayRefundByOrderId(String orderId);
}
