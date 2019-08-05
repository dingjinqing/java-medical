package com.vpu.mp.service.pojo.shop.market.groupdraw;

import com.vpu.mp.service.pojo.shop.base.BasePageParam;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * 拼团抽奖
 *
 * @author 郑保乐
 */
@Getter
@Setter
public class GroupDrawListParam extends BasePageParam {

    /** 活动名称 **/
    private String activityName;
    /** 活动开始时间 **/
    private LocalDate startTime;
    /** 活动结束时间 **/
    private LocalDate endTime;
    /** 活动状态 **/
    private Byte status;
}
