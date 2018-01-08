package com.logistics.service.audit.impl;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.common.Sort;
import cn.assist.easydao.common.SqlSort;
import cn.assist.easydao.dao.BaseDao;
import cn.assist.easydao.pojo.PagePojo;
import com.logistics.base.utils.RecordBean;
import com.logistics.service.audit.IAuditService;
import com.logistics.service.vo.AuditOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 审核单管理
 */
@Service("IAuditService")
public class AuditServiceImpl implements IAuditService {

    private static Logger logger = LoggerFactory.getLogger(AuditServiceImpl.class);

    @Override
    public PagePojo<AuditOrder> getAuditOrderPage(Conditions conn, int pageNo, int pageSize) {
        return BaseDao.dao.queryForListPage(AuditOrder.class,conn,new Sort("create_time", SqlSort.DESC),pageNo,pageSize);
    }

    @Override
    public RecordBean<String> auditOrder(String orderId,Integer auditId, Integer status, String reason) {
        StringBuffer sql = new StringBuffer();
        sql.append("UPDATE audit_order SET reason = ?,status = ? WHERE id = ?");
        int result = BaseDao.dao.update(sql.toString(),reason,status,auditId);
        if (result == 0) {
            return RecordBean.error("审核失败！");
        }
        StringBuffer sql_1 = new StringBuffer();
        if (orderId.contains("PUR")) {
            sql_1.append("UPDATE purchase SET status = ? WHERE pe_id = ?");
        } else {
            sql_1.append("UPDATE sales SET status = ? WHERE s_id = ?");
        }
        result = BaseDao.dao.update(sql_1.toString(),status,orderId);
        if (result == 0) {
            return RecordBean.error("审核失败！");
        }
        return RecordBean.success("审核成功！");
    }
}
