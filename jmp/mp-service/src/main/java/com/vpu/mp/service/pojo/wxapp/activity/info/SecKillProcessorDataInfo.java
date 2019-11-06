package com.vpu.mp.service.pojo.wxapp.activity.info;

import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;

/**
 * 小程序-商品列表-秒杀信息
 * @author 李晓冰
 * @date 2019年11月01日
 */
public class SecKillProcessorDataInfo extends ProcessorDataInfo {
    public SecKillProcessorDataInfo() {
        super();
        dataType = GoodsConstant.ACTIVITY_TYPE_SEC_KILL;
    }
}
