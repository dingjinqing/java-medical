package com.vpu.mp.service.pojo.shop.decoration.module;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 
 * @author lixinguo
 *
 */
@Data
public class ModuleGoodsGroup extends ModuleBase {
    /**
     *菜单样式radio，0顶部展示商品分组，1左侧展示商品分组
     */
    @JsonProperty("menu_style")
    private Byte menuStyle;

    /**
     *菜单位置radio，0一般样式，1滚动至顶部固定
     */
    @JsonProperty("position_style")
    private Byte positionStyle;

    /**
     *商品列表样式radio，0大图展示，1一行两个，2一行三个，3商品列表，4一行横滑
     */
    @JsonProperty("shop_style")
    private Byte shopStyle;

    /**
     *模块角度radio，0直角，1圆角
     */
    @JsonProperty("if_radius")
    private Byte ifRadius;

    /**
     *
     */
    @JsonProperty("sort_length")
    private Integer sortLength;

    /**
     *模块样式radio，1白底无边框，2边框投影，3白底有边框
     */
    @JsonProperty("module_style")
    private Byte moduleStyle;

    /**
     *
     */
    @JsonProperty("group_display")
    private Byte groupDisplay;

    /**
     *其它信息按钮下隐藏模块radio，1市场价，2销量，3评价数
     */
    @JsonProperty("show_market")
    private Byte showMarket;

    /**
     *
     */
    @JsonProperty("goods_module_bg")
    private Byte goodsModuleBg;

    /**
     *
     */
    @JsonProperty("goods_bg_color")
    private String goodsBgColor;

    /**
     * 显示商品名称
     */
    @JsonProperty("show_name")
    private Byte showName;

    /**
     * 显示商品价格
     */
    @JsonProperty("show_price")
    private Byte showPrice;

    /**
     *显示购买按钮
     */
    @JsonProperty("cart_btn")
    private Byte cartBtn;

    /**
     * 按钮形状
     */
    @JsonProperty("cart_btn_choose")
    private Byte cartBtnChoose;

    /**
     *
     */
    @JsonProperty("other_message")
    private Byte otherMessage;

    /**
     *
     */
    @JsonProperty("goods_img")
    private List<String> goodsImg;

    /**
     *
     */
    @JsonProperty("goods_name")
    private List<String> goodsName;

    /**
     *
     */
    @JsonProperty("goods_price")
    private List<BigDecimal> goodsPrice;

    /**
     *
     */
    @JsonProperty("market_price")
    private List<BigDecimal> marketPrice;

    /**
     *
     */
    @JsonProperty("goods_tag")
    private List<List<String>> goodsTag;

    /**
     *
     */
    @JsonProperty("label")
    private List<Label> label;

    @Data
    public static class Label{
        /**
         *
         */
        @JsonProperty("label_class")
        private String labelClass;

        /**
         *
         */
        @JsonProperty("label_parttern")
        private Byte labelParttern;

        /**
         *
         */
        @JsonProperty("label_name")
        private String labelName;

        /**
         *
         */
        @JsonProperty("new_label_img")
        private String newLabelImg;
    }
}
