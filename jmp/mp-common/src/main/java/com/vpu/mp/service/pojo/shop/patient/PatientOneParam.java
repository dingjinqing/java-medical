package com.vpu.mp.service.pojo.shop.patient;

import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;

@Data
public class  PatientOneParam {
    private Integer   id;
    private String    name;
    private String    mobile;
    private String    identityCode;
    private Byte      identityType;
    private String    treatmentCode;
    private String    insuranceCardCode;
    private Byte      sex;
    private Date birthday;
    private String    remarks;
    private Byte      isDefault;
}
