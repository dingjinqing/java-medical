package com.vpu.mp.service.pojo.shop.summary;

import lombok.Data;

import java.util.List;

/**
 * 图表数据出参（x轴为 value，y轴为 key）
 */
@Data
public class ChartXValueYKey {

    private List<Number> xAxis;
    private List<String> yAxis;
}
