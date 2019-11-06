package com.vpu.mp.service.pojo.wxapp.activity.info;

import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;

/**
 * 小程序-商品列表-砍价信息
 * @author 李晓冰
 * @date 2019年11月01日
 */
public class BargainProcessorDataInfo extends ProcessorDataInfo {
    public BargainProcessorDataInfo() {
        super();
        dataType = GoodsConstant.ACTIVITY_TYPE_BARGAIN;
    }
}
