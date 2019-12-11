package com.vpu.mp.controller.wxapp;

import com.vpu.mp.service.foundation.data.JsonResult;
import org.springframework.web.bind.annotation.PatchMapping;
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



    @PatchMapping("/goods/list")
    public JsonResult getFreeShippingGoods(){
        return null;
    }
}
