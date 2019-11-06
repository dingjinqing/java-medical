package com.vpu.mp.service.pojo.wxapp.activity.info;

import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;

/**
 * @author 李晓冰
 * @date 2019年10月29日
 */
public class GroupBuyProcessorDataInfo extends ProcessorDataInfo {
    public GroupBuyProcessorDataInfo() {
        super();
        dataType = GoodsConstant.ACTIVITY_TYPE_GROUP_BUY;
    }
}
