package com.vpu.mp.service.shop.activity.processor;

import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.pojo.wxapp.order.OrderBeforeVo;

/**
 * 小程序-订单生成时-营销处理器接口
 * @author wangbingbing
 */
public interface OrderCreatePayBeforeProcessor {
    void processPayBefore(OrderBeforeVo order) throws MpException;
}
