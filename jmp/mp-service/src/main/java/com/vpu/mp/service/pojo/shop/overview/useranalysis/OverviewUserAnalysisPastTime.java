package com.vpu.mp.service.pojo.shop.overview.useranalysis;

import lombok.Data;

import java.util.Date;

/**
 * 与最近N天相比的上一个N天
 * @author liangchen
 * @date 2019.11.06
 */
@Data
public class OverviewUserAnalysisPastTime {
    /** 开始时间 */
    private Date startTime;
    /** 结束时间 */
    private Date endTime;
}
