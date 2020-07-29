package com.vpu.mp.controller.admin;

import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.sms.SmsSendRecordAdminParam;
import com.vpu.mp.service.shop.sms.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 赵晓东
 * @description
 * @create 2020-07-27 14:00
 **/

@RestController
@RequestMapping("/api/admin/sms")
public class AdminSmsSendRecordController extends AdminBaseController {

    @Autowired
    private SmsService smsService;

    @RequestMapping("/list")
    public JsonResult getAdminSmsSendRecord(@RequestBody SmsSendRecordAdminParam smsSendRecordAdminParam){
        return super.success(smsService.getAdminSmsSendRecordPageList(smsSendRecordAdminParam));
    }

}
