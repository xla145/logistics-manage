package com.logistics.service.activtiy;

import cn.assist.easydao.pojo.PagePojo;
import com.logistics.base.utils.DataObj;
import com.logistics.service.model.ActivityDisplayModel;
import com.logistics.service.vo.activity.ActivityDisplayImgs;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/16/016.
 * 活动图片管理
 */
public interface IActivityDisplayService {

    /**
     *
     * @Title: getProductActivity
     * @Description: TODO(获取好玩活动图片列表)
     * @param @param map
     * @param @param pageNo
     * @param @param pageSize
     * @param @return    参数
     * @return PagePojo<ProductActivity>    返回类型
     * @throws
     */
    public PagePojo<ActivityDisplayModel> getProductActivityPhoto(Map<String,Object> map, int pageNo, int pageSize);

    /**
     *
     * @Title: addProductActivity
     * @Description: TODO(添加活动图片)
     * @param @param productActivity
     * @param @return    参数
     * @return DataObj<ProductActivity>    返回类型
     * @throws
     */
    public DataObj<ActivityDisplayModel> addProductActivityPhoto(ActivityDisplayModel activityDisplayModel);

    /**
     *
     * @Title: delProductActivity
     * @Description: TODO(删除活动图片)
     * @param @param ids
     * @param @return    参数
     * @return DataObj<ProductActivity>    返回类型
     * @throws
     */
    public DataObj<String> delProductActivityPhoto(String[] ids);

    /**
     *
     * @Title: updateProductActivity
     * @Description: TODO(更新活动图片)
     * @param @param productActivity
     * @param @return    参数
     * @return DataObj<ActivityDisplayModel>    返回类型
     * @throws
     */
    public DataObj<ActivityDisplayModel> updateProductActivityPhoto(ActivityDisplayModel activityDisplayModel);
    /**
     *
     * @Title: getProductActivity
     * @Description: TODO(获取好玩活动图片信息通过产品编号)
     * @param @param productId
     * @param @return    参数
     * @return ProductActivityModel    返回类型
     * @throws
     */
    public ActivityDisplayModel getProductActivityPhoto(String id);

    /**
     * 保存产品轮播图
     * */
    public DataObj<String> batchAddProductImgs(List<ActivityDisplayImgs> listImages, String did);

    /**
     * 启用/暂停
     * @param id
     * @param status
     * @return
     */
    public DataObj<String> upOrDownActivtiyDisplay(String[] id,Integer status);

}
