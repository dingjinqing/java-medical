package com.vpu.mp.service.pojo.wxapp.activity.info;

import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.wxapp.activity.info.ProcessorDataInfo;

/**
 * 小程序-商品列表-预售信息
 * @author 李晓冰
 * @date 2019年11月01日
 */
public class PreSaleProcessorDataInfo extends ProcessorDataInfo {
    public PreSaleProcessorDataInfo() {
        super();
        dataType = GoodsConstant.ACTIVITY_TYPE_PRE_SALE;
    }
}
