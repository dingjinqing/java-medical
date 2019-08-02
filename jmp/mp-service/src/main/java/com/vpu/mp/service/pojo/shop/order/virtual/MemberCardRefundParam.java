package com.vpu.mp.service.pojo.shop.order.virtual;

import lombok.Data;

/**
 * 虚拟商品退款
 *
 * @author 郑保乐
 */
@Data
public class MemberCardRefundParam {

    private Integer orderId;
    /** 余额 **/
    private Double account;
    /** 现金 **/
    private Double money;
    /** 积分 **/
    private Integer score;
}
