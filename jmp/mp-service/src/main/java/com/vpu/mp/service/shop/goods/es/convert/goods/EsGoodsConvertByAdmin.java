package com.vpu.mp.service.shop.goods.es.convert.goods;

import com.vpu.mp.service.pojo.shop.goods.goods.GoodsPageListVo;
import com.vpu.mp.service.shop.goods.es.EsGoods;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

public class EsGoodsConvertByAdmin implements EsGoodsConvertInterface {
    @Override
    public GoodsPageListVo convertToGoodsPageListVo(EsGoods esGoods) {
        GoodsPageListVo vo = new GoodsPageListVo();
        BeanUtils.copyProperties(esGoods,vo);
        if( null != esGoods.getMaxSpecPrdPrices() ){
            vo.setPrdMaxShopPrice(esGoods.getMaxSpecPrdPrices());
        }
        if( null != esGoods.getMinSpecPrdPrices() ){
            vo.setPrdMinShopPrice(esGoods.getMinSpecPrdPrices());
        }
        if(StringUtils.isNotBlank(esGoods.getCatName())){
            String[] catName = esGoods.getCatName().split(" ");
            vo.setCatName(catName[catName.length-1]);
        }

        return vo;
    }
}
