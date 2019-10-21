package com.vpu.mp.service.pojo.wxapp.comment;

import lombok.Data;

import java.math.BigDecimal;

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
}
