package com.vpu.mp.controller.wxapp;

import com.vpu.mp.common.foundation.data.ImSessionConstant;
import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.common.foundation.data.JsonResultCode;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.common.pojo.shop.table.ImSessionDo;
import com.vpu.mp.service.pojo.wxapp.medical.im.base.ImSessionItemBase;
import com.vpu.mp.service.pojo.wxapp.medical.im.param.*;
import com.vpu.mp.service.pojo.wxapp.medical.im.vo.ImSessionItemRenderVo;
import com.vpu.mp.service.pojo.wxapp.medical.im.vo.ImSessionListVo;
import com.vpu.mp.service.pojo.wxapp.medical.im.vo.ImSessionUnReadInfoVo;
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
public class WxAppImSessionController extends WxAppBaseController {

    @Autowired
    private ImSessionService imSessionService;

    @PostMapping("/api/wxapp/im/session/page/list")
    public JsonResult pageList(@RequestBody ImSessionPageListParam pageListParam) {
        PageResult<ImSessionListVo> pageResult = imSessionService.pageList(pageListParam);
        return success(pageResult);
    }

    /**
     * 过往聊天内容初始化
     * @param param 会话详情查询分页信息
     * @return 过往会话内容
     */
    @PostMapping("/api/wxapp/im/session/render")
    public JsonResult renderSession(@RequestBody ImSessionRenderPageParam param) {
        if (param.getSessionId() == null) {
            return fail(JsonResultCode.IM_SESSION_ID_IS_NULL);
        }

        if (!imSessionService.sessionExist(param.getSessionId())) {
            return fail(JsonResultCode.IM_SESSION_NOT_EXIST);
        }

        PageResult<ImSessionItemRenderVo> imSessionItemRenderVoPageResult = imSessionService.renderSession(param);
        return success(imSessionItemRenderVoPageResult);
    }

    /**
     * 查询会话状态
     * @param sessionId 会话id
     * @return 会话状态
     */
    @PostMapping("/api/wxapp/im/session/status/{sessionId}")
    public JsonResult getSessionStatus(@PathVariable("sessionId") Integer sessionId) {
        if (sessionId == null) {
            return fail(JsonResultCode.IM_SESSION_ID_IS_NULL);
        }

        Byte sessionStatus = imSessionService.getSessionStatus(sessionId);
        if (sessionStatus == null) {
            return fail(JsonResultCode.IM_SESSION_NOT_EXIST);
        }
        return success(sessionStatus);
    }

    /**
     * 根据订单号查询会话信息
     * @param param
     * @return
     */
    @PostMapping("/api/wxapp/im/session/get/orderSn")
    public JsonResult getSessionIdByOrderSn(@RequestBody ImSessionQueryParam param){
        ImSessionDo imSessionDo = imSessionService.getSessionInfoByOrderSn(param.getOrderSn());
        return success(imSessionDo.getId());
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
    public JsonResult sendMsg(@RequestBody ImSessionSendMsgParam param) {
        if (param.getSessionId() == null || param.getFromId() == null || param.getToId() == null) {
            return fail(JsonResultCode.IM_SESSION_PARAM_LACK);
        }
        Byte status = imSessionService.sendMsg(param);
        if (ImSessionConstant.SESSION_CAN_NOT_USE.equals(status)) {
            return fail(JsonResultCode.IM_SESSION_CAN_NOT_USE);
        }

        return success();
    }

    /**
     * 拉取对方消息
     * @param param
     * @return
     */
    @PostMapping("/api/wxapp/im/session/pull")
    public JsonResult pullMsg(@RequestBody ImSessionPullMsgParam param) {
        if (param.getSessionId() == null || param.getPullFromId() == null || param.getSelfId() == null) {
            return fail(JsonResultCode.IM_SESSION_PARAM_LACK);
        }
        List<ImSessionItemBase> imSessionItemPullVos = imSessionService.pullMsg(param);
        if (imSessionItemPullVos == null) {
            return fail(JsonResultCode.IM_SESSION_CAN_NOT_USE);
        }
        return success(imSessionItemPullVos);
    }

    @PostMapping("/api/wxapp/im/session/new")
    public JsonResult newSession(@RequestBody ImSessionNewParam param) {
        Integer integer = imSessionService.insertNewSession(param);
        return success(integer);
    }

    @PostMapping("/api/wxapp/im/test")
    public JsonResult test(@RequestBody ImSessionUnReadMessageInfoParam param){
        List<ImSessionUnReadInfoVo> unReadMessageInfo = imSessionService.getUnReadMessageInfo(param);
        return success(unReadMessageInfo);
    }
}
