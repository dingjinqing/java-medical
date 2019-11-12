package com.vpu.mp.service.pojo.wxapp.goods.goods.detail;

import lombok.Data;

/**
 * 小程序-商品详情参数
 * @author 李晓冰
 * @date 2019年11月07日
 */
@Data
public class GoodsDetailMpParam {
    private Integer goodsId;

    private Integer userId;

    /** 指定该商品的详情页营销活动id */
    private Integer activityId;
    /**指定该商品的详情页营销活动类型*/
    private Byte activityType;
}
