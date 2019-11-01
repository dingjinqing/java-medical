package com.vpu.mp.service.shop.activity.processor.grade;

import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.wxapp.activity.capsule.ActivityGoodsListCapsule;
import com.vpu.mp.service.pojo.wxapp.activity.info.grade.GradeCardForListInfo;
import com.vpu.mp.service.pojo.wxapp.activity.param.ActivityGoodsListMpParam;
import com.vpu.mp.service.shop.activity.dao.MemberCardProcessorDao;
import com.vpu.mp.service.shop.activity.processor.ActivityGoodsListProcessor;
import com.vpu.mp.service.shop.activity.processor.ActivityProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 李晓冰
 * @date 2019年10月31日
 */
public class GradeCardForListProcessor  extends MemberCardProcessorDao implements ActivityGoodsListProcessor<GradeCardForListInfo> {
    @Override
    public int getPriority() {
        return GoodsConstant.ACTIVITY_MEMBER_GRADE_PRIORITY;
    }

    @Override
    public ActivityGoodsListMpParam filterParam(List<ActivityGoodsListCapsule> capsules) {
        ActivityGoodsListMpParam param = new ActivityGoodsListMpParam();
        List<Integer> goodsIds = new ArrayList<>();

        capsules.forEach(capsule->{
            // 如果商品是1 3 5 10 活动，且商品已被首单特惠处理过了则跳过会员价处理
            if (ActivityProcessor.isGoodsTypeIn13510(capsule.getGoodsType())||
                capsule.getProcessedTypes().contains(GoodsConstant.ACTIVITY_TYPE_FIRST_SPECIAL)){
                return;
            }
            goodsIds.add(capsule.getCapsuleId());
        });
        param.setGoodsIds(goodsIds);
        return param;
    }

    @Override
    public Map<Integer, GradeCardForListInfo> getActivityInfo(ActivityGoodsListMpParam param) {
        return getGoodsGradeCardForListInfo(param.getUserId(),param.getGoodsIds());
    }

    @Override
    public void process(Map<Integer, GradeCardForListInfo> activityInfos, List<ActivityGoodsListCapsule> capsules) {
        capsules.forEach(capsule->{
            Integer goodsId = capsule.getCapsuleId();
            GradeCardForListInfo grade = activityInfos.get(goodsId);
            if (grade == null) {
                return;
            }
            capsule.getActivities().add(grade);
            capsule.getProcessedTypes().add(grade.getActivityType());
        });
    }
}
