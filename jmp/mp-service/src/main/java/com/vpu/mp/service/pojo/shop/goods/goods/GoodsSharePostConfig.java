package com.vpu.mp.service.pojo.shop.goods.goods;

import lombok.Data;

/**
 * @author 李晓冰
 * @date 2019年08月21日
 */
@Data
public class GoodsSharePostConfig {
    public static final Byte DEFAULT_ACTION=1;
    public static final Byte DEFAULT_SHARE_IMG_ACTION=1;
    /**
     * 海报样式1默认样式，2自定义样式
     */
    private Byte shareAction;

    /**
     * 文档
     */
    private String shareDoc;

    /**
     * 商品分享图方式1商品主图，2自定义
     */
    private Byte shareImgAction;

    /**
     * 自定义商品分享图地址
     */
    private String shareImgUrl;
}
