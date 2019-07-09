package com.vpu.mp.service.pojo.shop.config.pledge;

import com.vpu.mp.service.foundation.JsonResultMessage;
import com.vpu.mp.service.pojo.shop.config.pledge.group.UpdateGroup;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * @author luguangyao
 */
@Data
@NoArgsConstructor
public class PledgeParam {

    @NotNull(groups = UpdateGroup.class,message = JsonResultMessage.MSG_PARAM_ERROR)
    private Integer   id;
    @NotBlank(message = JsonResultMessage.CONFIG_PLEDGE_NAME_NULL)
    @Size(min = 1,max = 5,message = JsonResultMessage.CONFIG_PLEDGE_NAME_LENGTH)
    private String    pledgeName;
    @NotBlank
    private String    pledgeLogo;
    @NotBlank(message = JsonResultMessage.CONFIG_PLEDGE_CONTENT_NULL)
    @Size(min = 1,max = 300,message = JsonResultMessage.CONFIG_PLEDGE_CONTENT_LENGTH)
    private String    pledgeContent;
}
