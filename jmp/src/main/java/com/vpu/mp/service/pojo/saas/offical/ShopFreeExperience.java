package com.vpu.mp.service.pojo.saas.offical;

import javax.validation.constraints.NotBlank;

import com.vpu.mp.service.foundation.JsonResultMessage;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ShopFreeExperience {
	@NotBlank(message = JsonResultMessage.MSG_ACCOUNT_NAME_NOT_NULL)
    private String    contact;
    @NotBlank(message = JsonResultMessage.MSG_ACCOUNT_MODILE_NOT_NULL)
    private String    mobile;
    private String    source;
}
