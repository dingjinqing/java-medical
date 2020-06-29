package com.vpu.jmd.service.shop.bo.audit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 详情
 *
 * @author zhaojianqiang
 * @date 2020年6月3日上午10:46:24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SystemMpAuditDetailVo {
	private SystemAuditDetailVo auditInfo;
	private List<AuditNoteVo> auditRecord;
	private List<AuditNoteVo> hisAuditRecord;
}
