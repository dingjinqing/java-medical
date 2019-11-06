package com.vpu.mp.service.pojo.wxapp.activity.info;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 处理器数据基类
 * @author 李晓冰
 * @date 2019年10月29日
 */
@Data
public class ProcessorDataInfo {
    protected Integer dataId;
    protected Byte dataType;
    /**活动对应的价格*/
    @JsonIgnore
    protected BigDecimal dataPrice;
}
