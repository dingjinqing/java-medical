package com.vpu.mp.service.pojo.shop.member;

import com.vpu.mp.service.foundation.Page;
import lombok.Data;

/**
 * 
 * 标签管理入参
 * 
 * @author 黄壮壮 2019-07-09 11:09
 */

@Data
public class TagPageListParam {
	final static private Page DEFAULT_PAGE = new Page();
	private Page page=DEFAULT_PAGE;
}
