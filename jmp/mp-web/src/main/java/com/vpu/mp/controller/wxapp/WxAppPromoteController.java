package com.vpu.mp.controller.wxapp;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.market.friendpromote.LaunchVo;
import com.vpu.mp.service.pojo.shop.market.friendpromote.PromoteInfo;
import com.vpu.mp.service.pojo.shop.market.friendpromote.PromoteParam;
import com.vpu.mp.service.pojo.shop.market.friendpromote.PromoteVo;
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

    /**
     * 好友助力发起
     *
     * @param param 用户id 助力活动码
     * @return launchVo 发起信息
     */
    @PostMapping("/launch")
    public JsonResult promoteLaunch(@RequestBody PromoteParam param) {

        LaunchVo launchVo = shop().friendPromoteService.friendPromoteLaunch(param);

        return success(launchVo);
    }

    /**
     * 好友帮忙点击
     *
     * @param param 用户id 助力活动码
     * @return launchVo 发起信息
     */
    @PostMapping("/participate")
    public JsonResult promoteParticipate(@RequestBody PromoteParam param) {

        PromoteVo promoteVo = shop().friendPromoteService.friendPromote(param);

        return success(promoteVo);
    }
}