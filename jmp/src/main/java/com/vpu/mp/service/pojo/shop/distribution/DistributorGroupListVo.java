package com.vpu.mp.service.pojo.shop.distribution;

import com.vpu.mp.service.foundation.util.Page;

import lombok.Data;

/**
 * 分销员分组列表出参
 * @author 常乐
 * 2019年7月24日
 */
@Data
public class DistributorGroupListVo {
	public Integer id;
	public String groupName;
	public Integer isDefault;
	public Integer delFlag;
	
	 /**
     * 	分页信息
     */
    private Integer currentPage = Page.DEFAULT_CURRENT_PAGE;
    private Integer pageRows = Page.DEFAULT_PAGE_ROWS;
}
