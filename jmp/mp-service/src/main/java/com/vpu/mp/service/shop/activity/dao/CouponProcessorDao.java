package com.vpu.mp.service.shop.activity.dao;

import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.database.DslPlus;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import org.jooq.Condition;
import org.jooq.Record5;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.Tables.MRKING_VOUCHER;

/**
 * @author 李晓冰
 * @date 2019年10月30日
 */
@Service
public class CouponProcessorDao extends ShopBaseService {

    /**普通优惠券*/
    private final byte COUPON_TYPE_NORMAL = 0;
    private final String ACT_CODE_VOUCHER = "voucher";

    /**
     * 获取关联的最紧密的优惠券信息，排序规则：先满折满减，再面值（金额的话取最大，打折取最小），再时间
     * @param goodsId 商品id
     * @param catId 平台分类
     * @param sortId 商家分类
     * @param date 时间
     * @return 最紧密优惠券信息
     */
    public Record5<Integer, String, BigDecimal, Byte, BigDecimal> getGoodsCouponClosestInfo(Integer goodsId, Integer catId, Integer sortId, Timestamp date) {
        // 优惠券使用时间限制，小于指定结束时间或者优惠券是领取后指定天数内生效
        Condition timeCondition = MRKING_VOUCHER.END_TIME.gt(date).or(MRKING_VOUCHER.END_TIME.isNull());
        // 优惠券剩余量限制，限量券的剩余数大于0或者是不限量券
        Condition surplusCondition = (MRKING_VOUCHER.TOTAL_AMOUNT.gt(0).and(MRKING_VOUCHER.SURPLUS.gt(0))).or(MRKING_VOUCHER.TOTAL_AMOUNT.eq(0));
        // 优惠券指定使用对象限制，全部商品或指定条件
        Condition usableTargetCondition = MRKING_VOUCHER.RECOMMEND_GOODS_ID.isNull().and(MRKING_VOUCHER.RECOMMEND_CAT_ID.isNull()).and(MRKING_VOUCHER.RECOMMEND_SORT_ID.isNull());
        usableTargetCondition = usableTargetCondition.or(DslPlus.findInSet(goodsId,MRKING_VOUCHER.RECOMMEND_GOODS_ID)
            .or(DslPlus.findInSet(catId,MRKING_VOUCHER.RECOMMEND_CAT_ID)).or(DslPlus.findInSet(sortId,MRKING_VOUCHER.RECOMMEND_SORT_ID)));

        Condition baseCondition = MRKING_VOUCHER.DEL_FLAG.eq(DelFlag.NORMAL.getCode()).and(MRKING_VOUCHER.ENABLED.eq(GoodsConstant.USE_STATUS))
            .and(MRKING_VOUCHER.TYPE.eq(COUPON_TYPE_NORMAL));

        List<Record5<Integer, String, BigDecimal, Byte, BigDecimal>> record5s = db().select(MRKING_VOUCHER.ID, MRKING_VOUCHER.ACT_CODE, MRKING_VOUCHER.DENOMINATION, MRKING_VOUCHER.USE_CONSUME_RESTRICT, MRKING_VOUCHER.LEAST_CONSUME)
            .from(MRKING_VOUCHER).where(baseCondition.or(timeCondition).or(surplusCondition).or(usableTargetCondition))
            .orderBy(MRKING_VOUCHER.ACT_CODE.desc(), MRKING_VOUCHER.DENOMINATION, MRKING_VOUCHER.CREATE_TIME.desc()).fetch().stream().collect(Collectors.toList());
        if (record5s.size() <= 0) {
            return null;
        }

        // 减金额的desc是从大到小取第一条，而打折的需要取折扣最小者
        if (record5s.get(0).get(MRKING_VOUCHER.ACT_CODE).equals(ACT_CODE_VOUCHER)) {
            return record5s.get(0);
        } else {
            return record5s.get(record5s.size()-1);
        }
    }
}
