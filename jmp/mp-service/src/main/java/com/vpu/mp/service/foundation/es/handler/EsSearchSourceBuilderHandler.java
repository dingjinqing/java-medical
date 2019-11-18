package com.vpu.mp.service.foundation.es.handler;

import com.vpu.mp.service.foundation.es.EsSearchSourceBuilderParam;
import com.vpu.mp.service.foundation.util.Page;
import com.vpu.mp.service.pojo.shop.goods.es.EsSearchParam;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Component;

/**
 * ElasticSearch SearchSourceBuilder Handler
 * @author 卢光耀
 * @date 2019/11/12 4:12 下午
 *
*/
@Component
public class EsSearchSourceBuilderHandler {

    public SearchSourceBuilder assemblySearchSourceBuilder(EsSearchSourceBuilderParam param){
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        if( param == null ){
            return sourceBuilder;
        }
        if( param.getQueryBuilder() != null ){
            sourceBuilder.query(param.getQueryBuilder());
        }
        if( param.getAggregations() != null ){
            param.getAggregations().forEach(sourceBuilder::aggregation);
        }
        String[] includeSource = param.getIncludeSource();
        String[] excludeSource = param.getExcludeSource();
        if( includeSource != null || excludeSource != null ){
            sourceBuilder.fetchSource(includeSource,excludeSource);
        }
        if( param.getFrom() != null && param.getSize() != null ){
            sourceBuilder
                .from( param.getFrom() )
                .size( param.getSize() );
        }
        return sourceBuilder;
    }

}
