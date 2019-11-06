package com.vpu.mp.service.pojo.wxapp.activity.info;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 李晓冰
 * @date 2019年11月04日
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GoodsLabelProcessorDataInfo extends ProcessorDataInfo {
    private String name;
    private Short listPattern;
}
