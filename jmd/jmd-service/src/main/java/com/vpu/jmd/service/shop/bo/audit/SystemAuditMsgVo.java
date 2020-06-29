package com.vpu.jmd.service.shop.bo.audit;

import com.vpu.mp.service.foundation.data.JsonResultCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author zhaojianqiang
 * @date 2020年6月3日下午5:01:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemAuditMsgVo {
	protected Integer errcode;
	protected JsonResultCode errmsg;
}
