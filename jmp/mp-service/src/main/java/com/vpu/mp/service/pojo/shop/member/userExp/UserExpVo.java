package com.vpu.mp.service.pojo.shop.member.userExp;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author 黄壮壮
 * 	会员导出参数选择
 */
public class UserExpVo {
	/**
	 * 	用户id
	 */
	@JsonProperty(value="user_id",index=0)
	private Integer userId;
	
	/**
	 * 	昵称
	 */
	@JsonProperty(value="username",index=1)
	private String username;
	
	/**
	 * 	手机号
	 */
	@JsonProperty(value="mobile",index=2)
	private String mobile;
	
	/**
	 * OpenID
	 */
	@JsonProperty(value="wx_openid",index=3)
	private String wxOpenid;
	
	/**
	 *	 余额
	 */
	@JsonProperty(value="account",index=4)
	private BigDecimal account;
	
	/**
	 * 	积分
	 */
	@JsonProperty(value="score",index=5)
	private Integer score;
	
	/**
	 * 用户来源
	 */
	@JsonProperty(value="user_source",index=6)
	private String userSource;
	
	/**
	 * 	注册时间
	 */
	@JsonProperty(value="create_time",index=7)
	private String createTime;
	
	/**
	 * 	会员卡
	 */
	@JsonProperty(value="user_card",index=8)
	private String userCard;
	
	/**
	 * 	地址
	 */
	@JsonProperty(value="user_address",index=9)
	private String userAddress;
	
	/**
	 * 	累计消费金额
	 */
	@JsonProperty(value="order_amount",index=10)
	private BigDecimal orderAmount;
	
	/**
	 * 	累计消费单数
	 */
	@JsonProperty(value="order",index=11)
	private Integer order;
	
	/**
	 *	 累计退款金额
	 */
	@JsonProperty(value="return_order_money",index=12)
	private BigDecimal return_order_money;
	
	/**
	 * 	累计退款订单数
	 */
	@JsonProperty(value="return_order",index=13)
	private Integer return_order;
	
	/**
	 * 	备注
	 */
	@JsonProperty(value="remark",index=14)
	private String remark;
	
	/**
	 *	 邀请
	 */
	@JsonProperty(value="invite_user_name",index=15)
	private String inviteUserName;
	
	/**
	 * 	邀请人手机号
	 */
	@JsonProperty(value="invite_mobile",index=16)
	private String inviteMobile;
	
	/**
	 *	 邀请人分销员分组
	 */
	@JsonProperty(value="invite_group_name",index=17)
	private String inviteGroupName;
	
	/**
	 * 	获返利订单数量
	 */
	@JsonProperty(value="rebate_order_num",index=18)
	private String rebateOrderNum;
	
	/**
	 * 	返利商品总金额
	 */
	@JsonProperty(value="calculate_money",index=19)
	private BigDecimal calculateMoney;
	
	/**
	 * 	获返利订单佣金总额
	 */
	@JsonProperty(value="rebate_money",index=20)
	private BigDecimal rebateMoney;
	
	/**
	 * 	获返利订单佣金总额
	 */
	@JsonProperty(value="withdraw_money",index=21)
	private BigDecimal withdraw_money;
	
	/**
	 * 	下级用户数
	 */
	@JsonProperty(value="sublayer_number",index=22)
	private Integer sublayerNumber;
	
	/**
	 * 	分销员等级
	 */
	@JsonProperty(value="level_name",index=23)
	private String levelName;
	
	/**
	 * 	分销员分组
	 */
	@JsonProperty(value="group_name",index=24)
	private String groupName;

}
