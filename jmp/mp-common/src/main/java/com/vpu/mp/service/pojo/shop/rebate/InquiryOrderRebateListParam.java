package com.vpu.mp.service.pojo.shop.rebate;

import com.vpu.mp.common.pojo.shop.base.BasePageParam;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author yangpengcheng
 * @date 2020/8/26
 **/
@Data
public class InquiryOrderRebateListParam extends BasePageParam {
    private String doctorName;
    private Byte status;
    private Timestamp startTime;
    private Timestamp endTime;
}
