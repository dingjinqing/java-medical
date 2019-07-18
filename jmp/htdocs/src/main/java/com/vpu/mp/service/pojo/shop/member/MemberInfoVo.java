package com.vpu.mp.service.pojo.shop.member;

import java.sql.Timestamp;

import lombok.Data;

/**
 * 
  * 会员列表查询出参
 * @author 黄壮壮
 * 2019-07-08 10:17
 */
@Data
public class MemberInfoVo {
	//ID
	private Integer userId;
	//昵称
	private String userName;
	//手机号
	private String mobile;
	//邀请人
	private String inviteUserName;
	//余额
	private Double account;
	//积分
	private Integer score;
	//来源 门店来源-1未录入0后台>0为门店
	private Byte source;
	//创建时间
	private Timestamp createTime;
	
}
