package com.vpu.mp.service.pojo.shop.operation;

import com.vpu.mp.service.pojo.shop.base.BasePageParam;
import lombok.Data;

import java.sql.Timestamp;

/**
 * RecordAdminAction入参
 * @author: 卢光耀
 * @date: 2019-07-16 14:19
 *
*/
@Data
public class RecordAdminActionParam extends BasePageParam {
    private String    userName;
    private Byte      actionType;
    private Timestamp startCreateTime;
    private Timestamp endCreateTime;

}
