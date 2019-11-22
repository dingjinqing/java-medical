package com.vpu.mp.service.pojo.shop.coupon.give;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 定向发券时的优惠券详情
 *
 * @author liangchen 2019.10.22
 */
@Data
public class CouponDetailsVo {
  /** 是否限制库存：0限制，1不限制 */
  private Byte limitSurplusFlag;
  /** 优惠券库存 */
  private Integer surplus;
  /** voucher：减价；discount:打折 */
  private String actCode;
  /** 优惠券名称 */
  private String actName;
  /** 面值 */
  private BigDecimal denomination;
  /** 优惠券有效期类型标记，0固定时间段有效,1领取后开始指定时间段内有效 */
  private Byte validityType;
  /** 开始时间 */
  private Timestamp startTime;
  /** 结束时间 */
  private Timestamp endTime;
  /** 优惠券有效天数 */
  private Integer validity;
  /** 优惠券有效小时数 */
  private Integer validityHour;
  /** 优惠券有效分钟数 */
  private Integer validityMinute;
}
