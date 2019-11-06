package com.vpu.mp.service.pojo.shop.goods.es;

import lombok.Getter;

/**
 * es和goods 字段映射关系
 * @author 卢光耀
 * @date 2019/11/5 5:37 下午
 *
*/

public enum EsSearchName {
    /**
     * 商品名称
     */
    GOODS_NAME("goods_name"),
    GOODS_ID("goods_id"),
    SHOP_ID("shop_id"),
    GOODS_AD("goods_ad"),
    GOODS_SN("goods_sn"),
    CAT_ID("cat_id"),
    GOODS_IMG("goods_img"),
    UNIT("unit"),
    SORT_ID("sort_id"),
    BRAND_ID("brand_id"),
    MARKET_PRICE("market_price"),
    LIMIT_BUY_NUM("limit_buy_num"),
    ADD_SALE_NUM("add_sale_num"),
    GOODS_WEIGHT("goods_weight"),
    IS_CARD_EXCLUSIVE("is_card_exclusive"),
    IS_ON_SALE("is_on_sale"),
    SALE_TYPE("sale_type"),
    IS_PAGE_UP("is_page_up"),
    GOODS_PAGE_ID("goods_page_id"),
    GOODS_NUMBER("goods_number"),
    SHOP_PRICE("shop_price"),
    GOODS_TYPE("goods_type"),
    GOODS_SALE_NUM("goods_sale_num"),
    GOODS_COLLECT_NUM("goods_collect_num"),
    SUB_ACCOUNT_ID("sub_account_id"),
    STATE("state"),
    COST_PRICE("cost_price"),
    SOURCE("source"),
    IS_CONTROL_PRICE("is_control_price"),
    PV("pv"),
    COMMENT_NUM("comment_num"),
    BASE_SALE("base_sale"),
    V1("v1"),
    V2("v2"),
    V3("v3"),
    V4("v4"),
    V5("v5"),
    V6("v6"),
    V7("v7"),
    V8("v8"),
    V9("v9"),
    SHOW_PRICE("show_price"),
    PRD_SNS("prd_sns"),
    CAT_NAME("cat_name"),
    FIRST_CAT_ID("first_cat_id"),
    SECOND_CAT_ID("second_cat_id"),
    THIRD_CAT_ID("third_cat_id"),
    FULL_CAT_ID("full_cat_id"),
    SORT_NAME("sort_name"),
    FIRST_SORT_ID("first_sort_id"),
    SECOND_SORT_ID("second_sort_id"),
    FULL_SORT_ID("full_sort_id"),
    BRAND_NAME("brand_name"),
    GOODS_LABEL("goods_label"),
    MAX_SPEC_PRD_PRICE("max_spec_prd_price"),
    MIN_SPEC_PRD_PRICE("min_spec_prd_price");
    @Getter
    private String esName;
    EsSearchName(String esName){
        this.esName = esName;
    }



}
