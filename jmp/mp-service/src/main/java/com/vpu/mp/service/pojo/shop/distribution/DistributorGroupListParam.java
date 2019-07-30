package com.vpu.mp.service.pojo.shop.distribution;

import com.vpu.mp.service.foundation.util.Page;

import lombok.Data;

/**
 * 分销员分组列表入参
 * @author 常乐
 * 2019年7月24日
 */
@Data
public class DistributorGroupListParam {
	public String groupName;
	public Integer currentpage = Page.DEFAULT_CURRENT_PAGE;
	public Integer pageRows = Page.DEFAULT_PAGE_ROWS;
}
