package com.logistics.service.activtiy.impl;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.common.SqlExpr;
import cn.assist.easydao.dao.BaseDao;
import cn.assist.easydao.pojo.PagePojo;
import com.alibaba.fastjson.JSON;
import com.logistics.base.utils.CommonUtil;
import com.logistics.base.utils.ConfigUtil;
import com.logistics.base.utils.DataObj;
import com.logistics.service.model.ActivityDisplayModel;
import com.logistics.service.activtiy.IActivityDisplayService;
import com.logistics.service.vo.activity.ActivityDisplay;
import com.logistics.service.vo.activity.ActivityDisplayImgs;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/16/016.
 * 活动图片管理
 */
@Service("IActivityDisplayService")
public class ActivityDisplayServiceImpl implements IActivityDisplayService {
    private static Logger logger = LoggerFactory.getLogger(ActivityDisplayServiceImpl.class);
    private final static Integer EFFECTIVE_STATUS = 1;
    private final static Integer INVALID_STATUS = 0;

    /**
     * 获取活动图片信息
     * @param map
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public PagePojo<ActivityDisplayModel> getProductActivityPhoto(Map<String, Object> map, int pageNo, int pageSize) {
        StringBuffer sql = new StringBuffer();
        List<Object> params = new ArrayList<Object>();
        sql.append("SELECT ad.id,ad.title,ad.activity_time,ad.addr,ad.author,ad.release_time,ad.number,ad.expire_time,ad.`status`,ad.url ");
        sql.append("FROM activity_display ad ");
        sql.append("WHERE 1 = 1 ");
        String releaseStartTime = (String) map.get("startTime");
        String releaseEndTime = (String) map.get("endTime");
        Integer status  = (Integer) map.get("status");
        if (releaseStartTime != null && StringUtils.isNotEmpty(releaseStartTime) && releaseEndTime == null) {
            sql.append("AND ad.release_time >= ? ");
            params.add(releaseStartTime);
        }
        if (releaseStartTime == null && releaseEndTime != null && StringUtils.isNotEmpty(releaseEndTime)) {
            sql.append("AND ad.release_time <= ? ");
            params.add(releaseEndTime);
        }
        if (releaseStartTime != null && StringUtils.isNotEmpty(releaseStartTime) && releaseEndTime != null && StringUtils.isNotEmpty(releaseEndTime)) {
            sql.append("AND ad.release_time BETWEEN ? AND ? ");
            params.add(releaseStartTime);
            params.add(releaseEndTime);
        }
        if (status != null) {
            sql.append("AND ad.status = ?");
            params.add(status);
        }
        sql.append("ORDER BY ad.create_time DESC");
        return BaseDao.dao.queryForListPage(ActivityDisplayModel.class, sql.toString(), params, null, pageNo, pageSize);
    }

    /**
     * 添加活动图片信息
     * @param activityDisplayModel
     * @return
     */
    @Transactional
    @Override
    public DataObj<ActivityDisplayModel> addProductActivityPhoto(ActivityDisplayModel activityDisplayModel) {
        logger.info("添加活动图片信息"+ JSON.toJSON(activityDisplayModel));
        try{
            ActivityDisplay activityDisplay = new ActivityDisplay();
            String did = CommonUtil.getNumberRandom(6);//生成六位的编号
            activityDisplay.setId(did);
            activityDisplay.setName(activityDisplayModel.getTitle());
            activityDisplay.setTitle(activityDisplayModel.getTitle());
            activityDisplay.setActivityTime(activityDisplayModel.getActivityTime());
            activityDisplay.setAddr(activityDisplayModel.getAddr());
            activityDisplay.setExpireTime(activityDisplayModel.getExpireTime());
            activityDisplay.setAuthor(activityDisplayModel.getAuthor());
            activityDisplay.setOperatorId(activityDisplayModel.getOperatorId());
            activityDisplay.setNumber(activityDisplayModel.getActivityDisplayImgsList().size());
            activityDisplay.setUrl(ConfigUtil.getValue("online.domain")+"/page/historyVisit/"+did);
            activityDisplay.setUpdateTime(new Date());
            activityDisplay.setCreateTime(new Date());
            int result = BaseDao.dao.insert(activityDisplay);
            if (result < 1) {
                return new DataObj<>("添加活动图片信息失败！");
            }
            batchAddProductImgs(activityDisplayModel.activityDisplayImgsList,did);
        } catch (Exception e){
            e.printStackTrace();
            logger.error("添加活动图片信息失败"+e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new DataObj<>("添加活动图片信息失败！");
        }
        return DataObj.getSuccessData(activityDisplayModel);
    }

    @Override
    public DataObj<String> delProductActivityPhoto(String[] ids) {
        return null;
    }

    /**
     * 修改活动图片信息
     * @param activityDisplayModel
     * @return
     */
    @Transactional
    @Override
    public DataObj<ActivityDisplayModel> updateProductActivityPhoto(ActivityDisplayModel activityDisplayModel) {
        logger.info("修改活动图片信息"+ JSON.toJSON(activityDisplayModel));
        try{
            ActivityDisplay activityDisplay = new ActivityDisplay();
            activityDisplay.setId(activityDisplayModel.getId());
            activityDisplay.setName(activityDisplayModel.getTitle());
            activityDisplay.setTitle(activityDisplayModel.getTitle());
            activityDisplay.setActivityTime(activityDisplayModel.getActivityTime());
            activityDisplay.setAddr(activityDisplayModel.getAddr());
            activityDisplay.setExpireTime(activityDisplayModel.getExpireTime());
            activityDisplay.setAuthor(activityDisplayModel.getAuthor());
            activityDisplay.setOperatorId(activityDisplayModel.getOperatorId());
            activityDisplay.setNumber(activityDisplayModel.getActivityDisplayImgsList().size());
            activityDisplay.setUpdateTime(new Date());
            int result = BaseDao.dao.update(activityDisplay);
            if (result < 1) {
                return new DataObj<>("修改活动图片信息失败！");
            }
            batchAddProductImgs(activityDisplayModel.activityDisplayImgsList,activityDisplayModel.getId());
        } catch (Exception e){
            e.printStackTrace();
            logger.error("修改活动图片信息失败"+e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new DataObj<>("修改活动图片信息失败！");
        }
        return DataObj.getSuccessData(activityDisplayModel);
    }

    /**
     * 获取活动图片信息
     * @param id
     * @return
     */
    @Override
    public ActivityDisplayModel getProductActivityPhoto(String id) {
        StringBuffer sql = new StringBuffer();
        List<Object> params = new ArrayList<Object>();
        sql.append("SELECT ad.id,ad.title,ad.activity_time,ad.addr,ad.author,ad.release_time,ad.number,ad.expire_time,ad.`status`,ad.author,ad.url ");
        sql.append("FROM activity_display ad ");
        sql.append("WHERE 1 = 1 AND id = ?");
        ActivityDisplayModel activityDisplayModel = BaseDao.dao.queryForEntity(ActivityDisplayModel.class,sql.toString(),id);
        List<ActivityDisplayImgs> activityDisplayImgsList = BaseDao.dao.queryForListEntity(ActivityDisplayImgs.class,new Conditions("activity_display_id", SqlExpr.EQUAL,id));
        activityDisplayModel.setImgJson(JSON.toJSONString(activityDisplayImgsList));
        return activityDisplayModel;
    }

    /**
     * 上传图片信息
     * @param listImages
     * @param did
     * @return
     */
    @Override
    public DataObj<String> batchAddProductImgs(List<ActivityDisplayImgs> listImages, String did) {
        BaseDao.dao.update("DELETE FROM activity_display_imgs WHERE activity_display_id = ?",did);
        StringBuffer sql = new StringBuffer();
        List<Object> parmas =new ArrayList<Object>();
        sql.append("INSERT INTO activity_display_imgs(activity_display_id,url) VALUES");
        for(ActivityDisplayImgs pImage:listImages){
            sql.append("(?,?),");
            parmas.add(did);
            parmas.add(pImage.getUrl());
        }
        int result = BaseDao.dao.insert(sql.deleteCharAt(sql.length()-1).toString(), parmas.toArray());
        if (result > 1) {
            return DataObj.getSuccessData("插入图片成功！");
        }
        return new DataObj<>("上传图片失败！");
    }

    /**
     * 发布活动图片链接
     * @param id
     * @param status
     * @return
     */
    @Override
    public DataObj<String> upOrDownActivtiyDisplay(String[] id, Integer status) {
        StringBuffer sql  = new StringBuffer();
        sql.append("UPDATE activity_display set status = ? ,update_time = now() ");
        if (status == INVALID_STATUS) {
           sql.append(" ,release_time = now() ") ;
        }
        sql.append("WHERE id in (");
        sql.append(CommonUtil.arrayToSqlIn(id));
        sql.append(")");
        status = status == INVALID_STATUS?EFFECTIVE_STATUS:INVALID_STATUS;
        int result = BaseDao.dao.update(sql.toString(),status);
        if (result < 1) {
            return new DataObj<>("发布失败！");
        }
        return DataObj.getSuccessData("");
    }
}
