package com.vpu.sql.constant;

/**
 * sql操作
 * @author 卢光耀
 * @date 2020-01-06 14:03
 *
*/
public enum DBOperator {
    DROP("drop"),CREATE("create"),ALTER("alter");


    private String operator;

    DBOperator(String operator){
        this.operator = operator;
    }

    public String getOperator(){
        return this.operator;
    }

    public static DBOperator getDBOperator(String operator){
        for( DBOperator dbOperator: values() ){
            if( dbOperator.getOperator().equals(operator) ){
                return dbOperator;
            }
        }
        return null;
    }
}
