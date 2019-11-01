package com.vpu.mp.service.shop.activity.processor.reduction;

import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.wxapp.activity.capsule.ActivityGoodsListCapsule;
import com.vpu.mp.service.pojo.wxapp.activity.info.reduction.FullReductionForListInfo;
import com.vpu.mp.service.pojo.wxapp.activity.param.ActivityGoodsListMpParam;
import com.vpu.mp.service.shop.activity.dao.FullReductionProcessorDao;
import com.vpu.mp.service.shop.activity.processor.ActivityGoodsListProcessor;
import com.vpu.mp.service.shop.activity.processor.ActivityProcessor;
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
public class FullReductionForListProcessor extends FullReductionProcessorDao implements ActivityGoodsListProcessor<FullReductionForListInfo> {

    @Override
    public int getPriority() {
        return GoodsConstant.ACTIVITY_FULL_REDUCTION_PRIORITY;
    }

    @Override
    public ActivityGoodsListMpParam filterParam(List<ActivityGoodsListCapsule> capsules) {
       ActivityGoodsListMpParam param = new ActivityGoodsListMpParam();
       param.setDate(DateUtil.getLocalDateTime());

       List<ActivityGoodsListMpParam.AllIdsParam> idParams = new ArrayList<>();
        for (ActivityGoodsListCapsule capsule : capsules) {
            // 这几种活动的商品可以不考虑
            if (ActivityProcessor.isGoodsTypeIn135610(capsule.getGoodsType())) {
                continue;
            }
            ActivityGoodsListMpParam.AllIdsParam allIds = new ActivityGoodsListMpParam.AllIdsParam();
            allIds.goodsId = capsule.getCapsuleId();
            allIds.catId = capsule.getCatId();
            allIds.sortId = capsule.getSortId();
            allIds.brandId = capsule.getBrandId();
            idParams.add(allIds);
        }
        param.setIdsParams(idParams);
        return param;
    }

    @Override
    public Map<Integer, FullReductionForListInfo> getActivityInfo(ActivityGoodsListMpParam activityGoodsListMpParam) {
        List<ActivityGoodsListMpParam.AllIdsParam> idsParams = activityGoodsListMpParam.getIdsParams();
        Timestamp date = activityGoodsListMpParam.getDate();

        Map<Integer,FullReductionForListInfo> returnMap = new HashMap<>();

        for (ActivityGoodsListMpParam.AllIdsParam idsParam : idsParams) {
            boolean isFullReductionListInfo = getIsFullReductionListInfo(idsParam.goodsId, idsParam.catId, idsParam.sortId, idsParam.brandId, date);

            if (!isFullReductionListInfo)
                continue;

            FullReductionForListInfo info =new FullReductionForListInfo();
            returnMap.put(idsParam.goodsId,info);
        }
        return returnMap;
    }

    @Override
    public void process(Map<Integer, FullReductionForListInfo> activityInfos, List<ActivityGoodsListCapsule> capsules) {
       capsules.forEach(capsule->{
           Integer goodsId = capsule.getCapsuleId();
           FullReductionForListInfo activity = activityInfos.get(goodsId);
           if (activity == null) {
               return;
           }
           capsule.getActivities().add(activityInfos.get(goodsId));
       });
    }
}
