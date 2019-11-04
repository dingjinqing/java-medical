package com.vpu.mp.service.shop.activity.processor.list;

import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.shop.goods.label.GoodsLabelCoupleTypeEnum;
import com.vpu.mp.service.pojo.wxapp.activity.capsule.ActivityGoodsListCapsule;
import com.vpu.mp.service.pojo.wxapp.activity.info.list.GoodsLabelForListInfo;
import com.vpu.mp.service.pojo.wxapp.activity.param.ActivityGoodsListMpParam;
import com.vpu.mp.service.shop.activity.dao.GoodsLabelProcessorDao;
import com.vpu.mp.service.shop.activity.processor.ActivityGoodsListProcessor;
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
public class GoodsLabelForListProcessor extends GoodsLabelProcessorDao implements ActivityGoodsListProcessor<GoodsLabelForListInfo> {
    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public ActivityGoodsListMpParam filterParam(List<ActivityGoodsListCapsule> capsules) {
        ActivityGoodsListMpParam param = new ActivityGoodsListMpParam();
        List<Integer> goodsIds = new ArrayList<>();
        List<Integer> sortIds = new ArrayList<>();
        List<Integer> catIds = new ArrayList<>();
        List<ActivityGoodsListMpParam.AllIdsParam> idsParams = new ArrayList<>();
        capsules.forEach(capsule->{
            goodsIds.add(capsule.getGoodsId());
            sortIds.add(capsule.getSortId());
            catIds.add(capsule.getCatId());
            idsParams.add(new ActivityGoodsListMpParam.AllIdsParam(capsule.getGoodsId(),capsule.getCatId(),capsule.getSortId(),null));
        });

        param.setGoodsIds(goodsIds);
        param.setSortIds(sortIds);
        param.setCatIds(catIds);
        param.setIdsParams(idsParams);
        return param;
    }

    @Override
    public Map<Integer, GoodsLabelForListInfo> getActivityInfo(ActivityGoodsListMpParam param) {
        Map<Byte, Map<Integer, GoodsLabelForListInfo>> goodsLabelsMap = getGoodsClosestLabelsInfo(param.getGoodsIds(), param.getCatIds(), param.getSortIds());
        List<ActivityGoodsListMpParam.AllIdsParam> idsParams = param.getIdsParams();
        Map<Integer,GoodsLabelForListInfo> returnMap = new HashMap<>();

        idsParams.forEach(allIdsParam -> {
            Integer goodsId = allIdsParam.goodsId;
            Map<Integer, GoodsLabelForListInfo> goodsIdMap = goodsLabelsMap.get(GoodsLabelCoupleTypeEnum.GOODSTYPE.getCode());
            Map<Integer, GoodsLabelForListInfo> catIdMap = goodsLabelsMap.get(GoodsLabelCoupleTypeEnum.CATTYPE.getCode());
            Map<Integer, GoodsLabelForListInfo> sortIdMap = goodsLabelsMap.get(GoodsLabelCoupleTypeEnum.SORTTYPE.getCode());
            Map<Integer, GoodsLabelForListInfo> allGoodsMap = goodsLabelsMap.get(GoodsLabelCoupleTypeEnum.ALLTYPE.getCode());
            if (goodsIdMap.get(allIdsParam.goodsId) != null) {
                returnMap.put(goodsId,goodsIdMap.get(allIdsParam.goodsId));
            } else if (catIdMap.get(allIdsParam.catId) != null) {
                returnMap.put(goodsId,catIdMap.get(allIdsParam.catId));
            } else if (sortIdMap.get(allIdsParam.sortId) != null) {
                returnMap.put(goodsId, sortIdMap.get(allIdsParam.sortId));
            } else if (allGoodsMap.size() > 0) {
                returnMap.put(goodsId, allGoodsMap.get(GoodsConstant.LABEL_GTA_DEFAULT_VALUE));
            } else {
                return;
            }
        });
        return returnMap;
    }

    @Override
    public void process(Map<Integer, GoodsLabelForListInfo> activityInfos, List<ActivityGoodsListCapsule> capsules) {
        capsules.forEach(capsule->{
            Integer goodsId = capsule.getGoodsId();
            GoodsLabelForListInfo label = activityInfos.get(goodsId);
            if (label == null) {
                return;
            }
            capsule.setGoodsLabel(label);
        });
    }
}
