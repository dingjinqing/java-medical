package com.vpu.mp.common.pojo.shop.prescription;

import com.vpu.mp.common.pojo.shop.base.BasePageParam;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author 孔德成
 * @date 2020/7/6 9:36
 */
@Data
public class PrescriptionListParam  extends BasePageParam {

    /**
     * 患者id
     */
    @NotNull
    private Integer patientId;

}
