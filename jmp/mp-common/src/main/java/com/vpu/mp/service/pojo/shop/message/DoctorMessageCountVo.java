package com.vpu.mp.service.pojo.shop.message;

import lombok.Data;

/**
 * @author 赵晓东
 * @description
 * @create 2020-07-24 14:35
 **/

@Data
public class DoctorMessageCountVo {

    /**
     * 待问诊数量
     */
    private Integer notImSessionCount;

    /**
     * 已问诊数量
     */
    private Integer alreadyImSessionCount;

    /**
     * 待开方数量
     */
    private Integer notOrderInfoCount;

    /**
     * 已开方数量
     */
    private Integer alreadyOrderInfoCount;

    /**
     * 待续方数量
     */
    private Integer notPrescription;

    /**
     * 已续方数量
     */
    private Integer alreadyPrescription;
}
