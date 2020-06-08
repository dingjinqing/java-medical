package com.vpu.mp.service.shop.goods.es.convert.goods;

import com.google.common.collect.Lists;
import com.vpu.mp.service.pojo.shop.goods.goods.GoodsPageListVo;
import com.vpu.mp.service.shop.goods.es.goods.EsGoods;
import com.vpu.mp.service.shop.goods.es.goods.EsGoodsProduct;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class GoodsPageListVoForProductConverter implements EsGoodsConvertInterface<List<GoodsPageListVo>>{
    @Override
    public List<GoodsPageListVo> convert(EsGoods esGoods) {
        List<GoodsPageListVo> results = Lists.newArrayList();
        for(EsGoodsProduct product : esGoods.getPrds()){
            GoodsPageListVo vo = new GoodsPageListVo();
            BeanUtils.copyProperties(esGoods,vo);
            vo.setGoodsName(esGoods.getGoodsName()+product.getPrdDesc());
            if( null != esGoods.getMaxSpecPrdPrices() ){
                vo.setPrdMaxShopPrice(esGoods.getMaxSpecPrdPrices());
            }
            vo.setPrdPrice(product.getPrdRealPrice());
            if( null != esGoods.getMinSpecPrdPrices() ){
                vo.setPrdMinShopPrice(esGoods.getMinSpecPrdPrices());
            }
            vo.setIsDefaultPrd(esGoods.getDefPrd());

            if( esGoods.getDefPrd()!= null && esGoods.getDefPrd() && !esGoods.getPrds().isEmpty() ){
                vo.setPrdId(CollectionUtils.isNotEmpty(esGoods.getPrds())?esGoods.getPrds().get(0).getPrdId():0);
            }else{
                vo.setPrdId(product.getPrdId());
            }
            vo.setPrdTypeNum(esGoods.getPrds().size()==1?0:esGoods.getPrds().size());
            vo.setPrdImg(product.getPrdImg());
            vo.setPrdPrice(Optional.of(product.getPrdRealPrice()).orElse(BigDecimal.ZERO));
            vo.setPrdNumber(Optional.of(product.getPrdNumber()).orElse(0));
            if(StringUtils.isNotBlank(esGoods.getCatName())){
                String[] catName = esGoods.getCatName().split(" ");
                vo.setCatName(catName[catName.length-1]);
            }
            results.add(vo);
        }





        return results;
    }
}
