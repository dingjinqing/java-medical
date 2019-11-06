package com.vpu.mp.service.shop.activity.processor;

import com.vpu.mp.service.pojo.wxapp.activity.capsule.ActivityGoodsListCapsule;
import com.vpu.mp.service.pojo.wxapp.activity.info.ActivityForListInfo;
import com.vpu.mp.service.pojo.wxapp.activity.param.ActivityGoodsListMpParam;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 李晓冰
 * @date 2019年10月29日
 */
public interface ActivityGoodsListProcessor{

    Byte getPriority();

    default ActivityGoodsListMpParam filterParamForList(List<ActivityGoodsListCapsule> capsules,Integer userId){
        ActivityGoodsListMpParam param = filterParamForList(capsules);
        param.setUserId(userId);
        return param;
    }

    default ActivityGoodsListMpParam filterParamForList(List<ActivityGoodsListCapsule> capsules){
        ActivityGoodsListMpParam param = new ActivityGoodsListMpParam();
        List<Integer> goodsIds = capsules.stream().map(ActivityGoodsListCapsule::getGoodsId).collect(Collectors.toList());
        param.setGoodsIds(goodsIds);
        return param;
    }

    Map<Integer,? extends ActivityForListInfo> getActivityInfoForList(ActivityGoodsListMpParam param);

    void processForList(Map<Integer,? extends ActivityForListInfo> activityInfos,List<ActivityGoodsListCapsule> capsules);
}
