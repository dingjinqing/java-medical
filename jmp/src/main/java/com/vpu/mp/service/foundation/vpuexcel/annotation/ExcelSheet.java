package com.vpu.mp.service.foundation.vpuexcel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 李晓冰
 * @date 2019年07月18日
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ExcelSheet {

    /**
     * sheet下标，0开始
     * @return
     */
    int sheetNum() default 0;

    /**
     * 表头行
     * @return
     */
    int headLineNum() default 0;

    /**
     * 数据开始行
     * @return
     */
    int beginDataNum() default 1;
}
