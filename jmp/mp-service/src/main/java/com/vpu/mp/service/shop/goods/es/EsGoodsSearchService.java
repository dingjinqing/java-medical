package com.vpu.mp.service.shop.goods.es;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class EsGoodsSearchService {
    @Autowired
    @Qualifier("esConfig")
    private RestHighLevelClient restHighLevelClient;

    public void getEsGoodsByFilter(){

    }
}
