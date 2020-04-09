package com.vpu.sql.constant;

public enum  Scope {

    main("main"),shop("shop");



    private String name;
    public String getName() {
        return name;
    }
    Scope(String name){
        this.name = name;
    }


}
