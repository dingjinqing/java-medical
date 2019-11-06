package com.vpu.mp.service.shop.goods.es;

import com.vpu.mp.config.es.EsSearchConstant;
import com.vpu.mp.service.foundation.es.EsManager;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.goods.es.*;
import com.vpu.mp.service.pojo.shop.goods.es.Operator;
import com.vpu.mp.service.pojo.shop.goods.goods.GoodsPageListParam;
import com.vpu.mp.service.shop.goods.es.convert.EsConvertFactory;
import com.vpu.mp.service.shop.goods.es.convert.param.EsParamConvertInterface;
import com.vpu.mp.service.shop.goods.es.convert.param.GoodsPageConvertEsParam;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.vpu.mp.service.pojo.shop.goods.es.EsSearchName.*;

/**
 *  不可直接调用
 * @author 卢光耀
 * @date 2019/11/5 10:16 上午
 *
*/
@Service
public class EsSearchService extends ShopBaseService {


    private static final EsSearchName[] DEFAULT = {GOODS_ID,SHOP_ID,IS_ON_SALE,GOODS_NUMBER};

    private static final EsSearchName[] GOODS_SEARCH = {
        GOODS_ID,
        SHOP_ID,
        IS_ON_SALE,
        GOODS_NUMBER,
        GOODS_NAME,
        FIRST_CAT_ID,
        FIRST_SORT_ID,
        GOODS_IMG,
        GOODS_SN,
        SHOP_PRICE,
        SOURCE,
        GOODS_TYPE,
        CAT_ID,
        CAT_NAME,
        SORT_ID,
        SORT_NAME,
        BRAND_NAME,
        BRAND_ID,
        GOODS_SALE_NUM,
        GOODS_LABEL,
        PRD_SNS,
        MAX_SPEC_PRD_PRICE,
        MIN_SPEC_PRD_PRICE
    };

    protected static  String[] DEFAULT_STR = new String[DEFAULT.length];
    static{
        DEFAULT_STR = Arrays.stream(DEFAULT)
            .map(EsSearchName::getEsName)
            .collect(Collectors.toList())
            .toArray(new String[DEFAULT.length]);
    }

    protected static  String[] GOODS_SEARCH_STR = new String[GOODS_SEARCH.length];
    static{
        GOODS_SEARCH_STR = Arrays.stream(GOODS_SEARCH)
            .map(EsSearchName::getEsName).collect(Collectors.toList())
            .toArray(new String[GOODS_SEARCH.length]);
    }

    @Autowired
    private EsManager esManager;


    public boolean esState(){
        return esManager.esState();
    }
    /**
     * GoodsPageListParam to EsSearchParam
     * @param param 页面的传参
     * @return es的查询param
     */
    protected EsSearchParam goodsParamConvertEsGoodsParam(GoodsPageListParam param,Integer shopId){
        EsParamConvertInterface convert = EsConvertFactory.getParamConvert(GoodsPageConvertEsParam.class);
        return convert.goodsPageConvert(param,shopId);
    }
    protected SearchResponse getEsGoodsAggregationsByParam(EsSearchParam param) throws Exception {
        if( param.getFactList() == null || param.getFactList().isEmpty() ){
            throw new Exception("elasticSearch search Aggregations... but FactList is null");
        }
        return search(assemblySearchRequest(assemblySearchBuilder(param),
            assemblyTermsAggregationBuilder(param.getFactList())));
    }
    /**
     * 调用esManager的searchResponse
     * @param searchRequest 查询请求
     * @return SearchResponse
     * @throws IOException 连接异常
     */
    private SearchResponse search(SearchRequest searchRequest) throws IOException {
        return esManager.searchResponse(searchRequest);
    }
    /**
     * 封装fact查询builder
     * @param factList 聚合参数{@link EsGoodsConstant.EsGoodsSearchFact}
     * @return List<AggregationBuilder>
     */
    private List<AggregationBuilder> assemblyTermsAggregationBuilder(List<Fact> factList){
        return factList.stream().map(x-> AggregationBuilders.terms(x.getName())
            .field(x.getFieldName())
            //这里查询数量暂时设置为50，有需要再扩展
            .size(50))
            .collect(Collectors.toList());
    }
    /**
     * 封装SearchRequest
     * @param param es对应的查询字段
     * @return SearchRequest
     * @throws IllegalAccessException
     */
    private SearchRequest assemblySearchRequestForAdmin(EsSearchParam param) throws IllegalAccessException {
        return assemblySearchRequest(assemblySearchBuilder(param),null);
    }
    /**
     * admin平台商品搜索
     * @param builder 搜索条件
     * @param aggregations  聚合条件
     * @return SearchResponse
     */
    private SearchRequest assemblySearchRequest(QueryBuilder builder, List<AggregationBuilder> aggregations) {
        //TODO 到时候再考虑索引名称要不要写死
        SearchRequest searchRequest = new SearchRequest("es_goods");
        SearchSourceBuilder sourceBuilder = assemblySearchSourceBuilder(builder,aggregations,DEFAULT_STR);
        return searchRequest.source(sourceBuilder);
    }
    protected SearchSourceBuilder assemblySearchSourceBuilder(QueryBuilder builder,List<AggregationBuilder> aggregations){
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        if( builder != null ){
            sourceBuilder.query(builder);
        }
        if( aggregations != null ){
            aggregations.forEach(sourceBuilder::aggregation);
        }
        return sourceBuilder;
    }
    protected SearchSourceBuilder assemblySearchSourceBuilder(QueryBuilder builder,List<AggregationBuilder> aggregations,String[] array){
        SearchSourceBuilder sourceBuilder = assemblySearchSourceBuilder(builder,aggregations);
        if( array != null  ){
            sourceBuilder.fetchSource(array,null);
        }
        return sourceBuilder;
    }
    protected BoolQueryBuilder assemblySearchBuilder(EsSearchParam param) {
        List<FieldProperty> propertyList = param.getSearchList();
        BoolQueryBuilder resultQueryBuilder = QueryBuilders.boolQuery();
        List<MatchQueryBuilder> matchQueryBuilders = assemblyMatchQueryBuilder(propertyList);
        List<RangeQueryBuilder> rangeQueryBuilders = assemblyRangeQueryBuilder(propertyList);
        if( matchQueryBuilders != null && !matchQueryBuilders.isEmpty() ){
            resultQueryBuilder.must(assemblyDisMaxQueryBuilder(matchQueryBuilders));
        }
        if( rangeQueryBuilders != null && !rangeQueryBuilders.isEmpty() ){
            rangeQueryBuilders.forEach(resultQueryBuilder::filter);
        }

        return resultQueryBuilder;
    }

    /**
     * 封装disMax查询结构
     * @param builderList match查询条件
     * @return DisMaxQueryBuilder
     */
    protected DisMaxQueryBuilder assemblyDisMaxQueryBuilder(List<MatchQueryBuilder> builderList){
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
    protected List<MatchQueryBuilder> assemblyMatchQueryBuilder(List<FieldProperty> propertyList){
        return  propertyList.stream()
            .filter(x->x.getOperator().equals(Operator.EQ))
            .map(x->QueryBuilders.matchQuery(x.getSearchName().getEsName(),x.getValue()))
            .collect(Collectors.toList());
    }
    /**
     * 封装range查询结构
     * @param propertyList es对应的查询字段
     * @return List<MatchQueryBuilder>
     */
    protected List<RangeQueryBuilder> assemblyRangeQueryBuilder(List<FieldProperty> propertyList){
        List<RangeQueryBuilder> resultQueryBuilders = new ArrayList<>(propertyList.size());
        for (FieldProperty x : propertyList) {
            if (!x.getOperator().equals(Operator.EQ)) {
                switch (x.getOperator()) {
                    case GT:
                        resultQueryBuilders.add(
                            QueryBuilders.rangeQuery(x.getSearchName().getEsName()).gt(x.getValue())
                        );
                        break;
                    case GTE:
                        resultQueryBuilders.add(
                            QueryBuilders.rangeQuery(x.getSearchName().getEsName()).gte(x.getValue())
                        );
                        break;
                    case LT:
                        resultQueryBuilders.add(
                            QueryBuilders.rangeQuery(x.getSearchName().getEsName()).lt(x.getValue())
                        );
                        break;
                    case LTE:
                        resultQueryBuilders.add(
                            QueryBuilders.rangeQuery(x.getSearchName().getEsName()).lte(x.getValue())
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
