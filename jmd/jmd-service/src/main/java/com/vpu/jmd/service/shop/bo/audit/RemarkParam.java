package com.vpu.jmd.service.shop.bo.audit;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 添加备注
 * @author zhaojianqiang
 * @date 2020年6月4日下午3:56:42
 */
@Data
public class RemarkParam {
	@NotNull
	private Integer auditId;
	@NotNull
	private String remark;
	private Integer accountId;
}
