package com.vpu.mp.service.pojo.shop.market.presale;

import com.vpu.mp.service.pojo.shop.base.BasePageParam;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

/**
 * 定金膨胀列表查询入参
 *
 * @author 郑保乐
 */
@Getter
@Setter
public class PreSaleListParam extends BasePageParam {

    /** 进行中 **/
    public static final byte ONGOING = 0;
    /** 未开始 **/
    public static final byte NOT_STARTED = 1;
    /** 已过期 **/
    public static final byte EXPIRED = 2;
    /** 已停用 **/
    public static final byte DISABLED = 3;

    private String name;
    /** 定金支付开始时间 **/
    private Timestamp preStartTime;
    /** 定金支付结束时间 **/
    private Timestamp preEndTime;
    /** 尾款支付开始时间 **/
    private Timestamp startTime;
    /** 尾款支付结束时间 **/
    private Timestamp endTime;
}
