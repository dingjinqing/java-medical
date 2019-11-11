package com.vpu.mp.service.pojo.wxapp.activity.info;

import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author 李晓冰
 * @date 2019年11月01日
 */
@Getter
@Setter
public class FirstSpecialProcessorDataInfo extends ProcessorDataInfo {
    public FirstSpecialProcessorDataInfo() {
        dataType = GoodsConstant.ACTIVITY_TYPE_FIRST_SPECIAL;
    }

    /**
     * 规格id
     */
    private Integer prdId;
    /**
     * 活动价格
     */
    private BigDecimal prdPrice;
}
