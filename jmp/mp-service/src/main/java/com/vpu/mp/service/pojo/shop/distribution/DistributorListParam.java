package com.vpu.mp.service.pojo.shop.distribution;

import java.sql.Timestamp;

import com.vpu.mp.service.foundation.util.Page;

import lombok.Data;

/**
 * 分销员列表入参
 * @author 常乐
 * 2019年8月5日
 */
@Data
public class DistributorListParam {
	public String mobile;
	public String username;
	public String realName;
	public String invitedMobile;
	public String invitedUsername;
	public Timestamp startCreateTime;
	public Timestamp endCreateTime;
	public Byte distributorLevel;
	public Integer distributorGroup;
	
	public Integer currentpage = Page.DEFAULT_CURRENT_PAGE;
	public Integer pageRows = Page.DEFAULT_PAGE_ROWS;
}
