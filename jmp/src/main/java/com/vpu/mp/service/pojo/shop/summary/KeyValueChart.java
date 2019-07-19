package com.vpu.mp.service.pojo.shop.summary;

import com.vpu.mp.controller.I18N;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * 图表数据出参（x轴为 key，y轴为 value）
 *
 * @author 郑保乐
 */
@Getter
@Setter
@NoArgsConstructor
public class KeyValueChart extends AbstractChart {

    @I18N(propertiesFileName = "source")
    private List<String> xAxis;
    private List<Integer> yAxis;

    @Override
    public void setKeys(List<String> keys) {
        xAxis = keys;
    }

    @Override
    public void setValues(List<Integer> values) {
        super.setValues(values);
        yAxis = values;
    }
}
