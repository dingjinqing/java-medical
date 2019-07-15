package com.vpu.mp.service.pojo.shop.summary;

import java.util.List;

/**
 * 图表数据
 *
 * @author 郑保乐
 */
public interface ChartData {

    void setKeys(List<String> keys);

    void setValues(List<Integer> values);
}
