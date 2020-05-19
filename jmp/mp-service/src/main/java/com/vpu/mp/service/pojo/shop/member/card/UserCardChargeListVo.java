package com.vpu.mp.service.pojo.shop.member.card;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author: 王兵兵
 * @create: 2020-05-18 17:33
 **/
@Setter
@Getter
public class UserCardChargeListVo {
    /**
     * 充值单号
     */
    private String orderSn;
    /**
     * 会员卡名称
     */
    private String cardName;
    /**
     * 卡ID
     */
    private Integer cardId;
    /**
     * 会员昵称
     */
    private String username;
    /**
     * 会员手机号
     */
    private String mobile;
    /**
     * 充值时间
     */
    private Timestamp createTime;
    /**
     * 充值金额
     */
    private BigDecimal charge;
    /**
     * 当次充值后卡余额
     */
    private BigDecimal afterChargeMoney;
    /**
     *
     */
    private Integer reasonId;
    /**
     * 充值原因
     */
    private String reason;
    /**
     * 当次续费后有效期
     */
    private Byte changeType;
}
