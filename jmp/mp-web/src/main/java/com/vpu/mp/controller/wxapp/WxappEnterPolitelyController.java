package com.vpu.mp.controller.wxapp;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.wxapp.market.enterpolitely.EnterPolitelyParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Wxapp enter politely controller.
 *
 * @author liufei
 * @date 12 /23/19 开屏有礼
 */
@RestController
@RequestMapping("/api/wxapp/enterpolitely")
public class WxappEnterPolitelyController extends WxAppBaseController {

    /**
     * Gets the gift.
     *
     * @return the the gift
     */
    @PostMapping("/index")
    public JsonResult getTheGift(@RequestBody @Validated EnterPolitelyParam param) {
        return this.success(shop().enterPolitelyService.enterPolitely(param.getUserId()));
    }
}
