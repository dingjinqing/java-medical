package com.vpu.mp.service.pojo.shop.member.card;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author 黄壮壮
 * @Date: 2019年8月1日
 * @Description: 普通会员卡出参
 */

@Data
@NoArgsConstructor
public class NormalCardVo {
	/**
	 * 1-基本信息
	 */
	/** 会员卡id */
	private Integer id;
	/** 会员卡名称 */
	private String cardName;

	/** 背景类型 0： 背景色；1：背景图 */
	private Byte bgType;
	/** 背景色 */
	private String bgColor;
	/** 背景图 */
	private String bgImg;

	/**
	 * 会员有效期类型 0：固定日期；1：自领取多少内有效；2：永久有效
	 */
	private Byte expiredType;
	/** 开始时间 */
	private Timestamp startTime;
	/** 结束时间 */
	private Timestamp endTime;
	/** 自领取之日内多少时间 */
	private Integer receiveDay;
	/** 时间类型 */
	private Byte dateType;

	/** 使用须知 */
	private String desc;
	/** 联系电话 */
	private String mobile;

	/**
	 * 2-会员权益
	 */

	/** 会员折扣开关， 0表示关闭，1表示开启 */
	private Byte powerCount;
	/** 会员折扣 值为 0-10之间 */
	private BigDecimal disCount;
	/** 会员折扣: 全部商品；1代表全部商品，0代表指定商品 */
	private Byte discountIsAll;

	/** 会员专享商品 on表示打开 */
	private String powerPayOwnGood;

	/**
	 * 积分获取开关， 0表示关闭，1表示开启
	 */
	private Byte powerScore;
	/** 购物送积分策略json序列化对象 */
	private ScoreJson scoreJson;

	/** 卡充值开关 0关闭；1开启 */
	private Byte powerCard;
	/** 开卡送多少元 */
	private Integer sendMoney;
	/** 卡充值送积分策略json数据 */
	private PowerCardJson powerCardJson;
	/**
	 * 3-使用门店
	 */
	/**
	 * 使用门店类型 0：全部门店；1：部分门店；-1：不可在门店使用
	 */
	private String storeListType;
	/** 门店Id */
	private String[] storeList;

	/**
	 * 领取设置
	 */
	/**
	 * 领取类型 0：直接领取；1：需要购买；2：需要领取码
	 */
	private Byte isPay;
	/**
	 * 是否需要激活 0： 否；1： 是
	 */
	private Byte activation;
	/** 是否审核 0： 否；1： 是 */
	private Byte examine;

}
