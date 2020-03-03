package com.vpu.mp.service.pojo.shop.market.friendpromote;

import lombok.Data;


/**
 * 是否可以发起好友助力活动
 * @author liangchen
 * @date 2020.02.27
 */
@Data
public class LaunchVo {
    /** 活动码 */
    private String actCode;
    /** 发起id */
    private Integer launchId;
    /** 发起用户id */
    private Integer launchUserId;
    /** 返回信息 */
    private String msg;
}
