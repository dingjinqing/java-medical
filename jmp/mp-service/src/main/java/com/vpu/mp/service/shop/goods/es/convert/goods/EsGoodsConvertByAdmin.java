package com.vpu.mp.service.shop.goods.es.convert.goods;

import com.vpu.mp.service.pojo.shop.goods.goods.GoodsPageListVo;
import com.vpu.mp.service.shop.goods.es.EsGoods;
import org.springframework.beans.BeanUtils;

public class EsGoodsConvertByAdmin implements EsGoodsConvertInterface {
    @Override
    public GoodsPageListVo convertToGoodsPageListVo(EsGoods esGoods) {
        GoodsPageListVo vo = new GoodsPageListVo();
        BeanUtils.copyProperties(esGoods,vo);
        return vo;
    }
}
