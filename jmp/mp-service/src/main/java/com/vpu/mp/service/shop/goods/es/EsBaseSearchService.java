package com.vpu.mp.service.shop.goods.es;

import com.vpu.mp.service.foundation.es.*;
import com.vpu.mp.service.foundation.es.handler.EsAggregationHandler;
import com.vpu.mp.service.foundation.es.handler.EsQueryBuilderHandler;
import com.vpu.mp.service.foundation.es.handler.EsRequestHandler;
import com.vpu.mp.service.foundation.es.handler.EsSearchSourceBuilderHandler;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.Page;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
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
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
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
        return convert.convert(param,shopId);
    }

    /**
     * ElasticSearch assembly page
     * @return {@link Page}
     * @throws IOException 连接异常
     */
    Integer assemblyPage(SearchSourceBuilder sourceBuilder,String indexName) throws IOException {
        CountRequest countRequest = assemblyCountRequest(sourceBuilder,indexName);
        CountResponse countResponse = esManager.getDocumentCount(countRequest);
        return Long.valueOf(countResponse.getCount()).intValue();
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
     * 封装SearchSourceBuilder(通常是封装用来getCount所需的SearchSourceBuilder)
     * @param param es对应的查询
     * @return {@link SearchSourceBuilder}
     */
    SearchSourceBuilder assemblySearchSourceBuilder(EsSearchSourceBuilderParam param){
        return esSearchSourceBuilderHandler.assemblySearchSourceBuilder(param);
    }
    /**
     * assembly search SearchSourceBuilder
     * @param esPage 分页
     * @param searchParam EsSearchSourceBuilderParam
     * @return {@link SearchSourceBuilder}
     */
    SearchSourceBuilder assemblyPageSearchSourceBuilder(Page esPage,EsSearchSourceBuilderParam searchParam){

        Integer size = esPage.getPageRows();
        Integer from = (esPage.getCurrentPage()-1)*size;
        if( from > esPage.getTotalRows() ){
            from = esPage.getTotalRows();
        }
        searchParam.setIncludeSource(GOODS_SEARCH_STR);
        searchParam.setFrom(from);
        searchParam.setSize(size);
        return assemblySearchSourceBuilder(searchParam);
    }
    /**
     * assembly search didn't need Page SearchSourceBuilder
     * @return {@link SearchSourceBuilder}
     */

    protected PageResult<EsGoods> searchGoodsPageByParam(EsSearchParam param) throws IOException {
        Page esPage;
        SearchSourceBuilder sourceBuilder;
        Integer pageRows = param.getPageRows();
        Integer currentPage= param.getCurrentPage();

        PageResult<EsGoods> result = new PageResult<>();
        //isQueryByPage == false代表ES搜索不需要进行分页
        if( !param.isQueryByPage() ){
            esPage = Page.getPage(1,1,1);
            EsSearchSourceBuilderParam searchParam = assemblySourceBuilderParam(param,esPage,GOODS_SEARCH_STR,null);
            sourceBuilder = assemblySearchSourceBuilder(searchParam);
            result.setPage(esPage);
        }else{
            sourceBuilder =  getSearchSourceBuilderAndPage(result,EsDataInitService.ES_GOODS,param,GOODS_SEARCH_STR,null);
        }
        result.setDataList(searchEsGoods(assemblySearchRequest(sourceBuilder,EsDataInitService.ES_GOODS)));
        return result;
    }
    SearchSourceBuilder getSearchSourceBuilderAndPage(PageResult<EsGoods> result,String indexName,EsSearchParam param,
                                                      String[] includeSource,String[] excludeSource) throws IOException {
        SearchSourceBuilder sourceBuilder = SearchSourceBuilder.searchSource();
        if( param == null ){
            return sourceBuilder;
        }
        QueryBuilder queryBuilder = assemblySearchBuilder(param.getSearchList());
        sourceBuilder.query(queryBuilder);
        if( param.isQueryByPage() ){
            Integer totalRow =  assemblyPage(sourceBuilder,indexName);
            Page page = Page.getPage(totalRow,param.getCurrentPage(),param.getPageRows());
            Integer size = page.getPageRows();
            Integer from = (page.getCurrentPage()-1)*size;
            if( from > page.getTotalRows() ){
                from = page.getTotalRows();
            }
            sourceBuilder
                .from( from )
                .size( size );
        }
        if( param.getFactList() != null ){
            assemblyAggregationBuilder(param.getFactList()).forEach(sourceBuilder::aggregation);
        }
        if( includeSource != null || excludeSource != null ){
            sourceBuilder.fetchSource(includeSource,excludeSource);
        }

        return sourceBuilder;
    }

    /**
     * 根据搜索请求返回List<EsGoods>
     * @param searchRequest
     * @return
     * @throws IOException
     */
    private List<EsGoods> searchEsGoods(SearchRequest searchRequest) throws IOException {
        SearchResponse searchResponse = search(searchRequest);
        SearchHit[] hits = searchResponse.getHits().getHits();
        List<EsGoods> data = new LinkedList<>();
        for( SearchHit hit:hits){
            data.add(Util.parseJson(hit.getSourceAsString(),EsGoods.class, EsManager.ES_FILED_SERIALIZER));
        }
        return data;
    }


    EsSearchSourceBuilderParam assemblySourceBuilderParam(EsSearchParam param, Page esPage,
                                                          String[] include, String[] exclude) {
        EsSearchSourceBuilderParamBuilder builder = EsSearchSourceBuilderParamBuilder.builder();
        if( param.getSearchList() != null && !param.getSearchList().isEmpty()){
            builder.queryBuilder(assemblySearchBuilder(param.getSearchList()));
        }
        if( param.getFactList() != null && !param.getFactList().isEmpty() ){
            builder.aggregations(assemblyAggregationBuilder(param.getFactList()));
        }
        if( !param.isQueryByPage() ){
            return builder.build();
        }
        Integer size = esPage.getPageRows();
        Integer from = (esPage.getCurrentPage()-1)*size;
        if( from > esPage.getTotalRows() ){
            from = esPage.getTotalRows();
        }
        builder.from(from);
        builder.size(size);
        if( include != null ){
            builder.includeSource(include);
        }
        if( exclude != null){
            builder.excludeSource(exclude);
        }
        return builder.build();
    }
}
