package com.vpu.mp.controller.wxapp;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.market.groupbuy.param.GroupBuyIdParam;
import com.vpu.mp.service.pojo.wxapp.login.WxAppSessionUser;
import com.vpu.mp.service.pojo.wxapp.market.groupbuy.GroupBuyInfoParam;
import com.vpu.mp.service.pojo.wxapp.market.groupbuy.GroupBuyInfoVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 多人拼团接口
 * @author 孔德成
 * @date 2019/12/10 14:48
 */
@RestController
@RequestMapping("/api/wxapp/groupbuy")
public class WxAppGroupBuyController extends WxAppBaseController {


    /**
     * 拼团详情
     * @param param
     * @return
     */
    @PostMapping("/info")
    public JsonResult getGroupBuyInfo(@RequestBody @Valid GroupBuyInfoParam param){
        WxAppSessionUser user = wxAppAuth.user();
        param.setUserId(user.getUserId());
        GroupBuyInfoVo groupBuyInfo = shop().groupBuy.getGroupBuyInfo(param,getLang());
        return success(groupBuyInfo);
    }

    /**
     * 获取拼团说明
     * @param param id
     * @return 拼团说明
     */
    @PostMapping("/copywriting")
    public JsonResult getActivityCopywriting(@RequestBody @Validated GroupBuyIdParam param){
        String activityCopywriting = shop().groupBuy.getActivityCopywriting(param.getId());
        return success(activityCopywriting);
    }

}
