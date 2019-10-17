package com.vpu.mp.service.pojo.wxapp.cart;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 孔德成
 * @date 2019/10/16 11;49
 */
@Data
public class WxAppCartGoods {
    // 购物车;列表id
    private Integer rec_id;
    // 商品id
    private Integer goods_id;
    // 商品名称
    private String goods_name;
// 规格id
    private Integer product_id;
    // 是否选中
    private Byte is_checked;
    // 商品规格
    private String goods_specs;
    // 商品价格
    private BigDecimal goods_price;
    // 购物车商品价格
    private BigDecimal cart_goods_price;
    /**商品活动类型  1：加价购主商品， 2： 满折满减*/
    private Byte action;
    // 关联ID 加价购ID， 满折满减ID
    private Integer identity_id;
    /**扩展字段: 如：换购挡位ID*/
    private Integer extend_id;
    /**
     * 商品数量
     */
    private Integer goods_number;
    /**
     * 商品数量
     */
    private String goods_img;
    /**
     * 用户id
     */
    private Integer user_id;
    /**
     *  规格数量
     */
    private Integer prd_number;
    /**
     * 图片
     */
    private String prd_img;
    /**
     * 删除标识
     */
    private Byte del_flag;
    private Integer limit_buy_num;
    private Integer limit_max_num;
    private Integer goods_type;
    private Byte is_delete;
    private Byte is_on_sale;
    private String tip;
}
