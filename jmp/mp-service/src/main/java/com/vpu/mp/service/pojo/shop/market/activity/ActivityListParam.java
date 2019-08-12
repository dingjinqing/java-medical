package com.vpu.mp.service.pojo.shop.market.activity;

import com.vpu.mp.service.pojo.shop.base.BasePageParam;
import lombok.Getter;
import lombok.Setter;

/**
 * 活动有礼列表入参
 *
 * @author 郑保乐
 */
@Getter
@Setter
public class ActivityListParam extends BasePageParam {

    /** 进行中 **/
    public static final int ONGOING = 0;
    /** 未开始 **/
    public static final int NOT_STARTED = 1;
    /** 已过期 **/
    public static final int EXPIRED = 2;
    /** 已停用 **/
    public static final int DISABLED = 3;

    /** 状态 **/
    private Integer status;
}
