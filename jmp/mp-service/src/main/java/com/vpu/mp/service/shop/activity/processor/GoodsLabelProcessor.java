package com.vpu.mp.service.shop.activity.processor;

import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.shop.goods.label.GoodsLabelCoupleTypeEnum;
import com.vpu.mp.service.pojo.wxapp.activity.capsule.ActivityGoodsListCapsule;
import com.vpu.mp.service.pojo.wxapp.activity.info.ProcessorDataInfo;
import com.vpu.mp.service.pojo.wxapp.activity.info.GoodsLabelProcessorDataInfo;
import com.vpu.mp.service.pojo.wxapp.activity.param.GoodsBaseCapsuleParam;
import com.vpu.mp.service.shop.activity.dao.GoodsLabelProcessorDao;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 李晓冰
 * @date 2019年11月04日
 */
@Service
public class GoodsLabelProcessor extends GoodsLabelProcessorDao implements ActivityGoodsListProcessor {
    @Override
    public Byte getPriority() {
        return 0;
    }

    @Override
    public GoodsBaseCapsuleParam filterParamForList(List<ActivityGoodsListCapsule> capsules) {
        GoodsBaseCapsuleParam param = new GoodsBaseCapsuleParam();
        List<Integer> goodsIds = new ArrayList<>();
        List<Integer> sortIds = new ArrayList<>();
        List<Integer> catIds = new ArrayList<>();
        List<GoodsBaseCapsuleParam.AllIdsParam> idsParams = new ArrayList<>();
        capsules.forEach(capsule->{
            goodsIds.add(capsule.getGoodsId());
            sortIds.add(capsule.getSortId());
            catIds.add(capsule.getCatId());
            idsParams.add(new GoodsBaseCapsuleParam.AllIdsParam(capsule.getGoodsId(),capsule.getCatId(),capsule.getSortId(),null));
        });

        param.setGoodsIds(goodsIds);
        param.setSortIds(sortIds);
        param.setCatIds(catIds);
        param.setIdsParams(idsParams);
        return param;
    }

    @Override
    public Map<Integer, GoodsLabelProcessorDataInfo> getActivityInfoForList(GoodsBaseCapsuleParam param) {
        Map<Byte, Map<Integer, GoodsLabelProcessorDataInfo>> goodsLabelsMap = getGoodsClosestLabelsInfo(param.getGoodsIds(), param.getCatIds(), param.getSortIds());
        List<GoodsBaseCapsuleParam.AllIdsParam> idsParams = param.getIdsParams();
        Map<Integer, GoodsLabelProcessorDataInfo> returnMap = new HashMap<>();

        idsParams.forEach(allIdsParam -> {
            Integer goodsId = allIdsParam.goodsId;
            Map<Integer, GoodsLabelProcessorDataInfo> goodsIdMap = goodsLabelsMap.get(GoodsLabelCoupleTypeEnum.GOODSTYPE.getCode());
            Map<Integer, GoodsLabelProcessorDataInfo> catIdMap = goodsLabelsMap.get(GoodsLabelCoupleTypeEnum.CATTYPE.getCode());
            Map<Integer, GoodsLabelProcessorDataInfo> sortIdMap = goodsLabelsMap.get(GoodsLabelCoupleTypeEnum.SORTTYPE.getCode());
            Map<Integer, GoodsLabelProcessorDataInfo> allGoodsMap = goodsLabelsMap.get(GoodsLabelCoupleTypeEnum.ALLTYPE.getCode());
            if (goodsIdMap.get(allIdsParam.goodsId) != null) {
                returnMap.put(goodsId,goodsIdMap.get(allIdsParam.goodsId));
            } else if (catIdMap.get(allIdsParam.catId) != null) {
                returnMap.put(goodsId,catIdMap.get(allIdsParam.catId));
            } else if (sortIdMap.get(allIdsParam.sortId) != null) {
                returnMap.put(goodsId, sortIdMap.get(allIdsParam.sortId));
            } else if (allGoodsMap.size() > 0) {
                returnMap.put(goodsId, allGoodsMap.get(GoodsConstant.LABEL_GTA_DEFAULT_VALUE));
            } else {
                return ;
            }
        });
        return returnMap;
    }

    @Override
    public void processForList(Map<Integer,? extends ProcessorDataInfo> activityInfos, List<ActivityGoodsListCapsule> capsules) {
        capsules.forEach(capsule->{
            Integer goodsId = capsule.getGoodsId();
            GoodsLabelProcessorDataInfo label = (GoodsLabelProcessorDataInfo) activityInfos.get(goodsId);
            if (label == null) {
                return;
            }
            capsule.setGoodsLabel(label);
        });
    }
}
