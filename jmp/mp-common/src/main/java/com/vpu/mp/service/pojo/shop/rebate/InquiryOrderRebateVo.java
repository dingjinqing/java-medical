package com.vpu.mp.service.pojo.shop.rebate;

import com.vpu.mp.common.pojo.shop.table.InquiryOrderRebateDo;
import lombok.Data;

/**
 * @author yangpengcheng
 * @date 2020/8/26
 **/
@Data
public class InquiryOrderRebateVo extends InquiryOrderRebateDo {
    private String doctorName;
    private String mobile;
}
