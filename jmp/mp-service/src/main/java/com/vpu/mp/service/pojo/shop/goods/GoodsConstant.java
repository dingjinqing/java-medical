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
     * 是推荐品牌,普通品牌，推荐分类，普通分类码
     */
    public static final Byte RECOMMEND_BRAND = 1;
    public static final Byte NORMAL_BRAND = 0;
    public static final Byte RECOMMEND_SORT = 1;
    public static final Byte NORMAL_SORT = 0;
    /**
     * 未关联品牌分类时的分类id
     */
    public static final Integer NO_CLASSIFY_ID = 0;

    /**
     * 一级分类的父节点id值：0
     */
    public static final Integer ROOT_PARENT_ID = 0;

    /**
     * 展示售罄商品
     */
    public static final Byte SOLD_OUT_GOODS_SHOW = 1;

    /**
     * 小程序-商品分类页面-目录项类型码
     * 1全部品牌，2推荐品牌，3推荐品牌列表展示，4推荐品牌按分类展示，
     * 5推荐分类，6普通分类，7商品内容
     */

    public static final Byte ALL_BRAND_TYPE  = 1;
    public static final Byte RECOMMEND_BRAND_TYPE  = 2;
    public static final Byte RECOMMEND_BRAND_LIST_TYPE=3;
    public static final Byte RECOMMEND_BRAND_CLASSIFY_TYPE=4;
    public static final Byte RECOMMEND_SORT_TYPE  = 5;
    public static final Byte NORMAL_SORT_TYPE  = 6;
    public static final Byte GOODS_TYPE  = 7;
}
