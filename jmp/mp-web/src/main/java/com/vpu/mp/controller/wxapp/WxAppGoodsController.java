package com.vpu.mp.controller.wxapp;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.goods.goods.GoodsIdParams;
import com.vpu.mp.service.pojo.wxapp.activity.capsule.ActivityGoodsListCapsule;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 李晓冰
 * @date 2019年10月14日
 * 小程序商品相关接口
 */
@RestController
public class WxAppGoodsController extends WxAppBaseController{

    @PostMapping("/api/wxapp/goods/test")
    public JsonResult test(@RequestBody GoodsIdParams params){
        Integer userId = wxAppAuth.user().getUserId();
        List<ActivityGoodsListCapsule> test = shop().goodsMp.test();
        return success(test);
    }
}
