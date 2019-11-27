package com.vpu.mp.service.shop.goods.es;

import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.goods.es.EsSearchParam;
import com.vpu.mp.service.pojo.shop.goods.goods.GoodsPageListParam;
import com.vpu.mp.service.pojo.shop.goods.goods.GoodsPageListVo;
import com.vpu.mp.service.shop.goods.es.convert.goods.EsGoodsConvertInterface;
import com.vpu.mp.service.shop.goods.es.convert.goods.GoodsPageListVoConverter;
import com.vpu.mp.service.shop.goods.es.goods.EsGoods;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * es搜索相关(Admin)
 * @author 卢光耀
 * @date 2019/10/25 2:04 下午
 *
*/
@Service
public class EsGoodsSearchService extends EsBaseSearchService{


    private static final EsGoodsConvertInterface<GoodsPageListVo> CONVERT =new GoodsPageListVoConverter();

    public PageResult<GoodsPageListVo> searchGoodsPageByParam(GoodsPageListParam goodsPageListParam) throws IOException {

        Integer shopId = getShopId();
        EsSearchParam param = goodsParamConvertEsGoodsParam(goodsPageListParam,shopId);
        PageResult<EsGoods> pageResult = searchGoodsPageByParam(param);
        return esPageConvertVoPage(pageResult);
    }

    private PageResult<GoodsPageListVo> esPageConvertVoPage(PageResult<EsGoods> esPage){

        PageResult<GoodsPageListVo> result = new PageResult<>();
        result.setPage(esPage.getPage());
        List<EsGoods> esGoodsList = esPage.getDataList();
        List<GoodsPageListVo> voList = new ArrayList<>(esGoodsList.size());
        esGoodsList.forEach(x-> voList.add(CONVERT.convert(x)));
        result.setDataList(voList);
        return result;
    }


}
