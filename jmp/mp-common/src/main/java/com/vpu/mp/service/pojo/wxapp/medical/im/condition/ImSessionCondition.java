package com.vpu.mp.service.pojo.wxapp.medical.im.condition;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author 李晓冰
 * @date 2020年07月22日
 */
@Data
public class ImSessionCondition {
    private Byte status;

    private Timestamp lessCreateTime;

    private Timestamp limitTime;
}
