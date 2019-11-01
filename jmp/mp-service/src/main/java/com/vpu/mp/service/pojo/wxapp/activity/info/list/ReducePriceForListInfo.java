package com.vpu.mp.service.pojo.wxapp.activity.info.list;

import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.wxapp.activity.info.ActivityForListInfo;

/**
 * @author 李晓冰
 * @date 2019年11月01日
 */
public class ReducePriceForListInfo extends ActivityForListInfo {
    public ReducePriceForListInfo() {
        super();
        activityType = GoodsConstant.ACTIVITY_TYPE_REDUCE_PRICE;
    }
}
