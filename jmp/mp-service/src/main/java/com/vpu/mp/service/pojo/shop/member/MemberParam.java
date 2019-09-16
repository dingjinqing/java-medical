package com.vpu.mp.service.pojo.shop.member;

import lombok.Data;

/**
* @author 黄壮壮
* @Date: 2019年9月9日
* @Description: 请求和更新会员信息最简单的参数
*/
@Data
public class MemberParam {
	/** -会员id */
	private Integer userId;
	/** -会员名称  */
	private String username;
	
	/** ------邀请人id-----------*/
	private Integer inviteId;

}
