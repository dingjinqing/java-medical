package com.vpu.mp.service.shop.goods.es;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.vpu.mp.service.foundation.jedis.data.GoodsBrandDataHelper;
import com.vpu.mp.service.foundation.jedis.data.SortDataHelper;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.shop.goods.es.EsSearchName;
import com.vpu.mp.service.pojo.shop.goods.es.EsSearchParam;
import com.vpu.mp.service.pojo.wxapp.goods.brand.GoodsBrandMpPinYinVo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsDetailMpBo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsListMpBo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.list.GoodsListMpParam;
import com.vpu.mp.service.pojo.wxapp.goods.goodssort.GoodsSearchSortMpVo;
import com.vpu.mp.service.pojo.wxapp.goods.goodssort.GoodsSortCacheInfo;
import com.vpu.mp.service.pojo.wxapp.goods.label.GoodsLabelMpVo;
import com.vpu.mp.service.pojo.wxapp.goods.search.GoodsSearchFilterConditionMpVo;
import com.vpu.mp.service.pojo.wxapp.goods.search.GoodsSearchMpParam;
import com.vpu.mp.service.shop.goods.es.convert.EsConvertFactory;
import com.vpu.mp.service.shop.goods.es.convert.goods.EsGoodsConvertInterface;
import com.vpu.mp.service.shop.goods.es.convert.goods.GoodsDetailBoConverter;
import com.vpu.mp.service.shop.goods.es.convert.goods.GoodsListMpBoConverter;
import com.vpu.mp.service.shop.goods.es.convert.param.GoodsListMpConverter;
import com.vpu.mp.service.shop.goods.es.goods.EsGoods;
import com.vpu.mp.service.shop.goods.es.goods.EsGoodsConstant;
import com.vpu.mp.service.shop.goods.es.goods.label.EsGoodsLabel;
import com.vpu.mp.service.shop.goods.es.goods.label.EsGoodsLabelSearchService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * WX小程序ElasticSearch搜索
 * @author 卢光耀
 * @date 2019/11/15 9:29 上午
 *
*/
@Service
@Slf4j
public class EsGoodsSearchMpService extends EsBaseSearchService {

    private static final EsGoodsConvertInterface<GoodsListMpBo> LIST_CONVERT =new GoodsListMpBoConverter();

    private static final EsGoodsConvertInterface<GoodsDetailMpBo> DETAIL_CONVERT =new GoodsDetailBoConverter();



    @Autowired private EsGoodsLabelSearchService esGoodsLabelSearchService;

    @Autowired private SortDataHelper sortDataHelper;

    @Autowired private GoodsBrandDataHelper goodsBrandDataHelper;

    @Autowired private EsGoodsSearchService esGoodsSearchService;

    /**
     * 商品列表的基本信息(ElasticSearch)
     * @param mpParam 查询条件 {@link GoodsListMpParam}
     * @return 分页结果
     * @throws IOException ElasticSearch连接异常
     */
    public PageResult<GoodsListMpBo> queryGoodsByParam(GoodsListMpParam mpParam) throws IOException {
        Integer shopId = getShopId();
        EsSearchParam param = assemblyEsSearchParam(mpParam,shopId);
        try {
            PageResult<EsGoods> esGoodsPage = searchGoodsPageByParam(param);
            return esPageConvertVoPage(esGoodsPage);
        } catch (IOException e) {
            log.error("EsGoodsSearchMpService-->queryGoodsByParam ElasticSearch connection error when querying");
            throw e;
        }
    }

    /**
     * 商品详情的基本信息(ElasticSearch)
     * @param goodsId 商品ID
     * @return 详情页Bo
     * @throws IOException ElasticSearch连接异常
     */
    public GoodsDetailMpBo queryGoodsById(Integer goodsId) throws IOException {
        Integer shopId = getShopId();
        try {
            EsGoods esGoods = getEsGoodsById(goodsId,shopId);
            return DETAIL_CONVERT.convert(esGoods);
        } catch (IOException e) {
            log.error("EsGoodsSearchMpService-->queryGoodsById ElasticSearch connection error when querying");
            throw e;
        }
    }


    /**
     * 小程序搜索列表页-商品列表
     * @param mpParam 查询条件 {@link GoodsSearchMpParam}
     * @return 分页结果
     * @throws IOException ElasticSearch连接异常
     */
    public PageResult<GoodsListMpBo> queryGoodsByParam(GoodsSearchMpParam mpParam) throws IOException {
        Integer shopId = getShopId();

        EsSearchParam param = assemblyEsSearchParam(mpParam,shopId);

        try {
            PageResult<EsGoods> esGoodsPage = searchGoodsPageByParam(param);
            return esPageConvertVoPage(esGoodsPage);
        } catch (IOException e) {
            log.error("EsGoodsSearchMpService-->queryGoodsByParam ElasticSearch connection error when querying");
            throw e;
        }
    }

    /**
     * 小程序搜索列表页-筛选条件倒推
     * @return {@link GoodsSearchFilterConditionMpVo}
     * @throws IOException 连接异常
     */
    public GoodsSearchFilterConditionMpVo getGoodsParam() throws IOException {
        SearchRequest request = assemblySearchRequestForWxGoodsParam();

        SearchResponse response = search(request);
        GoodsSearchFilterConditionMpVo vo = assemblyParamVo(response);
        List<EsGoodsLabel> lables = esGoodsLabelSearchService.getLabelForAllGoods();

        vo.setGoodsLabels(lables.stream().map(GoodsLabelMpVo::new).collect(Collectors.toList()));
        return vo;
    }

    private GoodsSearchFilterConditionMpVo assemblyParamVo(SearchResponse response) {
        GoodsSearchFilterConditionMpVo vo = new GoodsSearchFilterConditionMpVo();
        List<GoodsSearchSortMpVo> sortVos = Lists.newArrayList();
        List<EsGoodsLabel> list= Lists.newArrayList();
        Aggregations aggregations = response.getAggregations();
        Terms sortAggregation = aggregations.get(EsSearchName.SORT_ID);
        List<Integer> sortIds = Lists.newArrayList();
        Map<Integer,Long> sortNumberMap = Maps.newHashMap();
        sortAggregation.getBuckets().forEach(x->{
            Integer sortId = Integer.valueOf(x.getKeyAsString());
            sortNumberMap.put(sortId,x.getDocCount());
            sortIds.add(sortId);
        });
        Map<Short,List<GoodsSortCacheInfo>> sortInfoMap =sortDataHelper.getAndGroup(sortIds);
        Map<Integer,List<GoodsSortCacheInfo>> childGroupMap = sortInfoMap.get(GoodsConstant.SECOND_LEVEL).stream().
            collect(Collectors.groupingBy(GoodsSortCacheInfo::getParentId));
        sortInfoMap.get(GoodsConstant.ROOT_LEVEL).forEach(x->{
            GoodsSearchSortMpVo sortVo = new GoodsSearchSortMpVo(x.getSortId(),x.getSortName());
            Long numbers = sortNumberMap.getOrDefault(x.getSortId(),0L);
            List<GoodsSortCacheInfo> childSortInfos = childGroupMap.get(x.getSortId());
            if( childSortInfos != null && !childGroupMap.isEmpty() ){
                List<GoodsSearchSortMpVo> childVos = Lists.newArrayList();
                for(GoodsSortCacheInfo y: childSortInfos  ){
                    Long childNum = sortNumberMap.getOrDefault(y.getSortId(),0L);
                    childVos.add(new GoodsSearchSortMpVo(y.getSortId(),y.getSortName(),childNum));
                    numbers+=childNum;
                }
                sortVo.setGoodsNum(numbers);
                sortVo.setChildren(childVos);
            }
            sortVos.add(sortVo);
        });
        vo.setSorts(sortVos);

        Terms goodsBrandAggregation = aggregations.get(EsSearchName.BRAND_ID);
        List<Integer> brandIds = Lists.newArrayList();
        goodsBrandAggregation.getBuckets().forEach(x->{
            Integer brandId = Integer.valueOf(x.getKeyAsString());
            brandIds.add(brandId);
        });
        List<GoodsBrandMpPinYinVo> brandVos = goodsBrandDataHelper.getAndGroup(brandIds);
        vo.setGoodsBrands(brandVos);

        Terms goodsTypeAggregation = aggregations.get(EsSearchName.GOODS_TYPE);
        List<Integer> goodsTypes = Lists.newArrayList();
        goodsTypeAggregation.getBuckets().forEach(x->{
            Integer goodsType = Integer.valueOf(x.getKeyAsString());
            goodsTypes.add(goodsType);
        });
        vo.setActivityTypes(goodsTypes);
        return vo;
    }


    /**
     * 封装微信商品列表和esGoods有关的需要倒推的筛选条件的SearchRequest
     *
     * @return SearchRequest
     */
    private SearchRequest assemblySearchRequestForWxGoodsParam(){
        Integer shopId = getShopId();
        SearchRequest request = new SearchRequest(EsGoodsConstant.LABEL_INDEX_NAME);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.fetchSource(false);
        sourceBuilder.query(QueryBuilders.termQuery(EsSearchName.SHOP_ID,shopId));
        sourceBuilder.aggregation(AggregationBuilders.terms(EsSearchName.SORT_ID).field(EsSearchName.SORT_ID));
        sourceBuilder.aggregation(AggregationBuilders.terms(EsSearchName.BRAND_ID).field(EsSearchName.BRAND_ID));
        sourceBuilder.aggregation(AggregationBuilders.terms(EsSearchName.GOODS_TYPE).field(EsSearchName.GOODS_TYPE));
        request.source(sourceBuilder);
        return request;
    }



    /**
     * 数据转换(PageResult<EsGoods> --> PageResult<GoodsListMpBo>)
     * @param source source 源
     * @return PageResult<GoodsListMpBo>
     */
    private PageResult<GoodsListMpBo> esPageConvertVoPage(PageResult<EsGoods> source){
        PageResult<GoodsListMpBo> target = new PageResult<>();
        target.setPage(source.getPage());
        List<EsGoods> esGoodsList = source.getDataList();
        List<GoodsListMpBo> voList = new ArrayList<>(esGoodsList.size());
        esGoodsList.forEach(x-> voList.add(LIST_CONVERT.convert(x)));
        target.setDataList(voList);
        return target;
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
    /**
     * assembly EsSearchParam
     * @param mpParam {@link GoodsListMpParam}
     * @param shopId 门店ID
     * @return {@link EsSearchParam}
     */
    private EsSearchParam assemblyEsSearchParam(GoodsSearchMpParam mpParam,Integer shopId){
        return EsConvertFactory.getParamConvert(GoodsListMpConverter.class).convert(mpParam,shopId);
    }


}
