package com.vpu.mp.service.pojo.shop.rebate;

import com.vpu.mp.common.pojo.shop.table.DoctorTotalRebateDo;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author yangpengcheng
 * @date 2020/8/27
 **/
@Data
public class DoctorTotalRebateVo extends DoctorTotalRebateDo {
    /**
     * 累计提现金额
     */
    private BigDecimal accruingWithDrawCash=BigDecimal.ZERO;
    /**
     * 待提现金额
     */
    private BigDecimal waitWithDrawCash=BigDecimal.ZERO;
}
