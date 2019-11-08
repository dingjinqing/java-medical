package com.vpu.mp.service.shop.goods.es;

import com.vpu.mp.service.foundation.es.annotation.EsFiled;
import com.vpu.mp.service.foundation.es.annotation.EsFiledTypeConstant;
import com.vpu.mp.service.pojo.shop.goods.es.EsSearchName;
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

    public final static String ES_GOODS = "es_goods";

    private void createIndex(String indexName) throws IOException {
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(indexName);
        createIndexRequest.settings(Settings.builder()
            .put("index.number_of_replicas",0)
            .put("index.number_of_shards",5)
            );
        if (ES_GOODS.equals(indexName)) {
            XContentBuilder mapping = createGoodsMapping();
            log.info("\nElasticSearch中未找到对应的Index，执行init...执行的语句---------\n{}", Strings.toString(mapping));
            createIndexRequest.mapping(mapping);
        }
//        createIndexRequest.set

        restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
    }

    /**
     * EsGoods Mapping init
     * @return XContentBuilder
     * @throws IOException
     */
    private XContentBuilder createGoodsMapping() throws IOException {
        XContentBuilder xContentBuilder = JsonXContent.contentBuilder()
            .startObject()
                .startObject("properties");
        Field[] fieldArray = EsGoods.class.getDeclaredFields();
        for( Field field : fieldArray ){
            EsFiled a = field.getAnnotation(EsFiled.class);
            if( a != null ){
                xContentBuilder.startObject(a.name().getEsName());
                xContentBuilder.field("type",a.type());
                xContentBuilder.field("index",a.index());
                if( EsFiledTypeConstant.DATE.equals(a.type()) ){
                    xContentBuilder.field("format","yyyy-MM-dd HH:mm:ss");
                }
                if(StringUtils.isNotBlank(a.analyzer()) ){
                    xContentBuilder.field("analyzer",a.analyzer());
                }
                if( EsSearchName.NULL != a.copyTo() ){
                    xContentBuilder.field("copy_to",a.copyTo().getEsName());
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
    private boolean assertIndex(String indexName) throws IOException {
        boolean result ;
        GetIndexRequest getIndexRequest = new GetIndexRequest(indexName);
        getIndexRequest.humanReadable(true);

        try{
            result = restHighLevelClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
        }catch (Exception e){
            log.error("\nElasticSearch index init fail，host/port fail");
            return true;
        }
        return result;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            if( !assertIndex(ES_GOODS) ){
                createIndex(ES_GOODS);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
