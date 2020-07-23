package com.vpu.mp.service.pojo.shop.message;

import lombok.Data;

/**
 * @author 赵晓东
 * @description 用户消息入参
 * @create 2020-07-23 15:33
 **/
@Data
public class UserMessageParam {
    /**
     * 消息内容
     */
    private String    messageContent;
    /**
     * 消息类型
     */
    private Byte      messageType;
    /**
     * 消息接受者id
     */
    private Integer   receiverId;
    /**
     * 消息接受者姓名
     */
    private String    receiverName;
    /**
     * 消息发送者id
     */
    private Integer   senderId;
    /**
     * 消息发送者姓名
     */
    private String    senderName;
    /**
     * 消息关联id
     */
    private Integer   messageRelevanceId;
}
