package com.vpu.mp.common.pojo.shop.table;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author chenjie
 * @date 2020年08月11日
 */
@Data
public class UserDoctorAttentionDo {
    private Integer   id;
    private Integer   userId;
    private Integer   doctorId;
    private Byte      isDelete;
    private Timestamp createTime;
    private Timestamp updateTime;
}
