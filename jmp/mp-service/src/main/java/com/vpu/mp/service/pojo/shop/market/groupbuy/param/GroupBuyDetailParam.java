package com.vpu.mp.service.pojo.shop.market.groupbuy.param;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 孔德成
 * @date 2019/7/23 13:40
 */
@Setter
@Getter
public class GroupBuyDetailParam  extends  BasePageGroupBuyParam{

    private Integer activityId;


    private String mobile;

    private String nickName;

    private byte status;


}
