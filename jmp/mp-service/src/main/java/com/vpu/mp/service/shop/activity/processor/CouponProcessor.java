package com.vpu.mp.service.shop.activity.processor;

import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.wxapp.activity.capsule.ActivityGoodsListCapsule;
import com.vpu.mp.service.pojo.wxapp.activity.info.ActivityForListInfo;
import com.vpu.mp.service.pojo.wxapp.activity.info.list.CouponForLsitInfo;
import com.vpu.mp.service.pojo.wxapp.activity.param.ActivityGoodsListMpParam;
import com.vpu.mp.service.shop.activity.dao.CouponProcessorDao;
import com.vpu.mp.service.shop.activity.processor.ActivityGoodsListProcessor;
import org.jooq.Record5;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.vpu.mp.db.shop.Tables.MRKING_VOUCHER;

/**
 * @author 李晓冰
 * @date 2019年10月30日
 */
@Service
public class CouponProcessor extends CouponProcessorDao implements ActivityGoodsListProcessor {
    @Override
    public Byte getPriority() {
        return GoodsConstant.ACTIVITY_COUPON_PRIORITY;
    }

    @Override
    public ActivityGoodsListMpParam filterParamForList(List<ActivityGoodsListCapsule> capsules) {
        ActivityGoodsListMpParam param = new ActivityGoodsListMpParam();
        param.setDate(DateUtil.getLocalDateTime());

        List<ActivityGoodsListMpParam.AllIdsParam> idParams = new ArrayList<>();
        for (ActivityGoodsListCapsule capsule : capsules) {
            // 这几种活动的商品可以不考虑
            if (GoodsConstant.isGoodsTypeIn13510(capsule.getGoodsType())) {
                continue;
            }
            ActivityGoodsListMpParam.AllIdsParam allIds = new ActivityGoodsListMpParam.AllIdsParam();
            allIds.goodsId = capsule.getGoodsId();
            allIds.catId = capsule.getCatId();
            allIds.sortId = capsule.getSortId();
            idParams.add(allIds);
        }
        param.setIdsParams(idParams);
        return param;
    }

    @Override
    public Map<Integer, CouponForLsitInfo> getActivityInfoForList(ActivityGoodsListMpParam activityGoodsListMpParam) {
        List<ActivityGoodsListMpParam.AllIdsParam> idsParams = activityGoodsListMpParam.getIdsParams();
        Timestamp date = activityGoodsListMpParam.getDate();
        Map<Integer, CouponForLsitInfo> returnMap = new HashMap<>();

        idsParams.forEach(idsParam->{
            Record5<Integer, String, BigDecimal, Byte, BigDecimal> closestInfo = getGoodsCouponClosestInfo(idsParam.goodsId, idsParam.catId, idsParam.sortId, date);
            if (closestInfo == null) {
               return;
            }
            Integer goodsId = idsParam.goodsId;
            CouponForLsitInfo info = new CouponForLsitInfo();
            info.setActivityId(closestInfo.get(MRKING_VOUCHER.ID));
            info.setActCode(closestInfo.get(MRKING_VOUCHER.ACT_CODE));
            info.setDenomination(closestInfo.get(MRKING_VOUCHER.DENOMINATION));
            info.setUseConsumeRestrict(closestInfo.get(MRKING_VOUCHER.USE_CONSUME_RESTRICT));
            info.setLeastConsume(closestInfo.get(MRKING_VOUCHER.LEAST_CONSUME));
            returnMap.put(goodsId,info);
        });
        return returnMap;
    }

    @Override
    public void processForList(Map<Integer,? extends ActivityForListInfo> activityInfos, List<ActivityGoodsListCapsule> capsules) {
        capsules.forEach(capsule->{
            Integer goodsId = capsule.getGoodsId();
            ActivityForListInfo activity = activityInfos.get(goodsId);
            if (activity == null) {
                return;
            }
            capsule.getActivities().add(activity);
        });
    }
}
