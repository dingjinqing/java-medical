package com.vpu.mp.service.pojo.shop.summary;

import com.vpu.mp.service.foundation.JsonResultMessage;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * 访问页面统计入参
 *
 * @author 郑保乐
 */
@Data
public class VisitPageParam {

    @NotBlank(message = JsonResultMessage.MSG_PARAM_ERROR)
    private String startDate;
    @NotBlank(message = JsonResultMessage.MSG_PARAM_ERROR)
    private String endDate;

    /**
     * 按第几个字段排序
     */
    @NotBlank(message = JsonResultMessage.MSG_PARAM_ERROR)
    @Min(1) @Max(8)
    private Integer action;

    /**
     * 排序方式（1: ASC 2: DESC）
     */
    @NotBlank(message = JsonResultMessage.MSG_PARAM_ERROR)
    @Min(1) @Max(2)
    private Integer asc;
}
