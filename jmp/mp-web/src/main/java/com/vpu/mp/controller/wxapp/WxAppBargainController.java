package com.vpu.mp.controller.wxapp;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.wxapp.login.WxAppSessionUser;
import com.vpu.mp.service.pojo.wxapp.market.bargain.BargainRecordListQueryParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: 王兵兵
 * @create: 2019-10-24 11:20
 **/
@RestController
public class WxAppBargainController extends WxAppBaseController {
    @PostMapping("/api/wxapp/bargain/list")
    public JsonResult getBargainRecordList(@RequestBody BargainRecordListQueryParam param) {
        WxAppSessionUser user = wxAppAuth.user();
        return success(shop().bargain.bargainRecord.getRecordPageList(user.getUserId(),param));
    }
}
