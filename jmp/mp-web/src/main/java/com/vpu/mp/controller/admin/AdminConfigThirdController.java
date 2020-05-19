package com.vpu.mp.controller.admin;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.config.trade.third.ThirdAppAuthIdParam;
import com.vpu.mp.service.pojo.shop.config.trade.third.ThirdAppKeyParam;
import com.vpu.mp.service.pojo.shop.config.trade.third.ThirdAuthorizeParam;
import com.vpu.mp.service.pojo.shop.config.trade.third.ThirdInfoParam;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 第三方配置
 * @author 孔德成
 * @date 2020/5/18
 */
@RestController
@RequestMapping("/api/admin/config/third/auth")
public class AdminConfigThirdController  extends AdminBaseController {

    /**
     * 获取erp/pos/crm配置信息
     * @return
     */
    @PostMapping("/get")
    public JsonResult getThirdAuthInfo(@RequestBody @Valid ThirdInfoParam param){
        return success(shop().thirdAuthService.getThirdAuthInfo(param));
    }

    /**
     * 授权
     * @return
     */
    @PostMapping("/enable")
    public JsonResult authorize(@RequestBody @Validated ThirdAuthorizeParam param){
        shop().thirdAuthService.authorize(param);
        return success();
    }

    /**
     * 重置sessionKey
     * @return
     */
    @PostMapping("/resetsessionkey")
    public JsonResult resetSessionKey(@RequestBody @Valid ThirdAppAuthIdParam param){
        int i = shop().thirdAuthService.resetSessionKey(param.getId());
        if (i==1)
            return success();
        else
            return fail();
    }

    /**
     * 保存appkey
     * @return
     */
    @PostMapping("/appkey/save")
    public JsonResult saveAppKey(@RequestBody @Valid ThirdAppKeyParam param){
        shop().thirdAuthService.saveAppKey(param);
        return success();
    }

}
