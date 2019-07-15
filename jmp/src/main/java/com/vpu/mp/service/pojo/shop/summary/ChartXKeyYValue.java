package com.vpu.mp.service.pojo.shop.summary;

import lombok.Data;

import java.util.List;

/**
 * 图表数据出参（x轴为 key，y轴为 value）
 */
@Data
public class ChartXKeyYValue {

    private List<String> xAxis;
    private List<Number> yAxis;
}
