package com.vpu.mp.service.pojo.shop.coupon.give;

import lombok.Data;

/**
 * 定向发券明细
 *
 * @author liangchen
 * @date 2019年7月30日
 */
@Data
public class CouponGiveDetailParam {
  /** 活动Id */
  private Integer accessId;
  /** 优惠券Id */
  private Integer actId;
  /** 手机号 */
  private String mobile;
  /** 用户昵称 */
  private String username;
  /** 是否使用 0：未使用 1：已使用 2：已过期 3：已废除 */
  public static final Integer ISUSED_DEFAULT_VALUE = -1;

  private Integer isUsed = ISUSED_DEFAULT_VALUE;

  /** 分页信息 */
  private Integer currentPage;

  private Integer pageRows;
}
