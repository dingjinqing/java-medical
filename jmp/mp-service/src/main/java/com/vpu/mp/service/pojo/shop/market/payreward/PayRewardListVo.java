package com.vpu.mp.service.pojo.shop.market.payreward;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author 孔德成
 * @date 2019/8/13 14:59
 */
@Data
public class PayRewardListVo {

    private Integer id;
    private String     actName;
    private Timestamp startTime;
    private Timestamp  endTime;

    private Byte       type;
    private Byte      status;
    /**
     *  触发条件
     */
    private BigDecimal denomination;
}
