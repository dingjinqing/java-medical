package com.vpu.mp.service.shop.goods.es;

import com.vpu.mp.service.foundation.es.EsManager;
import com.vpu.mp.service.foundation.es.EsSearchSourceBuilderParam;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.Page;
import com.vpu.mp.service.pojo.shop.goods.es.EsSearchName;
import com.vpu.mp.service.pojo.shop.goods.es.EsSearchParam;
import com.vpu.mp.service.pojo.shop.goods.es.FieldProperty;
import com.vpu.mp.service.pojo.shop.goods.es.Operator;
import com.vpu.mp.service.shop.order.goods.OrderGoodsService;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 
 * @author 卢光耀
 * @date 2019/11/8 5:15 下午
 *
*/
@Service
public class EsUtilSearchService extends EsBaseSearchService{
    @Autowired
    private EsManager esManager;
    @Autowired
    private OrderGoodsService orderGoodsService;

    public int getZhiXiaoGoodsNumbers() throws IOException {
        int result = 0;
        List<FieldProperty> propertyList = new ArrayList<>();
        propertyList.add(new FieldProperty(EsSearchName.SHOP_ID,getShopId()));
        propertyList.add(new FieldProperty(EsSearchName.UPDATE_TIME, DateUtil.getBeforeLocalFor(30), Operator.LT));
        BoolQueryBuilder searchBuilder = assemblySearchBuilder(propertyList);
        EsSearchSourceBuilderParam param = EsSearchSourceBuilderParam.builder()
            .queryBuilder(searchBuilder)
            .build();
        SearchSourceBuilder sourceBuilder = assemblySearchSourceBuilder(param);
        SearchRequest searchRequest = new SearchRequest("es_goods");
        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = esManager.searchResponse(searchRequest);
        List<Integer> ids = Arrays.stream(searchResponse.getHits().getHits())
            .map(x->x.getSourceAsMap())
            .map(x->Integer.parseInt(x.get(EsSearchName.GOODS_ID.getEsName()).toString()))
            .collect(Collectors.toList());
        List<Integer> buydIds = orderGoodsService.getZhixiaoGoodsIds();
        for(Integer id: buydIds) {
            if (ids.contains(id)) {
                result++;
            }
        }
        return result;
    }
    @Override
    public boolean esState(){
        return super.esState();

    }
}
