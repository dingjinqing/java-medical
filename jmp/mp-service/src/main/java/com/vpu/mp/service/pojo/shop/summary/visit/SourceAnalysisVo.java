package com.vpu.mp.service.pojo.shop.summary.visit;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * @author liufei
 * @date 1/17/20
 */
@Data
@Builder
public class SourceAnalysisVo {
    Map<String, Integer> chartsData;
}
