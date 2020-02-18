package com.vpu.mp.service.pojo.shop.member.card;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 获得生成/导入记录
 * @author zhaojianqiang
 * @time   下午3:22:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchGroupVo {
	private Integer batchId;
	private Integer successNum;
	private Integer failNum;
	private String batchName;
}
