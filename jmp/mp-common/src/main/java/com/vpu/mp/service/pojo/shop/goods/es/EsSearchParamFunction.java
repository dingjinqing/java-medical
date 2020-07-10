package com.vpu.mp.service.pojo.shop.goods.es;


import org.elasticsearch.index.query.QueryBuilder;

import java.util.List;

@FunctionalInterface
public interface EsSearchParamFunction {

    QueryBuilder buildQueryBuilder(List<FieldProperty> searchList);


}
