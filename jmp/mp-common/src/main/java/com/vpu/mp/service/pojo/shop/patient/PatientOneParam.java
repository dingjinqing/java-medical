package com.vpu.mp.service.pojo.shop.patient;

import lombok.Data;

import java.util.Date;

@Data
public class PatientOneParam {
    private Integer   id;
    private String    name;
    private String    mobile;
    private String    historyInnerNo;
    private String    identityNo;
    private Byte      identityType;
    private String    treatmentNo;
    private Byte      areaType;
    private Byte      sex;
    private String    diseaseHistory;
    private String    allergyHistory;
    private String    memo;
    private Byte      isDelete;
    private Date      birthday;
}
