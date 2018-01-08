package com.logistics.service.audit;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.pojo.PagePojo;
import com.logistics.base.utils.RecordBean;
import com.logistics.service.vo.AuditOrder;

/**
 * 审核单信息
 */
public interface IAuditService {

    /**
     * 获取审核单信息
     * @param conn
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PagePojo<AuditOrder> getAuditOrderPage(Conditions conn, int pageNo, int pageSize);

    /**
     * 批量添加采购单商品
     * @param status
     * @param auditId
     * @param reason
     * @return
     */
    public RecordBean<String> auditOrder(String orderId,Integer auditId, Integer status, String reason);



}
