package com.vpu.mp.service.pojo.shop.member.card;

import java.math.BigDecimal;
import java.sql.Timestamp;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author 黄壮壮
 * @Date: 2019年7月30日
 * @Description: 会员卡公共属性
 */

@Data
@NoArgsConstructor
public class CardParam {
	/** 会员卡类型，名称 */
	private Byte cardType;
	private String cardName;

	/** 背景类型 0： 背景色；1：背景图 */
	private Byte bgType;
	/** 背景色 */
	private String bgColor;
	/** 背景图 */
	private String bgImg;

	/** 会员折扣 值为 0-10之间 */
	private BigDecimal disCount;
	/** 会员折扣: 全部商品；1代表全部商品，0代表指定商品 */
	private Byte discountIsAll;

	/** 添加的商品Id */
	private Integer[] goodsId;
	/** 添加的商家分类Id */
	private Integer[] shopCategoryIds;
	/** 添加的平台分类Id */
	private Integer[] platformCategoryIds;

	/**
	 * 积分获取开关，会员折扣开关， 0表示关闭，1表示开启
	 */
	private Byte powerScore;
	private Byte powerCount;

	/** 会员专享商品 on表示打开 */
	private String powerPayOwnGood;

	/** 开卡送多少积分 */
	private Integer score;
	/** 购物满多少 */
	private BigDecimal[] goodsMoney;
	/** 购物满多少送多少积分 */
	private BigDecimal[] getScores;

	/**
	 * 购物送积分 0：购物满多少送多少积分；1：购物每满多少送多少积分
	 */
	private Byte offset;
	/** 购物每满多少 */
	private BigDecimal perGoodsMoney;
	/** 购物每满多少送多少积分 */
	private BigDecimal perGetScores;

	/**
	 * 卡充值开关 0关闭；1开启
	 */
	private Byte powerCard;
	/** 开卡送多少元 */
	private Integer sendMoney;
	/**
	 * 充值类型 0：充值满多少送多少 ；1: 充值每满多少送多少；2：仅充值；
	 */
	private Byte offsetMoney;
	/** 充值满多少元 */
	private BigDecimal[] money;
	/** 充值满送多少元 */
	private BigDecimal[] getMoney;
	/** 每充值多少元 */
	private BigDecimal perMoney;
	/** 每充值送多少元 */
	private BigDecimal perGetMoney;

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

	/**
	 * 使用门店类型 0：全部门店；1：部分门店；-1：不可在门店使用
	 */
	private String storeListType;
	/** 门店Id */
	private String[] storeList;

	/**
	 * 领取类型 0：直接领取；1：需要购买；2：需要领取码
	 */
	private Byte isPay;
	/**
	 * 购买类型 0： 现金购买；1：积分购买
	 */
	private Byte payType;
	/** 现金购买金额 */
	private BigDecimal payMoney;
	/** 积分购买金额 */
	private BigDecimal payScore;

	/** 使用须知 */
	private String desc;
	/** 联系电话 */
	private String mobile;

	/**
	 * 限次会员卡适用商品 0： 不可兑换商品 ；1 ：部分商品；2：全部商品
	 */
	private Byte isExchange;
	/** 限次会员卡允许兑换次数 */
	private Integer exchangCount;
	/** 运费策略 0: 免运费 ; 1: 使用商品运费策略 */
	private Byte exchangFreight;
	/** 可兑换商品id */
	private String[] exchangGoods;
	/**
	 * 限次会员卡允许使用时间 1：工作日； 2：双休 0：不限制
	 */
	private Integer useTime;
	/** 允许使用总次数 */
	private Integer count;
	/** 发放总数 */
	private Integer stock;
	/** 领取限制 */
	private Integer limits;

	/** 等级会员卡 */
	/**
	 * 会员卡是否启用 1:使用中，2:停止使用
	 */
	private Byte flag;
	/** 累积积分达到多少 */
	private BigDecimal gradeScore;
	/** 累积消费总额达到多少 */
	private BigDecimal gradeMoney;
	/** 等级 */
	private String grade;

	/**
	 * 是否需要激活 0： 否；1： 是
	 */
	private Byte activation;
	/** 激活需要填写的信息 */
	private String[] activationCfgBox;

	/** 是否审核 0： 否；1： 是 */
	private Byte examine;
}
