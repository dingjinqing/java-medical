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

    /**
     * 此参数是否是必须匹配,默认是必须
     */
    private boolean isMust = Boolean.TRUE;

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

    public boolean isMust() {
        return isMust;
    }

    public void setMust(boolean must) {
        isMust = must;
    }

    public void setValue(Object value) {
        this.value = value;
        this.useFullQuery = value instanceof List;
    }


}
