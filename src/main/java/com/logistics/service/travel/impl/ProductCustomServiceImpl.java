package com.logistics.service.travel.impl;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.common.Sort;
import cn.assist.easydao.common.SqlExpr;
import cn.assist.easydao.common.SqlSort;
import cn.assist.easydao.dao.BaseDao;
import cn.assist.easydao.pojo.PagePojo;
import com.alibaba.fastjson.JSON;
import com.logistics.base.utils.CommonUtil;
import com.logistics.base.utils.DataObj;
import com.logistics.base.utils.DateUtil;
import com.logistics.service.travel.IProductCustomService;
import com.logistics.service.vo.travel.TravelCustom;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/29/029.
 * 定制旅游服务实现类
 */
@Service("IProductCustomService")
public class ProductCustomServiceImpl implements IProductCustomService {
    private static Logger logger = LoggerFactory.getLogger(ProductCustomServiceImpl.class);
    private final static Integer DEL_STATUS = -1;
    private final static Integer WAIT_DEAL_STATUS = 5;
    private final static Integer DAELED_STATUS = 15;
    @Override
    public PagePojo<TravelCustom> productTravelCustomPage(Map<String, Object> map, int pageNo, int pageSize) {
        StringBuffer sql = new StringBuffer();
        List<Object> params = new ArrayList<Object>();
        sql.append("SELECT tc.mid,tc.name,tc.contact,tc.create_time,tc.departure,tc.destination,tc.travel_day,tc.travel_number,tc.budget,tc.source,tc.status,tc.plan_time,tc.operation_log ");
        sql.append("FROM travel_custom tc ");
        String createTime = (String) map.get("createTime");
        String name = (String) map.get("name");
        Integer source = (Integer) map.get("source");
        Integer status  = (Integer) map.get("status");
        sql.append("WHERE 1=1 AND status != "+DEL_STATUS+" ");
        if (createTime != null && StringUtils.isNotEmpty(createTime)) {
            sql.append("AND tc.create_time BETWEEN ? AND ? ");
            String[] time = createTime.split(" - ");
            params.add(time[0]);
            params.add(time[1]);
        }
        if (status != null) {
            sql.append("AND tc.status =  ? ");
            params.add(status);
        }
        if (name != null && StringUtils.isNotEmpty(name)) {
            sql.append("AND tc.name like ? ");
            params.add(CommonUtil.queryLike(name));
        }
        if (source != null) {
            sql.append("AND tc.source = ? ");
            params.add(source);
        }
        Sort sort = new Sort("tc.create_time", SqlSort.DESC);
        return BaseDao.dao.queryForListPage(TravelCustom.class,sql.toString(),params,sort,pageNo,pageSize);
    }

    @Override
    public DataObj<TravelCustom> addProductTravelCustom(TravelCustom productTravelCustom) {
        logger.info("添加定制旅游信息"+ JSON.toJSON(productTravelCustom));
        String mid = CommonUtil.getId("CTR");
        productTravelCustom.setCreateTime(new Date());
        productTravelCustom.setUpdateTime(new Date());
        productTravelCustom.setMid(mid);
        productTravelCustom.setUid(-1);
        try {
            int result  = BaseDao.dao.insert(productTravelCustom);
            if (result < 1) {
                return new DataObj<>("添加定制旅游失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("添加旅游定制信息异常"+e.getMessage());
            return new DataObj<>("添加旅游定制信息异常！");
        }
        return DataObj.getSuccessData(productTravelCustom);
    }

    @Override
    public DataObj<String> delProductTravelCustom(String[] ids) {
        logger.info("删除的编号"+JSON.toJSON(ids));
        StringBuffer sql = new StringBuffer();
        sql.append("UPDATE travel_custom SET status = ?,update_time = now() ");
        sql.append("WHERE mid IN (");
        sql.append(CommonUtil.arrayToSqlIn(ids));
        sql.append(")");
        try {
            int result = BaseDao.dao.update(sql.toString(),DEL_STATUS);
            if (result < 1) {
                return new DataObj<>("删除定制旅游信息失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("删除定制旅游信息异常"+e.getMessage());
            return new DataObj<>("删除定制旅游信息异常");
        }
        return DataObj.getSuccessData("成功");
    }

    @Override
    public DataObj<String> dealProductTravelCustom(String[] ids, Integer operationId, String operationName) {
        logger.info("处理的编号"+JSON.toJSON(ids));
        String oper = " 操作员：" + operationId + "(" + operationName + "), 操作时间:" + DateUtil.getDateTimeStr()+"把状态修改为已处理";
        StringBuffer sql = new StringBuffer();
        sql.append("UPDATE travel_custom SET status = ?,update_time = now(),operation_log = CONCAT(`operation_log`, ?) ");
        sql.append("WHERE mid IN (");
        sql.append(CommonUtil.arrayToSqlIn(ids));
        sql.append(")");
        try {
            int result = BaseDao.dao.update(sql.toString(),DAELED_STATUS,oper);
            if (result < 1) {
                return new DataObj<>("修改定制旅游信息状态失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("修改定制旅游信息状态异常"+e.getMessage());
            return new DataObj<>("修改定制旅游信息状态异常");
        }
        return DataObj.getSuccessData("成功");
    }

    @Override
    public DataObj<TravelCustom> updateProductTravelCustom(TravelCustom productTravelCustom) {
        logger.info("修改定制旅游信息"+ JSON.toJSON(productTravelCustom));
        StringBuffer operationLog = new StringBuffer();
        productTravelCustom.setUpdateTime(new Date());
        try {
            TravelCustom productTravelCustom1 = getProductTravelCustom(productTravelCustom.getMid());
            operationLog.append(CommonUtil.nullToBlank(productTravelCustom1.getOperationLog()));
            operationLog.append("\n"+productTravelCustom.getOperationLog());
            productTravelCustom.setOperationLog(operationLog.toString());
            int result  = BaseDao.dao.update(productTravelCustom);
            if (result < 1) {
                return new DataObj<>("修改定制旅游失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("修改旅游定制信息异常"+e.getMessage());
            return new DataObj<>("修改旅游定制信息异常！");
        }
        return DataObj.getSuccessData(productTravelCustom);
    }

    @Override
    public TravelCustom getProductTravelCustom(String mid) {
       return BaseDao.dao.queryForEntity(TravelCustom.class,new Conditions("mid",SqlExpr.EQUAL,mid));
    }
}
