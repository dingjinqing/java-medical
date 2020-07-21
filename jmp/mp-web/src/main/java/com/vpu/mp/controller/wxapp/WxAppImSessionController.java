package com.vpu.mp.controller.wxapp;

import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.common.foundation.data.JsonResultCode;
import com.vpu.mp.service.pojo.wxapp.medical.im.param.ImSessionSendMsgParam;
import com.vpu.mp.service.pojo.wxapp.medical.im.vo.ImSessionRenderVo;
import com.vpu.mp.service.shop.im.ImSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 李晓冰
 * @date 2020年07月21日
 */
@RestController
public class WxAppImSessionController extends WxAppBaseController{

    @Autowired
    private ImSessionService imSessionService;

    @PostMapping("/api/wxapp/im/render/session/{sessionId}")
    public JsonResult renderSession(@PathVariable("sessionId") Integer sessionId) {
        if (sessionId == null) {
            return fail(JsonResultCode.IM_SESSION_ID_IS_NULL);
        }

        if (!imSessionService.sessionExist(sessionId)) {
            return fail(JsonResultCode.IM_SESSION_NOT_EXIST);
        }

        ImSessionRenderVo imSessionRenderVo = imSessionService.renderSession(sessionId);
        return success(imSessionRenderVo);
    }

    @PostMapping("/api/wxapp/im/session/send")
    public JsonResult sendMsg(@RequestBody ImSessionSendMsgParam param){
        imSessionService.sendMsg(param);
        return success();
    }

    @PostMapping("/api/wxapp/im/session/pull")
    public JsonResult pullMsg(){
        return null;
    }

}
