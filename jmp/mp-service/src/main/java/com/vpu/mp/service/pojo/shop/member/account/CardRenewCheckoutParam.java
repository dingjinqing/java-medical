package com.vpu.mp.service.pojo.shop.member.account;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 会员卡续费-支付相关
 * @author liangchen
 * @date 2020.04.08
 */
@Data
public class CardRenewCheckoutParam {
    /** 用户id */
    private Integer userId;
    /** 会员卡id */
    private Integer cardId;
    /** 会员卡编号 */
    private String cardNo;
    /** 会员卡余额扣减 */
    private BigDecimal memberCardBalance;
    /** 扣减余额会员卡编号 */
    private String memberCardNo;
    /** 用户支付金额 */
    private BigDecimal moneyPaid;
    /** 需要花费金额 */
    private BigDecimal renewNum;
    /** 需要花费积分数 */
    private Integer scoreNum;
    /** 使用账户余额数量 */
    private BigDecimal useAccount;
}
