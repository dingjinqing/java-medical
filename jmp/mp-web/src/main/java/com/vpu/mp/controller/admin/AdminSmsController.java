package com.vpu.mp.controller.admin;

import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.pojo.shop.sms.SmsAccountParam;
import com.vpu.mp.service.pojo.shop.sms.SmsConfigParam;
import com.vpu.mp.service.pojo.shop.sms.SmsSendRecordAdminParam;
import com.vpu.mp.service.shop.sms.SmsAccountService;
import com.vpu.mp.service.shop.sms.SmsConfigService;
import com.vpu.mp.service.shop.sms.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 短信
 * @author 赵晓东
 * @description
 * @create 2020-07-27 14:00
 **/
@RestController
public class AdminSmsController extends AdminBaseController {

    @Autowired
    private SmsService smsService;
    @Autowired
    private SmsAccountService smsAccountService;
    @Autowired
    private SmsConfigService smsConfigService;

    @PostMapping("/api/admin/sms/list")
    public JsonResult getAdminSmsSendRecord(@RequestBody SmsSendRecordAdminParam smsSendRecordAdminParam){
        return super.success(smsService.getAdminSmsSendRecordPageList(smsSendRecordAdminParam));
    }

    /**
     * 添加账户
     * @param param
     * @return
     */
    @PostMapping("/api/admin/sms/account/create")
    public JsonResult createSmsAccount(@RequestBody @Validated SmsAccountParam param){

        try {
            return success(smsAccountService.createSmsAccount(param));
        } catch (MpException e) {
            e.printStackTrace();
        }
        return fail();
    }

    /**
     * 店铺短信配置
     * @param smsConfigParam 短信配置内容
     * @return JsonResult
     */
    @PostMapping("/api/admin/sms/config")
    public JsonResult smsConfig(@RequestBody SmsConfigParam smsConfigParam) {
        smsConfigParam.setShopId(shopId());
        smsConfigService.updateSmsConfig(smsConfigParam);
        return success();
    }

    /**
     * 展示当前短信配置信息
     * @return JsonResult
     */
    @PostMapping("/api/admin/sms/show")
    public JsonResult showSmsConfig() {
        return this.success(smsConfigService.selectSmsConfig(shopId()));
    }

}
