package com.vpu.mp.common.pojo.shop.prescription;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author 孔德成
 * @date 2020/7/7 18:27
 */
@Data
public class PrescriptionInfoVo {

    /**
     * 处方号
     */
    private String    prescriptionNo;
    /**
     * 患者就诊号
     */
    private String    patientTreatmentNo;
    /**
     * 患者名称
     */
    private String    patientName;
    /**
     * 患者年龄
     */
    private Integer   patientAge;
    /**
     * 性别
     */
    private Byte      patientSex;
    /**
     * 科室名称
     */
    private String    departmentName;
    /**
     * 诊断名称
     */
    private String    diagnosisDetail;
    /**
     * 医师名称
     */
    private String    doctorName;
    /**
     * 开方时间
     */
    private Timestamp prescriptionCreateTime;

    /**
     * 处方明细
     */
    private List<PrescriptionItemInfoVo> itemList;



}
