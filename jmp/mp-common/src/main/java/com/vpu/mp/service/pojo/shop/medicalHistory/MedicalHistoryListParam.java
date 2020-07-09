package com.vpu.mp.service.pojo.shop.medicalHistory;

import com.vpu.mp.common.pojo.shop.base.BasePageParam;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author 赵晓东
 * @description 病历单分页查询入参
 * @create 2020-07-09 09:05
 */
@Data
public class MedicalHistoryListParam extends BasePageParam {

    /**
     * 患者ID
     */
    private int patientId;
}
