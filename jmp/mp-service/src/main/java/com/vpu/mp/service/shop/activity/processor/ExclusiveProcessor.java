package com.vpu.mp.service.shop.activity.processor;

import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.shop.member.card.CardConstant;
import com.vpu.mp.service.pojo.wxapp.activity.capsule.ActivityGoodsListCapsule;
import com.vpu.mp.service.pojo.wxapp.activity.capsule.GoodsDetailMpCapsule;
import com.vpu.mp.service.pojo.wxapp.activity.info.ProcessorDataInfo;
import com.vpu.mp.service.pojo.wxapp.activity.info.ExclusiveProcessorDataInfo;
import com.vpu.mp.service.pojo.wxapp.activity.param.GoodsBaseCapsuleParam;
import com.vpu.mp.service.pojo.wxapp.activity.param.GoodsDetailCapsuleParam;
import com.vpu.mp.service.shop.activity.dao.MemberCardProcessorDao;
import org.jooq.Record;
import org.jooq.Record2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.Tables.MEMBER_CARD;
import static com.vpu.mp.db.shop.Tables.USER_CARD;

/**
 *  会员专享
 * @author 李晓冰
 * @date 2019年10月31日
 */
@Service
public class ExclusiveProcessor implements ActivityGoodsListProcessor,GoodsDetailProcessor,ActivityCartListStrategy {
    @Autowired
    MemberCardProcessorDao memberCardProcessorDao;

    /*****************商品列表处理*******************/
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
    /*****************商品详情处理******************/
    @Override
    public void processGoodsDetail(GoodsDetailMpCapsule capsule, GoodsDetailCapsuleParam param) {

        if (!GoodsConstant.CARD_EXCLUSIVE.equals(capsule.getIsExclusive())) {
            capsule.setUserCanBuy(true);
            return;
        }

        capsule.setUserCanBuy(false);
        // 获取商品所有专享卡（包含普通卡和等级卡）
        List<ExclusiveProcessorDataInfo> exclusiveInfo = memberCardProcessorDao.getExclusiveInfo(param.getGoodsId(), param.getCatId(), param.getSortId(), param.getBrandId());
        Map<Byte, List<ExclusiveProcessorDataInfo>> map = exclusiveInfo.stream().collect(Collectors.groupingBy(ExclusiveProcessorDataInfo::getCardType));

        // 普通卡
        List<ExclusiveProcessorDataInfo> normalCards = map.getOrDefault(CardConstant.MCARD_TP_NORMAL, new ArrayList<>(0));
        // 等级卡
        List<ExclusiveProcessorDataInfo> gradeCards = map.getOrDefault(CardConstant.MCARD_TP_GRADE, new ArrayList<>(0));

        // 获取当前用户拥有的所有会员卡信息
        List<Record> userAllCard = memberCardProcessorDao.getUserAllCard(param.getUserId());
        // 获取用户等级
        Record2<Integer, String> userGrade = memberCardProcessorDao.getUserGradeCard(param.getUserId());
        Map<Integer, Record> userAllCardMap = userAllCard.stream().collect(Collectors.toMap(x -> x.get(USER_CARD.CARD_ID), x -> x));

        Timestamp now = DateUtil.getLocalDateTime();

        // 判断用户和专享普通卡的状态关系
        normalCards.forEach(normalCard->{
            Record record = userAllCardMap.get(normalCard.getId());
            if (record == null) {
                // 用户待领取
                normalCard.setStatus(CardConstant.USER_CARD_STATUS_NOT_HAS);
            } else {
                // 是否需要激活
                Byte isNeedActive = normalCard.getActivation();
                // 激活时间
                Timestamp activeTime = record.get(USER_CARD.ACTIVATION_TIME);
                // 卡有效时间，为null表示永久型
                Timestamp expireTime = record.get(USER_CARD.EXPIRE_TIME);

                // 已激活或不需要激活
                if ((CardConstant.MCARD_ACT_NO.equals(isNeedActive) || (CardConstant.MCARD_ACT_YES.equals(isNeedActive) && activeTime != null))) {
                    if (expireTime == null || expireTime.compareTo(now) > 0) {
                        // 未过期可使用
                        normalCard.setStatus(CardConstant.USER_CARD_STATUS_HAS);
                        capsule.setUserCanBuy(true);
                    } else {
                        // 已过期
                        normalCard.setStatus(CardConstant.USER_CARD_STATUS_OUT_OF_EXPIRE);
                    }
                } else {
                    // 需要激活
                    normalCard.setStatus(CardConstant.USER_CARD_STATUS_NEED_ACTVATION);
                }
            }
        });

        if (gradeCards.size() == 0||userGrade == null) {
            return;
        }

        ExclusiveProcessorDataInfo minGradeCard = gradeCards.get(0);
        if (userGrade.get(MEMBER_CARD.GRADE).compareTo(minGradeCard.getGrade()) > 0) {
            capsule.setUserCanBuy(true);
        }

    }

    /**
     * 购物车
     */
    @Override
    public void doCartOperation() {

    }
}
