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
	private String mobile;
	private String username;
	private String realName;
	private String invitedMobile;
	private String invitedUsername;
	private Timestamp startCreateTime;
	private Timestamp endCreateTime;
	private Byte distributorLevel;
	private Integer distributorGroup;
	
	private Integer currentPage = Page.DEFAULT_CURRENT_PAGE;
	private Integer pageRows = Page.DEFAULT_PAGE_ROWS;
}
