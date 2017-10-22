package com.logistics.service.model;

import com.logistics.service.vo.activity.ActivityDisplay;
import com.logistics.service.vo.activity.ActivityDisplayImgs;

import java.util.List;

/**
 * Created by Administrator on 2017/8/15/015.
 */
public class ActivityDisplayModel extends ActivityDisplay {

    public List<ActivityDisplayImgs> activityDisplayImgsList;//活动图片

    public String imgJson ;//活动图片json数据

    public List<ActivityDisplayImgs> getActivityDisplayImgsList() {
        return activityDisplayImgsList;
    }

    public void setActivityDisplayImgsList(List<ActivityDisplayImgs> activityDisplayImgsList) {
        this.activityDisplayImgsList = activityDisplayImgsList;
    }

    public String getImgJson() {
        return imgJson;
    }

    public void setImgJson(String imgJson) {
        this.imgJson = imgJson;
    }
}
