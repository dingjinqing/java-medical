package com.vpu.mp.controller.wxapp;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.wxapp.market.seckill.SecKillProductParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 秒杀
 * @author: 王兵兵
 * @create: 2019-11-13 15:03
 **/
@RestController
public class WxAppSecKillController extends WxAppBaseController {

    /**
     * 	校验秒杀规格当前可用状态
     */
    @PostMapping("/api/wxapp/seckill/check")
    public JsonResult checkSeckillProductStock(@RequestBody @Validated SecKillProductParam param) {
        return success(shop().seckill.canApplySecKill(param,wxAppAuth.user().getUserId()));
    }
}
