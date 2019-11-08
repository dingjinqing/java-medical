package com.vpu.mp.service.shop.activity.processor;

import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.wxapp.activity.capsule.ActivityGoodsListCapsule;
import com.vpu.mp.service.pojo.wxapp.activity.capsule.GoodsDetailMpCapsule;
import com.vpu.mp.service.pojo.wxapp.activity.info.ProcessorDataInfo;
import com.vpu.mp.service.pojo.wxapp.activity.info.CouponProcessorDataInfo;
import com.vpu.mp.service.pojo.wxapp.activity.param.GoodsBaseCapsuleParam;
import com.vpu.mp.service.pojo.wxapp.activity.param.GoodsDetailCapsuleParam;
import com.vpu.mp.service.shop.activity.dao.CouponProcessorDao;
import org.jooq.Record5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.Tables.MRKING_VOUCHER;

/**
 * @author 李晓冰
 * @date 2019年10月30日
 */
@Service
public class CouponProcessor implements ActivityGoodsListProcessor,GoodsDetailProcessor{
    @Autowired
    CouponProcessorDao couponProcessorDao;
    /*****************商品列表处理*******************/
    @Override
    public Byte getPriority() {
        return GoodsConstant.ACTIVITY_COUPON_PRIORITY;
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
            idParams.add(allIds);
        }
        param.setIdsParams(idParams);
        return param;
    }

    @Override
    public Map<Integer, CouponProcessorDataInfo> getActivityInfoForList(GoodsBaseCapsuleParam param) {
        List<GoodsBaseCapsuleParam.AllIdsParam> idsParams = param.getIdsParams();
        Timestamp date = param.getDate();
        Map<Integer, CouponProcessorDataInfo> returnMap = new HashMap<>();

        idsParams.forEach(idsParam->{
            Record5<Integer, String, BigDecimal, Byte, BigDecimal> closestInfo = couponProcessorDao.getGoodsCouponClosestInfo(idsParam.goodsId, idsParam.catId, idsParam.sortId, date);
            if (closestInfo == null) {
               return;
            }
            Integer goodsId = idsParam.goodsId;
            CouponProcessorDataInfo info = new CouponProcessorDataInfo();
            info.setDataId(closestInfo.get(MRKING_VOUCHER.ID));
            info.setActCode(closestInfo.get(MRKING_VOUCHER.ACT_CODE));
            info.setDenomination(closestInfo.get(MRKING_VOUCHER.DENOMINATION));
            info.setUseConsumeRestrict(closestInfo.get(MRKING_VOUCHER.USE_CONSUME_RESTRICT));
            info.setLeastConsume(closestInfo.get(MRKING_VOUCHER.LEAST_CONSUME));
            returnMap.put(goodsId,info);
        });
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
            capsule.getActivities().add(activity);
        });
    }

    /*****************商品详情处理******************/
    private List<CouponProcessorDataInfo> getGoodsDetailData(GoodsDetailCapsuleParam param) {
        List<CouponProcessorDataInfo> goodsCouponForDetail = couponProcessorDao.getGoodsCouponForDetail(param.getGoodsId(), param.getCatId(), param.getSortId(), DateUtil.getLocalDateTime());
        List<Integer> couponIds = goodsCouponForDetail.stream().map(CouponProcessorDataInfo::getId).collect(Collectors.toList());
        Map<Integer, Integer> userCouponsAlreadyNum = couponProcessorDao.getUserCouponsAlreadyNum(param.getUserId(), couponIds);
        goodsCouponForDetail.forEach(coupon->{
            int receivePer = coupon.getReceivePerPerson();
            int already = userCouponsAlreadyNum.get(coupon.getId());

            if (receivePer == 0) {
                coupon.setCanFetch(true);
            } else {
                if (receivePer>already) {
                    coupon.setCanFetch(true);
                } else {
                    coupon.setCanFetch(false);
                }
            }
        });
        return goodsCouponForDetail;
    }

    @Override
    public Byte getPriorityForDetail() {
        return 0;
    }

    @Override
    public void processGoodsDetail(GoodsDetailMpCapsule capsule, GoodsDetailCapsuleParam param) {
        List<CouponProcessorDataInfo> coupon = getGoodsDetailData(param);
        capsule.setCoupons(coupon);
    }
}
