package com.vpu.mp.service.pojo.shop.patient;

import lombok.Data;

@Data
public class PatientMoreInfoParam {
    private Integer   id;
    private String    name;
    private Byte      checked=0;
}
