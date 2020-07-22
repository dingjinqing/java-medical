package com.vpu.mp.service.pojo.wxapp.medical.im.param;

import lombok.Data;

/**
 * 发送消息入参类
 * @author 李晓冰
 * @date 2020年07月21日
 */
@Data
public class ImSessionSendMsgParam {
    /**
     * 患者id
     */
    private Integer patientId;
    /**
     * 消息发送者
     */
    private Integer fromId;
    /**
     * 消息接受者
     */
    private Integer toId;
    /**
     * 消息内容
     * */
    private String msg;
}
