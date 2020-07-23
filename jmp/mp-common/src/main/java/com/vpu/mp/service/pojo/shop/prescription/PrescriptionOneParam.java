package com.vpu.mp.service.pojo.shop.prescription;

import lombok.Data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yangpengcheng
 */
@Data
public class PrescriptionOneParam {
    private Integer patientId;
    private Integer doctorId;
    private Integer departmentCode;
    private Integer departmentName;
    private String diagnosisName;
    private Timestamp diagnoseTime;
    private String doctorAdvice;
    private List<Integer> goodsIdList=new ArrayList<>();
}
