package com.vpu.jmd.service.shop.bo.audit;

import lombok.Data;

/**
 *
 * @author zhaojianqiang
 * @date 2020年6月10日下午4:55:04
 */
@Data
public class MpAuditSomeVo {
	private Byte auditStatus;
	private String  wxAuditFailReason;
}
