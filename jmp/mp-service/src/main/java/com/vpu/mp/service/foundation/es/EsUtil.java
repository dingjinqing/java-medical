package com.vpu.mp.service.foundation.es;

import com.vpu.mp.service.foundation.es.annotation.EsFiled;
import com.vpu.mp.service.foundation.es.annotation.EsFiledSerializer;
import com.vpu.mp.service.foundation.es.annotation.EsFiledTypeConstant;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.shop.goods.es.goods.EsGoods;
import com.vpu.mp.service.shop.goods.es.goods.EsGoodsConstant;
import com.vpu.mp.service.shop.goods.es.goods.label.EsGoodsLabel;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.common.xcontent.json.JsonXContent;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Objects;

public class EsUtil {


    public static final EsFiledSerializer ES_FILED_SERIALIZER = new EsFiledSerializer();

    /**
     * EsGoods Mapping init
     * @return XContentBuilder
     */
    public static XContentBuilder createGoodsMapping(Class<?> clz) {
        XContentBuilder xContentBuilder=null;
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
     * 生成商品索引名称
     * @return 别名
     */
    private static String getEsGoodsIndexName(){
        return EsGoodsConstant.GOODS_INDEX_NAME_PREFIX + Calendar.getInstance().getTimeInMillis();
    }
    /**
     * 生成商品标签索引名称
     * @return 别名
     */
    private static String getEsLabelIndexName(){
        return EsGoodsConstant.LABEL_INDEX_NAME_PREFIX+ Calendar.getInstance().getTimeInMillis();
    }

    /**
     * 根据索引名称生成对应的新建索引请求
     * @param aliaName 索引别名
     * @return 新建索引请求
     */
    public static CreateIndexRequest getCreateRequest(String aliaName,Boolean setAlias){
        String indexName;
        XContentBuilder mapping;
        if( aliaName.equals(EsGoodsConstant.GOODS_ALIA_NAME) ){
            indexName = getEsGoodsIndexName();
            mapping = createGoodsMapping(EsGoods.class);
        }else if ( aliaName.equals(EsGoodsConstant.LABEL_ALIA_NAME) ){
            indexName = getEsLabelIndexName();
            mapping = EsUtil.createGoodsMapping(EsGoodsLabel.class);
        }else{
            throw new Error("ElasticSearch create index error");
        }
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(indexName);
        createIndexRequest.settings(Settings.builder()
            //由于现阶段的ElasticSearch的部署是单机版因此副分片数量设为0（副分片需要至少两个ES服务才能生效）
            .put("index.number_of_replicas",0)
            .put("index.number_of_shards",3)
        );
        createIndexRequest.mapping(mapping);
        if( setAlias ){
            createIndexRequest.alias(new Alias(aliaName));
        }
        return createIndexRequest;
    }


    /**
     * 封装es的commit的request
     * @param indexName 索引名称
     * @param object 对应的索引对象
     * @return IndexRequest
     */
    public static IndexRequest assemblyRequest(@NotNull String indexName, Object object){
        IndexRequest request = new IndexRequest(indexName);
        //商品索引ID设为shopId+goodsId
        if( object instanceof EsGoods ){
            EsGoods goods = (EsGoods)object;
            request.id(goods.getShopId().toString()+goods.getGoodsId().toString());
        }
        //商品标签索引ID设为shopId+goodsId+labelId
        if( object instanceof EsGoodsLabel){
            EsGoodsLabel goodsLabel = (EsGoodsLabel)object;
            request.id(goodsLabel.getShopId().toString()+goodsLabel.getGoodsId().toString()+goodsLabel.getId().toString());
        }
        String objectJsonStr = Util.toJson(object, ES_FILED_SERIALIZER);
        Objects.requireNonNull(objectJsonStr);
        request.source(objectJsonStr, XContentType.JSON);
        return request;
    }
//    public static List<String> get
}
