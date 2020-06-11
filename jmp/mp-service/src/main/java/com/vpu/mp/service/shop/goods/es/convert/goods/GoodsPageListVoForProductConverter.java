package com.vpu.mp.service.shop.goods.es.convert.goods;

import com.google.common.collect.Lists;
import com.vpu.mp.service.pojo.shop.goods.goods.GoodsPageListVo;
import com.vpu.mp.service.shop.goods.es.goods.EsGoods;
import com.vpu.mp.service.shop.goods.es.goods.EsGoodsProduct;
import com.vpu.mp.service.shop.goods.es.goods.product.EsGoodsProductEntity;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class GoodsPageListVoForProductConverter {
    public static GoodsPageListVo convert(EsGoodsProductEntity esGoods) {
            GoodsPageListVo vo = new GoodsPageListVo();
            BeanUtils.copyProperties(esGoods,vo);
            vo.setGoodsName(esGoods.getGoodsName()+esGoods.getPrdDesc());
            if( null != esGoods.getMaxSpecPrdPrices() ){
                vo.setPrdMaxShopPrice(esGoods.getMaxSpecPrdPrices());
            }
            vo.setPrdPrice(esGoods.getPrdRealPrice());
            if( null != esGoods.getMinSpecPrdPrices() ){
                vo.setPrdMinShopPrice(esGoods.getMinSpecPrdPrices());
            }
            vo.setIsDefaultPrd(esGoods.getDefPrd());


                vo.setPrdId(esGoods.getPrdId());
            vo.setPrdImg(esGoods.getPrdImg());
            vo.setPrdPrice(Optional.ofNullable(esGoods.getPrdRealPrice()).orElse(BigDecimal.ZERO));
            vo.setPrdNumber(Optional.ofNullable(esGoods.getPrdNumber()).orElse(0));
            if(StringUtils.isNotBlank(esGoods.getCatName())){
                String[] catName = esGoods.getCatName().split(" ");
                vo.setCatName(catName[catName.length-1]);
            }





        return vo;
    }
}
