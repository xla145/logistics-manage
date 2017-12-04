
package com.logistics.service.vo;

import cn.assist.easydao.annotation.Id;

import cn.assist.easydao.annotation.Temporary;

import cn.assist.easydao.pojo.BasePojo;

/** product_access_log
	ID	INT(11)
	PID	VARCHAR(30)
	PRODUCT_NUM	INT(11)
	CAT_ID	INT(11)
	W_ID	VARCHAR(255)
	SYMBOL	TINYINT(2)
	OPERATOR_ID	INT(11)
	OPERATOR_NAME	VARCHAR(20)
	REMARK	VARCHAR(255)
*/
public class ProductAccessLog extends BasePojo {
	@Temporary
	private static final long serialVersionUID = 1L;
	@Id
	private Integer id;
	private String pid;
	private Integer productNum;
	private Integer catId;
	private String wid;
	private Integer symbol;
	private Integer operatorId;
	private String operatorName;
	private String remark;

	public void setId(Integer id){
		this.id=id;
	}
	public Integer getId(){
		return id;
	}
	public void setPid(String pid){
		this.pid=pid;
	}
	public String getPid(){
		return pid;
	}
	public void setProductNum(Integer productNum){
		this.productNum=productNum;
	}
	public Integer getProductNum(){
		return productNum;
	}
	public void setCatId(Integer catId){
		this.catId=catId;
	}
	public Integer getCatId(){
		return catId;
	}
	public Integer getSymbol() {
		return symbol;
	}

	public void setSymbol(Integer symbol) {
		this.symbol = symbol;
	}

	public void setOperatorId(Integer operatorId){
		this.operatorId=operatorId;
	}
	public Integer getOperatorId(){
		return operatorId;
	}
	public void setOperatorName(String operatorName){
		this.operatorName=operatorName;
	}
	public String getOperatorName(){
		return operatorName;
	}
	public void setRemark(String remark){
		this.remark=remark;
	}
	public String getRemark(){
		return remark;
	}

	public String getWid() {
		return wid;
	}

	public void setWid(String wid) {
		this.wid = wid;
	}
}

