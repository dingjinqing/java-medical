package com.vpu.mp.controller.wxapp;

import com.vpu.mp.common.foundation.data.JsonResult;
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

}
