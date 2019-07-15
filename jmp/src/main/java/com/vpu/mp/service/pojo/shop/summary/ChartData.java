package com.vpu.mp.service.pojo.shop.summary;

import java.util.List;

/**
 * 图标数据
 */
public interface ChartData {

    void setKeys(List<String> keys);

    void setValues(List<Integer> values);
}
