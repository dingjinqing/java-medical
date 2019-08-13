package com.vpu.mp.service.pojo.shop.distribution;

import java.sql.Timestamp;

import com.vpu.mp.service.foundation.util.Page;

import lombok.Data;

/**
 * 分销员已邀请用户列表
 * @author 常乐
 * 2019年8月7日
 */
@Data
public class DistributorInvitedListParam {
	private Integer userId;
	private String mobile;
	private String username;
	private Timestamp startCreateTime;
	private Timestamp endCreateTime;
	
	private Integer currentPage = Page.DEFAULT_CURRENT_PAGE;
	private Integer pageRows = Page.DEFAULT_PAGE_ROWS;
}
