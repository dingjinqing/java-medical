package com.vpu.mp.service.pojo.shop.market.groupbuy.param;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author 孔德成
 * @date 2019/7/22 14:16
 */

@NoArgsConstructor
@Setter
@Getter
public class GroupBuyListParam  extends BasePageGroupBuyParam{

    /**
     *  1全部拼团活动 2 进行中 3 未开始 4 已过期 5 已停用
     */
    @NotNull
    private Byte type;


}
