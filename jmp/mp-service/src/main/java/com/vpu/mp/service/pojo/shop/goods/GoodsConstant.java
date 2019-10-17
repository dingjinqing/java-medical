package com.vpu.mp.service.pojo.shop.goods;

/**
 * @author 李晓冰
 * @date 2019年10月14日
 * 商品及相关类的通用常量对应类
 */
public class GoodsConstant {
    /**
     * 销售状态：在售
     */
    public static final Byte ON_SALE = 1;

    /**
     * 会员专享状态：是
     */
    public static final Byte CARD_EXCLUSIVE = 1;

    /**
     * 可在商品列表展示的标签
     */
    public static final Byte GOODS_LIST = 1;

    /**
     * 是推荐品牌
     */
    public static final Byte RECOMMEND_BRAND = 1;

    /**
     * 未关联品牌分类时的分类id
     */
    public static final Integer NO_CLASSIFY_ID = 0;
}
