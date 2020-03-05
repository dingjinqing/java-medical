package com.vpu.mp.service.foundation.es.handler;

import com.google.common.collect.Lists;
import com.vpu.mp.service.pojo.shop.goods.es.EsSearchName;
import com.vpu.mp.service.pojo.shop.goods.es.FieldProperty;
import com.vpu.mp.service.pojo.shop.goods.es.Operator;
import org.apache.commons.collections4.CollectionUtils;
import org.elasticsearch.index.query.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
     * 查询语句结构bool->must->match/range
     * @param propertyList 查询条件
     * @return {@link BoolQueryBuilder}
     */
    public BoolQueryBuilder assemblySearchBuilder(List<FieldProperty> propertyList ) {
        BoolQueryBuilder resultQueryBuilder = QueryBuilders.boolQuery();

        AllQueryBuilders queryBuilderArrays = new AllQueryBuilders();

        for( FieldProperty x: propertyList ) {
            //elasticSearch查询优化,对filter query进行cache
            if( "shop_id".equals(x.getSearchName()) ){
                resultQueryBuilder.filter(QueryBuilders.termQuery(x.getSearchName(),x.getValue()));
                continue;
            }
            //关键字查询单独处理
            if(EsSearchName.KEY_WORDS.equals(x.getSearchName())){
                resultQueryBuilder.should(QueryBuilders.matchQuery(EsSearchName.GOODS_NAME,x.getValue()));
                resultQueryBuilder.should(QueryBuilders.termQuery(EsSearchName.BRAND_NAME,x.getValue()));
                resultQueryBuilder.should(QueryBuilders.termQuery(EsSearchName.SORT_NAME,x.getValue()));
                resultQueryBuilder.should(QueryBuilders.termQuery(EsSearchName.CAT_NAME,x.getValue()));

                continue;
            }
            queryBuilderArrays.addToQueryBuilders(x.isMust(),getQueryBuilderByProperty(x));

        }
        queryBuilderArrays.queryShouldBuilders.getAll().forEach(x-> {
            x.forEach(resultQueryBuilder::should);
        });
        queryBuilderArrays.queryMustBuilders.getAll().forEach(x-> {
            x.forEach(resultQueryBuilder::must);
        });
        if( !CollectionUtils.isEmpty(resultQueryBuilder.should()) ){
            resultQueryBuilder.minimumShouldMatch(1);
        }
        return resultQueryBuilder;
    }


    private QueryBuilder getQueryBuilderByProperty(FieldProperty fp){
        Objects.requireNonNull(fp);
        if( fp.getOperator().equals(Operator.EQ) ){
            if ( fp.isUseFullQuery() ){
                return QueryBuilders.termsQuery(fp.getSearchName(),(List<?>)fp.getValue());
            }else{
                return QueryBuilders.termQuery(fp.getSearchName(),fp.getValue());
            }
        }
        if( fp.getOperator().equals(Operator.SIM) ){
            return  QueryBuilders.matchQuery(fp.getSearchName(),fp.getValue());
        }
        if( fp.getOperator().equals(Operator.LT) ){
            return QueryBuilders.rangeQuery(fp.getSearchName()).lt(fp.getValue());
        }
        if( fp.getOperator().equals(Operator.LTE) ){
            return QueryBuilders.rangeQuery(fp.getSearchName()).lte(fp.getValue());
        }
        if( fp.getOperator().equals(Operator.GT) ){
            return QueryBuilders.rangeQuery(fp.getSearchName()).gt(fp.getValue());
        }
        if( fp.getOperator().equals(Operator.GTE) ){
            return QueryBuilders.rangeQuery(fp.getSearchName()).gte(fp.getValue());
        }
        return null;
    }

    private static class QueryMustBuilders extends QueryBuilderArrays {

    }
    private static class QueryShouldBuilders extends QueryBuilderArrays{

    }
    public static class AllQueryBuilders{
        private QueryMustBuilders queryMustBuilders;

        private QueryShouldBuilders queryShouldBuilders;

        AllQueryBuilders(){
            queryMustBuilders = new QueryMustBuilders();
            queryShouldBuilders = new QueryShouldBuilders();
        }
        void addToQueryBuilders(Boolean isMust, QueryBuilder queryBuilder){
            QueryBuilderArrays arrays;
            if( isMust ){
                arrays = queryMustBuilders;
            }else{
                arrays = queryShouldBuilders;
            }
            if( queryBuilder instanceof TermsQueryBuilder ){
                arrays.getTermsQueryBuilders().add((TermsQueryBuilder)queryBuilder);
            }
            if( queryBuilder instanceof TermQueryBuilder ){
                arrays.getTermQueryBuilders().add((TermQueryBuilder)queryBuilder);
            }
            if( queryBuilder instanceof RangeQueryBuilder ){
                arrays.getRangeQueryBuilders().add((RangeQueryBuilder)queryBuilder);
            }
            if( queryBuilder instanceof MatchQueryBuilder ){
                arrays.getMatchQueryBuilders().add((MatchQueryBuilder)queryBuilder);
            }
        }
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
