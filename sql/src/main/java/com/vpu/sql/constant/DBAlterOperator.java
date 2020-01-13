package com.vpu.sql.constant;

public enum DBAlterOperator {

    CHANGE("change"),

    MODIFY("modify"),

    ADD("add"),

    DROP("drop");


    private String operator;

    DBAlterOperator(String operator){
        this.operator = operator;
    }

    public String getOperator(){
        return this.operator;
    }
}
