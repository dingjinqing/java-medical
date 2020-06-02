package com.vpu.mp.service.shop.goods.es;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.goods.es.EsSearchParam;
import com.vpu.mp.service.pojo.shop.goods.goods.Goods;
import com.vpu.mp.service.pojo.shop.goods.goods.GoodsPageListParam;
import com.vpu.mp.service.pojo.shop.goods.goods.GoodsPageListVo;
import com.vpu.mp.service.pojo.shop.goods.label.GoodsLabelCoupleTypeEnum;
import com.vpu.mp.service.pojo.shop.goods.label.GoodsLabelSelectListVo;
import com.vpu.mp.service.shop.goods.es.convert.goods.EsGoodsConvertInterface;
import com.vpu.mp.service.shop.goods.es.convert.goods.GoodsPageListVoConverter;
import com.vpu.mp.service.shop.goods.es.convert.goods.GoodsPageListVoForProductConverter;
import com.vpu.mp.service.shop.goods.es.goods.EsGoods;
import com.vpu.mp.service.shop.goods.es.goods.EsGoodsConstant;
import com.vpu.mp.service.shop.goods.es.goods.label.EsGoodsLabel;
import com.vpu.mp.service.shop.goods.es.goods.label.EsGoodsLabelSearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * es搜索相关(Admin)
 * @author 卢光耀
 * @date 2019/10/25 2:04 下午
 *
*/
@Service
@Slf4j
public class EsGoodsSearchService extends EsBaseSearchService{

    @Autowired
    EsGoodsLabelSearchService esGoodsLabelSearchService;

    private static final EsGoodsConvertInterface<GoodsPageListVo> CONVERT =new GoodsPageListVoConverter();

    private static final EsGoodsConvertInterface<List<GoodsPageListVo>> PRODUCT_CONVERT =new GoodsPageListVoForProductConverter();

    /**
     * admin商品列表（商品纬度）
     * @param goodsPageListParam 查询参数
     * @return 搜索结果（分页）
     * @throws IOException 查询异常
     */
    public PageResult<GoodsPageListVo> searchGoodsPageByParam(GoodsPageListParam goodsPageListParam) throws IOException {
        Stopwatch stopwatch = Stopwatch.createStarted();
        Integer shopId = getShopId();
        assemblyGoodsLabelParam(goodsPageListParam);
        EsSearchParam param = goodsParamConvertEsGoodsParam(goodsPageListParam,shopId);
        PageResult<EsGoods> pageResult = searchGoodsPageByParamForPage(param);
        log.info("【ElasticSearch query】Time {} ms",stopwatch.elapsed(TimeUnit.MILLISECONDS));
        return esPageConvertVoPage(pageResult);
    }

    /**
     * 查询商品（商品规格纬度）
     * @param goodsPageListParam 查询参数
     * @return 搜索结果（分页）
     * @throws IOException 查询异常
     */
    public PageResult<GoodsPageListVo> searchGoodsPageForProduct(GoodsPageListParam goodsPageListParam) throws IOException {
        Integer shopId = getShopId();
        assemblyGoodsLabelParam(goodsPageListParam);
        EsSearchParam param = goodsParamConvertEsGoodsParam(goodsPageListParam,shopId);
        PageResult<EsGoods> pageResult = searchGoodsPageByParamForPage(param);
        return esPageConvertProductVoPage(pageResult);
    }

    /**
     * 对于admin调用 es查询接口传入的param进行预处理
     * @param goodsPageListParam es查询接口传入的param
     */
    private void assemblyGoodsLabelParam(GoodsPageListParam goodsPageListParam){
        if(goodsPageListParam.getLabelId() != null){
            goodsPageListParam.setGoodsIds(esGoodsLabelSearchService.getGoodsIdsByLabelIds(Lists.newArrayList(goodsPageListParam.getLabelId()),EsGoodsConstant.GOODS_SEARCH_PAGE));
            goodsPageListParam.setLabelId(null);
        }
    }

    public List<Integer> getGoodsIdsByParam(GoodsPageListParam goodsPageListParam) throws IOException {
        Integer shopId = getShopId();
        goodsPageListParam.setPageRows(10000);
        if(goodsPageListParam.getLabelId() != null){
            goodsPageListParam.setGoodsIds(esGoodsLabelSearchService.getGoodsIdsByLabelIds(Lists.newArrayList(goodsPageListParam.getLabelId()),EsGoodsConstant.GOODS_SEARCH_PAGE));
            goodsPageListParam.setLabelId(null);
        }
        EsSearchParam param = goodsParamConvertEsGoodsParam(goodsPageListParam,shopId);
        PageResult<EsGoods> pageResult = searchGoodsPageByParamForPage(param);
        return pageResult.dataList.stream().map(EsGoods::getGoodsId).collect(Collectors.toList());
    }

    private PageResult<GoodsPageListVo> esPageConvertVoPage(PageResult<EsGoods> esPage){

        PageResult<GoodsPageListVo> result = new PageResult<>();
        result.setPage(esPage.getPage());
        List<EsGoods> esGoodsList = esPage.getDataList();
        List<GoodsPageListVo> voList = new ArrayList<>(esGoodsList.size());
        if( !esGoodsList.isEmpty() ){
            Map<Integer,List<EsGoodsLabel>> labelMap = getGoodsLabel(
                esGoodsList.stream().map(EsGoods::getGoodsId).collect(Collectors.toList())
            );
            esGoodsList.forEach(x-> {
                GoodsPageListVo vo = CONVERT.convert(x);
                if( !labelMap.isEmpty() && labelMap.containsKey(vo.getGoodsId()) ){
                    //指定标签
                    List<GoodsLabelSelectListVo> specifiedLabelVos = Lists.newLinkedList();
                    //普通标签
                    List<GoodsLabelSelectListVo> ordinaryLabelVos = Lists.newLinkedList();
                    labelMap.get(vo.getGoodsId()).forEach(y->{
                        if(GoodsLabelCoupleTypeEnum.GOODSTYPE.getCode().equals(y.getType()) ){
                            specifiedLabelVos.add(new GoodsLabelSelectListVo(y.getId(),y.getName()));
                        }else{
                            ordinaryLabelVos.add(new GoodsLabelSelectListVo(y.getId(),y.getName()));
                        }
                    });
                    vo.setGoodsPointLabels(specifiedLabelVos);
                    vo.setGoodsNormalLabels(Sets.newHashSet(ordinaryLabelVos.iterator()));
                }
                voList.add(vo);
            });

        }
        result.setDataList(voList);
        return result;
    }
    private PageResult<GoodsPageListVo> esPageConvertProductVoPage(PageResult<EsGoods> esPage){

        PageResult<GoodsPageListVo> result = new PageResult<>();
        result.setPage(esPage.getPage());
        List<EsGoods> esGoodsList = esPage.getDataList();
        List<GoodsPageListVo> voList = Lists.newArrayList();
        if( !esGoodsList.isEmpty() ){
            Map<Integer,List<EsGoodsLabel>> labelMap = getGoodsLabel(
                esGoodsList.stream().map(EsGoods::getGoodsId).collect(Collectors.toList())
            );
            esGoodsList.forEach(x-> {
                List<GoodsPageListVo> vos = PRODUCT_CONVERT.convert(x);
                vos.forEach(vo->{
                    if( !labelMap.isEmpty() && labelMap.containsKey(vo.getGoodsId()) ){
                        List<GoodsLabelSelectListVo> labelVos = Lists.newLinkedList();
                        labelMap.get(vo.getGoodsId()).forEach(y->labelVos.add(new GoodsLabelSelectListVo(y.getId(),y.getName())));
                        vo.setGoodsPointLabels(labelVos);
                    }
                    voList.add(vo);
                });

            });

        }
        result.setDataList(voList);
        return result;
    }

    private Map<Integer,List<EsGoodsLabel>> getGoodsLabel(List<Integer> goodsIds){
        try {
           return esGoodsLabelSearchService.getGoodsLabelByGoodsId(goodsIds, EsGoodsConstant.ADMIN_GOODS_LIST_PAGE);
        } catch (IOException e) {
            e.printStackTrace();
            return Maps.newHashMap();
        }

    }


}
