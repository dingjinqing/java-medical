package com.vpu.mp.service.shop.goods.es.goods.label;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.vpu.mp.service.foundation.es.EsAggregationName;
import com.vpu.mp.service.foundation.es.EsManager;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.goods.es.EsLabelName;
import com.vpu.mp.service.pojo.shop.goods.label.GoodsLabelCoupleTypeEnum;
import com.vpu.mp.service.shop.goods.es.EsBaseSearchService;
import com.vpu.mp.service.shop.goods.es.goods.EsGoodsConstant;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.ParsedTopHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Goods Label Search Service
 * @author luguangyao
 * @date 2019/11/27 11:07 am
 *
*/
@Service
@Slf4j
public class EsGoodsLabelSearchService extends EsBaseSearchService {




    /**
     *  Search goods label by ElasticSearch
     * @param goodsIds goods id array
     * @param type  {@link EsGoodsConstant#GOODS_DETAIL_PAGE} page source
     * @return Map K-> goods id V->List {@link EsGoodsLabel}
     * @throws IOException connection error
     */
    public Map<Integer, List<EsGoodsLabel>> getGoodsLabelByGoodsId(List<Integer> goodsIds,Byte type) throws IOException {
        Integer shopId = getShopId();
        SearchRequest searchRequest = assemblySearchRequestByGoodsId(shopId,goodsIds,type);
        SearchResponse response = search(searchRequest);
        return getGoodsLabelForGoods(response);
    }

    /**
     * get goods number group by label id
     * @param labelIds label id
     * @return Map<Integer,Integer>
     * @throws IOException connection error
     */
    public Map<Integer,Integer> getLabelForGoodsNumber(List<Integer> labelIds) throws IOException {
        Integer shopId = getShopId();
        SearchRequest searchRequest = assemblySearchRequestByLabelId(shopId,labelIds);
        SearchResponse response = search(searchRequest);
        return getLabelForGoodsNumbers(response);
    }

    /**
     * get goodsLabel by count
     *
     * @return List<EsGoodsLabel>
     */
    public List<EsGoodsLabel> getLabelForAllGoods() throws IOException {
        SearchRequest searchRequest = assemblySearchRequestForCount();
        SearchResponse response = search(searchRequest);
        return getAllLabelInfos(response);
    }

    private List<EsGoodsLabel> getAllLabelInfos(SearchResponse response) {
        List<EsGoodsLabel> list= Lists.newArrayList();
        Aggregations aggregations = response.getAggregations();
        Terms goodsAggregation = aggregations.get(EsLabelName.ID);
        goodsAggregation.getBuckets().forEach(x->
            assemblyEsGoodsLabels(list, x.getAggregations())
        );
        return list;
    }

    private void assemblyEsGoodsLabels(List<EsGoodsLabel> list, Aggregations aggregations) {
        ParsedTopHits topHits = aggregations.get(EsAggregationName.LABEL_NAME);
        if( topHits != null ){
            for( SearchHit hit:topHits.getHits().getHits() ){
                String labelStr = hit.getSourceAsString();
                EsGoodsLabel esGoodsLabel = Util.parseJson(labelStr,EsGoodsLabel.class, EsManager.ES_FILED_SERIALIZER);
                list.add(esGoodsLabel);
            }
        }
    }


    /**
     * SearchResponse convert
     * @param response SearchResponse
     * @return Map<Integer,Integer>
     */
    private Map<Integer,Integer> getLabelForGoodsNumbers(SearchResponse response){
        Map<Integer,Integer> map = Maps.newHashMap();
        Aggregations aggregations = response.getAggregations();
        Terms goodsAggregation = aggregations.get(EsLabelName.ID);
        for (Terms.Bucket x : goodsAggregation.getBuckets()) {
            Integer key = Integer.valueOf(x.getKey().toString());
            map.put(key, Math.toIntExact(x.getDocCount()));
        }
        return map;
    }
    /**
     * SearchResponse convert
     * @param response SearchResponse
     * @return Map<Integer,List<EsGoodsLabel>>
     */
    private Map<Integer,List<EsGoodsLabel>> getGoodsLabelForGoods(SearchResponse response){
        Map<Integer,List<EsGoodsLabel>> map = Maps.newHashMap();
        Aggregations aggregations = response.getAggregations();
        Terms goodsAggregation = aggregations.get(EsLabelName.GOODS_ID);
        goodsAggregation.getBuckets().forEach(x->{
            Integer key = Integer.valueOf(x.getKey().toString());
            List<EsGoodsLabel> values= Lists.newArrayList();
            assemblyEsGoodsLabels(values, x.getAggregations());
            map.put(key,values);
        });
        return map;
    }

    /**
     * assembly SearchRequest by goodsIds
     *
     * @param shopId shop id
     * @param goodsIds goods id
     * @param type {@link EsGoodsConstant#GOODS_DETAIL_PAGE} page source
     * @return {@link SearchRequest}
     */
    private SearchRequest assemblySearchRequestByGoodsId(Integer shopId,List<Integer> goodsIds,Byte type){
        SearchRequest searchRequest = new SearchRequest(EsGoodsConstant.LABEL_INDEX_NAME);
        SearchSourceBuilder searchSourceBuilder = SearchSourceBuilder.searchSource();
        searchSourceBuilder.query(assemblyQueryBuilder(shopId,null,goodsIds,type));
        searchSourceBuilder.aggregation(assemblyLabelAggregationBuilderByGoodsId());
        //not need to return query data
        searchSourceBuilder.size(0);
        searchRequest.source(searchSourceBuilder);
        return searchRequest;
    }
    /**
     * assembly SearchRequest by labelId
     *
     * @param shopId shop id
     * @param labelIds goods id
     * @return {@link SearchRequest}
     */
    private SearchRequest assemblySearchRequestByLabelId(Integer shopId,List<Integer> labelIds){
        SearchRequest searchRequest = new SearchRequest(EsGoodsConstant.LABEL_INDEX_NAME);
        SearchSourceBuilder searchSourceBuilder = SearchSourceBuilder.searchSource();
        searchSourceBuilder.query(assemblyQueryBuilder(shopId,labelIds,null,null));
        searchSourceBuilder.aggregation(assemblyAggregationBuilderByLabelId());
        //not need to return query data
        searchSourceBuilder.size(0);
        searchRequest.source(searchSourceBuilder);
        return searchRequest;
    }
    /**
     * assembly SearchRequest for count goods
     *
     * @return {@link SearchRequest}
     */
    private SearchRequest assemblySearchRequestForCount(){
        SearchRequest searchRequest = new SearchRequest(EsGoodsConstant.LABEL_INDEX_NAME);
        SearchSourceBuilder searchSourceBuilder = SearchSourceBuilder.searchSource().
            query(QueryBuilders.termQuery(EsLabelName.SHOP_ID,getShopId())).
            aggregation(assemblyLabelAggregationBuilderByCount());
        //not need to return query data
        searchSourceBuilder.size(0);
        searchRequest.source(searchSourceBuilder);
        return searchRequest;
    }
    /**
     * assembly AggregationBuilder by labelId
     *
     * @return {@link AggregationBuilder}
     */
    private AggregationBuilder assemblyAggregationBuilderByLabelId(){
        return AggregationBuilders.terms(EsLabelName.ID)
            .field(EsLabelName.ID);
    }

    /**
     * assembly QueryBuilder
     *
     * @return {@link QueryBuilder}
     */
    private QueryBuilder assemblyQueryBuilder(Integer shopId,List<Integer> labelIds,List<Integer> goodsIds,Byte type){
        BoolQueryBuilder bool = QueryBuilders.boolQuery();
        bool.must(QueryBuilders.termQuery(EsLabelName.SHOP_ID,shopId));
        if( goodsIds != null && !goodsIds.isEmpty()){
            bool.must(QueryBuilders.termsQuery(EsLabelName.GOODS_ID,goodsIds));
        }
        if( labelIds != null && !labelIds.isEmpty()){
            bool.must(QueryBuilders.termsQuery(EsLabelName.ID,labelIds));
        }
        if( EsGoodsConstant.GOODS_DETAIL_PAGE.equals(type) ){
            bool.must(QueryBuilders.termQuery(EsLabelName.DETAIL_SHOW,Boolean.TRUE));
        }else if( EsGoodsConstant.GOODS_LIST_PAGE.equals(type) ){
            bool.must(QueryBuilders.termQuery(EsLabelName.LIST_SHOW,Boolean.TRUE));
        }else if( EsGoodsConstant.GOODS_SEARCH_PAGE.equals(type) ){
            bool.must(QueryBuilders.termQuery(EsLabelName.SEARCH_SHOW,Boolean.TRUE));
        }else if( EsGoodsConstant.ADMIN_GOODS_LIST_PAGE.equals(type) ){
            bool.must(QueryBuilders.termQuery(EsLabelName.TYPE, GoodsLabelCoupleTypeEnum.GOODSTYPE.getCode()));
        }
        return bool;
    }
}
