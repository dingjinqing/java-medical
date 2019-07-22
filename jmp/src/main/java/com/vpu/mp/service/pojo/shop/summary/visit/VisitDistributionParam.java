package com.vpu.mp.service.pojo.shop.summary.visit;

import lombok.Data;

import javax.validation.constraints.NotBlank;

import com.vpu.mp.service.foundation.data.JsonResultMessage;

import java.util.List;

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

    /**
     * 忽略哪些访问来源
     */
    private List<Integer> cancelBtn;
}
