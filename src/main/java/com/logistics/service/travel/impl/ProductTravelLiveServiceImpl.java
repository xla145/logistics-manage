package com.logistics.service.travel.impl;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.common.Sort;
import cn.assist.easydao.common.SqlExpr;
import cn.assist.easydao.common.SqlSort;
import cn.assist.easydao.dao.BaseDao;
import cn.assist.easydao.pojo.PagePojo;
import com.alibaba.fastjson.JSON;
import com.logistics.base.constant.ProductConstant;
import com.logistics.base.constant.ProductGroupTypeConstant;
import com.logistics.base.constant.TravelTrafficConstant;
import com.logistics.base.utils.CommonUtil;
import com.logistics.base.utils.DataObj;
import com.logistics.base.utils.DateUtil;
import com.logistics.base.utils.RecordBean;
import com.logistics.service.product.ProductService;
import com.logistics.service.model.ProductTravelModel;
import com.logistics.service.supplier.SupplierService;
import com.logistics.service.travel.IProductTravelLiveService;
import com.logistics.service.vo.Supplier;
import com.logistics.service.vo.product.Product;
import com.logistics.service.vo.product.ProductImage;
import com.logistics.service.vo.product.ProductLove;
import com.logistics.service.vo.travel.ProductTravel;
import com.logistics.service.vo.travel.ProductTravelArrange;
import com.logistics.service.vo.travel.ProductTravelSummary;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/23/023.
 */
@Service("IProductTravelLiveService")
public class ProductTravelLiveServiceImpl implements IProductTravelLiveService{

    @Autowired
    private ProductService productService;

    @Autowired
    private SupplierService supplierService;

    public static Logger logger = LoggerFactory.getLogger(ProductTravelLiveServiceImpl.class);
    /**
     * 获取旅游产品数据
     * @param map      the map
     * @param pageNo   the page no
     * @param pageSize the page size
     * @return
     */
    @Override
    public PagePojo<ProductTravelModel> productTravelPage(Map<String, Object> map, int pageNo, int pageSize) {
        StringBuffer sql = new StringBuffer();
        List<Object> params = new ArrayList<Object>();
        sql.append("SELECT p.pid,p.title,p.supplier_name,p.release_time,p.label,p.weight,p.price,p.status,SUM(pl.fever) want_number,pt.depart_time ");
        sql.append("FROM product p JOIN product_travel pt ON(p.pid = pt.pid) LEFT JOIN product_love pl ON(p.pid = pl.pid) ");
        String releaseTime = (String) map.get("releaseTime");
        String pid = (String) map.get("pid");
        Integer supplierId = (Integer) map.get("supplierId");
        Integer status  = (Integer) map.get("status");
        sql.append("WHERE 1=1 AND p.status != -1 ");
        sql.append("AND cat_id = "+ProductConstant.TRAVEL_LIVE+" ");
        if (releaseTime != null && StringUtils.isNotEmpty(releaseTime)) {
            sql.append("AND p.release_time BETWEEN ? AND ? ");
            String[] time = releaseTime.split(" - ");
            params.add(time[0]);
            params.add(time[1]);
        }
        if (status != null) {
            sql.append("AND p.status =  ? ");
            params.add(status);
        }
        if (pid != null) {
            sql.append("AND p.pid = ? ");
            params.add(pid);
        }
        if (supplierId != null) {
            sql.append("AND p.supplier_id = ? ");
            params.add(supplierId);
        }
        sql.append("GROUP BY p.pid ");
        Sort sort = new Sort("p.create_time", SqlSort.DESC);
        return BaseDao.dao.queryForListPage(ProductTravelModel.class,sql.toString(),params,sort,pageNo,pageSize);
    }

    /**
     * 添加旅游产品信息
     * @param productTravelModel
     * @return
     */
    @Transactional
    @Override
    public RecordBean<String> addProductTravel(ProductTravelModel productTravelModel) {
        logger.info("添加旅游信息"+ JSON.toJSON(productTravelModel));
        try {
            Product product = new Product();
            //获取供应商的
            if (productTravelModel.getSupplierId() != null) {
                Supplier supplier = supplierService.getSupplier(productTravelModel.getSupplierId());
                product.setSupplierId(productTravelModel.getSupplierId());
                product.setSupplierName(productTravelModel.getSupplierName());
            }
            product.setPid(productTravelModel.getPid());
            product.setCatId(ProductConstant.TRAVEL_LIVE);
            product.setName(productTravelModel.getTitle());
            product.setTitle(productTravelModel.getTitle());
            product.setSubTitle(productTravelModel.getSubTitle());
            product.setPrice(productTravelModel.getPrice());
            if (productTravelModel.getOriginalPrice() == null) {//如果没有原价，商品的售价等于原价
                product.setOriginalPrice(productTravelModel.getPrice());
            } else {
                product.setOriginalPrice(productTravelModel.getOriginalPrice());
            }
            product.setLabel(productTravelModel.getLabel());
            product.setProductGroupName(ProductGroupTypeConstant.TRAVEL.getName());
            product.setProductGroupType(ProductGroupTypeConstant.TRAVEL.getId());
            product.setReleaseTime(new Date());
            product.setCreateTime(new Date());
            product.setUpdateTime(new Date());
            product.setStock(10000);//默认库存为无限大
            product.setWeight(productTravelModel.getWeight());
            product.setStatus(ProductConstant.PRODUCT_STATUS_INITIAL);
            product.setInfo(CommonUtil.blankToDefault(productTravelModel.getInfo()));
            product.setImageUrl(productTravelModel.getImageUrl());
            List<Object> paramList = new ArrayList<Object>();
            String sql = CommonUtil.insertDuplicate(product,paramList);
            int result = BaseDao.dao.insert(sql,paramList.toArray());
            if (result < 1) {
                return RecordBean.error("添加旅游产品信息失败！");
            }
            ProductTravel productTravel = new ProductTravel();
            productTravel.setPid(productTravelModel.getPid());
            productTravel.setDepartTime(productTravelModel.getDepartTime());
            productTravel.setSuggestTime(productTravelModel.getSuggestTime());
            productTravel.setStroke(productTravelModel.getStroke());
            productTravel.setWithCost(productTravelModel.getWithCost());
            productTravel.setWithoutCost(productTravelModel.getWithoutCost());
            productTravel.setBookings(productTravelModel.getBookings());
            productTravel.setDestination(productTravelModel.getDestination());
            productTravel.setTravelDay(productTravelModel.getProductTravelArranges().size());
            productTravel.setTrafficMode(productTravelModel.getTrafficMode());
            productTravel.setTrafficModeName(TravelTrafficConstant.getNameById(productTravelModel.getTrafficMode()));
            productTravel.setDeparture(productTravelModel.getDeparture());
            productTravel.setTravelStatus(ProductConstant.PRODUCT_STATUS_INITIAL);
            productTravel.setRemark(productTravelModel.getRemark());
            List<Object> travelParamList = new ArrayList<Object>();
            String travelSql = CommonUtil.insertDuplicate(productTravel,travelParamList);
            int num = BaseDao.dao.insert(travelSql,travelParamList.toArray());
            if (num < 1) {
                return RecordBean.error("添加旅游产品信息失败！");
            }
            if (productTravelModel.getWantNumber() != null) {
                ProductLove productLove = new ProductLove();
                productLove.setPid(productTravelModel.getPid());
                productLove.setFever(productTravelModel.getWantNumber());
                productLove.setUid(-1);
                productLove.setIp("0.0.0.0");
                productLove.setCreateTime(new Date());
                productLove.setRemark("后台添加（想去的基数）");
                List<Object> loveParamList = new ArrayList<Object>();
                String loveSql = CommonUtil.insertDuplicate(productLove,loveParamList);
                BaseDao.dao.insert(loveSql,loveParamList.toArray());
            }
            if (productTravelModel.getProductImages() != null && productTravelModel.getProductImages().size() > 0){
                productService.batchAddProductImgs(productTravelModel.getProductImages(),productTravelModel.getPid());//添加轮播图
            }
            if (productTravelModel.getProductTravelArranges() != null && productTravelModel.getProductTravelArranges().size() > 0){
                batchAddProductTravelArrange(productTravelModel.getProductTravelArranges(),productTravelModel.getPid());//添加行程安排
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("添加旅游产品失败"+e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return RecordBean.error("添加旅游产品失败！");
        }
        return RecordBean.success("添加旅游产品成功！");
    }

    /**
     * 删除旅游产品信息
     * @param ids the ids
     * @return
     */
    @Override
    public DataObj<String> delProductTravel(String[] ids) {
        StringBuffer sql = new StringBuffer();
        sql.append("UPDATE product SET status =? ,update_time = now() WHERE pid = ?");
        int result = BaseDao.dao.update(sql.toString(),ProductConstant.PRODUCT_STATUS_DEL,ids[0]);
        if (result < 1) {
            return new DataObj<>("删除失败！");
        }
        return DataObj.getSuccessData("成功！");
    }

    /**
     * 更新旅游产品信息
     * @param productTravelModel the product goods model
     * @return
     */
    @Override
    public DataObj<ProductTravelModel> updateProductTravel(ProductTravelModel productTravelModel) {
        logger.info("修改旅游信息"+ JSON.toJSON(productTravelModel));
        try {
            Product product = new Product();
            //获取供应商的
            if (productTravelModel.getSupplierId() != null) {
                Supplier supplier = supplierService.getSupplier(productTravelModel.getSupplierId());
                product.setSupplierId(productTravelModel.getSupplierId());
                product.setSupplierName(productTravelModel.getSupplierName());
            }
            product.setPid(productTravelModel.getPid());
            product.setName(productTravelModel.getTitle());
            product.setTitle(productTravelModel.getTitle());
            product.setSubTitle(productTravelModel.getSubTitle());
            product.setPrice(productTravelModel.getPrice());
            product.setOriginalPrice(productTravelModel.getOriginalPrice());
            product.setLabel(productTravelModel.getLabel());
            product.setUpdateTime(new Date());
            product.setSupplierId(productTravelModel.getSupplierId());
            product.setSupplierName(productTravelModel.getSupplierName());
            product.setWeight(productTravelModel.getWeight());
            product.setInfo(CommonUtil.blankToDefault(productTravelModel.getInfo()));
            product.setImageUrl(productTravelModel.getImageUrl());
            int result = BaseDao.dao.update(product);
            if (result < 1) {
                return new DataObj<>("修改旅游产品信息失败！");
            }
            StringBuffer productTravelSql= new StringBuffer();
            productTravelSql.append("UPDATE product_travel SET destination = ?,departure =? ,depart_time =?,traffic_mode=?,traffic_mode_name =?,suggest_time=?,stroke=?,travel_day=?,with_cost=?,without_cost=?,bookings=?,remark=? WHERE pid =?");
            int num = BaseDao.dao.update(productTravelSql.toString(),productTravelModel.getDestination(),productTravelModel.getDeparture(),productTravelModel.getDepartTime(),productTravelModel.getTrafficMode(),TravelTrafficConstant.getNameById(productTravelModel.getTrafficMode()),
                    productTravelModel.getSuggestTime(),productTravelModel.getStroke(),productTravelModel.getProductTravelArranges().size(),productTravelModel.getWithCost(),productTravelModel.getWithoutCost(),productTravelModel.getBookings(),productTravelModel.getRemark(),productTravelModel.getPid());
            if (num < 1) {
                return new DataObj<>("修改旅游产品信息失败！");
            }
            if (productTravelModel.getWantNumber() != null) {
                ProductLove productLove = new ProductLove();
                productLove.setPid(productTravelModel.getPid());
                productLove.setFever(productTravelModel.getWantNumber());
                productLove.setUid(-1);
                productLove.setIp("0.0.0.0");
                productLove.setCreateTime(new Date());
                productLove.setRemark("后台添加（想去的基数）");
                List<Object> loveParamList = new ArrayList<Object>();
                String loveSql = CommonUtil.insertDuplicate(productLove,loveParamList);
                BaseDao.dao.insert(loveSql,loveParamList.toArray());
            }
            productService.batchAddProductImgs(productTravelModel.getProductImages(),productTravelModel.getPid());//添加轮播图
            if (productTravelModel.getProductTravelArranges() != null && productTravelModel.getProductTravelArranges().size() > 0){
                batchAddProductTravelArrange(productTravelModel.getProductTravelArranges(),productTravelModel.getPid());//添加行程安排
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("添加旅游产品失败"+e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new DataObj<>("添加旅游产品失败！");
        }
        return DataObj.getSuccessData(productTravelModel);
    }

    /**
     * 获取旅游产品对象
     * @param productId the product id
     * @return
     */
    @Override
    public ProductTravelModel getProductTravel(String productId) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT p.pid,p.name,p.product_group_type,p.sub_title,p.title,p.supplier_id,p.supplier_name,p.release_time,p.label,p.weight,p.price,p.original_price,p.`status`,p.info,p.image_url" +
                ",pt.departure,pt.destination,pt.depart_time,pt.suggest_time,pt.traffic_mode,pt.stroke,pt.with_cost,pt.without_cost,pt.bookings,pt.remark,pt.travel_day ");
        sql.append("FROM product p JOIN product_travel pt ON(p.pid = pt.pid) ");
        sql.append("WHERE p.pid = ?");
        ProductTravelModel productTravelModel = BaseDao.dao.queryForEntity(ProductTravelModel.class,sql.toString(),productId);
        productTravelModel.setDepartTime(CommonUtil.arrayStampToString(productTravelModel.getDepartTime()));
        List<ProductTravelArrange> productTravelArrangeList = BaseDao.dao.queryForListEntity(ProductTravelArrange.class,new Conditions("pid", SqlExpr.EQUAL,productId));
        productTravelModel.setProductTravelArranges(productTravelArrangeList);
        List<ProductImage> productImageList = BaseDao.dao.queryForListEntity(ProductImage.class,new Conditions("pid", SqlExpr.EQUAL,productId));
        productTravelModel.setProductImages(productImageList);
        StringBuffer loveSql = new StringBuffer();
        loveSql.append("SELECT fever FROM product_love WHERE pid = ? AND uid = -1");
        ProductLove productLove = BaseDao.dao.queryForEntity(ProductLove.class,loveSql.toString(),productId);//返回想要的人数
        if (productLove != null) {
            productTravelModel.setWantNumber(productLove.getFever());
        }
        return productTravelModel;
    }

    @Override
    public DataObj<String> upOrDownProductTravel(String[] ids, Integer status) {
        StringBuffer sql = new StringBuffer();
        sql.append("UPDATE product SET status =? ,update_time = now() WHERE pid = ?");
        status = status == ProductConstant.PRODUCT_STATUS_ONOFFER?ProductConstant.PRODUCT_STATUS_ADMINOUT:ProductConstant.PRODUCT_STATUS_ONOFFER;
        int result = BaseDao.dao.update(sql.toString(),status,ids[0]);
        if (result < 1) {
            return new DataObj<>("上下架失败！");
        }
        return DataObj.getSuccessData("成功！");
    }

    @Override
    public PagePojo<Supplier> supplierPage(Map<String, Object> map, int pageNo, int pageSize) {
        return null;
    }

    /**
     * 批量添加行程安排信息
     * @param productTravelArrangeList
     * @param productId
     * @return
     */
    @Override
    public DataObj<String> batchAddProductTravelArrange(List<ProductTravelArrange> productTravelArrangeList, String productId) {
        BaseDao.dao.update("DELETE FROM product_travel_arrange WHERE pid = ?",productId);
        StringBuffer sql = new StringBuffer();
        List<Object> parmas =new ArrayList<Object>();
        sql.append("INSERT INTO product_travel_arrange(pid,title,dining_name,accommodation,traffic_mode,traffic_mode_name,content) VALUES ");
        for(ProductTravelArrange productTravelArrange:productTravelArrangeList){
            sql.append("(?,?,?,?,?,?,?),");
            parmas.add(productId);
            parmas.add(productTravelArrange.getTitle());
            if (StringUtils.isNotBlank(productTravelArrange.getDiningName())) {
                parmas.add(productTravelArrange.getDiningName());
            } else {
                parmas.add("敬请自理");
            }
            parmas.add(productTravelArrange.getAccommodation());
            parmas.add(productTravelArrange.getTrafficMode());
            if (productTravelArrange.getTrafficMode() != null) {
                parmas.add(TravelTrafficConstant.getNameById(productTravelArrange.getTrafficMode()));
            } else {
                parmas.add("");
            }
            parmas.add(productTravelArrange.getContent());
        }
        int result = BaseDao.dao.insert(sql.deleteCharAt(sql.length()-1).toString(), parmas.toArray());
        if (result < 1) {
            return new DataObj<>("添加行程安排失败！");
        }
        return DataObj.getSuccessData("添加行程安排成功！");
    }

    /**
     * 获取有效的旅游产品
     * 
     * @return
     */
	@Override
	public List<ProductTravelSummary> getTravelSummaryList(Integer status) {
		StringBuffer sql = new StringBuffer("select ");
		sql.append(" a.pid,");
		sql.append(" a.name,");
		sql.append(" a.title,");
		sql.append(" a.sub_title,");
		sql.append(" a.price,");
		sql.append(" a.original_price,");
		
		sql.append(" b.without_cost,");
		sql.append(" b.travel_day,");
		sql.append(" b.with_cost,");
		sql.append(" b.destination,");
		sql.append(" b.depart_time,");
        sql.append(" b.suggest_time,");
		sql.append(" b.travel_status,");
		sql.append(" b.departure,");
		sql.append(" b.traffic_mode");
		
		sql.append(" from product as a inner join product_travel as b ");
		sql.append(" on a.pid = b.pid ");
		sql.append("WHERE 1=1 ");
		sql.append("AND a.cat_id = "+ProductConstant.TRAVEL_LIVE+" ");
		List<Object> params = new ArrayList<Object>();
		if (status != null) {
            sql.append("AND a.status = ?");
            params.add(status);
        }
        return  BaseDao.dao.queryForListEntity(ProductTravelSummary.class, sql.toString(),params);
	}

    @Override
    public RecordBean<String> copyProduct(String pid) {
        ProductTravelModel productTravelModel = getProductTravel(pid);
        pid = "YL-" + DateUtil.formatDate(new Date(), "YYMM") + CommonUtil.getNumberRandom(4);
        productTravelModel.setPid(pid);
        productTravelModel.setTitle("【复制的商品】"+productTravelModel.getTitle());
        if (!StringUtils.isBlank(productTravelModel.getDepartTime())) {
            String departTime = CommonUtil.arrayTimeToString(productTravelModel.getDepartTime());
            productTravelModel.setDepartTime(departTime);
        }
        return addProductTravel(productTravelModel);
    }
}
