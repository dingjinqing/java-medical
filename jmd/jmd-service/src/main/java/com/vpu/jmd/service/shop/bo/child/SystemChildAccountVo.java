package com.vpu.jmd.service.shop.bo.child;

import lombok.Data;

import java.sql.Timestamp;

/**
 * 子账户信息返回类
 * @author zhaojianqiang
 * @date 2020年6月2日上午9:55:04
 */
@Data
public class SystemChildAccountVo {
	private Integer accountId;
	private Integer systemUserId;
	private String accountName;
//	private String accountPwd;
	private Integer roleId;
	private Timestamp createTime;
	private String mobile;
	private String roleName;
}
