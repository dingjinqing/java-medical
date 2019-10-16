package com.vpu.mp.service.pojo.shop.decoration;

import lombok.Data;

/**
 * 申请成为分销员入参
 * @author 常乐
 * 2019年10月16日
 */
@Data
public class DistributorApplyParam {
	/**
	 * 分组id
	 */
	private Integer groupId;
	/**
	 * 用户ID
	 */
	private Integer userId;
	/**
	 * 备注
	 */
	private String remark;
}
