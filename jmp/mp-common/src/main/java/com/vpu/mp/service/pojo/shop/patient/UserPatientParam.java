package com.vpu.mp.service.pojo.shop.patient;

import lombok.Data;

@Data
public class UserPatientParam {
    private Integer   userId;
    private Integer   patientId;

    @Override
    public String toString() {
        return "UserPatientParam{" +
            "userId=" + userId +
            ", patientId=" + patientId +
            '}';
    }
}
