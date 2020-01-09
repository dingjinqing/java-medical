package com.vpu.mp.controller.wxapp;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.wxapp.market.shareReward.ShareParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liufei
 * @date 1/9/20
 */
@RestController
public class WxAppShareRewardController extends WxAppBaseController {

    @PostMapping("/api/wxapp/share/add")
    public JsonResult addShareRecord(@RequestBody @Validated ShareParam param) {
        return success();
    }
}
