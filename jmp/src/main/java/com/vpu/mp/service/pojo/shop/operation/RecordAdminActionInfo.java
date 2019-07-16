package com.vpu.mp.service.pojo.shop.operation;

import lombok.Builder;

import java.sql.Timestamp;

/**
 * 
 * @author: 卢光耀
 * @date: 2019-07-16 14:44
 *
*/
@Builder
public class RecordAdminActionInfo {
    private String userName;

    private String actionTypeName;

    private Timestamp createTime;

    private String content;
}
