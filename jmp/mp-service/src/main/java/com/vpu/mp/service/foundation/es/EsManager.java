package com.vpu.mp.service.foundation.es;

import com.vpu.mp.service.foundation.es.annotation.EsFiledSerializer;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.shop.goods.es.EsGoods;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Objects;

/**
 * ES对外服务
 * @author 卢光耀
 * @date 2019/10/9 10:07 上午
 *
*/
@Service
@Slf4j
public class EsManager {

    @Autowired(required = false)
    @Qualifier("esConfig")
    private RestHighLevelClient restHighLevelClient;

    public static final EsFiledSerializer ES_FILED_SERIALIZER = new EsFiledSerializer();

    /**
     * 通用搜索方法
     * @param searchRequest 查询参数
     * @return 查询响应
     * @throws IOException 连接异常
     */
    public SearchResponse searchResponse(@NotNull SearchRequest searchRequest) throws IOException {
        log.info("\n本次搜索条件【{}】",searchRequest.source().toString());
        return restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);
    }
    /**
     * 通用搜索方法
     * @param countRequest 查询参数
     * @return 查询响应
     * @throws IOException 连接异常
     */
    public CountResponse getCount(@NotNull CountRequest countRequest) throws IOException {
        log.info("\n本次统计数量的搜索条件【{}】",countRequest.source().toString());
        return restHighLevelClient.count(countRequest,RequestOptions.DEFAULT);
    }

    /**
     * 判断ES服务是否可用
     * @return true:可用/false:不可用
     */
    public boolean esState(){
        try {
            return restHighLevelClient.ping(RequestOptions.DEFAULT);
        } catch (Exception e) {
            log.error("ElasticSearch can't use");
            return false;
        }
    }

    /**
     * 单个对象建立索引（异步）
     * @param indexName 索引名称
     * @param object 对应的索引对象
     */
    public void createIndexAsync(@NotNull String indexName,Object object)  {
        IndexRequest request = assemblyRequest(indexName,object);
        log.error("ES_INDEX:{}", Util.toJson(object,ES_FILED_SERIALIZER));
        ActionListener<IndexResponse> listener = new ActionListener<IndexResponse>() {
            @Override
            public void onResponse(IndexResponse createIndexResponse) {
                log.info("索引建立成功");
            }
            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();
                log.info("索引建立失败");
            }
        };
        restHighLevelClient.indexAsync(request, RequestOptions.DEFAULT,listener);
    }

    /**
     * 封装es的commit的request
     * @param indexName 索引名称
     * @param object 对应的索引对象
     * @return IndexRequest
     */
    public IndexRequest assemblyRequest(@NotNull String indexName,Object object){
        IndexRequest request = new IndexRequest(indexName);
        //商品索引ID设为shopId+goodsId
        if( object instanceof EsGoods ){
            EsGoods goods = (EsGoods)object;
            request.id(goods.getSortId().toString()+goods.getGoodsId().toString());
        }
        request.source(Objects.requireNonNull(Util.toJson(object, ES_FILED_SERIALIZER)), XContentType.JSON);
        return request;
    }

    /**
     * 批量建立索引（同步）
     * @param request 批量索引的请求
     * @throws IOException 索引失败的异常（自己捕获处理）
     */
    public void batchCreateDocuments(BulkRequest request) throws IOException {
        BulkResponse response =  restHighLevelClient.bulk(request,RequestOptions.DEFAULT);
        if( response.hasFailures() ){
            log.error("EEOR");
        }
    }

    /**
     * 根据索引名称删除索引全部数据
     * @param indexName 索引名称
     */
    public void deleteIndexByName(String indexName){
        DeleteIndexRequest request = new DeleteIndexRequest(indexName);
        try {
            restHighLevelClient.indices().delete(request,RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据索引名称和documentID删除索引
     * @param indexName 索引名称
     * @param id documentId
     * @throws IOException 连接错误
     */
    private void deleteIndexById(String indexName,String id) throws IOException {
        DeleteRequest request = new DeleteRequest(indexName,id);
        restHighLevelClient.delete(request,RequestOptions.DEFAULT);

    }
}
