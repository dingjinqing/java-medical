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
	public Integer userId;
	public String mobile;
	public String username;
	public Timestamp startCreateTime;
	public Timestamp endCreateTime;
	
	public Integer currentpage = Page.DEFAULT_CURRENT_PAGE;
	public Integer pageRows = Page.DEFAULT_PAGE_ROWS;
}
