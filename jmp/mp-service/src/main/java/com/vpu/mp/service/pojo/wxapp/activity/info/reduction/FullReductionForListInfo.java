package com.vpu.mp.service.pojo.wxapp.activity.info.reduction;

import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.wxapp.activity.info.ActivityForListInfo;

/**
 * @author 李晓冰
 * @date 2019年10月30日
 */
public class FullReductionForListInfo extends ActivityForListInfo {
    public FullReductionForListInfo() {
        super();
        activityType = GoodsConstant.ACTIVITY_TYPE_FULL_REDUCTION;
    }
}
