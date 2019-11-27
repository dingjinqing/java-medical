package com.vpu.mp.service.pojo.shop.goods.es;

import lombok.Builder;
import lombok.Getter;
import org.elasticsearch.index.query.QueryBuilder;

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

    public FieldProperty(String searchName,Object value){
        this.operator = Operator.EQ;
        this.searchName = searchName;
        this.value = value;
    }
    public FieldProperty(String searchName,Object value,Operator operator){
        this.operator = operator;
        this.searchName = searchName;
        this.value = value;
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

    public void setValue(Object value) {
        this.value = value;
    }
}
