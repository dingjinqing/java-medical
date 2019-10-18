package com.vpu.mp.service.pojo.wxapp.comment;

import lombok.Data;

import java.sql.Timestamp;

/**
 * 评价列表出参
 *
 * @author liangchen 2019.10.15
 */
@Data
public class CommentListVo {
  /** 商品id */
  private Integer goodsId;
  /** 商品名称 */
  private String goodsName;
  /** 商品图片 */
  private String goodsImg;
  /** 订单提交时间 */
  private Timestamp createTime;
  /** 订单编号 */
  private String orderSn;
  /** 评价评分 */
  private Byte commstar;
  /** 评价心得 */
  private String commNote;
  /** 评价晒单 */
  private String commImg;
  /** 商家回复内容 */
  private String content;
  /** 评价有礼活动id */
  private Integer id;
  /** 评价有礼奖励类型 1积分 2优惠券 3余额 4幸运大抽奖 5自定义 */
  private Integer awardType;
  /** 评价有礼奖励内容 */
  private String award;
  /** 是否已评价标识 0：待评价 1：已评价 */
  private Byte commentFlag;
}
