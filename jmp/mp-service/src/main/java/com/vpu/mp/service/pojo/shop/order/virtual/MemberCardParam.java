package com.vpu.mp.service.pojo.shop.order.virtual;

import com.vpu.mp.service.pojo.shop.base.BasePageParam;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

/**
 * 会员卡订单查询入参
 *
 * @author 郑保乐
 */
@Getter
@Setter
public class MemberCardParam extends BasePageParam {

    /** 普通会员卡 **/
    public static final byte NORMAL_CARD = 0;
    /** 限次会员卡 **/
    public static final byte COUNT_CARD = 1;

    /** 未退款 **/
    public static final byte NOT_REFUND = 0;
    /** 部分退款 **/
    public static final byte PART_REFUND = 1;
    /** 完整退款 **/
    public static final byte TOTAL_REFUND = 2;

    /** 余额支付 **/
    public static final String PAY_ACCOUNT = "balance";
    /** 微信支付 **/
    public static final String PAY_WX = "wxpay";

    /** 下单用户信息（下单用户姓名或手机号）**/
    private String userInfo;
    /** 订单编号 **/
    private String orderSn;
    /** 会员卡号 **/
    private String cardNo;
    /** 下单时间 **/
    private Timestamp startTime;
    private Timestamp endTime;
    /** 会员卡类型 **/
    private Byte cardType;
    /** 是否退款订单 **/
    private Boolean refund;
}
