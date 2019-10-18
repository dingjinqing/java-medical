package com.vpu.mp.controller.wxapp;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.wxapp.store.StoreInfoParam;
import com.vpu.mp.service.pojo.wxapp.store.StoreListParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public JsonResult storeList(@RequestBody StoreListParam param) {
        return success(shop().store.getList(param));
    }

    /**
     * 门店信息
     */
    @PostMapping("/info")
    public JsonResult storeInfo(@RequestBody StoreInfoParam param) {
        return this.success(shop().store.getWxappStoreDetail(param));
    }
}
