package com.vpu.mp.service.shop.goods.es.convert.goods;

import com.vpu.mp.service.pojo.shop.goods.goods.GoodsPageListVo;
import com.vpu.mp.service.shop.goods.es.goods.EsGoods;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

/**
 * es
 * @author 卢光耀
 * @date 2019/11/12 11:01 上午
 *
*/
public class GoodsPageListVoConverter implements EsGoodsConvertInterface<GoodsPageListVo> {
    @Override
    public GoodsPageListVo convert(EsGoods esGoods) {
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
