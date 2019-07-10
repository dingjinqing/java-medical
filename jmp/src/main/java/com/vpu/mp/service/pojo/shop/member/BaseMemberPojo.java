package com.vpu.mp.service.pojo.shop.member;

import com.vpu.mp.service.foundation.Page;

import lombok.Data;

/**
 * 
 * 处理在会员列表中公共的方法和字段
 * @author 黄壮壮
 * 2019-07-10 10:47
 */
@Data
public class BaseMemberPojo {
	final static private Page DEFAULT_PAGE = new Page();
	private Page page=DEFAULT_PAGE;
}
