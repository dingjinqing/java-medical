package com.vpu.jmd.service.shop.bo;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 *
 * @author zhaojianqiang
 * @date 2020年6月17日下午4:12:13
 */
@Data
public class SonVersion {
	@NotNull
	private String api;
	private String passwd;
}
