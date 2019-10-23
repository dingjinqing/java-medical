package com.vpu.mp.service.pojo.wxapp.goods.goods.activity;

import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;

/**
 * @author 李晓冰
 * @date 2019年10月23日
 * 小程序-拼团活动返回信息类 activityType=1
 */
public class GroupBuyActivityVo extends ActivityBaseVo{
    public GroupBuyActivityVo() {
        activityType = GoodsConstant.GOODS_TYPE_GROUP_BUY;
    }
}
