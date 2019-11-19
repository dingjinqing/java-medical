package com.vpu.mp.service.shop.activity.processor;

import com.vpu.mp.service.pojo.wxapp.order.OrderBeforeParam;
import com.vpu.mp.service.pojo.wxapp.order.OrderBeforeVo;

/**
 * 小程序-订单结算确认页营销处理器接口
 * @author wangbingbing
 */
public interface OrderBeforeProcessor {
    void processOrderBefore(OrderBeforeParam orderBeforeParam, OrderBeforeVo vo);
}
