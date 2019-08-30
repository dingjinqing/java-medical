package com.vpu.mp.service.pojo.shop.distribution;

import lombok.Data;

/**
 * 分销员分组列表入参
 * @author 常乐
 * 2019年7月24日
 */
@Data
public class DistributorGroupListParam {
	private Integer id;
	private String groupName;
	private Integer currentPage ;
	private Integer pageRows;
}
