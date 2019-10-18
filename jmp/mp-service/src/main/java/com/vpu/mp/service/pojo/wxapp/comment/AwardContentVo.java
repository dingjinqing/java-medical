package com.vpu.mp.service.pojo.wxapp.comment;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 评价列表出参
 *
 * @author liangchen 2019.10.15
 */
@Data
public class AwardContentVo {
  /** 奖品类型 1积分 2优惠券 3余额 4幸运大抽奖 5自定义 */
  private Integer awardType;
  /** 积分数 */
  private Integer score;
  /** 优惠券id或抽奖id */
  private Integer activityId;
  /** 用户余额 */
  private BigDecimal account;
  /** 自定义奖品链接 */
  private String awardPath;
  /** 自定义奖品图片 */
  private String awardImg;
}
