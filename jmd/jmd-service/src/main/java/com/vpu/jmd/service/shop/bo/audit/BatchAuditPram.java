package com.vpu.jmd.service.shop.bo.audit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 批量审核小程序
 *
 * @author zhaojianqiang
 * @date 2020年6月4日下午8:44:55
 */
@Data
@NotNull
@AllArgsConstructor
@ToString
public class BatchAuditPram {
	private List<SystemAuditListVo> dataList;
	private AuditParam auditParam;
    private Integer taskJobId;
}
