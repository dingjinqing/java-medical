package com.vpu.mp.service.pojo.shop.member;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * 
 * @author 黄壮壮
 * @Date:  2019年8月29日
 * @Description: 会员列表查询出参
 */
@Data
public class MemberInfoVo {
	/** ID */
	private Integer userId;
	/** 昵称 */
	private String userName;
	/** 手机号 */
	private String mobile;
	/** 邀请人 */
	private String inviteUserName;
	/** 余额 */
	private BigDecimal account;
	/** 积分 */
	private Integer score;
	/** 来源 : -1 未录入 ; 0 后台; >0为门店id */
	private Byte source;
	/** 创建时间 */
	private Timestamp createTime;
	/** 用户持有的会员卡 */
	private String cardName;
	
	/** 邀请来源 */
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String inviteSource;
	
	/** 邀请来源活动id */
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Integer inviteActId;
	
	/** 来源名称  如： {@link com.vpu.mp.service.pojo.shop.member.SourceNameEnum.NOT_ACQUIRED } */
	private String sourceName;
	/** 0：恢复登录 ；1：禁止登录  {@link com.vpu.mp.service.pojo.shop.member.MemberConstant.DELETE_YES } */
	private Byte delFlag;
	
	
}
