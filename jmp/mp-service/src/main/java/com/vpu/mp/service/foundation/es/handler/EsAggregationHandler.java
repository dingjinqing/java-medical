package com.vpu.mp.service.foundation.es.handler;

import com.vpu.mp.service.pojo.shop.goods.es.Fact;
import com.vpu.mp.service.shop.goods.es.goods.EsGoodsConstant;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ElasticSearch Aggregation Handler
 * @author 卢光耀
 * @date 2019/11/12 4:18 下午
 *
*/
@Component
public class EsAggregationHandler {

    /**
     * 封装fact查询builder
     * @param factList 聚合参数{@link EsGoodsConstant.EsGoodsSearchFact}
     * @return List<AggregationBuilder>
     */
    public List<AggregationBuilder> assemblyAggregationBuilder(List<Fact> factList){
        return factList.stream().map(x-> AggregationBuilders.terms(x.getName())
            .field(x.getFieldName())
            //这里查询数量暂时设置为50，有需要再扩展
            .size(50))
            .collect(Collectors.toList());
    }
}
