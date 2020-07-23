package com.vpu.mp.controller.wxapp;

import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.common.foundation.data.JsonResultCode;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.service.pojo.wxapp.medical.im.SessionTest;
import com.vpu.mp.service.pojo.wxapp.medical.im.base.ImSessionItemBase;
import com.vpu.mp.service.pojo.wxapp.medical.im.param.ImSessionPageListParam;
import com.vpu.mp.service.pojo.wxapp.medical.im.param.ImSessionPullMsgParam;
import com.vpu.mp.service.pojo.wxapp.medical.im.param.ImSessionSendMsgParam;
import com.vpu.mp.service.pojo.wxapp.medical.im.vo.ImSessionListVo;
import com.vpu.mp.service.pojo.wxapp.medical.im.vo.ImSessionRenderVo;
import com.vpu.mp.service.shop.im.ImSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 李晓冰
 * @date 2020年07月21日
 */
@RestController
public class WxAppImSessionController extends WxAppBaseController{

    @Autowired
    private ImSessionService imSessionService;

    @PostMapping("/api/wxapp/im/session/page/list")
    public JsonResult pageList(@RequestBody ImSessionPageListParam pageListParam) {
        PageResult<ImSessionListVo> pageResult = imSessionService.pageList(pageListParam);
        return success(pageResult);
    }

    /**
     *  过往聊天内容初始化
     * @param sessionId 会话id
     * @return 过往会话内容
     */
    @PostMapping("/api/wxapp/im/session/render/{sessionId}")
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

    /**
     * 关闭会话
     * @param sessionId 会话id
     * @return jsonResult
     */
    @PostMapping("/api/wxapp/im/session/close/{sessionId}")
    public JsonResult closeSession(@PathVariable("sessionId") Integer sessionId) {
        if (sessionId == null) {
            return fail(JsonResultCode.IM_SESSION_ID_IS_NULL);
        }
        imSessionService.closeImSession(sessionId);
        return success();
    }

    /**
     * 会话变为进行中
     * @param sessionId 会话id
     * @return jsonResult
     */
    @PostMapping("/api/wxapp/im/session/going/{sessionId}")
    public JsonResult goingSession(@PathVariable("sessionId") Integer sessionId) {
        if (sessionId == null) {
            return fail(JsonResultCode.IM_SESSION_ID_IS_NULL);
        }
        imSessionService.updateSessionToGoingOn(sessionId);
        return success();
    }


    /**
     * 向对方发送消息
     * @param param
     * @return
     */
    @PostMapping("/api/wxapp/im/session/send")
    public JsonResult sendMsg(@RequestBody ImSessionSendMsgParam param){
        imSessionService.sendMsg(param);
        return success();
    }

    /**
     * 拉取对方消息
     * @param param
     * @return
     */
    @PostMapping("/api/wxapp/im/session/pull")
    public JsonResult pullMsg(@RequestBody  ImSessionPullMsgParam param){
        List<ImSessionItemBase> imSessionItemPullVos = imSessionService.pullMsg(param);
        return success(imSessionItemPullVos);
    }

    @PostMapping("/api/wxapp/im/session/test")
    public JsonResult test(@RequestBody SessionTest sessionTest) {
        if (Integer.valueOf(1).equals(sessionTest.getType())) {
            imSessionService.batchCancelSession(sessionTest.getOrderSns());
        }

        if (Integer.valueOf(1).equals(sessionTest.getType())) {
            imSessionService.batchCloseSession(sessionTest.getOrderSns());
        }
        return success();
    }

}
