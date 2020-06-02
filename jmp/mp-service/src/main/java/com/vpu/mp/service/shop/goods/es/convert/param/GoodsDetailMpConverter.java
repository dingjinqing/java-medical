package com.vpu.mp.service.shop.goods.es.convert.param;

import com.google.common.collect.Lists;
import com.vpu.mp.service.pojo.shop.goods.es.EsSearchName;
import com.vpu.mp.service.pojo.shop.goods.es.EsSearchParam;
import com.vpu.mp.service.pojo.shop.goods.es.FieldProperty;
import com.vpu.mp.service.pojo.shop.member.card.CardConstant;
import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.GoodsDetailMpParam;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * 小程序商品详情页查询条件转换
 */
public class GoodsDetailMpConverter {

    public EsSearchParam convert(GoodsDetailMpParam param){
        EsSearchParam resultParam = new EsSearchParam();
        List<FieldProperty> searchList = Lists.newArrayList();
        searchList.add(new FieldProperty(EsSearchName.GOODS_ID,Objects.requireNonNull(param.getGoodsId())));
        if(StringUtils.isNotBlank(param.getUserGrade()) && param.getUserGrade().equals(CardConstant.LOWEST_GRADE)){

        }
        resultParam.setSearchList(searchList);
        return resultParam;
    }
}
