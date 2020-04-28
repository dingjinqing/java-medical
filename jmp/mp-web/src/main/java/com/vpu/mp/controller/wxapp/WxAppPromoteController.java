package com.vpu.mp.controller.wxapp;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.market.friendpromote.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


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
    /**
     * 分享获得助力次数
     *
     * @param param 用户id 发起id
     * @return 提示信息
     */
    @PostMapping("/addTimes")
    public JsonResult addTimes(@RequestBody PromoteShareOrAuthParam param) {

        AddPromoteTimesVo vo = shop().friendPromoteService.addPromoteTimes(param);

        return success(vo);
    }
    /**
     * 助力用户详情
     *
     * @param param 发起id
     * @return 提示信息
     */
    @PostMapping("/detailList")
    public JsonResult promoteDetail(@RequestBody PromoteParam param) {

        List<PromoteDetail> vo = shop().friendPromoteService.friendPromoteDetailList(param);

        return success(vo);
    }
    /**
     * 获取活动说明
     *
     * @param param actCode
     * @return 活动说明
     */
    @PostMapping("/actCopywriting")
    public JsonResult actCopywriting(@RequestBody PromoteParam param) {
        promoteActCopywriting vo = saas.getShopApp(param.getShopId()).friendPromoteService.getActCopywriting(param);
        return success(vo);
    }
}