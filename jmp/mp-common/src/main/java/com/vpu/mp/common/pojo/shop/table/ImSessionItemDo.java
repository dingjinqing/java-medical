package com.vpu.mp.common.pojo.shop.table;

import lombok.Data;

import java.sql.Timestamp;

/**
 * 问诊会话详情 记录每一条会话
 * @author 李晓冰
 * @date 2020年07月21日
 */
@Data
public class ImSessionItemDo {
    private Integer   id;
    private Integer   imSessionId;
    private Integer   formId;
    private Integer   toId;
    private String    message;
    private Timestamp sendTime;
    private Timestamp createTime;
}
