package com.vpu.mp.controller.wxapp;

import com.vpu.mp.service.foundation.data.JsonResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 我的奖品
 * @author 孔德成
 * @date 2020/3/18
 */
@RestController
@RequestMapping("/api/wxapp/myprize")
public class WxAppMyPrizeController extends WxAppBaseController{

    @PostMapping("/list")
    public JsonResult getMyPrizeList(){
        return success();
    }


}
