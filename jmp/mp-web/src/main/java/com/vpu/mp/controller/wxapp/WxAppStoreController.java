package com.vpu.mp.controller.wxapp;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.store.store.StoreListQueryParam;
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
    public JsonResult storeList(@RequestBody StoreListQueryParam param) {
        return success(shop().store.getList(param));
    }

    /**
     * 门店信息
     */
    @GetMapping("/info/{storeId}")
    public JsonResult storeInfo(@PathVariable Integer storeId) {
        return this.success(shop().store.getStore(storeId));
    }
}
