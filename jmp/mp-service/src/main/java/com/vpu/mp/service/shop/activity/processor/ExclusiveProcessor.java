package com.vpu.mp.service.shop.activity.processor;

import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.shop.member.card.CardConstant;
import com.vpu.mp.service.pojo.wxapp.activity.capsule.ActivityGoodsListCapsule;
import com.vpu.mp.service.pojo.wxapp.activity.info.ProcessorDataInfo;
import com.vpu.mp.service.pojo.wxapp.activity.info.ExclusiveProcessorDataInfo;
import com.vpu.mp.service.pojo.wxapp.activity.param.GoodsBaseCapsuleParam;
import com.vpu.mp.service.shop.activity.dao.MemberCardProcessorDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author 李晓冰
 * @date 2019年10月31日
 */
@Service
public class ExclusiveProcessor implements ActivityGoodsListProcessor {
    @Autowired
    MemberCardProcessorDao memberCardProcessorDao;
    @Override
    public Byte getPriority() {
        return GoodsConstant.ACTIVITY_CARD_EXCLUSIVE_PRIORITY;
    }

    @Override
    public GoodsBaseCapsuleParam filterParamForList(List<ActivityGoodsListCapsule> capsules) {
        GoodsBaseCapsuleParam param = new GoodsBaseCapsuleParam();
        List<Integer> goodsIds = new ArrayList<>();
        List<Integer> sortIds = new ArrayList<>();
        List<Integer> catIds = new ArrayList<>();
        List<Integer> brandIds = new ArrayList<>();
        List<GoodsBaseCapsuleParam.AllIdsParam> idsParams = new ArrayList<>();
        capsules.forEach(capsule->{
            if (GoodsConstant.isGoodsTypeIn13510(capsule.getGoodsType())) {
                return;
            }
            goodsIds.add(capsule.getGoodsId());
            sortIds.add(capsule.getSortId());
            catIds.add(capsule.getCatId());
            brandIds.add(capsule.getBrandId());
           idsParams.add(new GoodsBaseCapsuleParam.AllIdsParam(capsule.getGoodsId(),capsule.getCatId(),capsule.getSortId(),capsule.getBrandId()));
        });

        param.setGoodsIds(goodsIds);
        param.setSortIds(sortIds);
        param.setCatIds(catIds);
        param.setBrandIds(brandIds);
        param.setIdsParams(idsParams);
        return param;
    }

    @Override
    public Map<Integer, ExclusiveProcessorDataInfo> getActivityInfoForList(GoodsBaseCapsuleParam param) {
        // 获取专享会员对应信息
        Map<Byte, List<Integer>> exclusiveInfo = memberCardProcessorDao.getExclusiveInfo(param.getGoodsIds(), param.getCatIds(), param.getSortIds(), param.getBrandIds());
        Map<Integer, ExclusiveProcessorDataInfo> returnMap = new HashMap<>();

        Set<Integer> goodsIds = new HashSet<>(exclusiveInfo.get(CardConstant.COUPLE_TP_GOODS));
        Set<Integer> catIds = new HashSet<>(exclusiveInfo.get(CardConstant.COUPLE_TP_PLAT));
        Set<Integer> sortIds = new HashSet<>(exclusiveInfo.get(CardConstant.COUPLE_TP_STORE));
        Set<Integer> brandIds = new HashSet<>(exclusiveInfo.get(CardConstant.COUPLE_TP_BRAND));

        // 依次判断商品是否是会员专享
        param.getIdsParams().forEach(idsParam->{
            ExclusiveProcessorDataInfo info = null;
            if (goodsIds.contains(idsParam.goodsId)){
                info = new ExclusiveProcessorDataInfo(idsParam.goodsId,CardConstant.COUPLE_TP_GOODS);
            } else if (catIds.contains(idsParam.catId)) {
                info = new ExclusiveProcessorDataInfo(idsParam.catId,CardConstant.COUPLE_TP_PLAT);
            } else if (sortIds.contains(idsParam.sortId)) {
                info = new ExclusiveProcessorDataInfo(idsParam.sortId,CardConstant.COUPLE_TP_STORE);
            } else if (brandIds.contains(idsParam.brandId)) {
                info = new ExclusiveProcessorDataInfo(idsParam.brandId, CardConstant.COUPLE_TP_BRAND);
            } else {
                return;
            }
            returnMap.put(idsParam.goodsId,info);
        });
        return returnMap;
    }

    @Override
    public void processForList(Map<Integer, ? extends ProcessorDataInfo> activityInfos, List<ActivityGoodsListCapsule> capsules) {
        capsules.forEach(capsule->{
            Integer goodsId = capsule.getGoodsId();
            ProcessorDataInfo activity = activityInfos.get(goodsId);
            if (activity == null) {
                return;
            }
            capsule.getActivities().add(activity);
            capsule.getProcessedTypes().add(GoodsConstant.ACTIVITY_TYPE_MEMBER_EXCLUSIVE);
        });
    }
}
