package com.vpu.mp.service.shop.activity.processor;

import com.vpu.mp.service.pojo.wxapp.activity.capsule.ActivityGoodsListCapsule;
import com.vpu.mp.service.pojo.wxapp.activity.info.ActivityForListInfo;
import com.vpu.mp.service.pojo.wxapp.activity.param.ActivityGoodsListMpParam;

/**
 * @author 李晓冰
 * @date 2019年10月29日
 */
public interface ActivityGoodsListProcessor<V extends ActivityForListInfo> extends
    ActivityProcessor<ActivityGoodsListMpParam, ActivityGoodsListCapsule,V> {

    @Override
    default ActivityGoodsListMpParam filterParam() {
        return new ActivityGoodsListMpParam();
    }
}
