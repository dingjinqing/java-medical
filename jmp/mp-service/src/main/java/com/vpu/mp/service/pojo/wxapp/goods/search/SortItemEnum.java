package com.vpu.mp.service.pojo.wxapp.goods.search;

import com.vpu.mp.service.pojo.shop.goods.es.EsSearchName;

/**
 *
 * @author 李晓冰
 * @date 2019年12月10日
 * 可选排序项
 */
public enum SortItemEnum {
    /** 不指定排序0*/
    NULL(EsSearchName.NULL),

    /** 商品销量1*/
    SALE_NUM(EsSearchName.GOODS_SALE_NUM),

    /** 商品价格2*/
    PRICE(EsSearchName.SHOW_PRICE),

    /** 上新时间3*/
    ADD_TIME(EsSearchName.CREATE_TIME),

    /** 评论数量4*/
    COMMENT_NUM(EsSearchName.COMMENT_NUM),

    /** 七天内访问数量5*/
    PV(EsSearchName.PV),

    /** 上架时间6*/
    SALE_TIME(EsSearchName.SALE_TIME);

    private String esName;

    SortItemEnum(String esName){
        this.esName = esName;
    }

    public String getEsName(){
        return this.esName;
    }
}
