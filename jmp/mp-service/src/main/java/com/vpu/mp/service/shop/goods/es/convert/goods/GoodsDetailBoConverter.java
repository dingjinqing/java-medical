package com.vpu.mp.service.shop.goods.es.convert.goods;

import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsDetailMpBo;
import com.vpu.mp.service.shop.goods.es.EsGoods;

/**
 * 小程序商品详情页数据(ElasticSearch -> GoodsDetailMpBo)转换器
 * @author 卢光耀
 * @date 2019/11/18 3:42 下午
 *
*/
public class GoodsDetailBoConverter implements EsGoodsConvertInterface<GoodsDetailMpBo> {
    @Override
    public GoodsDetailMpBo convert(EsGoods esGoods) {
        return null;
    }
}
