package com.vpu.mp.service.pojo.shop.summary;

import lombok.Data;

import java.util.List;

/**
 * 图表数据出参（x轴为 value，y轴为 key）
 */
@Data
public class ChartXValueYKey implements ChartData {

    private List<Integer> xAxis;
    private List<String> yAxis;

    @Override
    public void setKeys(List<String> keys) {
        yAxis = keys;
    }

    @Override
    public void setValues(List<Integer> values) {
        xAxis = values;
    }
}
