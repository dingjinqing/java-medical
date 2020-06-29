package com.vpu.jmd.service.shop.bo.audit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 成功失败数量
 * @author zhaojianqiang
 * @date 2020年6月4日下午8:53:31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchAuditVo {
	private Integer success;
	private Integer total;
	private Integer fail;

}
