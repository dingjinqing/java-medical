package com.vpu.mp.service.pojo.wxapp.activity.info;

import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.wxapp.activity.info.ProcessorDataInfo;

/**
 * @author 李晓冰
 * @date 2019年11月01日
 */
public class ReducePriceProcessorDataInfo extends ProcessorDataInfo {
    public ReducePriceProcessorDataInfo() {
        super();
        dataType = GoodsConstant.ACTIVITY_TYPE_REDUCE_PRICE;
    }
}
