package com.vpu.jmd.service.shop.bo.audit;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class AuditParam {
	@NotNull
	private String act;
	private Integer auditId;
	private String remark;
	/** 登录用户信息*/
	private Integer accountId;


	/** 是否全选*/
	//private Boolean selectAll=false;
	/** 批量auditId*/
	private List<Integer> batchAuditId;
}
