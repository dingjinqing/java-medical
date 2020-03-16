package com.vpu.mp.controller.wxapp;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.pojo.shop.market.form.FormDetailVo;
import com.vpu.mp.service.pojo.wxapp.login.WxAppSessionUser;
import com.vpu.mp.service.pojo.wxapp.market.form.FormGetParam;
import com.vpu.mp.service.pojo.wxapp.market.form.FormSubmitDataParam;
import com.vpu.mp.service.pojo.wxapp.market.form.FormSuccessParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 表单统计
 * @author 孔德成
 * @date 2020/3/13
 */
@RestController
@RequestMapping("/api/wxapp/form")
public class WxAppFormDecorationController extends WxAppBaseController{

    /**
     * 获取表单配置
     * @return
     */
    @PostMapping("/get")
    private JsonResult getInfo(@RequestBody @Validated FormGetParam param){
        Integer userId = wxAppAuth.user().getUserId();
        param.setUserId(userId);
        FormDetailVo formDecorationInfo = shop().formService.getFormDecorationInfo(param.getPageId(), param.getUserId());
        return success(formDecorationInfo);
    }

    /**
     * 提交表单
     * @return
     */
    @PostMapping("/submit")
    private JsonResult submitFormData(@RequestBody @Validated FormSubmitDataParam param) throws MpException {
        WxAppSessionUser user = wxAppAuth.user();
        param.setUser(user);
        shop().formService.submitFormDate(param);
        return success();
    }

    /**
     * 表单提交成功
     */
    @PostMapping("/success")
    private JsonResult submitSuccess(@RequestBody @Validated FormSuccessParam param){
        shop().formService.submitSuccess(param);
        return success();
    }

}
