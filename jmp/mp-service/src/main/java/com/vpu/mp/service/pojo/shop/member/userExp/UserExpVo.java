package com.vpu.mp.service.pojo.shop.member.userExp;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vpu.mp.service.foundation.data.JsonResultMessage;
import com.vpu.mp.service.foundation.excel.annotation.ExcelColumn;

import lombok.Data;

/**
 * 
 * @author 黄壮壮
 * 	会员导出参数选择
 */
@Data
public class UserExpVo {
	/**
	 * 	用户id,必须导出的数据
	 */
	@ExcelColumn(columnName=JsonResultMessage.UEXP_USER_ID) 
	@JsonProperty(value="user_id",index=0,required=true)
	private Integer userId;
	
	/**
	 * 	昵称
	 */
	@ExcelColumn(columnName=JsonResultMessage.UEXP_USERNAME)
	@JsonProperty(value="username",index=1)
	private String username;
	
	/**
	 * 	手机号
	 */
	@ExcelColumn(columnName=JsonResultMessage.UEXP_MOBILE)
	@JsonProperty(value="mobile",index=2)
	private String mobile;
	
	/**
	 * OpenID
	 */
	@ExcelColumn(columnName=JsonResultMessage.UEXP_WX_OPENID) 
	@JsonProperty(value="wx_openid",index=3)
	private String wxOpenid;
	
	/**
	 *	 余额
	 */
	@ExcelColumn(columnName=JsonResultMessage.UEXP_ACCOUNT) 
	@JsonProperty(value="account",index=4)
	private BigDecimal account;
	
	/**
	 * 	积分
	 */
	@ExcelColumn(columnName=JsonResultMessage.UEXP_SCORE)
	@JsonProperty(value="score",index=5)
	private Integer score;
	
	/**
	 * 用户来源
	 */
	@ExcelColumn(columnName=JsonResultMessage.UEXP_USER_SOURCE) 
	@JsonProperty(value="user_source",index=6)
	private String userSource;
	
	/**
	 * 	注册时间
	 */
	@ExcelColumn(columnName=JsonResultMessage.UEXP_CREATE_TIME)
	@JsonProperty(value="create_time",index=7)
	private String createTime;
	
	/**
	 * 	会员卡
	 */
	@ExcelColumn(columnName=JsonResultMessage.UEXP_USER_CARD) 
	@JsonProperty(value="user_card",index=8)
	private String userCard;
	
	/**
	 * 	地址
	 */
	@ExcelColumn(columnName=JsonResultMessage.UEXP_USER_ADDRESS)
	@JsonProperty(value="user_address",index=9)
	private String userAddress;
	
	/**
	 * 	累计消费金额
	 */
	@ExcelColumn(columnName=JsonResultMessage.UEXP_ORDER_AMOUNT)
	@JsonProperty(value="order_amount",index=10)
	private BigDecimal orderAmount;
	
	/**
	 * 	累计消费单数
	 */
	@ExcelColumn(columnName=JsonResultMessage.UEXP_ORDER)
	@JsonProperty(value="order",index=11)
	private Integer order;
	
	/**
	 *	 累计退款金额
	 */
	@ExcelColumn(columnName=JsonResultMessage.UEXP_RETURN_ORDER_MONEY)
	@JsonProperty(value="return_order_money",index=12)
	private BigDecimal return_order_money;
	
	/**
	 * 	累计退款订单数
	 */
	@ExcelColumn(columnName=JsonResultMessage.UEXP_RETURN_ORDER)
	@JsonProperty(value="return_order",index=13)
	private Integer return_order;
	
	/**
	 * 	备注
	 */
	@ExcelColumn(columnName=JsonResultMessage.UEXP_REMARK)
	@JsonProperty(value="remark",index=14)
	private String remark;
	
	/**
	 *	 邀请人
	 */
	@ExcelColumn(columnName=JsonResultMessage.UEXP_INVITE_USER_NAME)
	@JsonProperty(value="invite_user_name",index=15)
	private String inviteUserName;
	
	/**
	 * 	邀请人手机号
	 */
	@ExcelColumn(columnName=JsonResultMessage.UEXP_INVITE_MOBILE)
	@JsonProperty(value="invite_mobile",index=16)
	private String inviteMobile;
	
	/**
	 *	 邀请人分销员分组
	 */
	@ExcelColumn(columnName=JsonResultMessage.UEXP_INVITE_GROUP_NAME)
	@JsonProperty(value="invite_group_name",index=17)
	private String inviteGroupName;
	
	/**
	 * 	获返利订单数量
	 */
	@ExcelColumn(columnName=JsonResultMessage.UEXP_REBATE_ORDER_NUM)
	@JsonProperty(value="rebate_order_num",index=18)
	private String rebateOrderNum;
	
	/**
	 * 	返利商品总金额
	 */
	@ExcelColumn(columnName=JsonResultMessage.UEXP_CALCULATE_MONEY)
	@JsonProperty(value="calculate_money",index=19)
	private BigDecimal calculateMoney;
	
	/**
	 * 	获返利订单佣金总额
	 */
	@ExcelColumn(columnName=JsonResultMessage.UEXP_REBATE_MONEY)
	@JsonProperty(value="rebate_money",index=20)
	private BigDecimal rebateMoney;
	
	/**
	 * 	获返利订单佣金总额
	 */
	@ExcelColumn(columnName=JsonResultMessage.UEXP_SUBLAYER_NUMBER) 
	@JsonProperty(value="withdraw_money",index=21)
	private BigDecimal withdraw_money;
	
	/**
	 * 	下级用户数
	 */
	@ExcelColumn(columnName=JsonResultMessage.UEXP_WITHDRAW_MONEY) 
	@JsonProperty(value="sublayer_number",index=22)
	private Integer sublayerNumber;
	
	/**
	 * 	分销员等级
	 */
	@ExcelColumn(columnName=JsonResultMessage.UEXP_LEVEL_NAME) 
	@JsonProperty(value="level_name",index=23)
	private String levelName;
	
	/**
	 * 	分销员分组
	 */
	@ExcelColumn(columnName=JsonResultMessage.UEXP_GROUP_NAM)
	@JsonProperty(value="group_name",index=24)
	private String groupName;

}
