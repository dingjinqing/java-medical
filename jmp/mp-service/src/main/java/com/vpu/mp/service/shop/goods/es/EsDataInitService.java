package com.vpu.mp.service.shop.goods.es;

import com.vpu.mp.config.es.annotation.EsFiled;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.json.JsonXContent;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Field;

/**
 * ES启动初始化
 * @author 卢光耀
 * @date 2019-10-08 10:59
 *
*/
@Component
public class EsDataInitService implements InitializingBean {

    @Autowired(required = false)
    @Qualifier("esConfig")
    private RestHighLevelClient restHighLevelClient;

    public final static String ES_GOODS = "es_goods";

    private void createIndex(String indexName) throws IOException {
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(indexName);
        createIndexRequest.settings(Settings.builder()
            .put("index.number_of_replicas",0)
            .put("index.number_of_shards",5));
        if (ES_GOODS.equals(indexName)) {
            createIndexRequest.mapping(createGoodsMapping());
        }
        restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
    }

    private XContentBuilder createGoodsMapping() throws IOException {
        XContentBuilder xContentBuilder = JsonXContent.contentBuilder()
            .startObject()
                .startObject("properties");
        Field[] fieldArray = EsGoods.class.getDeclaredFields();
        for( Field field : fieldArray ){
            EsFiled a = field.getAnnotation(EsFiled.class);
            if( a != null ){
                xContentBuilder.startObject(a.name());
                xContentBuilder.field("type",a.type());
                xContentBuilder.field("index",a.index());
                if(StringUtils.isNotBlank(a.analyzer()) ){
                    xContentBuilder.field("analyzer",a.analyzer());
                }
                if( a.type().equals("scaled_float") ){
                    xContentBuilder.field("scaling_factor",a.scaledNumber());
                }
                xContentBuilder.endObject();
            }
        }
        xContentBuilder.endObject().endObject();
        return xContentBuilder;
    }
    private boolean assertIndex(String indexName) throws IOException {
        GetIndexRequest getIndexRequest = new GetIndexRequest(indexName);
        getIndexRequest.humanReadable(true);
        return restHighLevelClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
//        try {
//            if( !assertIndex(ES_GOODS) ){
//                createIndex(ES_GOODS);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
