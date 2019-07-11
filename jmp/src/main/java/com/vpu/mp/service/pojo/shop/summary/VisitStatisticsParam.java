package com.vpu.mp.service.pojo.shop.summary;

import com.vpu.mp.service.foundation.JsonResultMessage;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class VisitStatisticsParam {

    public static final int SESSION_COUNT = 1;
    public static final int PV = 2;
    public static final int UV = 3;
    public static final int UV_NEW = 4;
    public static final int STAY_TIME_UV = 5;
    public static final int STAY_TIME_SESSION = 6;
    public static final int VISIT_DEPTH = 7;

    @NotBlank(message = JsonResultMessage.MSG_PARAM_ERROR)
    private String startDate;
    @NotBlank(message = JsonResultMessage.MSG_PARAM_ERROR)
    private String endDate;
    private Integer action;
    private Integer grading;
}
