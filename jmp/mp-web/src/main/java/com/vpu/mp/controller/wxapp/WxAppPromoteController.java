package com.vpu.mp.controller.wxapp;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.market.friendpromote.PromoteInfo;
import com.vpu.mp.service.pojo.shop.market.friendpromote.PromoteParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 好友助力
 *
 * @author liangchen
 * @date 2020.03.02
 */
@RestController
@RequestMapping("/api/wxapp/promote")
public class WxAppPromoteController extends WxAppBaseController {

    /**
     * 好友助力活动展示
     *
     * @param param 用户id 助力活动码 发起id
     * @return promoteInfo 助力活动信息
     */
    @PostMapping("/info")
    public JsonResult promoteInfo(@RequestBody PromoteParam param) {

        PromoteInfo promoteInfo = shop().friendPromoteService.promoteInfo(param);

        return success(promoteInfo);
    }
}