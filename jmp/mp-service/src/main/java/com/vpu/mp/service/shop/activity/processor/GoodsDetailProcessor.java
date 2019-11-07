package com.vpu.mp.service.shop.activity.processor;

import com.vpu.mp.service.pojo.wxapp.activity.capsule.GoodsDetailMpCapsule;
import com.vpu.mp.service.pojo.wxapp.activity.param.GoodsBaseCapsuleParam;

/**
 * 小程序-商品详情处理器接口
 * @author 李晓冰
 * @date 2019年11月07日
 */
public interface GoodsDetailProcessor {
    void processGoodsDetail(GoodsDetailMpCapsule capsule, GoodsBaseCapsuleParam param);
}
