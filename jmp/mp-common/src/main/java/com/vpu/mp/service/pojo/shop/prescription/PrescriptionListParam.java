package com.vpu.mp.service.pojo.shop.prescription;

import com.vpu.mp.common.pojo.shop.base.BasePageParam;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author 孔德成
 * @date 2020/7/6 9:36
 */
@Data
public class PrescriptionListParam extends BasePageParam {

    /**
     * 患者id
     */
    private String patientName;
    /**
     * 电话
     */
    private String patientMobile;
    /**
     * 处方号
     */
    private String prescriptionCode;
    /**
     * 科室名称
     */
    private String departmentName;
    /**
     * 医师名称
     */
    private String doctorName;
    /**
     * 医师id
     */
    private String doctorCode;
    /**
     * 诊断名称
     */
    private String    diagnosisName;
    /**
     * 就诊时间-开始
     */
    private Timestamp diagnoseStartTime;
    /**
     * 就诊时间-结束
     */
    private Timestamp diagnoseEndTime;

}
