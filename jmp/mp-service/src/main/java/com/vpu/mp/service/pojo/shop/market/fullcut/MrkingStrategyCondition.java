package com.vpu.mp.service.pojo.shop.market.fullcut;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author: 王兵兵
 * @create: 2019-08-09 16:38
 **/
@Data
public class MrkingStrategyCondition implements Comparable<MrkingStrategyCondition> {

    /** 满多少金额 */
    private BigDecimal fullMoney;

    /** 减多少钱 */
    private BigDecimal
        reduceMoney;

    /** 满几件 或 第几件（第X件打折） */
    private Integer amount;

    /** 打几折 */
    private BigDecimal discount;

    @Override
    public int compareTo(MrkingStrategyCondition o) {
        //倒序
        if(getFullMoney() != null && o.getFullMoney() != null) {
            return getFullMoney().compareTo(o.getFullMoney()) > 0 ? -1 : 1;
        }
        if(getAmount() != null && o.getAmount() != null) {
            return getAmount().compareTo(o.getAmount()) > 0 ? -1 : 1;
        }
        //正常情况不会
        return 0;
    }
}
