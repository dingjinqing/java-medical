package com.vpu.mp.service.foundation.es.handler;

import com.google.common.collect.Lists;
import com.vpu.mp.service.pojo.shop.goods.es.FieldProperty;
import com.vpu.mp.service.pojo.shop.goods.es.Operator;
import org.elasticsearch.index.query.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ElasticSearch QueryBuilder Handler
 * @author 卢光耀
 * @date 2019/11/12 4:14 下午
 *
*/
@Component
public class EsQueryBuilderHandler {
    /**
     * 查询语句结构bool->match/range
     * @param propertyList 查询条件
     * @return {@link BoolQueryBuilder}
     */
    public BoolQueryBuilder assemblySearchBuilder(List<FieldProperty> propertyList ) {
        BoolQueryBuilder resultQueryBuilder = QueryBuilders.boolQuery();
        List<TermQueryBuilder> termQueryBuilders = Lists.newArrayList();
        List<MatchQueryBuilder> matchQueryBuilders = Lists.newArrayList();
        List<RangeQueryBuilder> rangeQueryBuilders = Lists.newArrayList();
        for( FieldProperty x: propertyList ) {
            //elasticSearch查询优化,对filter query进行cache
            if( "shop_id".equals(x.getSearchName()) ){
                resultQueryBuilder.filter(QueryBuilders.termQuery(x.getSearchName(),x.getValue()));
                continue;
            }

            if( x.getOperator().equals(Operator.EQ) ){
                termQueryBuilders.add(QueryBuilders.termQuery(x.getSearchName(),x.getValue()));
            }else if( x.getOperator().equals(Operator.SIM) ){
                matchQueryBuilders.add(QueryBuilders.matchQuery(x.getSearchName(),x.getValue()));
            }else if( x.getOperator().equals(Operator.LT) ){
                rangeQueryBuilders.add(QueryBuilders.rangeQuery(x.getSearchName()).lt(x.getValue()));
            }else if( x.getOperator().equals(Operator.LTE) ){
                rangeQueryBuilders.add(QueryBuilders.rangeQuery(x.getSearchName()).lte(x.getValue()));
            }else if( x.getOperator().equals(Operator.GT) ){
                rangeQueryBuilders.add(QueryBuilders.rangeQuery(x.getSearchName()).gt(x.getValue()));
            }else if( x.getOperator().equals(Operator.GTE) ){
                rangeQueryBuilders.add(QueryBuilders.rangeQuery(x.getSearchName()).gte(x.getValue()));
            }
        }


        if(!matchQueryBuilders.isEmpty()){
            matchQueryBuilders.forEach(resultQueryBuilder::must);
        }
        if(!termQueryBuilders.isEmpty()){
            termQueryBuilders.forEach(resultQueryBuilder::must);
        }
        if(!rangeQueryBuilders.isEmpty()){
            rangeQueryBuilders.forEach(resultQueryBuilder::must);
        }

        return resultQueryBuilder;
    }
    /**
     * 封装disMax查询结构
     * @param builderList match查询条件
     * @return DisMaxQueryBuilder
     */
    private DisMaxQueryBuilder assemblyDisMaxQueryBuilder(List<MatchQueryBuilder> builderList){
        DisMaxQueryBuilder disMaxQueryBuilder = QueryBuilders.disMaxQuery();
        if( builderList != null && !builderList.isEmpty()){
            builderList.forEach(disMaxQueryBuilder::add);
        }
        return disMaxQueryBuilder;
    }

    /**
     * 封装match查询结构
     * @param propertyList es对应的查询字段
     * @return List<MatchQueryBuilder>
     */
    private List<MatchQueryBuilder> assemblyMatchQueryBuilder(List<FieldProperty> propertyList){
        return  propertyList.stream()
            .filter(x->x.getOperator().equals(Operator.EQ))
            .map(x->QueryBuilders.matchQuery(x.getSearchName(),x.getValue()))
            .collect(Collectors.toList());
    }
    /**
     * 封装range查询结构
     * @param propertyList es对应的查询字段
     * @return List<MatchQueryBuilder>
     */
    private List<RangeQueryBuilder> assemblyRangeQueryBuilder(List<FieldProperty> propertyList){
        List<RangeQueryBuilder> resultQueryBuilders = new ArrayList<>(propertyList.size());
        for (FieldProperty x : propertyList) {
            if (!x.getOperator().equals(Operator.EQ)) {
                switch (x.getOperator()) {
                    case GT:
                        resultQueryBuilders.add(
                            QueryBuilders.rangeQuery(x.getSearchName()).gt(x.getValue())
                        );
                        break;
                    case GTE:
                        resultQueryBuilders.add(
                            QueryBuilders.rangeQuery(x.getSearchName()).gte(x.getValue())
                        );
                        break;
                    case LT:
                        resultQueryBuilders.add(
                            QueryBuilders.rangeQuery(x.getSearchName()).lt(x.getValue())
                        );
                        break;
                    case LTE:
                        resultQueryBuilders.add(
                            QueryBuilders.rangeQuery(x.getSearchName()).lte(x.getValue())
                        );
                        break;
                    default:
                        break;
                }
            }
        }
        return  resultQueryBuilders;
    }
}
