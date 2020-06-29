package com.vpu.jmd.service.shop.bo.auth;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 添加权限入参
 * @author zhaojianqiang
 * @date 2020年6月18日下午1:53:24
 */
@Data
public class SystemMenuEditParam {
	@NotNull
	private List<String> roleMenuList;
	@NotNull
	private String roleName;
	@NotNull
	private Integer roleId;

}
