package com.vpu.mp.service.pojo.wxapp.comment;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 优惠券详情出参
 *
 * @author liangchen 2019.10.21
 */
@Data
public class CouponDetailsVo {
  /** 优惠券名称 */
  private String actName;
  /** 优惠券类型 */
  private String actCode;
  /** 打折/减价量 */
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
