package com.vpu.sql.constant;

/**
 * @author luguangyao
 */
public enum  Scope {
    /**
     * 主库，店铺库
     */
    main("main"),shop("shop");



    private String name;
    public String getName() {
        return name;
    }
    Scope(String name){
        this.name = name;
    }


}
