package com.vpu.mp.controller.wxapp;

import com.vpu.mp.db.main.tables.records.ShopRecord;
import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.util.RequestUtil;
import com.vpu.mp.service.pojo.shop.store.comment.ServiceCommentVo;
import com.vpu.mp.service.pojo.wxapp.store.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 王兵兵
 *
 * 2019年7月24日
 */
@Slf4j
@RestController
@RequestMapping("/api/wxapp/store")
public class WxAppStoreController extends WxAppBaseController{
    /**
     * 门店列表
     */
    @PostMapping("/list")
    public JsonResult storeList(@RequestBody @Validated StoreListParam param) {
        return success(shop().store.wxService.getList(param));
    }

    /**
     * 门店信息
     */
    @PostMapping("/info")
    public JsonResult storeInfo(@RequestBody @Validated StoreInfoParam param) {
        return this.success(shop().store.wxService.getWxappStoreDetail(param));
    }

    /**
     * 门店买单
     */
    @PostMapping("/payOrder")
    public JsonResult storePayOrder(@RequestBody @Validated({StorePayOrder.class}) StoreInfoParam param) {
        StorePayOrderVo vo = shop().store.wxService.storePayOrder(param);
        ShopRecord shopRecord = saas.shop.getShopById(shop().store.getShopId());
        // 店铺营业状态和logo
        vo.setShopBusinessState(shopRecord.getBusinessState());
        vo.setShopLogo(shopRecord.getShopAvatar());
        return this.success(vo);
    }

    /**
     * 门店买单支付
     */
    @PostMapping("/confirmPay")
    public JsonResult confirmPay(@RequestBody @Validated({StoreConfirmPay.class}) StoreInfoParam param, HttpServletRequest request) {
        param.setClientIp(RequestUtil.getIp(request));
        log.debug("客户端ip地址为：{}", param.getClientIp());
        return this.success(shop().store.wxService.storePay(param));
    }

    /**
     * 门店服务预约详情
     */
    @PostMapping("/service/reservation")
    public JsonResult reservation(@RequestBody @Validated ReservationParam param) {
        return this.success(shop().store.reservation.reservationDetail(param.getServiceId()));
    }

    /**
     * 门店服务预约订单确认
     */
    @PostMapping("/service/confirmReservation")
    public JsonResult confirmReservation(@RequestBody @Validated(ConfirmReservation.class) ReservationParam param) {
        return this.success(shop().store.reservation.createReservation(param.getServiceId(), param.getUserId()));
    }

    /**
     * 门店服务预约订单提交
     */
    @PostMapping("/service/submitReservation")
    public JsonResult submitReservation(@RequestBody @Validated SubmitReservationParam param, HttpServletRequest request) {
        param.setClientIp(RequestUtil.getIp(request));
        log.debug("客户端ip地址为：{}", param.getClientIp());
        return this.success(shop().store.reservation.submitReservation(param));
    }

    /**
     * 门店服务预约订单继续支付
     */
    @PostMapping("/service/reservationContinuePay")
    public JsonResult reservationContinuePay(@RequestBody @Validated OrderSn param, HttpServletRequest request) {
        param.setClientIp(RequestUtil.getIp(request));
        log.debug("客户端ip地址为：{}", param.getClientIp());
        return this.success(shop().store.reservation.continuePay(param.getOrderSn(), param.getClientIp()));
    }

    /**
     * 门店服务预约订单详情查询（根据订单编号）
     */
    @PostMapping("/service/reservationDetail")
    public JsonResult reservationDetail(@RequestBody @Validated(ValidCon.class) ReservationDetail param) {
        return this.success(shop().store.reservation.getReservationDetail(param));
    }

    /**
     * 门店服务预约订单确认完成
     */
    @PostMapping("/service/comfirmComplete")
    public JsonResult comfirmComplete(@RequestBody @Validated(ValidCon1.class) ReservationDetail param) {
        return this.success(shop().store.reservation.confirmComplete(param));
    }

    /**
     * 门店服务预约订单列表查询（获取全部）
     */
    @PostMapping("/service/reservationAllList")
    public JsonResult reservationAllList(@RequestBody @Validated(ValidCon.class) ReservationParam param) {
        return this.success(shop().store.reservation.reservationList(param.getUserId()));
    }

    /**
     * 门店服务预约订单列表查询（按照订单状态获取）
     */
    @PostMapping("/service/reservationList")
    public JsonResult reservationList(@RequestBody @Validated(ValidCon1.class) ReservationParam param) {
        return this.success(shop().store.reservation.reservationList(param.getUserId(), param.getOrderStatus()));
    }

    /**
     * 删除门店服务预约订单
     */
    @PostMapping("/service/reservationDel")
    public JsonResult reservationDel(@RequestBody @Validated(ValidCon2.class) ReservationDetail param) {
        shop().store.reservation.reservationDel(param.getOrderId());
        return this.success();
    }

    /**
     * 取消待付款预约订单
     */
    @PostMapping("/service/cancelReservation")
    public JsonResult cancelWaitToPayReservation(@RequestBody @Validated(ValidCon2.class) ReservationDetail param) {
        shop().store.reservation.cancelWaitToPayReservation(param.getOrderId(), param.getCancelReason());
        return this.success();
    }

    /**
     * 获取门店服务预约订单评价
     */
    @PostMapping("/service/reservationComment")
    public JsonResult reservationComment(@RequestBody @Validated(ValidCon.class) ReservationDetail param) {
        return this.success(shop().store.reservation.reservationComment(param.getOrderSn()));
    }

    /**
     * 添加门店服务预约订单评价
     */
    @PostMapping("/service/createComment")
    public JsonResult createComment(@RequestBody @Validated(ValidCon.class) ServiceCommentVo param) {
        shop().store.reservation.createComment(param);
        return this.success();
    }
}
