package com.vpu.mp.service.shop.goods.es;

import com.vpu.mp.service.foundation.es.EsManager;
import com.vpu.mp.service.pojo.saas.category.SysCatevo;
import com.vpu.mp.service.pojo.shop.goods.es.EsSearchParam;
import com.vpu.mp.service.pojo.shop.goods.es.Fact;
import com.vpu.mp.service.pojo.shop.goods.goods.GoodsPageListParam;
import com.vpu.mp.service.pojo.shop.goods.sort.Sort;
import com.vpu.mp.service.saas.categroy.SysCatServiceHelper;
import com.vpu.mp.service.shop.goods.GoodsSortService;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class EsGoodsSearchService {

    @Autowired
    private EsManager esManager;
    @Autowired
    private GoodsSortService goodsSortService;

    public void getEsGoodsByFilter(){

    }

    public void test() throws IOException {
        EsSearchParam param = new EsSearchParam();
//        List<Fact> list = new ArrayList<>();
//        Fact fact = new Fact();
//        fact.setFieldName("brand");
//        fact.setName("brand");
//        Fact fact1 = new Fact();
//        fact1.setFieldName("first_cat_id");
//        fact1.setName("first_cat_id");
//        list.add(fact1);
//        Fact fact2 = new Fact();
//        fact2.setFieldName("second_cat_id");
//        fact2.setName("second_cat_id");
//        list.add(fact2);
//        param.setFactList(list);
        Aggregations s = getEsGoodsAggregationsByParam(param);
        Terms t = s.get(EsGoodsConstant.EsGoodsSearchFact.GOODS_CATEGORY_FIRST_FACT);

    }

    /**
     * GoodsPageListParam to EsSearchParam
     * @param goodsPageListParam 页面的穿参
     * @return es的查询param
     */
    public EsSearchParam goodsParamConvertEsGoodsParam(GoodsPageListParam goodsPageListParam){
        EsSearchParam  searchParam = new EsSearchParam();
        List<Fact> factList = new ArrayList<>();
        factList.add(getFactByName(EsGoodsConstant.EsGoodsSearchFact.GOODS_CATEGORY_FIRST_FACT));
        factList.add(getFactByName(EsGoodsConstant.EsGoodsSearchFact.GOODS_CATEGORY_SECOND_FACT));
        factList.add(getFactByName(EsGoodsConstant.EsGoodsSearchFact.GOODS_CATEGORY_THIRD_FACT));
        factList.add(getFactByName(EsGoodsConstant.EsGoodsSearchFact.GOODS_SORT_FIRST_FACT));
        factList.add(getFactByName(EsGoodsConstant.EsGoodsSearchFact.GOODS_SORT_SECOND_FACT));
        searchParam.setFactList(factList);
        return searchParam;
    }
    private Fact getFactByName(String name){
        return Fact.builder().name(name)
            .fieldName(name)
            .build();
    }

    public Aggregations getEsGoodsAggregationsByParam(EsSearchParam param) throws IOException {
        SearchResponse response = getAggregationsByParam(param);
        if( null == response ){
            return null;
        }
        return response.getAggregations();
    }

    private SearchResponse getAggregationsByParam(EsSearchParam param) throws IOException {
        //TODO 到时候再考虑索引名称要不要写死
        SearchRequest searchRequest = new SearchRequest("es_goods");
        List<Fact> factList = param.getFactList();
        if( null == factList || factList.isEmpty() ){
            return null;
        }
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        factList.forEach(x->{
            TermsAggregationBuilder aggregationBuilder = AggregationBuilders.terms(x.getName())
                .field(x.getFieldName())
                //这里查询数量暂时设置为50，有需要再扩展
                .size(50);
            searchSourceBuilder.aggregation(aggregationBuilder);
        });
        return esManager.searchResponse(searchRequest.source(searchSourceBuilder));
    }

    /**
     * 封装sysCatFact数据
     * @param aggregations es聚合结果
     * @return Map<sysCatId,numbers>
     */
    public Map<Integer,Integer> assemblySysCatFactDataToMap(Aggregations aggregations){
        return assemblyAggregationsMapData(aggregations
            , EsGoodsConstant.EsGoodsSearchFact.GOODS_CATEGORY_FIRST_FACT
            , EsGoodsConstant.EsGoodsSearchFact.GOODS_CATEGORY_SECOND_FACT
            , EsGoodsConstant.EsGoodsSearchFact.GOODS_CATEGORY_THIRD_FACT);

    }

    /**
     * 封装sysCatFact数据
     * @param aggregations es聚合结果
     * @return List<SysCateVo>
     */
    public List<SysCatevo> assemblySysCatFactDataToVo(Aggregations aggregations){
        Map<Integer,Integer> sysCatMap = assemblySysCatFactDataToMap(aggregations);
        List<Integer> sysCatIds = new ArrayList<>(sysCatMap.keySet());
        //TODO 暂时先走简化的缓存,后期对保证数据一致性做改造
        List<SysCatevo> result = SysCatServiceHelper.getSysCateVoByCatIds(sysCatIds);
        result.forEach(x->{
                x.setGoodsNumberSum(sysCatMap.get(x.getCatId()));
            });
        return result;
    }
    /**
     * 封装sortFact数据
     * @param aggregations es聚合结果
     * @return Map<sortId,numbers>
     */
    public Map<Integer,Integer> assemblySortFactDataToMap(Aggregations aggregations){
        return assemblyAggregationsMapData(aggregations
            , EsGoodsConstant.EsGoodsSearchFact.GOODS_SORT_FIRST_FACT
            , EsGoodsConstant.EsGoodsSearchFact.GOODS_SORT_SECOND_FACT);

    }
    /**
     * 封装sortFact数据
     * @param aggregations es聚合结果
     * @return List<SysCateVo>
     */
    public List<Sort> assemblySortFactDataToVo(Aggregations aggregations){
        Map<Integer,Integer> sortMap = assemblySortFactDataToMap(aggregations);
        List<Integer> sortIds = new ArrayList<>(sortMap.keySet());
        //TODO 暂时先走数据库,后期做改造走缓存
        List<Sort> result = goodsSortService.getList(sortIds);
        result.forEach(x->{
            x.setGoodsNumberSum(sortMap.get(x.getSortId()));
        });
        return result;
    }
    private Map<Integer,Integer> assemblyAggregationsMapData(Aggregations aggregations, String... factName){
        Map<Integer,Integer> resultMap = new HashMap<>();
        Map<String,Aggregation> aggregationsMap =aggregations.getAsMap();
        Arrays.stream(factName).forEach(x->{
            if( aggregationsMap.containsKey(x) ){
                Terms firstSysCat = aggregations.get(x);
                if( null != firstSysCat ){
                    firstSysCat.getBuckets().forEach(y->{
                        resultMap.put(Integer.parseInt(y.getKey().toString()),Math.toIntExact(y.getDocCount()));
                    });
                }
            }
        });
        return resultMap;
    }
}
