package com.vpu.mp.controller.wxapp;

import com.vpu.mp.db.main.tables.records.ShopRecord;
import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.wxapp.store.StoreInfoParam;
import com.vpu.mp.service.pojo.wxapp.store.StoreListParam;
import com.vpu.mp.service.pojo.wxapp.store.StorePayOrder;
import com.vpu.mp.service.pojo.wxapp.store.StorePayOrderVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 王兵兵
 *
 * 2019年7月24日
 */
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
     * 门店服务预约
     */
    @GetMapping("/service/reservation/{serviceId}")
    public JsonResult reservation(@PathVariable Integer serviceId) {
        return this.success(shop().store.wxService.reservationDetail(serviceId, null));
    }
}
