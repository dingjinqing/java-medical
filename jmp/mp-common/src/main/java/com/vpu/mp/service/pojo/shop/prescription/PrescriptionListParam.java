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
     * 患者名称
     */
    private String patientName;
    /**
     * 患者id
     */
    private Integer patientId;
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
    /**
     * 开方开始时间
     */
    private Timestamp createStartTime;
    /**
     * 开方结束时间
     */
    private Timestamp createEndTime;
    /**
     * 审核类型 处方审核类型 ''药品审核类型, 0不审核,1审核,2开方,3根据处方下单
     */
    private Byte auditType;
    /**
     * 结算标志：0：未结算，1：已结算
     */
    private Byte isValid;
    /**
     * 是否有效  0无效 1有效，默认1
     */
    private Byte isUsed;

}
