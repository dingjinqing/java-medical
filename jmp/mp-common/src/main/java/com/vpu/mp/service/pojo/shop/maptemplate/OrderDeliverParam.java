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
public class OrderDeliverParam {
    public static final String ORDERDETAIL = "已发货";
    private String orderSn;
    @Builder.Default
    private String orderDetail=ORDERDETAIL;
    private String deliverDate;
    private List<Integer> userIds;
}
