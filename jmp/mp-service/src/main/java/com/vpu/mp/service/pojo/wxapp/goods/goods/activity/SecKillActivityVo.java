package com.vpu.mp.service.pojo.wxapp.goods.goods.activity;

import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;

/**
 * @author 李晓冰
 * @date 2019年10月23日
 * 小程序-秒杀活动返回信息类 activityType=5
 */
public class SecKillActivityVo extends ActivityBaseVo{
    public SecKillActivityVo() {
        activityType = GoodsConstant.GOODS_TYPE_SECKILL;
    }
}
