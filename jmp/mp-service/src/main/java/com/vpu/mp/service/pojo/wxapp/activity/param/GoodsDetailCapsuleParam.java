package com.vpu.mp.service.pojo.wxapp.activity.param;

import lombok.Data;

/**
 * 商品详情处理器参数
 * @author 李晓冰
 * @date 2019年11月07日
 */
@Data
public class GoodsDetailCapsuleParam{
    private Integer userId;
    public Integer goodsId;
    public Integer catId;
    public Integer sortId;
    public Integer brandId;

    /** 指定该商品的详情页营销活动id */
    private Integer activityId;
    /**指定该商品的详情页营销活动类型*/
    private Byte activityType;
}
