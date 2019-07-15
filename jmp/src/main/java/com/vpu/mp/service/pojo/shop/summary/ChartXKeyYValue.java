package com.vpu.mp.service.pojo.shop.summary;

import lombok.Data;

import java.util.List;

/**
 * 图表数据出参（x轴为 key，y轴为 value）
 */
@Data
public class ChartXKeyYValue implements ChartData {

    private List<String> xAxis;
    private List<Integer> yAxis;

    @Override
    public void setKeys(List<String> keys) {
        xAxis = keys;
    }

    @Override
    public void setValues(List<Integer> values) {
        yAxis = values;
    }
}
