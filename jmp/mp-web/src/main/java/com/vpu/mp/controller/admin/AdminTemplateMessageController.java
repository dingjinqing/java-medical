package com.vpu.mp.controller.admin;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.market.message.MessageTemplateQuery;
import com.vpu.mp.service.pojo.shop.market.message.UserInfoQuery;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 营销管理-消息推送
 * @author 卢光耀
 * @date 2019-08-08 17:26
 *
*/
@RequestMapping("/api/admin/market/message/template")
@RestController
public class AdminTemplateMessageController extends AdminBaseController {

    @RequestMapping("/getUser")
    public JsonResult getSendUsers(@RequestBody UserInfoQuery query){
        
        return null;
    }
}
