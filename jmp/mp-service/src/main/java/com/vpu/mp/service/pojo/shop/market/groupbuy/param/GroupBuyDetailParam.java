package com.vpu.mp.service.pojo.shop.market.groupbuy.param;

import com.vpu.mp.service.foundation.util.Page;
import lombok.Data;

/**
 * @author 孔德成
 * @date 2019/7/23 13:40
 */
@Data
public class GroupBuyDetailParam  extends  BasePageGroupBuyParam{

    private Integer activityId;


    private String mobile;

    private String nickName;

    private byte status;


}
