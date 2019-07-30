package com.vpu.mp.service.foundation.util;

import java.lang.annotation.*;

/**
 * 国际化字段
 *
 * @author 郑保乐
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface I18N {

    /**
     * static.i18n 目录下的 properties 文件名称
     */
    String propertiesFileName();
}
