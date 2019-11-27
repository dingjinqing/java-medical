package com.vpu.mp.service.shop.goods.es;

import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.goods.es.EsSearchParam;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsDetailMpBo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsListMpBo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.list.GoodsListMpParam;
import com.vpu.mp.service.shop.goods.es.convert.EsConvertFactory;
import com.vpu.mp.service.shop.goods.es.convert.goods.EsGoodsConvertInterface;
import com.vpu.mp.service.shop.goods.es.convert.goods.GoodsDetailBoConverter;
import com.vpu.mp.service.shop.goods.es.convert.goods.GoodsListMpBoConverter;
import com.vpu.mp.service.shop.goods.es.convert.param.GoodsListMpConverter;
import com.vpu.mp.service.shop.goods.es.goods.EsGoods;
import lombok.extern.slf4j.Slf4j;
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

    private static final EsGoodsConvertInterface<GoodsListMpBo> LIST_CONVERT =new GoodsListMpBoConverter();

    private static final EsGoodsConvertInterface<GoodsDetailMpBo> DETAIL_CONVERT =new GoodsDetailBoConverter();
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

}
