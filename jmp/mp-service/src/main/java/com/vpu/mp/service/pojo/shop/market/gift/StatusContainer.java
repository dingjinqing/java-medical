package com.vpu.mp.service.pojo.shop.market.gift;

import java.sql.Timestamp;

/**
 * 营销活动状态
 *
 * @author 郑保乐
 */
public interface StatusContainer {

    /**
     * 获取活动开始时间
     *
     * @return 活动开始时间
     */
    Timestamp getStartTime();

    /**
     * 获取活动结束时间
     *
     * @return 活动结束时间
     */
    Timestamp getEndTime();

    /**
     * 获取状态
     *
     * @return 状态
     */
    Byte getStatus();
}
