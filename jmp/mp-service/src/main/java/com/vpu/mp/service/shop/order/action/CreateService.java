package com.vpu.mp.service.shop.order.action;

import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderOperateQueryParam;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderServiceCode;
import com.vpu.mp.service.shop.order.action.base.IorderOperate;
import org.springframework.stereotype.Service;

/**
 * 下单逻辑处理
 *
 * @author: 王兵兵
 * @create: 2019-10-23 16:15
 **/
@Service
public class CreateService extends ShopBaseService implements IorderOperate<OrderOperateQueryParam, OrderOperateQueryParam> {
    @Override
    public OrderServiceCode getServiceCode() {
        return OrderServiceCode.CREATE;
    }

    @Override
    public Object query(OrderOperateQueryParam param) throws MpException {
        return null;
    }

    @Override
    public JsonResultCode execute(OrderOperateQueryParam obj) {
        return null;
    }
}
