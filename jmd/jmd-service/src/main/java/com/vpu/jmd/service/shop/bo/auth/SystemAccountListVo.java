package com.vpu.jmd.service.shop.bo.auth;

import lombok.Data;

/**
 * 子账户列表
 *
 * @author zhaojianqiang
 * @date 2020年6月18日下午4:38:43
 */
@Data
public class SystemAccountListVo {

	private Integer accountId;
	private String accountName;
	private Integer roleId;
	private String roleName;
	private String mobile;
}
