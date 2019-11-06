package com.vpu.mp.service.pojo.wxapp.activity.info;

import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;

/**
 * @author 李晓冰
 * @date 2019年10月30日
 */
public class FullReductionProcessorDataInfo extends ProcessorDataInfo {
    public FullReductionProcessorDataInfo() {
        super();
        dataType = GoodsConstant.ACTIVITY_TYPE_FULL_REDUCTION;
    }
}
