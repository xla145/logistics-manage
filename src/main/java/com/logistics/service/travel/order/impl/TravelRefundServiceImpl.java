package com.logistics.service.travel.order.impl;

import cn.assist.easydao.common.*;
import com.logistics.base.constant.OrderConstant;
import com.logistics.base.constant.OrderTravelConstant;
import com.logistics.base.log.Log;
import com.logistics.service.coupon.CouponMemberService;
import com.logistics.service.helper.DataBean;
import com.logistics.service.travel.order.ITravelOrderService;
import com.logistics.service.travel.order.ITravelRefundService;
import com.logistics.service.pay.api.IPayOrderService;
import com.logistics.service.pay.api.IRefundOrderService;
import com.logistics.service.pay.constant.PayRefundOrderConstant;
import com.logistics.service.pay.vo.PayRefundOrder;
import com.logistics.service.sys.log.ISysOperatorLogService;
import com.logistics.service.vo.travel.OrderTravelSummary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.assist.easydao.pojo.PagePojo;

import com.alibaba.fastjson.JSON;
import com.logistics.base.utils.RecordBean;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/9/1/001.
 */

@Service("ITravelRefundService")
public class TravelRefundServiceImpl implements ITravelRefundService {
    Logger logger = LoggerFactory.getLogger(TravelRefundServiceImpl.class);

    @Autowired
    private IRefundOrderService iRefundOrderService;
    @Autowired
    private ITravelOrderService travelOrderService;
    @Autowired
    private ISysOperatorLogService iSysOperatorLogService;
    @Autowired
    private IPayOrderService iPayOrderService;
    @Autowired
    private CouponMemberService couponMemberService;

    /**
     * 获取退款订单信息
     * @param conn
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public PagePojo<PayRefundOrder> refundOrderTravelPage(Conditions conn, int pageNo, int pageSize) {
        return iRefundOrderService.payRefundOrderPage(conn,pageNo,pageSize);
    }

    /**
     * 审核订单信息
     * @param payRefundOrder
     * @param operatorId
     * @param operatorName
     * @return
     */
    @Log(operateName = "订单审核")
    @Override
    public RecordBean<PayRefundOrder> auditRefundOrder(PayRefundOrder payRefundOrder, Integer operatorId, String operatorName) {
        logger.info("审核退款单信息"+ JSON.toJSON(payRefundOrder)+"，审核人员（"+operatorName+")");
//        if (payRefundOrder.getStatus() != PayRefundOrderConstant.PAY_REFUND_STATUS_SUCCESS) {
//            logger.error("不能填写退款用户的信息,原因是状态是("+payRefundOrder.getStatus()+")");
//            return RecordBean.error("非审核成功状态的退款单，不能执行退款用户的信息填写操作！");
//        }
        DataBean<PayRefundOrder> result = iRefundOrderService.updateRefundOrder(payRefundOrder,operatorId,operatorName);
        if (!result.isSuccess()){
            return RecordBean.error("审核退款单失败！");
        }
        return RecordBean.success("审核退款单成功!",payRefundOrder);
    }

    /**
     * 获取退款单信息
     * @param proid
     * @return
     */
    @Override
    public PayRefundOrder getPayRefundOrder(String proid) {
        return iRefundOrderService.getPayRefundOrder(proid);
    }

    /**
     * 退款操作
     * @param orderId		订单id
     * @param amount		退款金额
     * @param reason 		退款原因：reason会显示给用户看  请注意用语标准
     * @param remark		备注
     * @param operId		操作人id
     * @param operName		操作人姓名
     * @return
     */
    @Log(operateName = "退款操作")
    @Transactional
    @Override
    public RecordBean<String> refund(String orderId, BigDecimal amount, String reason, String remark, int operId, String operName,boolean isRefundCoupon) {
        logger.info("退款单信息:订单号：【"+orderId +"】，审核人员【"+operName+"】"+",是否退优惠券【"+isRefundCoupon+"】");
        OrderTravelSummary orderTravel = travelOrderService.getOrderTravel(orderId);
        if(orderTravel == null){
            return RecordBean.error("退款失败！原因是（获取订单号"+orderId+"为的信息为空！）");
        }
        int orderStatus = orderTravel.getOrderTravelStatus();
        int payStatus = orderTravel.getPayStatus();

        if(payStatus != OrderConstant.ORDER_PAY_STATUS_SUCCESS){
            logger.info("[旅游订单 申请退款]-[操作失败]-[订单支付状态不对]-orderId:" + orderId + ",amount:" + amount + ",orderStatus:" + orderStatus + ",payStatus:" + payStatus + ",operId:" + operId + ",operName:" + operName);
            return RecordBean.error("[旅游订单 申请退款]-[操作失败]-[订单支付状态不对]");
        }
        if(orderStatus != OrderTravelConstant.STATUS_WAIT_TRAVEL){
            logger.info("[旅游订单 申请退款]-[操作失败]-[订单状态不对]-orderId:" + orderId + ",amount:" + amount + ",orderStatus:" + orderStatus + ",payStatus:" + payStatus + ",operId:" + operId + ",operName:" + operName);
            return RecordBean.error("[旅游订单 申请退款]-[操作失败]-[订单支付状态不对]");
        }

        if (isRefundCoupon) { //是否退优惠券
            iPayOrderService.returnCoupon(orderTravel.getBuyUid(),orderId,new Date(),reason);
        }

        DataBean<Boolean> result = iRefundOrderService.refundApply(orderTravel.getBuyUid(),orderTravel.getCatId(), orderId, amount, reason, remark, operId, operName);
        if (!result.isSuccess()) {
            return RecordBean.error("[退款申请失败！]"+result.getMsg());
        }
        return RecordBean.success("退款申请成功！");
    }

    @Override
    public List<PayRefundOrder> getPayRefundByOrderId(String orderId) {
        return iRefundOrderService.getPayRefundByOrderId(orderId);
    }
}
