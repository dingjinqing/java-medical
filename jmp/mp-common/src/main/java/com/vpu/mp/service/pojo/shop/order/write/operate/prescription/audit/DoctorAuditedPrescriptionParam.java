package com.vpu.mp.service.pojo.shop.order.write.operate.prescription.audit;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 医生审核过的处方
 * @author 孔德成
 * @date 2020/8/5 16:27
 */
@Data
public class DoctorAuditedPrescriptionParam {

    @NotNull
    private Byte type;

    private String doctorCode;
}
