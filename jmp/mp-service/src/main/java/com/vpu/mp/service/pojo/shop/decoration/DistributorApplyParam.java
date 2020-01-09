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
     * 用户ID
     */
	private Integer userId;
    /**
     * 审核校验
     */
	private String activationFields;
    /**
     * 审核字段
     */
	private String configFields;
    /**
     * 邀请码
     */
	private String inviteCode;
}
