package com.vpu.mp.service.pojo.shop.medicalhistory;

import com.vpu.mp.common.pojo.shop.base.BasePageParam;
import lombok.Data;

/**
 * @author 赵晓东
 * @description
 * @create 2020-07-09 18:50
 */
@Data
public class MedicalHistoryPageInfoParam extends BasePageParam {

    /**
     * 患者ID
     */
    private int patientId;

}
