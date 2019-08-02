package com.vpu.mp.service.pojo.shop.market.groupbuy.param;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author 孔德成
 * @date 2019/7/22 14:16
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class GroupBuyListParam  extends BasePageGroupBuyParam{

    /**
     *  1全部拼团活动 2 进行中 3 未开始 4 已过期 5 已停用
     */
    private Integer type;


}
