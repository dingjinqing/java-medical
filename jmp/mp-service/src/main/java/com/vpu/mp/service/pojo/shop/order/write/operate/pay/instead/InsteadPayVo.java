package com.vpu.mp.service.pojo.shop.order.write.operate.pay.instead;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

/**
 * 代付支付页
 * @author 王帅
 */
@Getter
@Setter
@ToString
public class InsteadPayVo {
    private Byte isShowEdit;
    private BigDecimal moneyPaid;
    private List<BigDecimal> threeStages;
    private String message;
    private BigDecimal amountPaid;
    private BigDecimal waitPayMoney;
}
