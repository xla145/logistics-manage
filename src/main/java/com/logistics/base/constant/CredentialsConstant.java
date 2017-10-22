package com.logistics.base.constant;

/**
 * 
* @ClassName: OrderTravelStatusConstant
* @Description: TODO(证件信息)
* @author Administrator
* @date 2017年7月19日
*
 */
public enum CredentialsConstant {

	PASSPORT(1,"护照",9),ID_CARD(2,"身份证",18),MACAO_PASS_HONGKONG(3,"港澳通行证",9),MACAO_PASS_TAIWAN(4,"台湾通行证",9);

	private Integer id;
	private String name;
	private Integer number;// 证件号码位数

	private CredentialsConstant(Integer id, String name,Integer number) {
		this.id = id;
		this.name = name;
		this.number = number;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}
}
