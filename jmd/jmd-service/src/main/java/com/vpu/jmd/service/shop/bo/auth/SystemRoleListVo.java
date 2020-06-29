package com.vpu.jmd.service.shop.bo.auth;

import lombok.Data;

import java.sql.Timestamp;

/**
 * 角色列表返回
 * @author zhaojianqiang
 * @date 2020年6月18日下午4:01:42
 */
@Data
public class SystemRoleListVo {
	private Integer roleId;
	private Integer systemUserId;
	private String roleName;
	private String privilegeList;
	private Timestamp createTime;
	private Timestamp delTime;
	private Byte delFlag;
}
