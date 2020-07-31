package com.vpu.mp.controller.wxapp;

import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.message.MessageParam;
import com.vpu.mp.service.shop.message.UserMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    private UserMessageService messageService;

    /**
     * 用户消息列表展示
     * @param messageParam 接收消息用户id
     * @return JsonResult
     */
    @RequestMapping("/list")
    public JsonResult showMessageList(@RequestBody MessageParam messageParam){
        return this.success(messageService.showUserMessage(messageParam.getUserId()));
    }

    /**
     * 用户未读消息统计
     * @param messageParam 接收消息用户id
     * @return JsonResult
     */
    @RequestMapping("/count")
    public JsonResult countMessage(@RequestBody MessageParam messageParam){
        return this.success(messageService.countMessage(messageParam.getUserId()));
    }

    /**
     * 消息读取状态变更
     * @param messageParam 消息id
     * @return JsonResult
     */
    @RequestMapping("/change")
    public JsonResult changeMessageStatus(@RequestBody MessageParam messageParam) {
        messageService.changeMessageStatus(messageParam.getMessageId(), messageParam.getStatus());
        return this.success();
    }

    /**
     * 用户删除消息
     * @param messageParam 用户消息入参
     * @return JsonResult
     */
    @RequestMapping("/delete")
    public JsonResult deleteUserMessage(@RequestBody MessageParam messageParam){
        messageService.deleteUserMessage(messageParam.getMessageId());
        return this.success();
    }
}
