package com.vpu.mp.service.pojo.shop.overview;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * author liufei
 * date 2019/7/18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssiDataOrder {
    /** 发货逾期 0: 有deliver个订单超过3天未发货 否订单发货进度良好 */
    public int deliver;
    /** 退款申请逾期 0: 有refund个订单退款申请超过3天未处理 否退款处理进度良好 */
    public int refund;
}
