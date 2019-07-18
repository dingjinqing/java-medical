package com.vpu.mp.service.pojo.shop.config.pledge;

import com.vpu.mp.service.foundation.JsonResultMessage;
import com.vpu.mp.service.pojo.shop.config.pledge.group.PledgeStateUpdateGroup;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * Pledge状态修改
 * @author: 卢光耀
 * @date: 2019-07-09 16:45
 *
*/
@Data
@NoArgsConstructor
public class PledgeStateUpdateParam {
    @NotNull(message = JsonResultMessage.MSG_PARAM_ERROR)
    private Integer   id;
    @NotNull(message = JsonResultMessage.MSG_PARAM_ERROR,groups = PledgeStateUpdateGroup.class)
    private Byte      state;
}
