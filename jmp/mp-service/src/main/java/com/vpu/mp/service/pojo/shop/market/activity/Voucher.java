package com.vpu.mp.service.pojo.shop.market.activity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * 优惠券
 *
 * @author 郑保乐
 */
@Data
public class Voucher {

    private Integer id;
    /** 是否有使用限制 **/
    private Boolean restrict;
    /** 满多少可用 **/
    private Integer leastConsume;
    /** 剩余可发放数量 **/
    private Integer availableQuantity;

    @JsonIgnore
    private Byte useConsumeRestrict;
}
