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
     * @param builder 搜索条件
     * @param from  查询从第几条开始
     * @param size 查询的条数
     * @return SearchResponse
     */
    public SearchRequest assemblySearchRequest(QueryBuilder builder,Integer from,Integer size) {
        //TODO 到时候再考虑索引名称要不要写死
        SearchRequest searchRequest = new SearchRequest("es_goods");
        SearchSourceBuilder sourceBuilder = assemblySearchSourceBuilder(builder,null,GOODS_SEARCH_STR);
        sourceBuilder.from(from);
        sourceBuilder.size(size);
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
        PageResult<EsGoods> result = new PageResult<>();
        BoolQueryBuilder searchBuilder = assemblySearchBuilder(param);
        Integer totalRow = getAllCount(searchBuilder).intValue();
        Page esPage = Page.getPage(totalRow,param.getCurrentPage(),param.getPageRows());

        Integer size = esPage.getPageRows();
        Integer from = (esPage.getCurrentPage()-1)*size;
        if( from > totalRow ){
            from = totalRow;
        }
        SearchResponse searchResponse = esManager.searchResponse(assemblySearchRequest(searchBuilder,from,size));
        SearchHit[] hits = searchResponse.getHits().getHits();
        List<EsGoods> data = new LinkedList<>();
        for( SearchHit hit:hits){
            data.add(Util.parseJson(hit.getSourceAsString(),EsGoods.class,EsManager.ES_FILED_SERIALIZER));
        }
        result.setDataList(data);
        result.setPage(esPage);
        return result;
    }

    private Long getAllCount(QueryBuilder queryBuilder) throws IOException {
        CountRequest countRequest = new CountRequest("es_goods");
        CountResponse countResponse = esManager.getCount(countRequest);
        return countResponse.getCount();
    }


}
