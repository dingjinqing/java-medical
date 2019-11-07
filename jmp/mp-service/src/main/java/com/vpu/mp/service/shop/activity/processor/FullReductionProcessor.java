package com.vpu.mp.service.shop.activity.processor;

import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.wxapp.activity.capsule.ActivityGoodsListCapsule;
import com.vpu.mp.service.pojo.wxapp.activity.info.ProcessorDataInfo;
import com.vpu.mp.service.pojo.wxapp.activity.info.FullReductionProcessorDataInfo;
import com.vpu.mp.service.pojo.wxapp.activity.param.GoodsBaseCapsuleParam;
import com.vpu.mp.service.shop.activity.dao.FullReductionProcessorDao;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 李晓冰
 * @date 2019年10月30日
 */
@Service
public class FullReductionProcessor extends FullReductionProcessorDao implements ActivityGoodsListProcessor {

    @Override
    public Byte getPriority() {
        return GoodsConstant.ACTIVITY_FULL_REDUCTION_PRIORITY;
    }

    @Override
    public GoodsBaseCapsuleParam filterParamForList(List<ActivityGoodsListCapsule> capsules) {
       GoodsBaseCapsuleParam param = new GoodsBaseCapsuleParam();
       param.setDate(DateUtil.getLocalDateTime());

       List<GoodsBaseCapsuleParam.AllIdsParam> idParams = new ArrayList<>();
        for (ActivityGoodsListCapsule capsule : capsules) {
            // 这几种活动的商品可以不考虑
            if (GoodsConstant.isGoodsTypeIn13510(capsule.getGoodsType())) {
                continue;
            }
            GoodsBaseCapsuleParam.AllIdsParam allIds = new GoodsBaseCapsuleParam.AllIdsParam();
            allIds.goodsId = capsule.getGoodsId();
            allIds.catId = capsule.getCatId();
            allIds.sortId = capsule.getSortId();
            allIds.brandId = capsule.getBrandId();
            idParams.add(allIds);
        }
        param.setIdsParams(idParams);
        return param;
    }

    @Override
    public Map<Integer, FullReductionProcessorDataInfo> getActivityInfoForList(GoodsBaseCapsuleParam param) {
        List<GoodsBaseCapsuleParam.AllIdsParam> idsParams = param.getIdsParams();
        Timestamp date = param.getDate();

        Map<Integer, FullReductionProcessorDataInfo> returnMap = new HashMap<>();

        for (GoodsBaseCapsuleParam.AllIdsParam idsParam : idsParams) {
            boolean isFullReductionListInfo = getIsFullReductionListInfo(idsParam.goodsId, idsParam.catId, idsParam.sortId, idsParam.brandId, date);

            if (!isFullReductionListInfo)
                continue;

            FullReductionProcessorDataInfo info =new FullReductionProcessorDataInfo();
            returnMap.put(idsParam.goodsId,info);
        }
        return returnMap;
    }

    @Override
    public void processForList(Map<Integer,? extends ProcessorDataInfo> activityInfos, List<ActivityGoodsListCapsule> capsules) {
       capsules.forEach(capsule->{
           Integer goodsId = capsule.getGoodsId();
           ProcessorDataInfo activity = activityInfos.get(goodsId);
           if (activity == null) {
               return;
           }
           capsule.getActivities().add(activityInfos.get(goodsId));
       });
    }
}
