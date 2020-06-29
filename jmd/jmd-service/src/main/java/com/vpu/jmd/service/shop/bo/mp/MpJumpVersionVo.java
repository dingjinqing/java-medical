package com.vpu.jmd.service.shop.bo.mp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

/**
 * @author 孔德成
 * @date 2020/6/12 9:52
 */
@Getter
@Setter
@ToString
public class MpJumpVersionVo {
    /**
     * 0:申请中，1:已提交
     */
    private Byte flag;

    private Timestamp addTime;
}
