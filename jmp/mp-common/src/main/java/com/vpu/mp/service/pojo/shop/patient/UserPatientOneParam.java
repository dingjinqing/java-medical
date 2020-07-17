package com.vpu.mp.service.pojo.shop.patient;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserPatientOneParam extends UserPatientParam{
    @NotNull
    private String name;
    @NotNull
    private String mobile;
    private String identityCode;
}
