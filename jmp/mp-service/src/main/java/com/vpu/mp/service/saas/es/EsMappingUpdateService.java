package com.vpu.mp.service.saas.es;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.vpu.mp.service.foundation.es.EsManager;
import com.vpu.mp.service.foundation.es.EsUtil;
import com.vpu.mp.service.foundation.es.annotation.EsFiled;
import com.vpu.mp.service.foundation.es.annotation.EsFiledSerializer;
import com.vpu.mp.service.foundation.es.annotation.EsFiledTypeConstant;
import com.vpu.mp.service.foundation.es.pojo.EsMappingInfo;
import com.vpu.mp.service.foundation.service.MainBaseService;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.shop.goods.es.goods.EsGoods;
import com.vpu.mp.service.shop.goods.es.goods.EsGoodsConstant;
import com.vpu.mp.service.shop.goods.es.goods.label.EsGoodsLabel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.AliasesRequest;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequest;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequest.AliasActions;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetMappingsRequest;
import org.elasticsearch.client.indices.GetMappingsResponse;
import org.elasticsearch.cluster.metadata.AliasAction;
import org.elasticsearch.common.compress.CompressedXContent;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.reindex.ReindexRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.TimeUnit;


@Service
@Slf4j
public class EsMappingUpdateService extends MainBaseService {

    private static final String ES_GOODS_NEW = EsGoodsConstant.GOODS_INDEX_NAME+"_new";

    @Autowired
    private EsManager esManager;


    public void updateEsGoodsMapping(){
        switchIndex();
////        reIndex();
//        createNewGoodsIndex();
//        try {
//            run();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }

    private List<EsGoods> test(){
        List<EsGoods> result = Lists.newArrayList();
        for (int i = 0; i < 100000; i++) {
            EsGoods goods = new EsGoods();
            goods.setGoodsId(i);
            goods.setShopId(245547);
            goods.setGoodsName("哈哈"+i);
            result.add(goods);
        }
        return result;

    }

    private void run() throws IOException {
        List<EsGoods> list = test();
        int allSize = list.size();
        int count = allSize/400 + 1;
        int nextNumber = 0;
        log.info("\n当前店铺【{}】,预计分【{}】批执行",245547,count);
        Stopwatch stopwatch = Stopwatch.createStarted();
        for (int i = 0; i < count; i++) {
            int endNumber = nextNumber+400;
            if( allSize < endNumber){
                endNumber = allSize;
                i = count;
            }
            log.info("\n开始执行第【{}】批，【{}】-【{}】条",i,nextNumber,endNumber);
            List<EsGoods> needIds = list.subList(nextNumber,endNumber);
            if( needIds.isEmpty() ){
                break;
            }
            nextNumber = endNumber;
            log.info("listSize【{}】",list.size());
            BulkRequest request = new BulkRequest();
            for( EsGoods esGoods:needIds ){
                request.add(esManager.assemblyRequest(ES_GOODS_NEW,esGoods));
            }

            esManager.batchDocuments(request);
            log.info("\n第【{}】批建立成功",i);
        }
        log.info("\n店铺【{}】索引建立完成，共耗时{}ms",245547,stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }

    public IndexRequest assemblyRequest(@NotNull String indexName, Object object){
        IndexRequest request = new IndexRequest(indexName);
        //商品索引ID设为shopId+goodsId
        if( object instanceof EsGoods ){
            EsGoods goods = (EsGoods)object;
            request.id(goods.getShopId().toString()+goods.getGoodsId().toString());
        }
        String objectJsonStr = Util.toJson(object, new EsFiledSerializer());
        Objects.requireNonNull(objectJsonStr);
        request.source(objectJsonStr, XContentType.JSON);
        return request;
    }

    public List<String> getEsGoodsDifferentFields(){
        CompressedXContent oldMapping = getOldMapping(EsGoodsConstant.GOODS_INDEX_NAME);
        Map<String,String> oldMappings = getOldMappingInfos(oldMapping);
        Map<String,String> newMappings = getNewMapping(EsGoods.class);
        return compared(oldMappings,newMappings);
    }



    public String createNewGoodsIndex(){
        CreateIndexRequest indexRequest = EsUtil.getCreateRequest(ES_GOODS_NEW);
        esManager.createIndexRequest(indexRequest);
        return "";
    }

    public CompressedXContent getOldMapping(String indexName){
        GetMappingsRequest request = new GetMappingsRequest();
        request.indices(indexName);
        GetMappingsResponse response = esManager.getMappingsResponse(request);
        return response.mappings().get(indexName).source();
    }

    private Map<String,String> getNewMapping(Class clz){
        Map<String,String> result = Maps.newHashMap();
        Field[] fieldArray = clz.getDeclaredFields();
        for( Field field : fieldArray ){
            EsFiled esFiled = field.getAnnotation(EsFiled.class);
            if( esFiled != null ){
                result.put(esFiled.name(),esFiled.type());
            }
        }
        return result;
    }

    private Map<String,String> getOldMappingInfos(CompressedXContent oldMapping){
        Map<String,String> result = Maps.newHashMap();
        JsonNode jsonNode = Util.toJsonNode(oldMapping.toString());
        Iterator<Map.Entry<String,JsonNode>> iterator =  jsonNode.get("properties").fields();
        while (iterator.hasNext()){
            Map.Entry<String,JsonNode> entry = iterator.next();
            result.put(entry.getKey(),entry.getValue().get("type").asText());
        }

        return result;
    }
    private List<String> compared(Map<String,String> old,Map<String,String> now){
        List<String> result = Lists.newArrayList();
        for ( Map.Entry<String,String> entry: now.entrySet()) {
            String fieldName = entry.getKey();
            String type = old.getOrDefault(fieldName,"");
            if( (!"".equals(type)) && type.equals(entry.getValue()) ){
                result.add(fieldName);
            }
        }
        return result;
    }

    private void reIndex(){
        ReindexRequest request = new ReindexRequest();
        request.setSourceIndices(ES_GOODS_NEW);
        request.setDestIndex(EsGoodsConstant.GOODS_INDEX_NAME);
        request.setRefresh(true);
        esManager.reIndex(request);
    }

    private void switchIndex(){
        IndicesAliasesRequest request = new IndicesAliasesRequest();
        AliasActions aliasActions = new AliasActions(AliasActions.Type.ADD)
            .index(EsGoodsConstant.GOODS_INDEX_NAME)
            .alias("vpu_goods_1587628058991");
        AliasActions aliasActions1 = new AliasActions(AliasActions.Type.REMOVE)
            .index(ES_GOODS_NEW)
            .alias("vpu_goods_1587628058991");
        AliasActions aliasActions2 = new AliasActions(AliasActions.Type.REMOVE)
            .index(EsGoodsConstant.GOODS_INDEX_NAME)
            .alias("vpu_goods_1587093419183");
        request.addAliasAction(aliasActions);
        request.addAliasAction(aliasActions1);
        request.addAliasAction(aliasActions2);
        esManager.switchIndexAlias(request);
    }

}
