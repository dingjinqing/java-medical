package com.vpu.mp.service.pojo.shop.patient;

import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author chenjie
 */
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
    private Integer age;
    private Byte isFetch;
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
    private String diseaseHistoryStr;
    private String familyDiseaseHistoryStr;
    private String diseaseHistoryNameStr;
    private Integer patientId;
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 用户昵称
     */
    private String wxNickName;
    /**
     * 注册时间
     */
    private Timestamp createTime;
    /**
     * 处方数量2
     */
    private Integer countPrescription;
}
