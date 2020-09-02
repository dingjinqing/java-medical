package com.vpu.mp.service.pojo.shop.patient;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author yangpengcheng
 */
@Data
public class UserPatientOneParam extends UserPatientParam{
    @NotNull
    private String name;
    @NotNull
    private String mobile;
    @NotNull
    private String identityCode;
    /**
     * 增量查询时间
     */
    private Long startTime;
    /**
     * 短信验证码
     */
    @NotNull
    private String mobileCheckCode;
}
