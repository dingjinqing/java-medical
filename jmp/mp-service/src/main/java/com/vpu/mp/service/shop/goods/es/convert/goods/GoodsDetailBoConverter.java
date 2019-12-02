package com.vpu.mp.service.shop.goods.es.convert.goods;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.JsonObject;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsDetailMpBo;
import com.vpu.mp.service.shop.goods.es.goods.EsGoods;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 小程序商品详情页数据(ElasticSearch -> GoodsDetailMpBo)转换器
 * @author 卢光耀
 * @date 2019/11/18 3:42 下午
 *
*/
public class GoodsDetailBoConverter implements EsGoodsConvertInterface<GoodsDetailMpBo> {
    @Override
    public GoodsDetailMpBo convert(EsGoods esGoods) {
        GoodsDetailMpBo bo = new GoodsDetailMpBo();
        copyValues(bo,esGoods);
        List<String> secondaryGoodsImages = esGoods.getSecondaryGoodsImages();
        if( secondaryGoodsImages != null && !secondaryGoodsImages.isEmpty() ){
            bo.setGoodsImgs(secondaryGoodsImages);
        }
        if( StringUtils.isNotBlank(esGoods.getVideoInfo()) ){
            JsonNode node = Util.toJsonNode(esGoods.getVideoInfo());
            if (node != null) {
                bo.setGoodsVideoId(node.get("id").asInt());
                bo.setGoodsVideoImg(node.get("img").asText());
                bo.setGoodsVideoSize(node.get("size").asDouble());
                bo.setGoodsVideo(node.get("url").asText());
                bo.setVideoWidth(node.get("width").asInt());
                bo.setVideoHeight(node.get("height").asInt());
            }
        }
        bo.setDeliverTemplateId(esGoods.getFreightTemplateId());
        bo.setLimitBuyNum(esGoods.getLimitBuyNum());
        bo.setLimitMaxNum(esGoods.getLimitMaxNum());
        bo.setIsExclusive(esGoods.getIsCardExclusive());
        bo.setIsPageUp(esGoods.getIsPageUp());
        bo.setGoodsDesc(esGoods.getGoodsDesc());
        bo.setGoodsAd(esGoods.getGoodsAd());
        bo.setBrandId(esGoods.getBrandId());
        bo.setBrandName(esGoods.getBrandName());
        bo.setGoodsWeight(esGoods.getGoodsWeight());
//        o.getAsString()

        return bo;
    }

    public static void main(String[] args) {
        JsonObject o = new JsonObject();
        o.addProperty("a","2");
        o.addProperty("b","3");
        o.addProperty("c","4");
        System.out.println(o.getAsString());
        System.out.println(o.get("a"));
    }
}
