package com.vpu.jmd.service.shop.bo.audit;

import lombok.Data;

import java.sql.Timestamp;

/**
 *
 * @author zhaojianqiang
 * @date 2020年6月4日下午3:53:34
 */
@Data
public class RemarkListVo {
	private Integer id;
	private Integer mpAuditId;
	private Byte operateStatus;
	private String remark;
	private Timestamp createTime;
	private Integer accountId;

}
