package com.vpu.mp.service.pojo.shop.summary;

/**
 * 通过 ref_date 字段表示日期的 POJO
 *
 * @param <T> 实际类型的包装类
 * @author 郑保乐
 */
public interface RefDateRecord<T> {

    String getRefDate();

    T getValue();
}
