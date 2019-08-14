package com.vpu.mp.service.pojo.shop.market.payreward.record;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author 孔德成
 * @date 2019/8/12 18:17
 */
@Data
public class PayRewardRecordParam {

    private Integer   id;
    private Integer   payRewardId;
    private String    orderSn;
    private Integer   userId;
    private Byte      type;
    private String    mrkingVoucherId;
    private Timestamp createTime;
    private Timestamp updateTime;

}
