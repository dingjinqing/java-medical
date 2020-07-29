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
    private String departmentCode;
    private String departmentName;
    private String diagnosisName;
    private Timestamp diagnoseTime;
    private String doctorAdvice;
    private Byte isUsed;
    private List<PrescriptionDrugVo> goodsList=new ArrayList<>();
}
