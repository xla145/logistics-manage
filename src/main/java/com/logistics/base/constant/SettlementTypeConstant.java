package com.logistics.base.constant;

/**
 * 供应商结算方式
 * */
public enum SettlementTypeConstant {
	
	DAY(1,"日结算"), MONTH(2,"月结算"), YEAR(3,"半年结算"), MATERIAL(3,"单笔结算");
	
	private Integer id;
	
	private String name;
	
	
    private SettlementTypeConstant(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

    
	public Integer getId() {
		return id;
	}


	public void setType(Integer id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
}
