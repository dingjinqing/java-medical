package com.vpu.mp.service.shop.activity.processor;

import com.vpu.mp.service.pojo.wxapp.activity.capsule.ActivityGoodsListCapsule;
import com.vpu.mp.service.pojo.wxapp.activity.info.ProcessorDataInfo;
import com.vpu.mp.service.pojo.wxapp.activity.info.GoodsCommentProcessorDataInfo;
import com.vpu.mp.service.pojo.wxapp.activity.param.ActivityGoodsListMpParam;
import com.vpu.mp.service.shop.activity.dao.GoodsCommentProcessorDao;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 李晓冰
 * @date 2019年11月04日
 */
@Service
public class GoodsCommentProcessor extends GoodsCommentProcessorDao implements ActivityGoodsListProcessor{
    @Override
    public Byte getPriority() {
        return 0;
    }

    @Override
    public ActivityGoodsListMpParam filterParamForList(List<ActivityGoodsListCapsule> capsules) {
        ActivityGoodsListMpParam param = new ActivityGoodsListMpParam();
        List<Integer> goodsIds = capsules.stream().map(ActivityGoodsListCapsule::getGoodsId).collect(Collectors.toList());
        param.setGoodsIds(goodsIds);
        return param;
    }

    @Override
    public Map<Integer, GoodsCommentProcessorDataInfo> getActivityInfoForList(ActivityGoodsListMpParam param) {
        Map<Integer, Long> goodsCommentNumInfo = getGoodsCommentNumInfo(param.getGoodsIds());
        Map<Integer, GoodsCommentProcessorDataInfo> returnMap = new HashMap<>();
        goodsCommentNumInfo.forEach((key,value)-> returnMap.put(key,new GoodsCommentProcessorDataInfo(value.intValue())));
        return returnMap;
    }

    @Override
    public void processForList(Map<Integer,? extends ProcessorDataInfo> activityInfos, List<ActivityGoodsListCapsule> capsules) {
        capsules.forEach(capsule->{
            Integer goodsId = capsule.getGoodsId();
            GoodsCommentProcessorDataInfo comment = (GoodsCommentProcessorDataInfo) activityInfos.get(goodsId);
            if (comment == null) {
                return;
            }
            capsule.setCommentNum(comment.getCommentNum());
        });
    }
}
