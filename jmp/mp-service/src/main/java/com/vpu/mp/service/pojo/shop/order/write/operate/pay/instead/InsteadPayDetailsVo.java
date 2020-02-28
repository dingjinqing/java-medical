package com.vpu.mp.service.pojo.shop.order.write.operate.pay.instead;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @author 王帅
 * 代付明细
 */
@Getter
@Setter
@ToString
public class InsteadPayDetailsVo {
    private String subOrderSn;
    private String mainOrderSn;
    private Integer userId;
    private String username;
    private Byte orderStatus;
    private BigDecimal moneyPaid;
    private BigDecimal refund_money;
    private String message;
    private String userAvatar;

}
