package com.vpu.jmd.service.shop.bo.auth;

import lombok.Data;

/**
 * 子账户列表入参
 *
 * @author zhaojianqiang
 * @date 2020年6月18日下午4:38:43
 */
@Data
public class SystemAccountListParam {

	private Integer currentPage;
	private Integer pageRows;
}
