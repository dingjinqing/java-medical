package com.vpu.mp.service.shop.goods.es;

import com.vpu.mp.service.foundation.es.annotation.EsFiled;
import com.vpu.mp.service.foundation.es.annotation.EsFiledTypeConstant;
import com.vpu.mp.service.foundation.jedis.JedisKeyConstant;
import com.vpu.mp.service.foundation.jedis.JedisManager;
import com.vpu.mp.service.shop.goods.es.goods.EsGoods;
import com.vpu.mp.service.shop.goods.es.goods.EsGoodsConstant;
import com.vpu.mp.service.shop.goods.es.goods.label.EsGoodsLabel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
import java.util.UUID;

/**
 * ES启动初始化
 * @author 卢光耀
 * @date 2019-10-08 10:59
 *
*/
@Component
@Slf4j
public class EsDataInitService implements InitializingBean {

    @Autowired(required = false)
    @Qualifier("esConfig")
    private RestHighLevelClient restHighLevelClient;

    @Autowired
    private JedisManager jedisManager;

    private void createIndex(String indexName) throws IOException {

        CreateIndexRequest createIndexRequest = new CreateIndexRequest(indexName);
        createIndexRequest.settings(Settings.builder()
            //由于现阶段的ElasticSearch的部署是单机版因此副分片数量设为0（副分片需要至少两个ES服务才能生效）
            .put("index.number_of_replicas",0)
            .put("index.number_of_shards",3)
            );
        if (EsGoodsConstant.GOODS_INDEX_NAME.equals(indexName)) {
            XContentBuilder mapping = createGoodsMapping(EsGoods.class);
            log.info("\nElasticSearch中未找到对应的GOODS INDEX，执行init...执行的语句---------\n{}", Strings.toString(mapping));
            createIndexRequest.mapping(mapping);
        }
        if (EsGoodsConstant.LABEL_INDEX_NAME.equals(indexName)) {
            XContentBuilder mapping = createGoodsMapping(EsGoodsLabel.class);
            log.info("\nElasticSearch中未找到对应的GOODS LABEL INDEX，执行init...执行的语句---------\n{}", Strings.toString(mapping));
            createIndexRequest.mapping(mapping);
        }
//        createIndexRequest.set

        restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
    }
    /**
     * EsGoods Mapping init
     * @return XContentBuilder
     * @throws IOException 连接异常
     */
    private XContentBuilder createGoodsMapping(Class<?> clz) throws IOException {
        XContentBuilder xContentBuilder = JsonXContent.contentBuilder()
            .startObject()
                .startObject("properties");
        Field[] fieldArray = clz.getDeclaredFields();
        for( Field field : fieldArray ){
            EsFiled a = field.getAnnotation(EsFiled.class);
            if( a != null ){
                xContentBuilder.startObject(a.name());
                xContentBuilder.field("type",a.type());
                xContentBuilder.field("index",a.index());
                if( EsFiledTypeConstant.DATE.equals(a.type()) ){
                    xContentBuilder.field("format","yyyy-MM-dd HH:mm:ss");
                }
                if(StringUtils.isNotBlank(a.analyzer()) ){
                    xContentBuilder.field("analyzer",a.analyzer());
                }
                if( StringUtils.isNotBlank(a.copyTo()) ){
                    xContentBuilder.field("copy_to",a.copyTo());
                }
                if(StringUtils.isNotBlank(a.searchAnalyzer()) ){
                    xContentBuilder.field("search_analyzer",a.searchAnalyzer());
                }
                if( "scaled_float".equals(a.type()) ){
                    xContentBuilder.field("scaling_factor",a.scaledNumber());
                }
                xContentBuilder.endObject();
            }
        }
        xContentBuilder.endObject().endObject();
        return xContentBuilder;
    }
    private boolean containIndex(String indexName){
        boolean result ;
        GetIndexRequest getIndexRequest = new GetIndexRequest(indexName);
        getIndexRequest.humanReadable(true);

        try{
            result = restHighLevelClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
        }catch (Exception e){
            log.error("\nElasticSearch index init fail，host/port fail");
            return false;
        }
        return !result;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        String requestId = UUID.randomUUID().toString();
        if( jedisManager.addLock(JedisKeyConstant.ES_INIT, requestId,1000*60) ){
            if(containIndex(EsGoodsConstant.GOODS_INDEX_NAME)){
                createIndex(EsGoodsConstant.GOODS_INDEX_NAME);
            }
            if(containIndex(EsGoodsConstant.LABEL_INDEX_NAME)){
                createIndex(EsGoodsConstant.LABEL_INDEX_NAME);
            }
            jedisManager.releaseLock(JedisKeyConstant.ES_INIT,requestId);
        }
    }
}
