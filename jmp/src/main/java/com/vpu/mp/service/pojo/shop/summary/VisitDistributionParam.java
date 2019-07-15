package com.vpu.mp.service.pojo.shop.summary;

import com.vpu.mp.service.foundation.JsonResultMessage;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 访问分布查询参数
 *
 * @author 郑保乐
 */
@Data
public class VisitDistributionParam {

    @NotBlank(message = JsonResultMessage.MSG_PARAM_ERROR)
    private String startDate;
    @NotBlank(message = JsonResultMessage.MSG_PARAM_ERROR)
    private String endDate;
}
