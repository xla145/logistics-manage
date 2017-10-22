package com.logistics.base.constant;

/**
 * Created by Administrator on 2017/8/30/030.
 */
public enum TravelDiningConstant {
    BREAKFAST(1,"早餐"),LUNCH(2,"午餐"),DINNER(3,"晚餐");

    private Integer id ;
    private String name;

    TravelDiningConstant(Integer id, String name){
        this.id = id;
        this.name = name;
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

    /**
     * 根据id获取
     * @param ids
     * @return
     */
    public static String getNameById(String ids){
        if (ids == null || ids.length() == 0) {
            return "";
        }
        StringBuffer names = new StringBuffer();
        for (String id:ids.split(",")){
            for (TravelDiningConstant travelConstant: TravelDiningConstant.values()){
                if (travelConstant.getId() == Integer.valueOf(id)) {
                    names.append(travelConstant.getName());
                    names.append(",");
                }
            }
        }
        if (names.length() == 0){
            return "";
        }
        return names.deleteCharAt(names.length()-1).toString();
    }
    public static void main(String[] args) {
        System.out.println(getNameById("1,2,3"));
    }
}
