package com.logistics.service.coupon.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.logistics.base.constant.CardConstant;
import com.logistics.base.constant.CouponTypeConstant;
import com.logistics.base.utils.*;
import com.logistics.service.coupon.CouponService;
import com.logistics.service.coupon.ICardService;
import com.logistics.service.model.CardModel;
import com.logistics.service.model.CouponModel;
import com.logistics.service.vo.coupon.Coupon;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.common.Sort;
import cn.assist.easydao.common.SqlSort;
import cn.assist.easydao.dao.BaseDao;
import cn.assist.easydao.pojo.PagePojo;

import com.logistics.service.vo.card.Card;
import com.logistics.service.vo.card.CardCdk;
import com.logistics.service.vo.card.CardCoupon;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * 代金券卡包接口
 *
 * @author caixb
 */
@Service("ICardService")
public class CardServiceImpl implements ICardService {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CouponService couponService;
	
	/**
	 * 分页获取卡包列表
	 * 
	 * @param conn
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PagePojo<CardModel> getCardPage(Conditions conn, int pageNo, int pageSize){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT id,name,status,info,create_time,update_time,CONCAT_WS(' - ',start_time,expire_time) valid_period,remark,coupon_number,total_amount ");
		sql.append("FROM card WHERE 1=1 ");
		List<Object> params = new ArrayList<Object>();
		if(conn != null && StringUtils.isNotBlank(conn.getConnSql())){
			sql.append(" and " + conn.getConnSql());
			params = conn.getConnParams();
		}
		Sort sort = new Sort("create_time", SqlSort.DESC);
		return BaseDao.dao.queryForListPage(CardModel.class,sql.toString(),params, sort, pageNo, pageSize);
	}
	
	/**
	 * 根据id获取卡包信息
	 * 
	 * @param cardId
	 * @return
	 */
	public CardModel getCard(int cardId){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT id,name,status,info,create_time,update_time,CONCAT_WS(' - ',start_time,expire_time) valid_period,remark,coupon_number,total_amount ");
		sql.append("FROM card ");
		sql.append("WHERE id = ?");
		CardModel cardModel = BaseDao.dao.queryForEntity(CardModel.class,sql.toString(),cardId);
		List<CouponModel> couponList = couponService.getCouponListByCardId(cardId);
		for (CouponModel couponModel:couponList) {
			couponModel.setTypeName(CouponTypeConstant.getCouponNameById(couponModel.getType()));
		}
		cardModel.setCouponList(couponList);
		return cardModel;
	}
	/**
	 * 新增卡
	 * 
	 * @param name
	 * @param info
	 * @param expireTime
	 * @param remark
	 * @param couponIds
	 * @return
	 */
	@Transactional
	public boolean addCard(String name,String info, String expireTime, String remark, String[] couponIds){
		try {
			Date date = new Date();
			Card card = new Card();
			card.setName(name);
			card.setInfo(info);
			card.setCreateTime(date);
			card.setUpdateTime(date);
			String[] time = expireTime.split(" - ");
			card.setStartTime(DateUtil.StringToDate(time[0],"yyyy-MM-dd HH:mm:ss"));
			card.setExpireTime(DateUtil.StringToDate(time[1],"yyyy-MM-dd HH:mm:ss"));
			card.setRemark(remark);
			card.setCouponNumber(couponIds.length);
			Double totalAmount = 0.00;
			//创建关联
			if(couponIds != null && couponIds.length > 0){
				List<CardCoupon> list = new ArrayList<CardCoupon>();
				for (int i = 0; i < couponIds.length; i++) {
					Coupon coupon = couponService.getCouponModel(couponIds[i]);
					totalAmount = BigDecimalUtils.add(totalAmount,coupon.getPrice().doubleValue());
				}
			}
			card.setTotalAmount(new BigDecimal(totalAmount));
			int id = BaseDao.dao.insertReturnId(card);
			if(id < 1){
				return false;
			}
			//创建关联
			if(couponIds != null && couponIds.length > 0){
				List<CardCoupon> list = new ArrayList<CardCoupon>();
				for (int i = 0; i < couponIds.length; i++) {
					CardCoupon cc = new CardCoupon();
					cc.setCardId(id);
					cc.setCouponId(couponIds[i]);
					list.add(cc);
				}
				BaseDao.dao.insert(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("添加卡包异常"+e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
		return true;
	}

	@Transactional
	@Override
	public boolean updateCard(Integer id,String name, String info, String expireTime, String remark, String[] couponIds) {
		try {
			Date date = new Date();
			Card card = new Card();
			card.setId(id);
			card.setName(name);
			card.setInfo(info);
			card.setUpdateTime(date);
			String[] time = expireTime.split(" - ");
			card.setStartTime(DateUtil.StringToDate(time[0],"yyyy-MM-dd HH:mm:ss"));
			card.setExpireTime(DateUtil.StringToDate(time[1],"yyyy-MM-dd HH:mm:ss"));
			card.setRemark(remark);
			card.setCouponNumber(couponIds.length);
			Double totalAmount = 0.00;
			//创建关联
			if(couponIds != null && couponIds.length > 0){
				List<CardCoupon> list = new ArrayList<CardCoupon>();
				for (int i = 0; i < couponIds.length; i++) {
					Coupon coupon = couponService.getCouponModel(couponIds[i]);
					totalAmount = BigDecimalUtils.add(totalAmount,coupon.getPrice().doubleValue());
				}
			}
			card.setTotalAmount(new BigDecimal(totalAmount));
			int result = BaseDao.dao.update(card);
			if(result < 1){
				return false;
			}
			BaseDao.dao.update("DELETE FROM card_coupon WHERE card_id = ?",id);
			//创建关联
			if(couponIds != null && couponIds.length > 0){
				List<CardCoupon> list = new ArrayList<CardCoupon>();
				for (int i = 0; i < couponIds.length; i++) {
					CardCoupon cc = new CardCoupon();
					cc.setCardId(id);
					cc.setCouponId(couponIds[i]);
					list.add(cc);
				}
				BaseDao.dao.insert(list);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("修改卡包异常"+e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
	}

	/**
	 * 分页获取卡包激活码列表
	 * 
	 * @param conn
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PagePojo<CardCdk> getCardCdkPage(Conditions conn, int pageNo, int pageSize){
		Sort sort = new Sort("status", SqlSort.DESC);
		return BaseDao.dao.queryForListPage(CardCdk.class, conn, sort, pageNo, pageSize);
	}
	
	/**
	 * 新增卡激活码
	 * 
	 * @param cardId	卡id
	 * @param cardName	卡名
	 * @param count		生成数量
	 * @param batchNo	批次号
	 * @param remark	备注
	 * @return
	 */
	public boolean addCardCdk(int cardId, String cardName, int count, String batchNo, String remark){
		Set<String> set = new HashSet<String>();
		List<CardCdk> list = new ArrayList<CardCdk>();
		createCdk(set, count);
		for (String cdk : set) {
			CardCdk cc = new CardCdk();
			cc.setCardId(cardId);
			cc.setCardName(cardName);
			cc.setCdk(cdk);
			cc.setStatus(CardConstant.CARD_CDK_STATUS_NONACTIVATED);
			cc.setBatchNo(batchNo);
			cc.setCreateTime(new Date());
			cc.setRemark(remark);
			cc.setCardId(cardId);
			list.add(cc);
		}
		int result = BaseDao.dao.insert(list);
		
		return result > 0;
	}

	@Override
	public RecordBean<String> startOrStopCard(String[] ids, Integer status) {
		StringBuffer sql = new StringBuffer();
		status = status == CardConstant.CARD_ENABLE_STATUS? CardConstant.CARD_DISABLE_STATUS: CardConstant.CARD_ENABLE_STATUS;
		sql.append("UPDATE card SET status = ? ,update_time = now() ");
		sql.append("WHERE id IN (");
		sql.append(CommonUtil.arrayToSqlIn(ids));
		sql.append(")");
		try {
			int result = BaseDao.dao.update(sql.toString(),status);
			if (result < 1) {
				return RecordBean.error("启用或停用失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("启用或停用异常！"+e.getMessage());
			return RecordBean.error("启用或停用异常！");
		}
		return RecordBean.success("成功！");
	}

	@Override
	public List<CardCdk> getCardCdkList(Conditions conn) {
		return BaseDao.dao.queryForListEntity(CardCdk.class,conn);
	}

	/**
	 * 
	 * 生成激活码
	 * @param set
	 * @param count
	 */
	private static void createCdk(Set<String> set, int count){
		if(set == null){
			set = new HashSet<String>();
		}
		int total = set.size() + count;
		for (int i = 0; i < count; i++) {
			String cdk = CommonUtil.getCharsRandom(5).toUpperCase()+CommonUtil.getNumberRandom(5);
			set.add(cdk);
		}
		int bad = total - set.size();
		if(bad > 0) {
			createCdk(set, bad);//出现重复的cdk，继续生成剩余的cdk
		}
	}
}
