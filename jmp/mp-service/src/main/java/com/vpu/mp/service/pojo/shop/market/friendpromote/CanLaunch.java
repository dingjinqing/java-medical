package com.vpu.mp.service.pojo.shop.market.friendpromote;

import lombok.Data;

/**
 * 是否可以发起好友助力活动
 * @author liangchen
 * @date 2020.02.27
 */
@Data
public class CanLaunch {
    /** 是否可发起 0否 1是 */
    private Byte code;
    /** 文字信息 */
    private String msg;
}
