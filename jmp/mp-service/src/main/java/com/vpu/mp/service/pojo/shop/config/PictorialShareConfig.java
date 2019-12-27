package com.vpu.mp.service.pojo.shop.config;

import lombok.Data;

/**
 * 商品、活动海报分享配置
 * @author 李晓冰
 * @date 2019年12月27日
 */
@Data
public class PictorialShareConfig {
    /** 默认样式*/
    public static final Byte DEFAULT_STYLE = 1;
    /** 自定义样式和文案*/
    public static final Byte CUSTOMER_STYLE = 1;
    /** 活动分享展示方式 1默认样式 2自定义样式和文案 */
    private Byte shareAction;
    /**  shareAction=2的时候起效果，自定义文案内容*/
    private String shareDoc;

    /** 使用商品主图*/
    public static final Byte DEFAULT_IMG = 1;
    /** 自定义图片*/
    public static final Byte CUSTOMER_IMG = 2;
    /**  shareAction=2的时候起效果，使用的分享图片 1使用商品主图 2自定义图片 */
    private Byte shareImgAction;
    /** shareImgAction=2的时候起效果，分享显示的图片相对地址 */
    private String shareImg;
}
