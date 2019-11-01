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
public class AssiDataOrder implements PendingRule<AssiDataOrder> {
    /**
     * 发货逾期 非零表示有deliver个订单超过3天未发货 否订单发货进度良好
     */
    public int deliver;
    /** 退款申请逾期 非零表示有refund个订单退款申请超过3天未处理 否退款处理进度良好 */
    public int refund;

    @Override
    public AssiDataOrder ruleHandler() {
        handler1(deliver, refund);
        return this;
    }
}
