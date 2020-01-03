package com.vpu.mp.controller.wxapp;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.wxapp.login.WxAppSessionUser;
import com.vpu.mp.service.pojo.wxapp.market.prize.PrizeRecordParam;
import com.vpu.mp.service.pojo.wxapp.market.prize.PrizeRecordVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 我的奖品
 * @author 孔德成
 * @date 2020/1/3 9:34
 */
@RestController
public class WxAppPrizeController  extends WxAppBaseController {



    @PostMapping("/api/wxapp/prize/list")
    private JsonResult getList(@RequestBody @Valid PrizeRecordParam param){
        WxAppSessionUser userInfo = this.wxAppAuth.user();
        PageResult<PrizeRecordVo> list = shop().prizeRecord.getList(userInfo.getUserId(), param);
        return success(list);
    }




}
