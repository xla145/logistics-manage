package com.logistics.base.constant;

/**
 * 产品类型
 * */
public enum ProductGroupTypeConstant {
	
	ACTIVITY(1,"活动","activity_index"),GOODS(2,"好物","goods_index"),TRAVEL(3,"旅游","travel_index");
	
	private Integer id;
	
	private String name;
	
	private String index;

	private ProductGroupTypeConstant(Integer id, String name, String index) {
		this.id = id;
		this.name = name;
		this.index = index;
	}

	private ProductGroupTypeConstant() {
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

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}
	/**
	 * 获取产品类型
	 * @param id
	 * */
	public static ProductGroupTypeConstant getProductTypeById(Integer id){
		ProductGroupTypeConstant[] type = ProductGroupTypeConstant.values();
		for(ProductGroupTypeConstant t:type) {
			if(id == t.getId()) {
				return t;
			}
		}
		return null;
	}
	
}
