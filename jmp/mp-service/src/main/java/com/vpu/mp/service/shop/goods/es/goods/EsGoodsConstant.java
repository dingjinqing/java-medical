package com.vpu.mp.service.shop.goods.es.goods;

/**
 * Es常量类
 * @author 卢光耀
 * @date 2019/10/10 10:11 上午
 *
*/
public class EsGoodsConstant {
    public static final String GOODS_INDEX_NAME = "es_goods";

    public static final String LABEL_INDEX_NAME = "es_goods_label";


    /**通用页*/
    public static final Byte GENERAL_PAGE = 0;
    /**小程序-商品详情页*/
    public static final Byte GOODS_DETAIL_PAGE = 1;
    /**小程序-商品列表页*/
    public static final Byte GOODS_LIST_PAGE = 2;
    /**小程序-商品筛选页*/
    public static final Byte GOODS_SEARCH_PAGE = 3;
    /**Admin-商品列表页*/
    public static final Byte ADMIN_GOODS_LIST_PAGE = 4;

    public static class EsGoodsSearchFact{
        public static final String GOODS_BRAND_FACT = "brand_id";

        public static final String GOODS_CATEGORY_FIRST_FACT = "first_cat_id";
        public static final String GOODS_CATEGORY_SECOND_FACT = "second_cat_id";
        public static final String GOODS_CATEGORY_THIRD_FACT = "third_cat_id";


        public static final String GOODS_SORT_FIRST_FACT = "first_sort_id";
        public static final String GOODS_SORT_SECOND_FACT = "second_sort_id";

        public static final String GOODS_LABEL_FACT = "goods_label";
    }

    public static class EsGoodsShowPriceReducePeriodAction{
        public static final Byte EVERY_DAY = 1;

        public static final Byte EVERY_MONTH = 2;

        public static final Byte EVERY_WEEK = 3;



    }
}
