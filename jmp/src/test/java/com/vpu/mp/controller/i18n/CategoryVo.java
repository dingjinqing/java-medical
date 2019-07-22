package com.vpu.mp.controller.i18n;

import com.vpu.mp.controller.I18N;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author 郑保乐
 */
@Data
@AllArgsConstructor
public class CategoryVo {

    @I18N(propertiesFileName = "category")
    private String name;
}
