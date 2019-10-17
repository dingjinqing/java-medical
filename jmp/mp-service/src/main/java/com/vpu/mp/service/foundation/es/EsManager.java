package com.vpu.mp.service.foundation.es;

import com.vpu.mp.config.es.annotation.EsFiledSerializer;
import com.vpu.mp.service.foundation.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.jooq.False;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

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
@ConditionalOnProperty(prefix="es",name = "open", havingValue = "on")
public class EsManager {

    @Autowired(required = false)
    @Qualifier("esConfig")
    private RestHighLevelClient restHighLevelClient;

    private static final EsFiledSerializer ES_FILED_SERIALIZER = new EsFiledSerializer();

    /**
     * 单个对象建立索引（异步）
     * @param indexName 索引名称
     * @param object 对应的索引对象
     */
    public void createIndexAsync(String indexName,Object object)  {
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
    public IndexRequest assemblyRequest(String indexName,Object object){
        IndexRequest request = new IndexRequest(indexName);
        request.source(Objects.requireNonNull(Util.toJson(object, ES_FILED_SERIALIZER)), XContentType.JSON);
        return request;
    }

    /**
     * 批量建立索引（同步）
     * @param request 批量索引的请求
     * @throws IOException 索引失败的异常（自己捕获处理）
     */
    public void batchCreateDocuments(BulkRequest request) throws IOException {
        restHighLevelClient.bulk(request,RequestOptions.DEFAULT);
    }

    /**
     * 根据索引名称删除索引
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
}
