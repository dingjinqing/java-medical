package com.vpu.mp.service.pojo.wxapp.goods.goods.detail.promotion;

import com.vpu.mp.service.foundation.data.BaseConstant;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 瞒包邮促销活动
 * @author 李晓冰
 * @date 2020年01月13日
 */
@Data
public class FreeShipPromotion extends PromotionBase {

    public FreeShipPromotion() {
        super.promotionType = BaseConstant.ACTIVITY_TYPE_FREESHIP_ORDER;
    }
    /**满包邮条件类型*/
    private Integer conType;
    /**满包邮条件价格*/
    private BigDecimal  money;
    /**满包邮条件数量*/
    private BigDecimal num;
}
