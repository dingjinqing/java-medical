package com.vpu.mp.service.shop.activity.processor;

import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.wxapp.activity.capsule.ActivityGoodsListCapsule;
import com.vpu.mp.service.pojo.wxapp.activity.capsule.GoodsDetailMpCapsule;
import com.vpu.mp.service.pojo.wxapp.activity.info.GradeCardProcessorDataInfo;
import com.vpu.mp.service.pojo.wxapp.activity.info.ProcessorDataInfo;
import com.vpu.mp.service.pojo.wxapp.activity.param.GoodsBaseCapsuleParam;
import com.vpu.mp.service.pojo.wxapp.activity.param.GoodsDetailCapsuleParam;
import com.vpu.mp.service.shop.activity.dao.MemberCardProcessorDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 李晓冰
 * @date 2019年10月31日
 */
@Service
public class GradeCardProcessor implements ActivityGoodsListProcessor,GoodsDetailProcessor,ActivityCartListStrategy{

    @Autowired
    MemberCardProcessorDao memberCardProcessorDao;

    /*****************商品列表处理*******************/
    @Override
    public Byte getPriority() {
        return GoodsConstant.ACTIVITY_MEMBER_GRADE_PRIORITY;
    }

    @Override
    public GoodsBaseCapsuleParam filterParamForList(List<ActivityGoodsListCapsule> capsules) {
        GoodsBaseCapsuleParam param = new GoodsBaseCapsuleParam();
        List<Integer> goodsIds = new ArrayList<>();

        capsules.forEach(capsule -> {
            // 如果商品是1 3 5 10 活动，且商品已被首单特惠处理过了则跳过会员价处理
            if (GoodsConstant.isGoodsTypeIn13510(capsule.getGoodsType()) ||
                capsule.getProcessedTypes().contains(GoodsConstant.ACTIVITY_TYPE_FIRST_SPECIAL)) {
                return;
            }
            goodsIds.add(capsule.getGoodsId());
        });
        param.setGoodsIds(goodsIds);
        return param;
    }

    @Override
    public Map<Integer, GradeCardProcessorDataInfo> getActivityInfoForList(GoodsBaseCapsuleParam param) {
        return memberCardProcessorDao.getGoodsGradeCardForListInfo(param.getUserId(), param.getGoodsIds());
    }

    @Override
    public void processForList(Map<Integer,? extends ProcessorDataInfo> activityInfos, List<ActivityGoodsListCapsule> capsules) {
        capsules.forEach(capsule -> {
            Integer goodsId = capsule.getGoodsId();
            ProcessorDataInfo activity = activityInfos.get(goodsId);
            if (activity == null) {
                return;
            }
            // 已被限时降价处理
            if (capsule.getProcessedTypes().contains(GoodsConstant.ACTIVITY_REDUCE_PRICE_PRIORITY)) {
                // 会员价比限时降价价格低则将限时降价的处理信息删除
                if (activity.getDataPrice().compareTo(capsule.getRealPrice()) < 0) {
                    capsule.getProcessedTypes().remove(GoodsConstant.ACTIVITY_REDUCE_PRICE_PRIORITY);
                    List<ProcessorDataInfo> collect = capsule.getActivities().stream().filter(x -> !GoodsConstant.ACTIVITY_REDUCE_PRICE_PRIORITY .equals(x.getDataType())).collect(Collectors.toList());
                    capsule.setActivities(collect);
                } else {// 没有限时降价的价格低
                    return;
                }
            }

            capsule.setRealPrice(activity.getDataPrice());
            // 如果商品是会员专享的话则价格显示会员价的价格，但是提示信息显示会员专享（ps:filterParam处已经过滤掉了首单特惠）
            if (!capsule.getProcessedTypes().contains(GoodsConstant.ACTIVITY_TYPE_MEMBER_EXCLUSIVE)) {
                capsule.getActivities().add(activity);
            }
            capsule.getProcessedTypes().add(GoodsConstant.ACTIVITY_TYPE_MEMBER_EXCLUSIVE);
        });
    }


    /*****************商品详情处理******************/
    @Override
    public void processGoodsDetail(GoodsDetailMpCapsule capsule, GoodsDetailCapsuleParam param) {
        List<GradeCardProcessorDataInfo> goodsGradeGradePrice = memberCardProcessorDao.getGoodsGradeGradePrice(param.getUserId(), param.getGoodsId());
        capsule.setGradeCard(goodsGradeGradePrice);
    }
    /**
     * 购物车
     */
    @Override
    public void doCartOperation() {

    }

}
