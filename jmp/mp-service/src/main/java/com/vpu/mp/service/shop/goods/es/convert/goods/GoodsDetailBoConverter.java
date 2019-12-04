package com.vpu.mp.service.shop.goods.es.convert.goods;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.ReferenceType;
import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsDetailMpBo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.GoodsPrdMpVo;
import com.vpu.mp.service.shop.goods.es.goods.EsGoods;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;

/**
 * 小程序商品详情页数据(ElasticSearch -> GoodsDetailMpBo)转换器
 * @author 卢光耀
 * @date 2019/11/18 3:42 下午
 *
*/
public class GoodsDetailBoConverter implements EsGoodsConvertInterface<GoodsDetailMpBo> {

    private final static ObjectMapper mapper = new ObjectMapper();
    @Override
    public GoodsDetailMpBo convert(EsGoods esGoods) {
        GoodsDetailMpBo bo = new GoodsDetailMpBo();
        copyValues(bo,esGoods);
        // vip level price
        assemblyGrdPrice(esGoods,bo);

        bo.setBaseSale(esGoods.getAddSaleNum());
        bo.setIsOnSale(esGoods.getIsOnSale());
        bo.setCatId(esGoods.getCatId());
        bo.setSortId(esGoods.getSortId());
        if( StringUtils.isNotBlank(esGoods.getPrdJson()) ){
            bo.setProducts(strToGoodsPrdMpVos(esGoods.getPrdJson()));
        }

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

    private void assemblyGrdPrice(EsGoods esGoods,GoodsDetailMpBo bo){
        List<GoodsDetailMpBo.GradePrd> gradePrdList = Lists.newArrayList();
        String vipGeneral = "v";
        for (int i = 0; i < 9; i++) {
            try {
                String vipLevel = vipGeneral + i;
                Field f = EsGoods.class.getDeclaredField(vipLevel);
                f.setAccessible(true);
                //prd:price;prd:price;
                if( f.get(esGoods) != null ){
                    String priceStr = f.get(esGoods).toString();
                    String[] prdPrices = priceStr.split(";");
                    for (String prdPrice : prdPrices) {
                        String[] prdAndPrice = prdPrice.split(":");
                        GoodsDetailMpBo.GradePrd gradePrd = new GoodsDetailMpBo.GradePrd();
                        gradePrd.setGrade(vipLevel);
                        gradePrd.setPrdId(Integer.valueOf(prdAndPrice[0]));
                        gradePrd.setGradePrice(BigDecimal.valueOf(Double.parseDouble(prdAndPrice[1])));
                        gradePrdList.add(gradePrd);
                    }
                    if( !gradePrdList.isEmpty() ){
                        bo.setGradeCardPrice(gradePrdList);
                    }
                }

            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

    }
    private List<GoodsPrdMpVo> strToGoodsPrdMpVos(String jsonStr){
        try {
            return mapper.readValue(jsonStr,new TypeReference<List<GoodsPrdMpVo>>(){});
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
