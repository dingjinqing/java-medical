package com.vpu.mp.common.pojo.shop.prescription;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author 孔德成
 * @date 2020/7/6 10:08
 */
@Data


public class PrescriptionListVo {

    /**
     * 处方号
     */
    private String    prescriptionNo;
    /**
     * 注册医院
     */
    private String    registerHospital;
    /**
     * 科室名称
     */
    private String    departmentName;
    /**
     * 医师名称
     */
    private String    doctorName;
    /**
     * 就诊时间
     */
    private Timestamp diagnoseTime;
    /**
     * 药师名称
     */
    private String    pharmacistName;
    /**
     * 疾病名称
     */
    private String    sicknessName;
    /**
     * 处方来源 0系统内部创建 1医院拉取
     */
    private Byte      source;
    /**
     * 处方审核状态 0待审核 1审核通过 2审核未通过
     */
    private Byte      status;
    /**
     * 处方有效期类型 0:未知（默认过期），1:永久有效，2:时间段内有效
     */
    private Byte      expireType;
    /**
     * 开方时间
     */
    private Timestamp prescriptionCreateTime;
}
