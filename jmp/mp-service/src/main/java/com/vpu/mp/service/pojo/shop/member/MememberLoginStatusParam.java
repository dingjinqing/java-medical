package com.vpu.mp.service.pojo.shop.member;

import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author 黄壮壮
 * @Date:  2019年8月13日
 * @Description: 会员用户批量设置禁止登陆-恢复登录-入参
 */
@Getter
@Setter
public class MememberLoginStatusParam {
	/** 会员用户ID列表  */
	@NotNull private List<Integer> userIdList;
	/** 用户登录设置状态 1-禁止登录，0-恢复登录  {@link com.vpu.mp.service.pojo.shop.member.MemberConstant.DELETE_YES }*/
	@NotNull private Byte isDelete;
}
