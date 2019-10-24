package com.vpu.mp.service.pojo.wxapp.goods.goods.activity;

import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 李晓冰
 * @date 2019年10月24日
 * 小程序-拼团活动返回信息类 activityType=6
 */
public class ReducePriceActivityVo extends ActivityBaseVo{
    public ReducePriceActivityVo() {
        activityType = GoodsConstant.GOODS_TYPE_REDUCE_PRICE;
    }

    /**
     * 活动是否正在进行中： true 是 false 否
     */
    @Getter
    @Setter
    private Boolean isActive;
}
