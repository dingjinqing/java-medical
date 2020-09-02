package com.vpu.mp.service.pojo.shop.maptemplate;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author chenjie
 * @date 2020年08月18日
 */
@Data
@Builder
public class OrderRefundSuccessParam {
    private String orderSn;
    private String refundTime;
    private String refundMoney;
    private String refundReason;
    private List<Integer> userIds;
}
