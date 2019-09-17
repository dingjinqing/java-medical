package com.vpu.mp.service.pojo.shop.market.message.content;

import com.vpu.mp.service.foundation.data.JsonResultMessage;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 添加消息推送的添加内容模版
 * @author 卢光耀
 * @date 2019-09-17 10:57
 *
*/
@Data
public class ContentMessageParam {
    @NotNull(message = JsonResultMessage.MSG_PARAM_ERROR)
    private Byte action;
    @NotBlank(message = JsonResultMessage.MSG_PARAM_ERROR)
    @Size(min = 1,max = 50,message = JsonResultMessage.MSG_PARAM_ERROR)
    private String content;
}
