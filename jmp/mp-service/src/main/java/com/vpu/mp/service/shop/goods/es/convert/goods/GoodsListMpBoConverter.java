package com.vpu.mp.service.shop.goods.es.convert.goods;

import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsListMpBo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.list.GoodsLabelMpVo;
import com.vpu.mp.service.shop.goods.es.goods.EsGoods;

public class GoodsListMpBoConverter implements EsGoodsConvertInterface<GoodsListMpBo> {
    @Override
    public GoodsListMpBo convert(EsGoods esGoods) {
        GoodsListMpBo bo = new GoodsListMpBo();
        GoodsLabelMpVo labelVo = new GoodsLabelMpVo();
        copyValues(bo,esGoods);
//        labelVo.setName(esGoods.getGoodsLabel());
//        bo.setLabel();
//        bo.setCommentNum();
        bo.setGoodsNumber(esGoods.getGoodsNumber());
        bo.setDefaultPrd(esGoods.getDefPrd());
        bo.setCommentNum(esGoods.getCommentNum());
        bo.setPrdMaxPrice(esGoods.getMaxSpecPrdPrices());
        bo.setCatId(esGoods.getCatId());
        bo.setBrandId(esGoods.getBrandId());
        bo.setGoodsId(esGoods.getGoodsId());
        bo.setActivityType(esGoods.getGoodsType());
        bo.setGoodsImg(esGoods.getGoodsImg());
        bo.setSortId(esGoods.getSortId());
        bo.setBaseSale(esGoods.getBaseSale());
        bo.setMarketPrice(esGoods.getMarketPrice());
        bo.setShopPrice(esGoods.getShopPrice());

        //设置是通过es返回的数据
        bo.setIsDisposedByEs(true);
        return bo;
    }
}
