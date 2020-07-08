package com.vpu.mp.common.pojo.shop.medicalHistory;

import com.vpu.mp.common.pojo.shop.base.BasePageParam;
import com.vpu.mp.common.pojo.shop.table.MedicalHistoryDo;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author 赵晓东
 * @description 病历单入参
 * @create 2020-07-07 10:34
 */
@Data
public class MedicalHistoryParam extends BasePageParam {

    /**
     * 病历单Id
     */
    @NotNull
    private Integer id;
}
