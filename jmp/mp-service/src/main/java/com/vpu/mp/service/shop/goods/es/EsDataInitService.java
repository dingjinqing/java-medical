package com.vpu.mp.service.shop.goods.es;

import com.google.common.collect.Lists;
import com.vpu.mp.service.foundation.es.EsManager;
import com.vpu.mp.service.foundation.es.EsUtil;
import com.vpu.mp.service.foundation.es.annotation.EsFiled;
import com.vpu.mp.service.foundation.es.annotation.EsFiledTypeConstant;
import com.vpu.mp.service.foundation.jedis.JedisKeyConstant;
import com.vpu.mp.service.foundation.jedis.JedisManager;
import com.vpu.mp.service.shop.goods.es.goods.EsGoods;
import com.vpu.mp.service.shop.goods.es.goods.EsGoodsConstant;
import com.vpu.mp.service.shop.goods.es.goods.label.EsGoodsLabel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.json.JsonXContent;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

/**
 * ES启动初始化
 * @author 卢光耀
 * @date 2019-10-08 10:59
 *
*/
//@Component
@Slf4j
public class EsDataInitService implements InitializingBean {

    @Autowired
    private EsManager esManager;

    @Autowired
    private JedisManager jedisManager;

    private void createIndex(String indexName){

        CreateIndexRequest createIndexRequest = EsUtil.getCreateRequest(indexName,Boolean.TRUE);
        esManager.createIndexRequest(createIndexRequest);
    }

    /**
     * 判断索引是否存在
     * @param indexName 索引标识
     * @return true｜false
     */
    private boolean containIndex(String indexName){
        GetIndexRequest getIndexRequest = new GetIndexRequest(indexName);
        getIndexRequest.humanReadable(true);
        return esManager.checkIndexExists(getIndexRequest);
    }

    @Override
    public void afterPropertiesSet() {
        String requestId = UUID.randomUUID().toString();
        if( jedisManager.addLock(JedisKeyConstant.ES_INIT, requestId,1000*600) ){
            if(!containIndex(EsGoodsConstant.GOODS_ALIA_NAME)){
                createIndex(EsGoodsConstant.GOODS_ALIA_NAME);
            }
            if(!containIndex(EsGoodsConstant.LABEL_ALIA_NAME)){
                createIndex(EsGoodsConstant.LABEL_ALIA_NAME);
            }
            if(!containIndex(EsGoodsConstant.PRODUCT_ALIA_NAME)){
                createIndex(EsGoodsConstant.PRODUCT_ALIA_NAME);
            }
            jedisManager.releaseLock(JedisKeyConstant.ES_INIT,requestId);
        }
    }
}
