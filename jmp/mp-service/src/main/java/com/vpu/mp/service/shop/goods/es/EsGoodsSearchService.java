package com.vpu.mp.service.shop.goods.es;

import com.vpu.mp.service.foundation.es.EsManager;
import com.vpu.mp.service.foundation.es.EsSearchSourceBuilderParam;
import com.vpu.mp.service.foundation.util.Page;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.goods.es.EsSearchParam;
import com.vpu.mp.service.pojo.shop.goods.goods.GoodsPageListParam;
import com.vpu.mp.service.pojo.shop.goods.goods.GoodsPageListVo;
import com.vpu.mp.service.shop.goods.es.convert.EsConvertFactory;
import com.vpu.mp.service.shop.goods.es.convert.goods.GoodsPageListVoConverter;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
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
public class EsGoodsSearchService extends EsBaseSearchService{


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
            voList.add(EsConvertFactory.getGoodsConvert(GoodsPageListVoConverter.class).convertToGoodsPageListVo(x));
        });
        result.setDataList(voList);
        return result;
    }

    private PageResult<EsGoods> searchGoodsPageByParam(EsSearchParam param) throws IOException {
        Integer pageRows = param.getPageRows();
        Integer currentPage= param.getCurrentPage();
        PageResult<EsGoods> result = new PageResult<>();

        EsSearchSourceBuilderParam searchParam =  EsSearchSourceBuilderParam.builder()
            .queryBuilder(assemblySearchBuilder(param.getSearchList()))
            .indexName(EsDataInitService.ES_GOODS)
            .build();



        Page esPage =  assemblyPage(searchParam,pageRows,currentPage);
        Integer size = esPage.getPageRows();
        Integer from = (currentPage-1)*size;
        if( from > esPage.getTotalRows() ){
            from = esPage.getTotalRows();
        }
        searchParam.setIncludeSource(GOODS_SEARCH_STR);
        searchParam.setFrom(from);
        searchParam.setSize(size);
        SearchSourceBuilder sourceBuilder = assemblySearchSourceBuilder(searchParam);

        result.setDataList(searchEsGoods(assemblySearchRequest(sourceBuilder,EsDataInitService.ES_GOODS)));
        result.setPage(esPage);
        return result;
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
}
