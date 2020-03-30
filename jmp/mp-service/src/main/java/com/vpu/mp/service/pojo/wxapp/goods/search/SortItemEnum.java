package com.vpu.mp.service.pojo.wxapp.goods.search;

import com.vpu.mp.service.pojo.shop.goods.es.EsSearchName;

/**
 *
 * @author 李晓冰
 * @date 2019年12月10日
 * 可选排序项
 */
public enum SortItemEnum {
    /** 商品销量0*/
    SALE_NUM(EsSearchName.GOODS_SALE_NUM),

    /** 商品价格1*/
    PRICE(EsSearchName.SHOW_PRICE),

    /** 上新时间2*/
    ADD_TIME(EsSearchName.UPDATE_TIME),

    /** 评论数量3*/
    COMMENT_NUM(EsSearchName.COMMENT_NUM),

    /** 7天内访问数量4*/
    PV(EsSearchName.PV),

    /** 上架时间5*/
    SALE_TIME(EsSearchName.ADD_ES_TIME);

    private String esName;

    SortItemEnum(String esName){
        this.esName = esName;
    }

    public String getEsName(){
        return this.esName;
    }
}
