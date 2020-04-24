package com.vpu.mp.service.foundation.es;

import com.vpu.mp.service.foundation.es.annotation.EsFiled;
import com.vpu.mp.service.foundation.es.annotation.EsFiledTypeConstant;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.shop.goods.es.goods.EsGoods;
import com.vpu.mp.service.shop.goods.es.goods.EsGoodsConstant;
import com.vpu.mp.service.shop.goods.es.goods.label.EsGoodsLabel;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.json.JsonXContent;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class EsUtil {


    /**
     * EsGoods Mapping init
     * @return XContentBuilder
     * @throws IOException 连接异常
     */
    public static XContentBuilder createGoodsMapping(Class<?> clz) {
        XContentBuilder xContentBuilder = null;
        try {
            xContentBuilder = JsonXContent.contentBuilder()
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
        } catch (IOException e) {
            e.printStackTrace();
        }

        return xContentBuilder;
    }

    /**
     * 生成索引别名
     * @param indexName 索引名称
     * @return 别名
     */
    public static String getAliaName(String indexName){
        String result = "";
        if ( StringUtils.isBlank(indexName) ){
            return result;
        }
        if (EsGoodsConstant.GOODS_INDEX_NAME.equals(indexName)){
            return "vpu_goods_"+ Calendar.getInstance().getTimeInMillis();
        }else{
            return "vpu_goods_label_" + Calendar.getInstance().getTimeInMillis();
        }

    }

    /**
     * 根据索引名称生成对应的新建索引请求
     * @param indexName 索引名称
     * @return 新建索引请求
     */
    public static CreateIndexRequest getCreateRequest(String indexName){
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(indexName);
        createIndexRequest.settings(Settings.builder()
            //由于现阶段的ElasticSearch的部署是单机版因此副分片数量设为0（副分片需要至少两个ES服务才能生效）
            .put("index.number_of_replicas",0)
            .put("index.number_of_shards",3)
        );
        if (indexName.contains(EsGoodsConstant.GOODS_INDEX_NAME) ) {
            createIndexRequest.alias(new Alias(EsUtil.getAliaName(EsGoodsConstant.GOODS_INDEX_NAME)));
            XContentBuilder mapping = createGoodsMapping(EsGoods.class);
            createIndexRequest.mapping(mapping);
        }
        if (indexName.contains(EsGoodsConstant.LABEL_INDEX_NAME)) {
            createIndexRequest.alias(new Alias(EsUtil.getAliaName(EsGoodsConstant.LABEL_INDEX_NAME)));
            XContentBuilder mapping = EsUtil.createGoodsMapping(EsGoodsLabel.class);
            createIndexRequest.mapping(mapping);
        }
        return createIndexRequest;
    }

//    public static List<String> get
}
