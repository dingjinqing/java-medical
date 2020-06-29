package com.vpu.jmd.service.shop.bo.child;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 店铺列表查询售后人列表入参
 * @author zhaojianqiang
 * @date 2020年6月2日上午10:46:19
 */
@Data
public class SystemChildShowParam {
	@NotNull
	private String act;
	@NotNull
	private Integer shopId;
	@NotNull
	private Integer currentPage;
	@NotNull
	private Integer pageRows;
}
