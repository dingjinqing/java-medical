package com.vpu.mp.controller.admin;

import com.vpu.mp.service.foundation.util.Page;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.market.message.*;
import org.springframework.web.bind.annotation.*;

import com.vpu.mp.service.foundation.data.JsonResult;

/**
 * 营销管理-消息推送
 * @author 卢光耀
 * @date 2019-08-08 17:26
 *
*/
@RequestMapping("/api/admin/market/message/template")
@RestController
public class AdminTemplateMessageController extends AdminBaseController {



    @PostMapping("/getUserNumbers")
    public JsonResult getSendUsers(@RequestBody UserInfoQuery query){
        return success(shop().messageTemplateService.getSendUsersSize(query));
    }
    @PostMapping("/getUserArray")
    public JsonResult getUserArray(@RequestBody MessageUserQuery query){
        return success(shop().messageTemplateService.getUserVoPage(query));
    }
    @PostMapping("/updateCheckedUser")
    public JsonResult updateCheckedUser(@RequestBody MessageUserQuery query){
        try{
            shop().messageTemplateService.updateClickStatus(query);
            return success();
        }catch (Exception e){
            e.printStackTrace();
            return fail();
        }
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
    @PostMapping("/analysis")
    public JsonResult queryMessageStatistics(@RequestBody MessageTemplateQuery param){
        return success(shop().messageTemplateService.queryStatisticsData(param));
    }
}
