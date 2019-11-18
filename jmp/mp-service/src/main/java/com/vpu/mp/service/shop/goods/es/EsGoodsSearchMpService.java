package com.vpu.mp.service.shop.goods.es;

import com.vpu.mp.service.foundation.es.EsSearchSourceBuilderParam;
import com.vpu.mp.service.foundation.util.Page;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.goods.es.EsSearchParam;
import com.vpu.mp.service.pojo.shop.goods.goods.GoodsPageListVo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsListMpBo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.list.GoodsListMpParam;
import com.vpu.mp.service.shop.goods.es.convert.EsConvertFactory;
import com.vpu.mp.service.shop.goods.es.convert.goods.EsGoodsConvertInterface;
import com.vpu.mp.service.shop.goods.es.convert.goods.GoodsListMpBoConverter;
import com.vpu.mp.service.shop.goods.es.convert.goods.GoodsPageListVoConverter;
import com.vpu.mp.service.shop.goods.es.convert.param.EsParamConvertInterface;
import com.vpu.mp.service.shop.goods.es.convert.param.GoodsListMpConverter;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * WX小程序ElasticSearch搜索
 * @author 卢光耀
 * @date 2019/11/15 9:29 上午
 *
*/
@Service
@Slf4j
public class EsGoodsSearchMpService extends EsBaseSearchService {

    private static final EsGoodsConvertInterface<GoodsListMpBo> CONVERT =new GoodsListMpBoConverter();
//    public static void main(String[] args) {
//        EsParamConvertInterface integrface = EsConvertFactory.getParamConvert(GoodsListMpConverter.class);
//        integrface.convert(null,122);
//        EsParamConvertInterface integrface1 = EsConvertFactory.getParamConvert(GoodsListMpConverter.class);
//        integrface1.convert(null,123);
//    }

    public PageResult<GoodsListMpBo> queryGoodsByParam(GoodsListMpParam mpParam){
        Integer shopId = getShopId();
        EsSearchParam param = assemblyEsSearchParam(mpParam,shopId);
        try {
            PageResult<EsGoods> esGoodsPage = searchGoodsPageByParam(param);
            return esPageConvertVoPage(esGoodsPage);
        } catch (IOException e) {
            log.error("EsGoodsSearchMpService-->queryGoodsByParam ElasticSearch connection error when querying");
        }
        return null;
    }
    private PageResult<GoodsListMpBo> esPageConvertVoPage(PageResult<EsGoods> esPage){

        PageResult<GoodsListMpBo> result = new PageResult<>();
        result.setPage(esPage.getPage());
        List<EsGoods> esGoodsList = esPage.getDataList();
        List<GoodsListMpBo> voList = new ArrayList<>(esGoodsList.size());
        esGoodsList.forEach(x-> voList.add(CONVERT.convert(x)));
        result.setDataList(voList);
        return result;
    }
    /**
     * assembly EsSearchParam
     * @param mpParam {@link GoodsListMpParam}
     * @param shopId 门店ID
     * @return {@link EsSearchParam}
     */
    private EsSearchParam assemblyEsSearchParam(GoodsListMpParam mpParam,Integer shopId){
        return EsConvertFactory.getParamConvert(GoodsListMpConverter.class).convert(mpParam,shopId);
    }

}
