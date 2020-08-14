package com.vpu.mp.service.pojo.saas.order;

import com.vpu.mp.common.pojo.shop.base.BasePageParam;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author yangpengcheng
 * @date 2020/8/14
 **/
@Data
public class MainInquiryOrderStatisticsParam extends BasePageParam {
    private Timestamp startTime;
    private Timestamp endTime;
}
