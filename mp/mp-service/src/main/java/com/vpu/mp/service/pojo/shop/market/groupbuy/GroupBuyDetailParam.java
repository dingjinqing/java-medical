package com.vpu.mp.service.pojo.shop.market.groupbuy;

import com.vpu.mp.service.foundation.util.Page;
import lombok.Data;

/**
 * @author 孔德成
 * @date 2019/7/23 13:40
 */
@Data
public class GroupBuyDetailParam {

    private Integer activityId;

    private Page page;

    private String mobile;

    private String nickName;

    private byte status;


}
