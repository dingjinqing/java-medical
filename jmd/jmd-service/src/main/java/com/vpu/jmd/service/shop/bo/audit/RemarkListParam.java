package com.vpu.jmd.service.shop.bo.audit;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RemarkListParam {
	@NotNull
	private Integer auditId;
	private Integer currentPage;
	private Integer pageRows;

}
