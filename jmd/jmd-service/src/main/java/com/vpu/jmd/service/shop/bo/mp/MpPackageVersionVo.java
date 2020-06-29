package com.vpu.jmd.service.shop.bo.mp;

import com.vpu.mp.service.foundation.data.JsonResultMessage;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 *
 * @author zhaojianqiang
 *
 * @date  2019年8月8日 下午2:49:17
 */
@Data
public class MpPackageVersionVo {
	@NotNull(message = JsonResultMessage.WX_MA_TEMPLATE_ID_NOT_NULL)
    private Integer templateId;
	@NotNull(message = JsonResultMessage.WX_MA_PACKAGE_VERSION_NOT_NULL)
    private Byte packageVersion;
}
