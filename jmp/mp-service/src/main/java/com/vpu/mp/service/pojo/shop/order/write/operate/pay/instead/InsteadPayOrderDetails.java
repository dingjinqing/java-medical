package com.vpu.mp.service.pojo.shop.order.write.operate.pay.instead;

import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.market.insteadpay.InsteadPay;
import com.vpu.mp.service.pojo.wxapp.order.OrderInfoMpVo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @author 王帅
 */
@Getter
@Setter
@ToString
public class InsteadPayOrderDetails {
    private OrderInfoMpVo order;
    private PageResult<InsteadPayDetailsVo> insteadPayDetails;
    private BigDecimal amountPaid;
    private BigDecimal waitPayMoney;
    private InsteadPay insteadPayCfg;
}
