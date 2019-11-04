package com.vpu.mp.service.shop.activity.processor.list;

import com.vpu.mp.service.pojo.wxapp.activity.capsule.ActivityGoodsListCapsule;
import com.vpu.mp.service.pojo.wxapp.activity.info.list.GoodsCommentForListInfo;
import com.vpu.mp.service.pojo.wxapp.activity.param.ActivityGoodsListMpParam;
import com.vpu.mp.service.shop.activity.dao.GoodsCommentProcessorDao;
import com.vpu.mp.service.shop.activity.processor.ActivityGoodsListProcessor;
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
public class GoodsCommentForListProcessor extends GoodsCommentProcessorDao implements ActivityGoodsListProcessor<GoodsCommentForListInfo> {
    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public ActivityGoodsListMpParam filterParam(List<ActivityGoodsListCapsule> capsules) {
        ActivityGoodsListMpParam param = new ActivityGoodsListMpParam();
        List<Integer> goodsIds = capsules.stream().map(x -> x.getGoodsId()).collect(Collectors.toList());
        param.setGoodsIds(goodsIds);
        return param;
    }

    @Override
    public Map<Integer, GoodsCommentForListInfo> getActivityInfo(ActivityGoodsListMpParam param) {
        Map<Integer, Long> goodsCommentNumInfo = getGoodsCommentNumInfo(param.getGoodsIds());
        Map<Integer,GoodsCommentForListInfo> returnMap = new HashMap<>();
        goodsCommentNumInfo.forEach((key,value)->{
            returnMap.put(key,new GoodsCommentForListInfo(value.intValue()));
        });
        return returnMap;
    }

    @Override
    public void process(Map<Integer, GoodsCommentForListInfo> activityInfos, List<ActivityGoodsListCapsule> capsules) {
        capsules.forEach(capsule->{
            Integer goodsId = capsule.getGoodsId();
            GoodsCommentForListInfo comment = activityInfos.get(goodsId);
            if (comment == null) {
                return;
            }
            capsule.setCommentNum(comment.getCommentNum());
        });
    }
}
