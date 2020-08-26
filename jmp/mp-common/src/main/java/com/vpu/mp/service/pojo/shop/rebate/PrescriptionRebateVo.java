package com.vpu.mp.service.pojo.shop.rebate;

import com.vpu.mp.common.pojo.shop.table.PrescriptionRebateDo;
import lombok.Data;

/**
 * @author yangpengcheng
 * @date 2020/8/26
 **/
@Data
public class PrescriptionRebateVo extends PrescriptionRebateDo {
    private String doctorName;
    private String mobile;
}
