package com.vpu.mp.service.pojo.shop.goods.es;

import lombok.Builder;
import lombok.Getter;
import org.elasticsearch.index.query.QueryBuilder;

import java.util.List;

/**
 *
 * @author 卢光耀
 * @date 2019/11/5 5:08 下午
 *
*/
public class FieldProperty {

    private String searchName;

    private Operator operator;

    private Object value;

    private boolean useFullQuery;

    public FieldProperty(String searchName,Object value){
        this.operator = Operator.EQ;
        this.searchName = searchName;
        this.value = value;
        this.useFullQuery = value instanceof List;
    }
    public FieldProperty(String searchName,Object value,Operator operator){
        this.operator = operator;
        this.searchName = searchName;
        this.value = value;
        this.useFullQuery = value instanceof List;
    }

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public Object getValue() {
        return value;
    }

    public boolean isUseFullQuery() {
        return useFullQuery;
    }

    public void setValue(Object value) {
        this.value = value;
        this.useFullQuery = value instanceof List;
    }


}
