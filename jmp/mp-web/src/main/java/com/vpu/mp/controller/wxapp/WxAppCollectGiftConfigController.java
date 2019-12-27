package com.vpu.mp.controller.wxapp;

import com.vpu.mp.controller.BaseController;
import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.market.collect.CollectGiftParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author 常乐
 * @Date 2019-12-25
 */
@RestController
public class WxAppCollectGiftConfigController extends WxAppBaseController {
    /**
     * 收藏有礼开关状态
     * @return
     */
    @GetMapping("/api/wxapp/collectGift/switch")
    public JsonResult getSwitchStatus(){
        Integer userId = wxAppAuth.user().getUserId();
        CollectGiftParam res = shop().config.collectGiftConfigService.collectGiftConfig(userId);
        return this.success(res);
    }

    @GetMapping("/api/wxapp/collectGift/setRewards")
    public JsonResult setRewards(){
        Integer userId = wxAppAuth.user().getUserId();
        shop().config.collectGiftConfigService.setRewards(userId);
        return this.success();
    }
}
