package com.vpu.mp.service.shop.goods.es;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.goods.es.EsSearchParam;
import com.vpu.mp.service.pojo.shop.goods.goods.GoodsPageListParam;
import com.vpu.mp.service.pojo.shop.goods.goods.GoodsPageListVo;
import com.vpu.mp.service.pojo.shop.goods.label.GoodsLabelSelectListVo;
import com.vpu.mp.service.shop.goods.es.convert.goods.EsGoodsConvertInterface;
import com.vpu.mp.service.shop.goods.es.convert.goods.GoodsPageListVoConverter;
import com.vpu.mp.service.shop.goods.es.goods.EsGoods;
import com.vpu.mp.service.shop.goods.es.goods.EsGoodsConstant;
import com.vpu.mp.service.shop.goods.es.goods.label.EsGoodsLabel;
import com.vpu.mp.service.shop.goods.es.goods.label.EsGoodsLabelSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * es搜索相关(Admin)
 * @author 卢光耀
 * @date 2019/10/25 2:04 下午
 *
*/
@Service
public class EsGoodsSearchService extends EsBaseSearchService{

    @Autowired
    EsGoodsLabelSearchService esGoodsLabelSearchService;

    private static final EsGoodsConvertInterface<GoodsPageListVo> CONVERT =new GoodsPageListVoConverter();

    public PageResult<GoodsPageListVo> searchGoodsPageByParam(GoodsPageListParam goodsPageListParam) throws IOException {

        Integer shopId = getShopId();
        EsSearchParam param = goodsParamConvertEsGoodsParam(goodsPageListParam,shopId);
        param.setQueryByPage(Boolean.TRUE);
        PageResult<EsGoods> pageResult = searchGoodsPageByParam(param);
        return esPageConvertVoPage(pageResult);
    }

    private PageResult<GoodsPageListVo> esPageConvertVoPage(PageResult<EsGoods> esPage){

        PageResult<GoodsPageListVo> result = new PageResult<>();
        result.setPage(esPage.getPage());
        List<EsGoods> esGoodsList = esPage.getDataList();
        if( !esGoodsList.isEmpty() ){
            Map<Integer,List<EsGoodsLabel>> labelMap = getGoodsLabel(
                esGoodsList.stream().map(EsGoods::getGoodsId).collect(Collectors.toList())
            );
            List<GoodsPageListVo> voList = new ArrayList<>(esGoodsList.size());
            esGoodsList.forEach(x-> {
                GoodsPageListVo vo = CONVERT.convert(x);
                if( !labelMap.isEmpty() && labelMap.containsKey(vo.getGoodsId()) ){
                    List<GoodsLabelSelectListVo> labelVos = Lists.newLinkedList();
                    labelMap.get(vo.getGoodsId()).forEach(y->labelVos.add(new GoodsLabelSelectListVo(y.getId(),y.getName())));
                    vo.setGoodsLabels(labelVos);
                }
                voList.add(vo);
            });
            result.setDataList(voList);
        }

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
