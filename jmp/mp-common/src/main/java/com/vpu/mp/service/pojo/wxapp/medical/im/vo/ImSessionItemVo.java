package com.vpu.mp.service.pojo.wxapp.medical.im.vo;

import lombok.Data;

import java.sql.Timestamp;

/**
 * 会话详情类
 * @author 李晓冰
 * @date 2020年07月21日
 */
@Data
public class ImSessionItemVo{
    /**会话消息*/
    private String message;

    private Timestamp sendTime;
}
