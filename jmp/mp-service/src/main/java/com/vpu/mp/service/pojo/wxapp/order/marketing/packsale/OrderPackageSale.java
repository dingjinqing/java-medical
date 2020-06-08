package com.vpu.mp.service.pojo.wxapp.order.marketing.packsale;

import com.vpu.mp.service.pojo.wxapp.order.marketing.base.BaseMarketingBaseVo;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author: 王兵兵
 * @create: 2020-04-15 11:08
 **/
@Getter
@Setter
public class OrderPackageSale extends BaseMarketingBaseVo {
    /**一口价结算金额*/
    BigDecimal packageTotalMoney;
}
