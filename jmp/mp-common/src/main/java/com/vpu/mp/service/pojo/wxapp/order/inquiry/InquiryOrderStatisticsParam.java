package com.vpu.mp.service.pojo.wxapp.order.inquiry;

import com.vpu.mp.common.pojo.shop.base.BasePageParam;
import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * @author yangpengcheng
 * @date 2020/8/3
 **/
@Data
public class InquiryOrderStatisticsParam extends BasePageParam {
    private String doctorName;
    private Timestamp startTime;
    private Timestamp endTime;
}
