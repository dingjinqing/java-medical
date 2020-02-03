package com.vpu.mp.controller.wxapp;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.pojo.shop.market.lottery.JoinLottery;
import com.vpu.mp.service.pojo.shop.market.lottery.JoinLotteryParam;
import com.vpu.mp.service.pojo.shop.market.lottery.LotteryVo;
import com.vpu.mp.service.pojo.wxapp.login.WxAppSessionUser;
import com.vpu.mp.service.pojo.wxapp.market.lottery.LotteryIdParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 抽奖
 * @author 孔德成
 * @date 2020/1/16 15:43
 */
@RestController
@RequestMapping("/api/wxapp/lottery")
public class WxAppLotteryController extends WxAppBaseController{

    /**
     * 获取活动信息
     * @param param
     * @return
     */
    @PostMapping("/get")
    public JsonResult get(@RequestBody @Valid LotteryIdParam param) {
        LotteryVo lotteryVo = shop().lottery.getLotteryVo(param.getId());
        if (lotteryVo==null){
            return fail(JsonResultCode.CODE_PARAM_ERROR);
        }
        return success(lotteryVo);
    }

    /**
     * 参加抽奖活动
     * @param param
     * @return
     */
    @PostMapping("/join")
    public JsonResult JoinLottery(@RequestBody @Valid JoinLotteryParam param){
        WxAppSessionUser user = wxAppAuth.user();
        param.setUserId(user.getUserId());
        JoinLottery joinLottery = shop().lottery.joinLottery(param);
        return success(joinLottery);
    }
}
