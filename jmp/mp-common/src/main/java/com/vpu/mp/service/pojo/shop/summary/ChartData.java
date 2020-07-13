package com.vpu.mp.service.pojo.shop.summary;

import java.util.List;

/**
 * 图表数据
 *
 * @author 郑保乐
 */
public interface ChartData {

    /**
     * 填充 keys
     *
     * @param keys keys
     */
    void setKeys(List<String> keys);

    /**
     * 填充 values
     *
     * @param values values
     */
    void setValues(List<Integer> values);
}
