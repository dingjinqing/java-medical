package com.vpu.mp.service.shop.goods.es;

import com.vpu.mp.service.foundation.es.annotation.EsFiledSerializer;
import com.vpu.mp.service.foundation.es.EsManager;
import com.vpu.mp.service.foundation.util.Page;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.goods.es.EsSearchParam;
import com.vpu.mp.service.pojo.shop.goods.goods.GoodsPageListParam;
import com.vpu.mp.service.pojo.shop.goods.goods.GoodsPageListVo;
import com.vpu.mp.service.shop.goods.es.convert.EsConvertFactory;
import com.vpu.mp.service.shop.goods.es.convert.goods.EsGoodsConvertByAdmin;
import com.vpu.mp.service.shop.goods.es.convert.goods.EsGoodsConvertInterface;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * es搜索相关
 * @author 卢光耀
 * @date 2019/10/25 2:04 下午
 *
*/
@Service
public class EsGoodsSearchService extends EsSearchService{

    @Autowired
    private EsManager esManager;


    /**
     * admin平台商品搜索
     * @param sourceBuilder  SearchSourceBuilder
     * @return SearchResponse
     */
    public SearchRequest assemblySearchRequest(SearchSourceBuilder sourceBuilder) {
        //TODO 到时候再考虑索引名称要不要写死
        SearchRequest searchRequest = new SearchRequest("es_goods");
        return searchRequest.source(sourceBuilder);
    }
    public PageResult<GoodsPageListVo> searchGoodsPageByParam(GoodsPageListParam goodsPageListParam) throws IOException {

        Integer shopId = getShopId();
        EsSearchParam param = goodsParamConvertEsGoodsParam(goodsPageListParam,shopId);
        PageResult<EsGoods> pageResult = searchGoodsPageByParam(param);
        return esPageConvertVoPage(pageResult);
    }

    private PageResult<GoodsPageListVo> esPageConvertVoPage(PageResult<EsGoods> esPage){
        PageResult<GoodsPageListVo> result = new PageResult<>();
        result.setPage(esPage.getPage());
        List<EsGoods> esGoodsList = esPage.getDataList();
        List<GoodsPageListVo> voList = new ArrayList<>(esGoodsList.size());
        esGoodsList.forEach(x->{
            voList.add(EsConvertFactory.getGoodsConvert(EsGoodsConvertByAdmin.class).convertToGoodsPageListVo(x));
        });
        result.setDataList(voList);
        return result;
    }

    private PageResult<EsGoods> searchGoodsPageByParam(EsSearchParam param) throws IOException {
        Integer pageRows = param.getPageRows();
        Integer currentPage= param.getCurrentPage();
        PageResult<EsGoods> result = new PageResult<>();
        BoolQueryBuilder searchBuilder = assemblySearchBuilder(param.getSearchList());
        SearchSourceBuilder sourceBuilder = assemblySearchSourceBuilder(searchBuilder,null,null);
        Page esPage =  assemblyPage(sourceBuilder,pageRows,currentPage);
        SearchResponse searchResponse = esManager.searchResponse(assemblySearchRequest(sourceBuilder));
        SearchHit[] hits = searchResponse.getHits().getHits();
        List<EsGoods> data = new LinkedList<>();
        for( SearchHit hit:hits){
            data.add(Util.parseJson(hit.getSourceAsString(),EsGoods.class,EsManager.ES_FILED_SERIALIZER));
        }
        result.setDataList(data);
        result.setPage(esPage);
        return result;
    }
    private Page assemblyPage(SearchSourceBuilder sourceBuilder,Integer pageRows,Integer currentPage) throws IOException {
        Integer totalRow = getAllCount(sourceBuilder).intValue();
        Integer size = pageRows;
        Integer from = (currentPage-1)*size;
        if( from > totalRow ){
            from = totalRow;
        }
        sourceBuilder.fetchSource(GOODS_SEARCH_STR,null);
        //from  查询从第几条开始
        sourceBuilder.from(from);
        //size 查询的条数
        sourceBuilder.size(size);
        return Page.getPage(totalRow,currentPage,pageRows);
    }

    /**
     * SearchSourceBuilder
     * @param builder 搜索条件

     * @return
     */
    private SearchSourceBuilder assemblySearchSourceBuilder(QueryBuilder builder){
        SearchSourceBuilder sourceBuilder = assemblySearchSourceBuilder(builder,null,GOODS_SEARCH_STR);

        return sourceBuilder;
    }

    private Long getAllCount(SearchSourceBuilder queryBuilder) throws IOException {
        CountRequest countRequest = new CountRequest("es_goods");
        countRequest.source(queryBuilder);
        CountResponse countResponse = esManager.getCount(countRequest);
        return countResponse.getCount();
    }


}
