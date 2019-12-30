package com.vpu.mp.service.pojo.wxapp.share;

import lombok.Data;

/**
 * 小程序-商品和各个活动分享操作基类
 * @author 李晓冰
 * @date 2019年12月27日
 */
@Data
public class GoodsShareInfo {
    public static final Byte ACTIVITY_DELETED = 1;
    public static final Byte GOODS_DELETED = 2;
    /** 分享操作返回码：0正常，1活动已删除无法使用,2活动商品已删除*/
    private Byte shareCode = 0;
    /** 活动分享展示方式 1默认样式 2自定义样式和文案 */
    private Byte shareAction;
    /** 自定义文案内容 shareAction=2的时候起效果 */
    private String shareDoc;
    /** 分享显示的图片相对地址 */
    private String imgUrl;
}
