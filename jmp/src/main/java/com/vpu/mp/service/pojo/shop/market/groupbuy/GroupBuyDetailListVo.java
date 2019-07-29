package com.vpu.mp.service.pojo.shop.market.groupbuy;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author 孔德成
 * @date 2019/7/23 14:13
 */
@Data
public class GroupBuyDetailListVo {

    /**
     * 活动名称
     */
    private String name;

    /**
     *
     */
    private String userName;

    private String mobile;

    private Short count;

    private Byte status;

    private Timestamp startTime;

    private Timestamp endTime;

    private Byte isDefault;

    private String  orderSn;

    GroupBuyDetailVo groupBuyDetail;

}
