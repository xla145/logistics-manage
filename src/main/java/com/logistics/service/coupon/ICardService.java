package com.logistics.service.coupon;

import java.util.Date;
import java.util.List;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.pojo.PagePojo;

import com.logistics.base.utils.RecordBean;
import com.logistics.service.model.CardModel;
import com.logistics.service.vo.card.Card;
import com.logistics.service.vo.card.CardCdk;

/**
 * 代金券卡包接口
 *
 * @author caixb
 */
public interface ICardService {

	/**
	 * 分页获取卡包列表
	 * 
	 * @param card
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PagePojo<CardModel> getCardPage(Conditions conn, int pageNo, int pageSize);
	
	/**
	 * 根据id获取卡包信息
	 * 
	 * @param cardId
	 * @return
	 */
	public CardModel getCard(int cardId);
	
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
	public boolean addCard(String name,String info, String expireTime, String remark, String[] couponIds);

	/**
	 * 修改增卡
	 *
	 * @param name
	 * @param info
	 * @param expireTime
	 * @param remark
	 * @param couponIds
	 * @return
	 */
	public boolean updateCard(Integer id,String name,String info, String expireTime, String remark, String[] couponIds);


	/**
	 * 分页获取卡包列表
	 * @param conn
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PagePojo<CardCdk> getCardCdkPage(Conditions conn, int pageNo, int pageSize);
	
	
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
	public boolean addCardCdk(int cardId, String cardName, int count, String batchNo, String remark);


	/**
	 *
	 * @param ids
	 * @param status
	 * @return
	 */
	public RecordBean<String> startOrStopCard(String[] ids , Integer status);

	/**
	 * 获取卡包列表
	 *
	 * @param conn
	 * @return
	 */
	public List<CardCdk> getCardCdkList(Conditions conn);
	
}
