package com.vpu.mp.service.pojo.shop.distribution;

/**
 * 返利测试配置入参数类
 * user：常乐
 */

import lombok.Data;

import java.sql.Timestamp;

@Data
public class DistributionStrategyParam {
	private Integer   id;
	/**
	 * 策略名称
	 */
	private String    strategyName;
	/**
	 * 策略等级
	 */
	private Byte      strategyLevel;
	/**
	 * 开始时间
	 */
	private Timestamp startTime;
	/**
	 *结束时间
	 */
	private Timestamp endTime;
	/**
	 * 自购返利 0:关闭；1：开启
	 */
	private Byte      selfPurchase;
	/**
	 * 成本保护 0：关闭；1：开启
	 */
	private Byte      costProtection;
	/**
	 * 返利比例
	 */
	private Double    fanliRatio;
	/**
	 * 间接
	 */
	private Double    rebateRatio;
	/**
	 * 二级返利比例
	 */
	private Double    fanliRatio_2;
	/**
	 * 间接
	 */
	private Double    rebateRatio_2;
	/**
	 * 三级返利比例
	 */
	private Double    fanliRatio_3;
	/**
	 * 间接
	 */
	private Double    rebateRatio_3;
	/**
	 * 四级返利比例
	 */
	private Double    fanliRatio_4;
	/**
	 * 间接
	 */
	private Double    rebateRatio_4;
	/**
	 * 五级返利比例
	 */
	private Double    fanliRatio_5;
	/**
	 * 间接
	 */
	private Double    rebateRatio_5;

	/**
	 * 是否首单返利 0：关闭；1：开启
	 */
	private Byte firstRebate;
	/**
	 * 首单返利比例
	 */
	private Double firstRatio;
	private Double firstRatio_2;
	private Double firstRatio_3;
	private Double firstRatio_4;
	private Double firstRatio_5;

	/**
	 * 返利商品类型 0：全部商品；1：部分商品
	 */
	private Byte      recommendType;
	/**
	 * 返利商品ID
	 */
	private String    recommendGoodsId;
	/**
	 * 返利分类ID
	 */
	private String    recommendCatId;
	/**
	 * 状态 0：启用 ；1：停用
	 */
	private Byte      status;
	/**
	 * 删除 0：未删除；1：删除
	 */
	private Byte      delFlag;
	/**
	 * 删除时间
	 */
	private Timestamp delTime;
	/**
	 * 返利商家分类IDs
	 */
	private String    recommendSortId;
	/**
	 * 赠送优惠券 0：关闭；1：开启
	 */
	private Byte      sendCoupon;
	private Timestamp createTime;
	private Timestamp updateTime;
	private Integer   nav;
	/**
	 * 	分页信息
	 */
	private int currentPage;
	private int pageRows;
}
