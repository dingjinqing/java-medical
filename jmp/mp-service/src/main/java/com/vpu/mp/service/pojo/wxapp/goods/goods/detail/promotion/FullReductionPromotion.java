package com.vpu.mp.service.pojo.wxapp.goods.goods.detail.promotion;

import com.vpu.mp.service.foundation.data.BaseConstant;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 满折满减活动策略营销
 * @author 李晓冰
 * @date 2020年01月13日
 */
@Data
public class FullReductionPromotion extends PromotionBase{

    public FullReductionPromotion() {
        super.promotionType = BaseConstant.ACTIVITY_TYPE_FULL_REDUCTION;
    }

    /**活动类型 1每满减 2满件 3满折 4仅第X件打折*/
    private Byte type;
    /**是否会员专享*/
    private Boolean isExclusive;

    /**满多少金额，活动中指定使用满金额策略时使用*/
    private BigDecimal fullMoney;
    /**减多少钱，活动中指定使用满金额策略时使用*/
    private BigDecimal reduceMoney;

    /**满几件或第几件（第X件打折），活动中指定使用满件数策略时使用*/
    private Integer amount;
    /**折扣，活动中指定使用满件数策略时使用*/
    private BigDecimal discount;
}
