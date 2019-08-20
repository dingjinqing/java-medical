package com.vpu.mp.service.pojo.shop.market.gift;

import com.vpu.mp.service.pojo.shop.base.BasePageParam;
import lombok.Getter;
import lombok.Setter;

/**
 * 定金膨胀列表查询入参
 *
 * @author 郑保乐
 */
@Getter
@Setter
public class GiftListParam extends BasePageParam {

    /** 进行中 **/
    public static final byte ONGOING = 0;
    /** 未开始 **/
    public static final byte NOT_STARTED = 1;
    /** 已过期 **/
    public static final byte EXPIRED = 2;
    /** 已停用 **/
    public static final byte DISABLED = 3;

    /** 活动名称 **/
    private String name;
    /** 状态 **/
    private Byte status;
}
