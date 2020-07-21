package com.vpu.mp.common.pojo.shop.table;

import java.sql.Timestamp;

/**
 * 问诊会话详情 记录每一条会话
 * @author 李晓冰
 * @date 2020年07月21日
 */
public class ImSessionItemDo {
    private Integer   id;
    private Integer   imSessionId;
    private Integer   formId;
    private Integer   toId;
    private String    message;
    private Timestamp createTime;
}
