package com.vpu.mp.service.shop.goods.es;

import com.vpu.mp.service.foundation.es.*;
import com.vpu.mp.service.foundation.es.handler.EsAggregationHandler;
import com.vpu.mp.service.foundation.es.handler.EsQueryBuilderHandler;
import com.vpu.mp.service.foundation.es.handler.EsRequestHandler;
import com.vpu.mp.service.foundation.es.handler.EsSearchSourceBuilderHandler;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.Page;
import com.vpu.mp.service.pojo.shop.goods.es.*;
import com.vpu.mp.service.pojo.shop.goods.goods.GoodsPageListParam;
import com.vpu.mp.service.shop.goods.es.convert.EsConvertFactory;
import com.vpu.mp.service.shop.goods.es.convert.param.EsParamConvertInterface;
import com.vpu.mp.service.shop.goods.es.convert.param.GoodsPageConvertEsParam;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
public class EsBaseSearchService extends ShopBaseService {


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

    protected static  String[] DEFAULT_STR;
    static{
        DEFAULT_STR = Arrays.stream(DEFAULT)
            .map(EsSearchName::getEsName)
            .collect(Collectors.toList())
            .toArray(new String[DEFAULT.length]);
    }

    protected static  String[] GOODS_SEARCH_STR ;
    static{
        GOODS_SEARCH_STR = Arrays.stream(GOODS_SEARCH)
            .map(EsSearchName::getEsName).collect(Collectors.toList())
            .toArray(new String[GOODS_SEARCH.length]);
    }

    @Autowired
    private EsManager esManager;
    @Autowired
    private EsAggregationHandler esAggregationHandler;
    @Autowired
    private EsQueryBuilderHandler esQueryBuilderHandler;
    @Autowired
    private EsSearchSourceBuilderHandler esSearchSourceBuilderHandler;
    @Autowired
    private EsRequestHandler esRequestHandler;

    protected BoolQueryBuilder assemblySearchBuilder(List<FieldProperty> searchList) {
        return esQueryBuilderHandler.assemblySearchBuilder(searchList);
    }

    protected List<AggregationBuilder> assemblyAggregationBuilder(List<Fact> factList){
        return esAggregationHandler.assemblyAggregationBuilder(factList);
    }

    protected boolean esState(){
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

    /**
     * ElasticSearch assembly page
     * @param searchParam 查询条件
     * @param pageRows 单页条数
     * @param currentPage 当前页数
     * @return {@link Page}
     * @throws IOException 连接异常
     */
    Page assemblyPage(EsSearchSourceBuilderParam searchParam, Integer pageRows, Integer currentPage) throws IOException {
        SearchSourceBuilder sourceBuilder = assemblySearchSourceBuilder(searchParam);
        CountRequest countRequest = assemblyCountRequest(sourceBuilder,searchParam.getIndexName());
        CountResponse countResponse = esManager.getDocumentCount(countRequest);
        Integer totalRow = Long.valueOf(countResponse.getCount()).intValue();
        return Page.getPage(totalRow,currentPage,pageRows);
    }


    /**
     * 调用esManager的searchResponse
     * @param searchRequest 查询请求
     * @return SearchResponse
     * @throws IOException 连接异常
     */
    protected SearchResponse search(SearchRequest searchRequest) throws IOException {
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
     * @param sourceBuilder es对应的查询
     * @param indexName 索引名称
     * @return SearchRequest
     */
    SearchRequest assemblySearchRequest(SearchSourceBuilder sourceBuilder, String indexName)  {
        return esRequestHandler.assemblySearchRequest(indexName,sourceBuilder);
    }
    /**
     * 封装CountRequest
     * @param sourceBuilder es对应的查询
     * @param indexName 索引名称
     * @return SearchRequest
     */
    protected CountRequest assemblyCountRequest(SearchSourceBuilder sourceBuilder, String indexName)  {
        return esRequestHandler.assemblyCountRequest(indexName,sourceBuilder);
    }

    /**
     * 封装SearchSourceBuilder
     * @param param es对应的查询
     * @return {@link SearchSourceBuilder}
     */
    SearchSourceBuilder assemblySearchSourceBuilder(EsSearchSourceBuilderParam param){
        return esSearchSourceBuilderHandler.assemblySearchSourceBuilder(param);
    }

}
