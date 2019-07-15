package com.vpu.mp.service.pojo.shop.summary;

import lombok.Data;

/**
 * 带日期数据容器
 */
@Data
public class RefDateRecordHolder<T extends Number> implements RefDateRecord<T> {

    private String refDate;
    private T value;
}
