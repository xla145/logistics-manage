package com.logistics.service.warehouse.impl;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.common.Sort;
import cn.assist.easydao.common.SqlSort;
import cn.assist.easydao.dao.BaseDao;
import cn.assist.easydao.pojo.PagePojo;
import com.logistics.base.constant.WarehouseConstant;
import com.logistics.base.utils.CommonUtil;
import com.logistics.base.utils.RecordBean;
import com.logistics.service.vo.Warehouse;
import com.logistics.service.warehouse.IWarehouseService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 仓库管理
 */
@Service("IWarehouseService")
public class WarehouseServiceImpl implements IWarehouseService{

    /**
     * 分页获取仓库信息
     * @param conn
     * @param pageNo 查询页码
     * @param pageSize 每页数量
     * @return
     */
    @Override
    public PagePojo<Warehouse> getWarehousePage(Conditions conn, int pageNo, int pageSize) {
        Sort sort = new Sort("create_time", SqlSort.DESC);
        return BaseDao.dao.queryForListPage(Warehouse.class,conn,sort,pageNo,pageSize);
    }

    /**
     * 获取仓库列表
     * @param conn 查询条件
     * @return
     */
    @Override
    public List<Warehouse> getWarehouseList(Conditions conn) {
        return BaseDao.dao.queryForListEntity(Warehouse.class,conn);
    }

    /**
     * 获取仓库信息
     * @param wid
     * @return
     */
    @Override
    public Warehouse getWarehouse(String wid) {
        return BaseDao.dao.queryForEntity(Warehouse.class,wid);
    }

    /**
     * 添加仓库信息
     * @param warehouse
     * @return
     */
    @Override
    public RecordBean<Warehouse> addWarehouse(Warehouse warehouse) {
        String wid = CommonUtil.getId("WID");
        Date time = new Date();
        warehouse.setWid(wid);
        warehouse.setCreateTime(time);
        warehouse.setUpdateTime(time);
        warehouse.setStatus(WarehouseConstant.PWAREHOUSE_DISABLE);
        int result = BaseDao.dao.insert(warehouse);
        if (result != 1) {
            return RecordBean.error("添加仓库信息失败！");
        }
        return RecordBean.success("添加仓库信息成功！",warehouse);
    }

    /**
     * 更新仓库信息
     * @param warehouse 仓库信息
     * @return
     */
    @Override
    public RecordBean<Warehouse> updateWarehouse(Warehouse warehouse) {
        Date time = new Date();
        warehouse.setUpdateTime(time);
        int result = BaseDao.dao.update(warehouse);
        if (result != 1) {
            return RecordBean.error("修改仓库信息失败！");
        }
        return RecordBean.success("修改仓库信息成功！",warehouse);
    }

    /**
     * 删除仓库信息
     * @param ids 仓库信息
     * @return
     */
    @Override
    public RecordBean<String> delWarehouse(String[] ids) {
        StringBuffer sql = new StringBuffer();
        sql.append("UPDATE warehouse SET status = ? WHERE id IN (");
        sql.append(CommonUtil.arrayToSqlIn(ids));
        sql.append(")");
        int result = BaseDao.dao.update(sql.toString(), WarehouseConstant.WAREHOUSE_DEL);
        if (result < 1) {
            return RecordBean.error("仓库删除失败！");
        }
        return RecordBean.success("仓库删除成功！");
    }

    /**
     * 更新仓库的状态 0：不可用 1：可用
     * @param ids
     * @param status
     * @return
     */
    @Override
    public RecordBean<String> changeStatus(String[] ids, Integer status) {
        StringBuffer sql = new StringBuffer();
        if (status == WarehouseConstant.PWAREHOUSE_ENABLE) {
            status = WarehouseConstant.PWAREHOUSE_DISABLE;
        } else {
            status = WarehouseConstant.PWAREHOUSE_ENABLE;
        }
        sql.append("UPDATE warehouse SET status = ? ,update_time = now() WHERE wid IN (");
        sql.append(CommonUtil.arrayToSqlIn(ids));
        sql.append(")");
        int result = BaseDao.dao.update(sql.toString(), status);
        if (result < 1) {
            return RecordBean.error("更新仓库状态失败！");
        }
        return RecordBean.success("更新仓库状态成功！");
    }
}
