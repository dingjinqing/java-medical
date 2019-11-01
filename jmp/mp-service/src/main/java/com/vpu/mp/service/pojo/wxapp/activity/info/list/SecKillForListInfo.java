package com.vpu.mp.service.pojo.wxapp.activity.info.list;

import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.wxapp.activity.info.ActivityForListInfo;

/**
 * 小程序-商品列表-秒杀信息
 * @author 李晓冰
 * @date 2019年11月01日
 */
public class SecKillForListInfo extends ActivityForListInfo {
    public SecKillForListInfo() {
        super();
        activityType = GoodsConstant.ACTIVITY_TYPE_SEC_KILL;
    }
}
