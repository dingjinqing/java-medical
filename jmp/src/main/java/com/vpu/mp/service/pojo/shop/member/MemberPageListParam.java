package com.vpu.mp.service.pojo.shop.member;

import lombok.Getter;
import lombok.Setter;

/**
 * admin系统店铺会员入参参数 TODO
 * 
 * @author 黄壮壮 2019-07-05 18:06
 */
@Getter
@Setter
public class MemberPageListParam extends BaseMemberPojo {

	private String mobile;
	private String username;
	/**
	 * 来源 门店来源-1未录入0后台>0为门店
	 */
	private Integer source;
	/**
	 * 邀请人
	 */
	private String inviteUserName;
	/**
	 * 创建时间
	 */
	private String createTime;
	/**
	 * 结束时间
	 */
	private String endTime;

}
