package com.vpu.jmd.service.shop.bo.audit;

import lombok.Data;

import java.sql.Timestamp;

/**
 *
 * @author zhaojianqiang
 * @date 2020年6月8日下午2:20:43
 */
@Data
public class AuditNoteVo {
	private Integer id;
	private Integer mpAuditId;
	private Byte operateStatus;
	private String remark;
	private Timestamp createTime;
	private Integer accountId;
	private String accountName;
	private Timestamp maCreateTime;
}
