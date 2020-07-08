package com.vpu.mp.common.pojo.shop.prescription;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * 处方全字段
 * @author 孔德成
 * @date 2020/7/2 18:09
 */
@Data
public class PrescriptionSimpleVo  {

    /**
     * 处方号
     */
    private String    prescriptionNo;
    /**
     * 医师
     */
    private String    doctorName;
    /**
     * 开方日期
     */
    private Timestamp prescriptionCreateTime;
    /**
     * 科室名称
     */
    private String    departmentName;
    /**
     * 疾病名称
     */
    private String    diagnosisName;

    private List<PrescriptionItemInfoVo> itemList;

}
