package com.vpu.mp.service.shop.activity.processor;

import com.vpu.mp.service.pojo.wxapp.activity.capsule.ActivityGoodsListCapsule;
import com.vpu.mp.service.pojo.wxapp.activity.info.ProcessorDataInfo;
import com.vpu.mp.service.pojo.wxapp.activity.param.GoodsBaseCapsuleParam;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 李晓冰
 * @date 2019年10月29日
 */
public interface ActivityGoodsListProcessor{

    Byte getPriority();

    default GoodsBaseCapsuleParam filterParamForList(List<ActivityGoodsListCapsule> capsules, Integer userId){
        GoodsBaseCapsuleParam param = filterParamForList(capsules);
        param.setUserId(userId);
        return param;
    }

    default GoodsBaseCapsuleParam filterParamForList(List<ActivityGoodsListCapsule> capsules){
        GoodsBaseCapsuleParam param = new GoodsBaseCapsuleParam();
        List<Integer> goodsIds = capsules.stream().map(ActivityGoodsListCapsule::getGoodsId).collect(Collectors.toList());
        param.setGoodsIds(goodsIds);
        return param;
    }

    Map<Integer,? extends ProcessorDataInfo> getActivityInfoForList(GoodsBaseCapsuleParam param);

    void processForList(Map<Integer,? extends ProcessorDataInfo> activityInfos, List<ActivityGoodsListCapsule> capsules);
}
