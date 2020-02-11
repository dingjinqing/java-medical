package com.vpu.mp.service.pojo.shop.overview.commodity;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * @author liufei
 * @date 2/10/2020
 */
@Data
@Builder
public class RankingVo {
    // 图表数据
    // 销售额
    // key：dayData，weekData，monthData，yearData
    Map<String, ChartData> sales;
    // 销售订单
    Map<String, ChartData> salesOrder;

    // 表格数据

}
