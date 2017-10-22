package com.logistics.base.constant;

/**
 * Created by Administrator on 2017/8/30/030.
 */
public enum TravelTrafficConstant {
    PLANE(1,"飞机"),STEAMER(2,"轮船"),CAR(3,"汽车"),TRAIN(4,"火车"),ONESELF(5,"自理");

    private Integer id ;
    private String name;

    TravelTrafficConstant(Integer id, String name){
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
        StringBuffer names = new StringBuffer();
        if (ids == null || ids.length() == 0) {
            return "";
        }
        for (String id:ids.split(",")){
            for (TravelTrafficConstant travelConstant: TravelTrafficConstant.values()){
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
