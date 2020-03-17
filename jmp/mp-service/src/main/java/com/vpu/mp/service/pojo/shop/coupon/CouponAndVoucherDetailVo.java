package com.vpu.mp.service.pojo.shop.coupon;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * customer_avail_coupons 表和 mrking_voucher表信息
 * @author 孔德成
 * @date 2020/3/17
 */
@Data
@NoArgsConstructor
public class CouponAndVoucherDetailVo {
    // customer_avail_coupons表数据
    private Integer id;
    private String couponSn;
    private Integer userId;
    private Integer actType;
    private Integer actId;
    private Timestamp startTime;
    private Timestamp endTime;
    private Byte type;
    private BigDecimal amount;
    private String actDesc;
    private Integer limitOrderAmount;
    private Byte isUsed;
    private Timestamp usedTime;
    private Byte accessMode;
    private Integer accessId;
    private Timestamp notifyTime;
    private String orderSn;
    private Byte delFlag;
    private Byte getSource;
    private Timestamp createTime;
    private Timestamp updateTime;
    private String wxOpenid;
    private String wxUnionId;
    //mrking_voucher表数据
    private String actName;
    private BigDecimal denomination;
    private String act_code;
    private String least_consume;
    private String use_explain;
    private String recommend_goods_id;
    private String recommend_cat_id;
    private String recommend_sort_id;
    private String use_score;
    private String score_number;
    private String is_delete;
    private String validity;
    private String validity_hour;
    private String validity_minute;
    private String random_max;
    private String random_min;
    private String coupon_type;
    private String receive_per_num;
    private String receive_num;


}
