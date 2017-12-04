package com.logistics.service.puchase.impl;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.common.Sort;
import cn.assist.easydao.common.SqlExpr;
import cn.assist.easydao.common.SqlSort;
import cn.assist.easydao.dao.BaseDao;
import cn.assist.easydao.pojo.PagePojo;
import com.alibaba.fastjson.JSON;
import com.yuelinghui.base.constant.VipActivityConstant;
import com.yuelinghui.base.utils.CommonUtil;
import com.yuelinghui.base.utils.DateUtil;
import com.yuelinghui.base.utils.RecordBean;
import com.yuelinghui.service.model.VipActivityModel;
import com.yuelinghui.service.product.api.IProductCategoryService;
import com.yuelinghui.service.product.vo.CategorySummary;
import com.yuelinghui.service.vipActivity.IVipActivityService;
import com.yuelinghui.service.vo.VipActivity;
import com.yuelinghui.service.vo.VipActivityProduct;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;

/**
 * 会员活动管理
 */
@Service("IVipActivityService")
public class PuchaseServiceImpl implements IVipActivityService{

    private static Logger logger = LoggerFactory.getLogger(PuchaseServiceImpl.class);
    @Autowired
    private IProductCategoryService iProductCategoryService;
    /**
     * 获取会员活动信息
     * @param map
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public PagePojo<VipActivityModel> getVipActivityPage(Map<String, Object> map, int pageNo, int pageSize) {
        StringBuffer sql = new StringBuffer();
        List<Object> params = new ArrayList<Object>();
        sql.append("SELECT va.id,va.title,va.vip,va.vip_name,va.status,CONCAT_WS ('~',va.start_time,va.end_time) activity_time,va.release_time,va.operator_id,va.operator_name,count(vap.pid) product_num ");
        sql.append("FROM vip_activity va LEFT JOIN vip_activity_product vap ON (va.id = vap.activity_id)");
        sql.append("WHERE 1 = 1 AND status != "+ VipActivityConstant.VIP_ACTIVITY_STATUS_DEL+" ");
        String activityTime = (String) map.get("activityTime");
        String releaseTime = (String) map.get("releaseTime");
        String title = (String) map.get("title");
        Integer status  = (Integer) map.get("status");
        if (releaseTime != null && StringUtils.isNotEmpty(releaseTime)) {
            String[] time = releaseTime.split(" - ");
            sql.append("AND va.release_time BETWEEN ? AND ? ");
            params.add(time[0]);
            params.add(time[1]);
        }
        if (activityTime != null && StringUtils.isNotEmpty(activityTime)) {
            String[] time = activityTime.split(" - ");
            sql.append("AND (start_time <= ? AND end_time >= ?) ");
            sql.append("OR ((start_time BETWEEN ? AND ?) OR (end_time BETWEEN ? AND ?)) ");
            params.add(time[0]);
            params.add(time[1]);
            params.add(time[0]);
            params.add(time[1]);
            params.add(time[0]);
            params.add(time[1]);
        }
        if (status != null) {
            sql.append("AND va.status =  ? ");
            params.add(status);
        }
        if (title != null && StringUtils.isNotEmpty(title)) {
            sql.append("AND va.title like ? ");
            params.add(CommonUtil.queryLike(title));
        }
        sql.append("GROUP BY va.id ");
        Sort sort = new Sort("va.create_time", SqlSort.DESC);
        return BaseDao.dao.queryForListPage(VipActivityModel.class, sql.toString(), params, sort, pageNo, pageSize);
    }

    /**
     * 获取活动的商品信息
     * @param conn
     * @return
     */
    @Override
    public List<VipActivity> getVipActivityList(Conditions conn) {
        return BaseDao.dao.queryForListEntity(VipActivity.class,conn);
    }

    /**
     * 添加会员活动信息
     * @param vipActivityModel
     * @return
     */
    @Transactional
    @Override
    public RecordBean<VipActivityModel> addVipActivityModel(VipActivityModel vipActivityModel) {
        logger.info("会员活动信息【"+ JSON.toJSON(vipActivityModel));
        try {
            VipActivity vipActivity = new VipActivity();
            String activityTime = vipActivityModel.getActivityTime();
            String[] time = activityTime.split(" - ");
            if (time != null && time.length > 0) {
                vipActivity.setStartTime(DateUtil.stringToDate(time[0],"yyyy-MM-dd HH:mm:ss"));
                vipActivity.setEndTime(DateUtil.stringToDate(time[1],"yyyy-MM-dd HH:mm:ss"));
            }
            vipActivity.setTitle(vipActivityModel.getTitle());
            vipActivity.setStatus(VipActivityConstant.VIP_ACTIVITY_STATUS_INITIAL);
            vipActivity.setVip(vipActivityModel.getVip());
            vipActivity.setVipName(vipActivityModel.getVipName());
            vipActivity.setReleaseTime(new Date());
            vipActivity.setCreateTime(new Date());
            vipActivity.setUpdateTime(new Date());
            vipActivity.setOperatorId(vipActivityModel.getOperatorId());
            vipActivity.setOperatorName(vipActivityModel.getOperatorName());
            int activityId = BaseDao.dao.insertReturnId(vipActivity);
            if (activityId < 0) {
                return RecordBean.error("添加会员活动失败！");
            }
            List<VipActivityProduct> vipActivityProducts = vipActivityModel.getVipActivityProductList();
            batchAddVipActivityProduct(vipActivityProducts,activityId);
        } catch (Exception e) {
            e.printStackTrace();
           logger.error("添加会员活动异常！原因是【"+e.getMessage()+"】");
           TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
           return RecordBean.error("添加会员活动异常！");
        }
        return RecordBean.success("添加会员活动成功！",vipActivityModel);
    }

    /**
     * 更新会员活动信息
     * @param vipActivityModel
     * @return
     */
    @Transactional
    @Override
    public RecordBean<VipActivityModel> updateVipActivityModel(VipActivityModel vipActivityModel) {
        logger.info("修改会员活动信息【" + JSON.toJSON(vipActivityModel));
        try {
            VipActivity vipActivity = new VipActivity();
            String activityTime = vipActivityModel.getActivityTime();
            String[] time = activityTime.split(" - ");
            if (time != null && time.length > 0) {
                vipActivity.setStartTime(DateUtil.stringToDate(time[0], "yyyy-MM-dd HH:mm:ss"));
                vipActivity.setEndTime(DateUtil.stringToDate(time[1], "yyyy-MM-dd HH:mm:ss"));
            }
            vipActivity.setId(vipActivityModel.getId());
            vipActivity.setTitle(vipActivityModel.getTitle());
            vipActivity.setVip(vipActivityModel.getVip());
            vipActivity.setVipName(vipActivityModel.getVipName());
            vipActivity.setUpdateTime(new Date());
            vipActivity.setOperatorId(vipActivityModel.getOperatorId());
            vipActivity.setOperatorName(vipActivityModel.getOperatorName());
            int result = BaseDao.dao.update(vipActivity);
            if (result < 1) {
                return RecordBean.error("修改会员活动失败！");
            }
            List<VipActivityProduct> vipActivityProducts = vipActivityModel.getVipActivityProductList();
            RecordBean<String> recordBean = batchAddVipActivityProduct(vipActivityProducts, vipActivityModel.getId());
            if (!recordBean.isSuccessCode()) {
                return RecordBean.error(recordBean.getMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("修改会员活动异常！原因是【" + e.getMessage() + "】");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return RecordBean.error("修改会员活动异常！");
        }
        return RecordBean.success("修改会员活动成功！",vipActivityModel);
    }
    /**
     * 获取catId下的所有子类（包括自己）的产品类型
     * @param catId
     * @return
     */
    @Override
    public Integer[] getProductCategory(Integer catId) {
        return iProductCategoryService.getValidSubclass(catId);
    }

    @Override
    public List<CategorySummary> getProductCategoryList(Integer catId) {
        return iProductCategoryService.getProductCategoryListByValid(catId);
    }

    /**
     * 批量添加活动商品
     * @param vipActivityProductList
     * @param activityId
     * @return
     */
    @Override
    public RecordBean<String> batchAddVipActivityProduct(List<VipActivityProduct> vipActivityProductList, Integer activityId) {
        BaseDao.dao.update("DELETE FROM vip_activity_product WHERE activity_id = ?",activityId);
        List<Map<String, Object>> activityProductList = getAllProductModelInEnableActivity();
        List<VipActivityProduct> vipActivityProducts = new ArrayList<VipActivityProduct>();
        vipActivityProducts.addAll(vipActivityProductList);
        System.out.println(JSON.toJSON(vipActivityProducts));
        StringBuffer pids = new StringBuffer();
        Iterator<VipActivityProduct> iterator = vipActivityProducts.iterator();
        while(iterator.hasNext()){
            VipActivityProduct vipActivityProduct = iterator.next();
            for (Map<String, Object> map:activityProductList) { // 如果添加的商品在有效的活动中，则不添加
                if (map.get("pid").equals(vipActivityProduct.getPid())){
                    pids.append(map.get("pid"));
                    iterator.remove();
                    break;
                }
            }
        }
        if (vipActivityProductList.size() > 0 && vipActivityProducts.size() == 0) {
            return RecordBean.error("添加活动商品信息失败！原因商品存在于其他活动中 pid ->"+pids.toString());
        }
        if (vipActivityProducts != null && vipActivityProducts.size() > 0) {
            StringBuffer sql = new StringBuffer();
            List<Object> params =new ArrayList<Object>();
            sql.append("INSERT INTO vip_activity_product(activity_id,pid,product_title,activity_price,product_original_price,product_price,cat_id,cat_name,create_time,update_time) VALUES");
            for (VipActivityProduct vipActivityProduct:vipActivityProducts){
                sql.append("(?,?,?,?,?,?,?,?,now(),now()),");
                params.add(activityId);
                params.add(vipActivityProduct.getPid());
                params.add(vipActivityProduct.getProductTitle());
                params.add(vipActivityProduct.getActivityPrice());
                params.add(vipActivityProduct.getProductOriginalPrice());
                params.add(vipActivityProduct.getProductPrice());
                params.add(vipActivityProduct.getCatId());
                params.add(vipActivityProduct.getCatName());
            }
            int result = BaseDao.dao.insert(sql.deleteCharAt(sql.length()-1).toString(), params.toArray());
            if (result < 1) {
                return RecordBean.error("添加活动商品信息失败！");
            }
        }
        return RecordBean.success("添加活动商品信息成功！");
    }

    /**
     * 获取活动下的所有商品
     * @param conn
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public PagePojo<VipActivityProduct> getVipActivityProductPage(Conditions conn, int pageNo, int pageSize) {
        Sort sort = new Sort("create_time",SqlSort.DESC);
        return BaseDao.dao.queryForListPage(VipActivityProduct.class,conn,sort,pageNo,pageSize);
    }

    /**
     * 获取活动下的所有商品
     * @param conn
     * @return
     */
    @Override
    public List<VipActivityProduct> getVipActivityProductList(Conditions conn) {
        return BaseDao.dao.queryForListEntity(VipActivityProduct.class,conn);
    }

    /**
     * 获取活动信息
     * @param activityId
     * @return
     */
    @Override
    public VipActivityModel getVipActivityModel(Integer activityId) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT va.id,va.title,va.vip,va.vip_name,va.status,CONCAT_WS (' - ',va.start_time,va.end_time) activity_time,va.release_time,va.operator_id,va.operator_name ");
        sql.append("FROM vip_activity va ");
        sql.append("WHERE va.id = ?");
        VipActivityModel vipActivityModel = BaseDao.dao.queryForEntity(VipActivityModel.class,sql.toString(),activityId);
        Conditions conditions = new Conditions("activity_id", SqlExpr.EQUAL,activityId);
        PagePojo<VipActivityProduct> activityProductPagePojo = getVipActivityProductPage(conditions,1,20000);
        vipActivityModel.setProductNum(activityProductPagePojo.getTotal());
        return vipActivityModel;
    }

    @Override
    public RecordBean<String> changeStatus(String[] activityId, Integer status) {
        StringBuffer sql = new StringBuffer();
        status = status == VipActivityConstant.VIP_ACTIVITY_STATUS_EANBLE? VipActivityConstant.VIP_ACTIVITY_STATUS_DISABLE: VipActivityConstant.VIP_ACTIVITY_STATUS_EANBLE;
        sql.append("UPDATE vip_activity SET status = ? ,update_time = now() ");
        sql.append("WHERE id IN (");
        sql.append(CommonUtil.arrayToSqlIn(activityId));
        sql.append(")");
        try {
            int result = BaseDao.dao.update(sql.toString(),status);
            if (result < 1) {
                return RecordBean.error("会员活动启用或停用失败！");
            }
        } catch (Exception e) {
            logger.error("会员活动启用或停用异常！"+e.getMessage());
            return RecordBean.error("会员活动启用或停用异常！");
        }
        return RecordBean.success("会员活动启用或停用成功！");
    }

    /**
     * 获取有效时间内的活动的所有商品
     * @return
     */
    List<Map<String, Object>> getAllProductModelInEnableActivity() {
        Date now = new Date();
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT va.id,vap.pid ");
        sql.append("FROM vip_activity va JOIN vip_activity_product vap ON (va.id = vap.activity_id) ");
        sql.append("WHERE 1 = 1 AND status != "+ VipActivityConstant.VIP_ACTIVITY_STATUS_DEL+" ");
        sql.append("AND (va.start_time <= ? AND va.end_time >= ?)");
        return BaseDao.dao.queryForListMap(sql.toString(),now,now);
    }
}
