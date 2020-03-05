package com.vpu.mp.controller.wxapp;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.market.payaward.PayAwardOrderParam;
import com.vpu.mp.service.pojo.shop.market.payaward.PayAwardOrderVo;
import com.vpu.mp.service.pojo.wxapp.login.WxAppSessionUser;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 支付有礼
 *
 * @author 孔德成
 * @date 2019/12/18 10:31
 */
@RestController
public class WxAppPayAwardController extends WxAppBaseController {


    /**
     * 查询订单的支付有礼活动
     *
     * @return
     */
    @PostMapping("/api/wxapp/payaward/prize/info")
    public JsonResult getOrderPayAwardPrize(@RequestBody @Validated PayAwardOrderParam param) {
        PayAwardOrderVo orderPayAward = shop().payAward.getOrderPayAward(param.getOrderSn(),getLang());
        return success(orderPayAward);
    }


}
