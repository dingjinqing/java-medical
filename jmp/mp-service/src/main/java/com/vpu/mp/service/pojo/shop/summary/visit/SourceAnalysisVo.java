package com.vpu.mp.service.pojo.shop.summary.visit;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author liufei
 * @date 1/19/20
 */
@Data
@Builder
public class SourceAnalysisVo {
    private List<LineChartVo> lineChart;
    private String startDate;
    private String endDate;
}
