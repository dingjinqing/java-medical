package com.vpu.mp.controller.wxapp;

import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderOperateQueryParam;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderServiceCode;
import com.vpu.mp.service.pojo.shop.order.write.operate.refund.RefundParam;
import com.vpu.mp.service.pojo.shop.order.write.operate.ship.ShipParam;
import com.vpu.mp.service.pojo.shop.store.account.StoreAccountVo;
import com.vpu.mp.service.pojo.wxapp.order.OrderListParam;
import com.vpu.mp.service.saas.shop.StoreAccountService;
import com.vpu.mp.service.shop.order.action.base.ExecuteResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 门店订单
 * @author 孔德成
 * @date 2020/9/23 10:45
 */
@Slf4j
@RestController
public class WxAppStoreOrderController extends WxAppBaseController{

    @Autowired
    private StoreAccountService setWxUserInfo;


    /**
     * 订单列表
     */
    @PostMapping("/api/wxapp/store/order/list")
    public JsonResult storeOrderList(@RequestBody @Valid OrderListParam param) {
        param.setWxUserInfo(wxAppAuth.user());
        StoreAccountVo storeAccountVo = setWxUserInfo.getStoreInfoById(wxAppAuth.user().getStoreAccountId());
        param.setStoreIds(storeAccountVo.getStoreLists());
        param.setPlatform(OrderConstant.PLATFORM_WXAPP_STORE);
        return success(shop().readOrder.getPageList(param));
    }



    /**
     * 	发货
     */
    @PostMapping("/api/wxapp/store/order/ship")
    public JsonResult ship(@RequestBody @Valid ShipParam param ) {
        param.setIsMp(OrderConstant.IS_MP_STORE_CLERK);
        param.setPlatform(OrderConstant.PLATFORM_WXAPP_STORE);
        ExecuteResult executeResult = shop().orderActionFactory.orderOperate(param);
        if(executeResult == null || executeResult.isSuccess()) {
            return success(executeResult == null ? null : executeResult.getResult());
        }else {
            return result(executeResult.getErrorCode(), executeResult.getResult(), executeResult.getErrorParam());
        }
    }
    /**
     * 延长收货、确认收货、取消订单、提醒发货、删除订单
     */
    @PostMapping("/api/wxapp/store/order/receive")
    public JsonResult cancel(@RequestBody @Valid OrderOperateQueryParam param) {
        param.setIsMp(OrderConstant.IS_MP_STORE_CLERK);
        param.setWxUserInfo(wxAppAuth.user());
        param.setPlatform(OrderConstant.PLATFORM_WXAPP_STORE);
        param.setAction((byte) OrderServiceCode.RECEIVE.ordinal());
        ExecuteResult executeResult = shop().orderActionFactory.orderOperate(param);
        if(executeResult == null || executeResult.isSuccess()) {
            return success(executeResult == null ? null : executeResult.getResult());
        }else {
            return result(executeResult.getErrorCode(), executeResult.getResult(), executeResult.getErrorParam());
        }
    }

    /**
     * 退款
     */
    @PostMapping("/refund")
    public JsonResult refundMoney(@RequestBody @Valid RefundParam param) {
        param.setIsMp(OrderConstant.IS_MP_STORE_CLERK);
        param.setWxUserInfo(wxAppAuth.user());
        param.setPlatform(OrderConstant.PLATFORM_WXAPP_STORE);
        param.setAction((byte) OrderServiceCode.RECEIVE.ordinal());
        ExecuteResult executeResult = shop().orderActionFactory.orderOperate(param);
        if(executeResult == null || executeResult.isSuccess()) {
            return success(executeResult == null ? null : executeResult.getResult());
        }else {
            return result(executeResult.getErrorCode(), executeResult.getResult(), executeResult.getErrorParam());
        }
    }

}
