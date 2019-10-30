package com.vpu.mp.service.shop.goods.es;

/**
 * EsGoods常量类
 * @author 卢光耀
 * @date 2019/10/10 10:11 上午
 *
*/
public class EsGoodsConstant {

    public static final String INDEX_NAME = "es_goods";

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
