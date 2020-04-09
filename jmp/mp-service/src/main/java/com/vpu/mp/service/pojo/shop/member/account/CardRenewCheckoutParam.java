package com.vpu.mp.service.pojo.shop.member.account;

import lombok.Data;

/**
 * 会员卡续费-支付相关
 * @author liangchen
 * @date 2020.04.08
 */
@Data
public class CardRenewCheckoutParam {
    /** 用户id */
    private Integer userId;
    /** 会员卡编号 */
    private String cardNo;
}
