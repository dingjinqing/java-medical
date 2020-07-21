package com.vpu.mp.service.pojo.shop.patient;

import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

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
    private String    patientCode;
    private Byte      isDefault;
    private String    diseaseHistory;
    private String    allergyHistory;
    private String    familyDiseaseHistory;
    private Byte      gestationType;
    private Byte      kidneyFunctionOk;
    private Byte      liverFunctionOk;
    private List<PatientMoreInfoParam> diseaseHistoryList;
    private List<PatientMoreInfoParam> familyDiseaseHistoryList;
}
