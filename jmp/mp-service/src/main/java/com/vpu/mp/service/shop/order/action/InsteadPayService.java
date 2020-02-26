package com.vpu.mp.service.shop.order.action;

import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.OrderInfoVo;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderOperateQueryParam;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderServiceCode;
import com.vpu.mp.service.shop.order.action.base.ExecuteResult;
import com.vpu.mp.service.shop.order.action.base.IorderOperate;
import com.vpu.mp.service.shop.order.info.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 好友代付
 * @author 王帅
 */
public class InsteadPayService extends ShopBaseService implements IorderOperate<OrderOperateQueryParam,OrderOperateQueryParam> {

    @Autowired
    private OrderInfoService orderInfo;

    @Override
    public OrderServiceCode getServiceCode() {
        return OrderServiceCode.INSTEAD_PAY;
    }

    @Override
    public Object query(OrderOperateQueryParam param) throws MpException {
        OrderInfoVo order = orderInfo.getByOrderId(param.getOrderId(), OrderInfoVo.class);
        if(order == null) {
            return ExecuteResult.create(JsonResultCode.CODE_ORDER_NOT_EXIST, null);
        }
        if(order.getOrderStatus().equals(OrderConstant.ORDER_WAIT_PAY)) {
            return ExecuteResult.create(JsonResultCode.CODE_ORDER_OPERATE_NO_INSTANCEOF, null);
        }
        if(order.getOrderStatus().equals(OrderConstant.ORDER_WAIT_DELIVERY)) {
            return ExecuteResult.create(JsonResultCode.CODE_ORDER_NOT_EXIST, "来晚了，其他好友已帮TA付款了");
        }


        return null;
    }

    @Override
    public ExecuteResult execute(OrderOperateQueryParam obj) {
        return null;
    }


}
