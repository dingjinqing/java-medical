package com.vpu.mp.service.pojo.shop.member.account;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 会员卡续费-支付相关
 * @author liangchen
 * @date 2020.04.08
 */
@Data
public class CardRenewCheckoutVo {
    /** 过期时间 */
    private Timestamp expireTime;
    /** 支付金额 */
    private BigDecimal money;
}
