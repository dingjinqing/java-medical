package com.vpu.mp.service.shop.goods.es;

import com.vpu.mp.service.foundation.es.EsSearchSourceBuilderParam;
import com.vpu.mp.service.pojo.saas.category.SysCatevo;
import com.vpu.mp.service.pojo.shop.goods.es.EsSearchParam;
import com.vpu.mp.service.pojo.shop.goods.goods.GoodsInitialVo;
import com.vpu.mp.service.pojo.shop.goods.goods.GoodsPageListParam;
import com.vpu.mp.service.pojo.shop.goods.sort.Sort;
import com.vpu.mp.service.saas.categroy.SysCatServiceHelper;
import com.vpu.mp.service.shop.goods.GoodsSortService;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * ES聚合查询对外服务
 * @author 卢光耀
 * @date 2019/11/5 10:15 上午
 *
*/
@Service
public class EsFactSearchService extends EsBaseSearchService{
    @Autowired
    private GoodsSortService goodsSortService;

    public void assemblyFactByAdminGoodsListInit(GoodsInitialVo goodsInitialVo,GoodsPageListParam goodsPageListParam) throws Exception {
        EsSearchParam param = getFactSearchParamByAdminGoodsListInit(goodsPageListParam,getShopId());
        EsSearchSourceBuilderParam searchParam = EsSearchSourceBuilderParam.builder()
            .queryBuilder(assemblySearchBuilder(param.getSearchList()))
            .aggregations(assemblyAggregationBuilder(param.getFactList()))
            .build();
        //es查询
        SearchRequest searchRequest = assemblySearchRequest(
            assemblySearchSourceBuilder(searchParam)
            ,EsDataInitService.ES_GOODS
        );

        assemblyGoodsInitialVo(goodsInitialVo,search(searchRequest));

    }

    private void assemblyGoodsInitialVo(GoodsInitialVo goodsInitialVo,SearchResponse response){
        Aggregations aggregations = response.getAggregations();
        goodsInitialVo.setSysCates(assemblySysCatFactDataToVo(aggregations));
        goodsInitialVo.setGoodsSorts(assemblySortFactDataToVo(aggregations));
    }
    /**
     * 封装sysCatFact数据
     * @param aggregations es聚合结果
     * @return List<SysCateVo>
     */
    private List<SysCatevo> assemblySysCatFactDataToVo(Aggregations aggregations){
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
     * 从Aggregations中组装数据
     * @param aggregations  es聚合结果
     * @param factName 聚合参数{@link EsGoodsConstant.EsGoodsSearchFact}
     * @return key(factName对应的ID)/value(聚合组装的结果)
     */
    private Map<Integer,Integer> assemblyAggregationsMapData(Aggregations aggregations, String... factName){
        Map<Integer,Integer> resultMap = new HashMap<>();
        Map<String, Aggregation> aggregationsMap =aggregations.getAsMap();
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
    /**
     * 封装sortFact数据
     * @param aggregations es聚合结果
     * @return Map<sortId,numbers>
     */
    private Map<Integer,Integer> assemblySortFactDataToMap(Aggregations aggregations){
        return assemblyAggregationsMapData(aggregations
            , EsGoodsConstant.EsGoodsSearchFact.GOODS_SORT_FIRST_FACT
            , EsGoodsConstant.EsGoodsSearchFact.GOODS_SORT_SECOND_FACT);
    }

    /**
     * 封装sysCatFact数据
     * @param aggregations es聚合结果
     * @return Map<sysCatId,numbers>
     */
    private Map<Integer,Integer> assemblySysCatFactDataToMap(Aggregations aggregations){
        return assemblyAggregationsMapData(aggregations
            , EsGoodsConstant.EsGoodsSearchFact.GOODS_CATEGORY_FIRST_FACT
            , EsGoodsConstant.EsGoodsSearchFact.GOODS_CATEGORY_SECOND_FACT
            , EsGoodsConstant.EsGoodsSearchFact.GOODS_CATEGORY_THIRD_FACT);

    }


    /**
     * 封装sortFact数据
     * @param aggregations es聚合结果
     * @return List<SysCateVo>
     */
    private List<Sort> assemblySortFactDataToVo(Aggregations aggregations){
        Map<Integer,Integer> sortMap = assemblySortFactDataToMap(aggregations);
        List<Integer> sortIds = new ArrayList<>(sortMap.keySet());
        //TODO 暂时先走数据库,后期做改造走缓存
        List<Sort> result = goodsSortService.getList(sortIds);
        result.forEach(x->{
            x.setGoodsNumberSum(sortMap.get(x.getSortId()));
        });
        return result;
    }
    private EsSearchParam getFactSearchParamByAdminGoodsListInit(GoodsPageListParam param, Integer shopId){
        updateGoodsPageListParamForEs(param);
        return goodsParamConvertEsGoodsParam(param,shopId);

    }
    private void updateGoodsPageListParamForEs(GoodsPageListParam param){
        //封装需要fact的字段
        param.setIsFactQuery(Boolean.TRUE);
        param.setFactNameList(Arrays.asList(
            EsGoodsConstant.EsGoodsSearchFact.GOODS_CATEGORY_FIRST_FACT,
            EsGoodsConstant.EsGoodsSearchFact.GOODS_CATEGORY_SECOND_FACT,
            EsGoodsConstant.EsGoodsSearchFact.GOODS_CATEGORY_THIRD_FACT,
            EsGoodsConstant.EsGoodsSearchFact.GOODS_SORT_FIRST_FACT,
            EsGoodsConstant.EsGoodsSearchFact.GOODS_SORT_SECOND_FACT)
        );
    }
}
