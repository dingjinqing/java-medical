package com.vpu.mp.controller.store;

import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.common.foundation.data.JsonResultCode;
import com.vpu.mp.service.pojo.shop.auth.StoreLoginParam;
import com.vpu.mp.service.pojo.shop.auth.StoreTokenAuthInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * @author chenjie
 * @date 2020年08月26日
 */
public class StoreLoginController extends StoreBaseController {
    @PostMapping(value = "/store/login")
    public JsonResult login(@RequestBody @Valid StoreLoginParam param) {
        StoreTokenAuthInfo info = storeAuth.login(param);

        if (info != null) {
            return this.success(info);
        } else {
            return this.fail(JsonResultCode.CODE_ACCOUNT_OR_PASSWORD_INCRRECT);
        }
    }


    @GetMapping(value = "/store/logout")
    public JsonResult logout() {
        storeAuth.logout();
        return success(JsonResultCode.CODE_SUCCESS);
    }

    /**
     * 判断用户是否在线，首页右上角是否显示用户信息用
     * @return
     */
    @GetMapping(value = "/store/login/isLogin")
    public JsonResult checkToken() {
        StoreTokenAuthInfo user = storeAuth.user();
        if(user!=null) {
            return success();
        }
        return fail();
    }
}
