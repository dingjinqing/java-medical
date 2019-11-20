package com.vpu.mp.service.pojo.shop.market.groupbuy.param;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author 孔德成
 * @date 2019/7/23 13:40
 */
@Setter
@Getter
public class GroupBuyDetailParam  extends  BasePageGroupBuyParam{

    @NotNull
    private Integer activityId;


    private String mobile;

    private String nickName;

    private Byte status;


}
