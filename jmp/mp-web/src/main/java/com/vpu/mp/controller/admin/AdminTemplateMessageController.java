package com.vpu.mp.controller.admin;

import com.vpu.mp.service.foundation.util.Page;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.market.message.MessageTemplateQuery;
import com.vpu.mp.service.pojo.shop.market.message.MessageTemplateVo;
import org.springframework.web.bind.annotation.*;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.market.message.MessageTemplateParam;
import com.vpu.mp.service.pojo.shop.market.message.UserInfoQuery;

/**
 * 营销管理-消息推送
 * @author 卢光耀
 * @date 2019-08-08 17:26
 *
*/
@RequestMapping("/api/admin/market/message/template")
@RestController
public class AdminTemplateMessageController extends AdminBaseController {



    @PostMapping("/getUser")
    public JsonResult getSendUsers(@RequestBody UserInfoQuery query){
        return success(shop().messageTemplateService.getSendUsersSize(query));
    }
    @PostMapping("/addMessage")
    public JsonResult addMessage(@RequestBody MessageTemplateParam param){
        shop().messageTemplateService.insertMessageTemplate(param);
        return success();
    }

    @GetMapping("/list")
    public JsonResult getTemplateMessagePage(@RequestBody MessageTemplateQuery param){
        return success(shop().messageTemplateService.getPageByParam(param));
    }
    @PostMapping("/delete")
    public JsonResult deleteById(@RequestBody Integer id){
        shop().messageTemplateService.deleteById(id);
        return success();
    }
    @PostMapping("/getDetail")
    public JsonResult getTemplateMessageDetail(@RequestBody Integer id ){
        return success(shop().messageTemplateService.getMessageDetail(id));
    }
    @GetMapping("/record/list")
    public JsonResult getSendRecordPage(@RequestBody MessageTemplateQuery param){
        return success(shop().messageTemplateService.getSendRecord(param));
    }
}
