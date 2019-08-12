package com.vpu.mp.service.pojo.shop.market.fullcut;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author: 王兵兵
 * @create: 2019-08-09 16:38
 **/
@Data
public class MrkingStrategyCondition {

    /** 满多少金额 */
    private BigDecimal fullMoney;

    /** 减多少钱 */
    private BigDecimal reduceMoney;

    /** 满几件 或 第几件（第X件打折） */
    private Integer amount;

    /** 打几折 */
    private BigDecimal discount;
}
