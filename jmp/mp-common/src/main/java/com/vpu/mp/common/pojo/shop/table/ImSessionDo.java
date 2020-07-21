package com.vpu.mp.common.pojo.shop.table;

import java.sql.Timestamp;

/**
 * 问诊会话
 * @author 李晓冰
 * @date 2020年07月21日
 */
public class ImSessionDo {
    private Integer   id;
    private Integer   doctorId;
    private Integer   userId;
    private Integer   patientId;
    private Byte      sessionStatus;
    private String    orderSn;
    private Timestamp limitTime;
    private Byte      isDelete;
    private Timestamp createTime;
    private Timestamp updateTime;
}
