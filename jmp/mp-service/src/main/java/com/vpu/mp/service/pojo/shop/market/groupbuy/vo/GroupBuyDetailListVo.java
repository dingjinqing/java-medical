package com.vpu.mp.service.pojo.shop.market.groupbuy.vo;

import java.sql.Timestamp;

import lombok.Data;

/**
 * @author 孔德成
 * @date 2019/7/23 14:13
 */
@Data
public class GroupBuyDetailListVo {

    public final static String COMMANDER_MOBILE="commanderMobile";
    public final static String COMMANDER_NAME="commanderName";

    /**
     * 活动名称
     */
    private String name;

    /**
     *参团用户
     */
    private String username;
    /**
     * 用户手机号
     */
    private String mobile;
    //团长信息
    private String commanderMobile;
    private String commanderName;

    private Short count;

    private Byte status;

    private Timestamp startTime;

    private Timestamp endTime;

    private Byte isDefault;

    private String  orderSn;


}
