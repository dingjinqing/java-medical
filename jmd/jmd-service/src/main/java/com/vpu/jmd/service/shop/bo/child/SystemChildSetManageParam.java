package com.vpu.jmd.service.shop.bo.child;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 设置对应的管理人员
 * @author zhaojianqiang
 * @date 2020年6月2日下午1:57:46
 */
@Data
public class SystemChildSetManageParam {
	@NotNull
	private Integer shopId;
	@NotNull
	private String act;
	/** 多个用逗号隔开*/
	private String managerId;

}
