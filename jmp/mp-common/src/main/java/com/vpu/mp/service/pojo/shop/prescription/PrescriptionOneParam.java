package com.vpu.mp.service.pojo.shop.prescription;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PrescriptionOneParam {
    private Integer patientId;
    private Integer doctorId;
    private Integer departmentCode;
    private Integer departmentName;
    private String diagnosisName;
    private String diagnoseTime;
    private String doctorAdvice;
    private List<Integer> goodsIdList=new ArrayList<>();
}
