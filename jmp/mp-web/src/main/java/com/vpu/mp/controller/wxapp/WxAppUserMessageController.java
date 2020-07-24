package com.vpu.mp.controller.wxapp;

import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.message.DoctorMessageCountParam;
import com.vpu.mp.service.shop.message.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 赵晓东
 * @description
 * @create 2020-07-23 16:45
 **/

@RestController
@RequestMapping("/api/wxapp/message")
public class WxAppUserMessageController extends WxAppBaseController{

    @Autowired
    private MessageService messageService;

    /**
     * 用户消息列表展示
     * @param userId 接收消息用户id
     * @return JsonResult
     */
    @RequestMapping("/list")
    @ResponseBody
    public JsonResult showMessageList(Integer userId){
        return this.success(messageService.showUserMessage(userId));
    }

    /**
     * 用户未读消息统计
     * @param userId 接收消息用户id
     * @return JsonResult
     */
    @RequestMapping("/count")
    @ResponseBody
    public JsonResult countMessage(Integer userId){
        return this.success(messageService.countMessage(userId));
    }

    /**
     * 消息读取状态变更
     * @param messageId 消息id
     * @param status 消息更新状态
     * @return JsonResult
     */
    @RequestMapping("/change")
    @ResponseBody
    public JsonResult changeMessageStatus(Integer messageId, Byte status) {
        messageService.changeMessageStatus(messageId, status);
        return this.success();
    }

    @RequestMapping("/doctor/count")
    @ResponseBody
    public JsonResult doctorMessageCount(DoctorMessageCountParam doctorMessageCountParam){
        return this.success(messageService.countDoctorMessage(doctorMessageCountParam));
    }



}
