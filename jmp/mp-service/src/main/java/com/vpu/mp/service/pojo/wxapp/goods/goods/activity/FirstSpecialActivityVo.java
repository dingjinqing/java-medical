package com.vpu.mp.service.pojo.wxapp.goods.goods.activity;

import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 李晓冰
 * @date 2019年10月28日
 * 首单特惠商品活动信息 activityType=18
 */
public class FirstSpecialActivityVo extends ActivityBaseVo {
    public FirstSpecialActivityVo() {
        activityType = GoodsConstant.ACTIVITY_TYPE_FIRST_SPECIAL;
    }

    /** 1 表示活动不限制开始结束时间，0 限制开始结束时间 */
    @Getter
    @Setter
    private Byte isForever;
}
