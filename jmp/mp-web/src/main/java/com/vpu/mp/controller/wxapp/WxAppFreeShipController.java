package com.vpu.mp.controller.wxapp;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.wxapp.market.freeshipping.FreeShippingGoodsListParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 满包邮
 * @author 孔德成
 * @date 2019/12/11 10:20
 */
@RestController
@RequestMapping("/api/wxapp/freeship")
public class WxAppFreeShipController extends WxAppBaseController{



    @PostMapping("/goods/list")
    public JsonResult getFreeShippingGoods(@RequestBody FreeShippingGoodsListParam param){
        Integer userId = wxAppAuth.user().getUserId();
        param.setUserId(userId);
        return success(shop().freeShipping.freeShipGoods.freeShipGoodsList(param));
    }
}
